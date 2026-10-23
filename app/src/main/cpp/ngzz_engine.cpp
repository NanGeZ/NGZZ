#include <jni.h>
#include <cstdio>
#include <cstring>
#include <cstdlib>
#include <ctime>
#include <chrono>
#include <string>
#include <vector>
#include <thread>
#include <atomic>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include <sys/ptrace.h>
#include <sys/stat.h>
#include <sys/system_properties.h>
#include <dlfcn.h>

#include "ngzz_obfuscate.h"
#include "ngzz_anti_hook.h"

enum {
    FLAG_SIGNATURE = 1 << 0,
    FLAG_ANTI_DEBUG = 1 << 1,
    FLAG_PACKAGE = 1 << 2,
    FLAG_PROXY = 1 << 3,
    FLAG_ROOT = 1 << 4,
    FLAG_NETWORK = 1 << 5,
    FLAG_EXPIRY = 1 << 6
};

static std::atomic<bool> g_guard_running{false};
static std::atomic<int> g_violation_count{0};
static std::atomic<int64_t> g_expiry_epoch{0};

static uint32_t rd32le(const uint8_t* p) {
    return (uint32_t)p[0] | ((uint32_t)p[1] << 8) | ((uint32_t)p[2] << 16) | ((uint32_t)p[3] << 24);
}
static uint16_t rd16le(const uint8_t* p) {
    return (uint16_t)(p[0] | (p[1] << 8));
}

static void load_config_from_apk(const std::string& apk_path) {
    FILE* f = fopen(apk_path.c_str(), "rb");
    if (!f) return;
    fseek(f, 0, SEEK_END);
    long fsize = ftell(f);
    if (fsize < 22) { fclose(f); return; }

    const long tail = fsize < 66000 ? fsize : 66000;
    std::vector<uint8_t> buf(static_cast<size_t>(tail));
    fseek(f, fsize - tail, SEEK_SET);
    if (fread(buf.data(), 1, buf.size(), f) != buf.size()) { fclose(f); return; }
    fclose(f);

    size_t eocd = 0;
    bool found = false;
    for (size_t i = buf.size() - 22; ; --i) {
        if (rd32le(&buf[i]) == 0x06054b50) { eocd = i; found = true; break; }
        if (i == 0) break;
    }
    if (!found) return;

    uint32_t cd_count = rd16le(&buf[eocd + 10]) | ((uint32_t)rd16le(&buf[eocd + 12]) << 16);
    uint32_t cd_off = rd32le(&buf[eocd + 16);

    long base = fsize - tail;
    size_t pos = (cd_off >= (uint32_t)base) ? (cd_off - base) : 0;
    const char* target = "assets/ngzz/config.bin";
    const size_t target_len = 22;

    for (uint32_t n = 0; n < cd_count && pos + 46 <= buf.size(); ++n) {
        if (rd32le(&buf[pos]) != 0x02014b50) break;
        uint16_t method = rd16le(&buf[pos + 10]);
        uint32_t csize = rd32le(&buf[pos + 20]);
        uint16_t name_len = rd16le(&buf[pos + 28]);
        uint16_t extra_len = rd16le(&buf[pos + 30]);
        uint16_t cmt_len = rd16le(&buf[pos + 32]);
        uint32_t lho = rd32le(&buf[pos + 42]);
        if (pos + 46 + name_len > buf.size()) break;
        if (name_len == target_len && memcmp(&buf[pos + 46], target, target_len) == 0) {
            if (method != 0 || csize < 16) return;
            if (lho < (uint32_t)base) return;
            size_t lp = lho - base;
            if (lp + 30 > buf.size()) return;
            if (rd32le(&buf[lp]) != 0x04034b50) return;
            uint16_t ln = rd16le(&buf[lp + 26]);
            uint16_t le = rd16le(&buf[lp + 28]);
            size_t data = lp + 30 + ln + le;
            if (data + 16 > buf.size()) return;
            if (memcmp(&buf[data], "NZCF", 4) == 0) {
                int64_t exp = 0;
                for (int b = 7; b >= 0; --b) {
                    exp = (exp << 8) | buf[data + 8 + b];
                }
                if (exp > 0) g_expiry_epoch.store(exp, std::memory_order_relaxed);
            }
            return;
        }
        pos += 46 + name_len + extra_len + cmt_len;
    }
}

__attribute__((noinline))
static void fatal_exit() {
    // Overwrite stack before exit to prevent dump
    volatile char buf[256];
    memset(const_cast<char*>(buf), 0, sizeof(buf));
    _exit(0);
}

static bool file_exists(const char* p) {
    return access(p, F_OK) == 0;
}

static bool tracer_pid() {
    FILE* f = fopen("/proc/self/status", "r");
    if (!f) return false;
    char line[256];
    bool traced = false;
    while (fgets(line, sizeof(line), f)) {
        if (strncmp(line, "TracerPid", 9) == 0) {
            int v = atoi(line + 10);
            traced = v != 0;
            break;
        }
    }
    fclose(f);
    return traced;
}

__attribute__((noinline))
static bool detect_signature() {
    // Obfuscated path checks
    if (!file_exists("/proc/self/maps")) return true;

    // Check for signature mismatch via cert validation
    // This is a placeholder - real impl would validate APK signature
    return tracer_pid();
}

__attribute__((noinline))
static bool detect_anti_debug() {
    // Use obfuscated strings
    const char* markers[] = {
        NGZZ_OBF("/data/local/tmp/frida-server"),
        NGZZ_OBF("/system/lib/libfrida-gadget.so"),
        NGZZ_OBF("/system/lib64/libfrida-gadget.so"),
        NGZZ_OBF("/system/lib/libxposed.so"),
        NGZZ_OBF("/system/framework/lspd.jar"),
        NGZZ_OBF("/system/lib/libgg.so")
    };

    for (size_t i = 0; i < sizeof(markers) / sizeof(markers[0]); ++i) {
        if (file_exists(markers[i])) return true;
    }

    // Enhanced: check for hook frameworks
    if (ngzz::check_all_hooks()) return true;

    // Check /proc/self/fd for suspicious file descriptors
    DIR* d = opendir("/proc/self/fd");
    if (d) {
        struct dirent* e;
        while ((e = readdir(d))) {
            if (e->d_name[0] == '.') continue;
            char path[256];
            snprintf(path, sizeof(path), "/proc/self/fd/%s", e->d_name);
            char link[512];
            ssize_t n = readlink(path, link, sizeof(link) - 1);
            if (n > 0) {
                link[n] = 0;
                if (strstr(link, "frida") || strstr(link, "xposed") || strstr(link, "gg")) {
                    closedir(d);
                    return true;
                }
            }
        }
        closedir(d);
    }

    return tracer_pid();
}

__attribute__((noinline))
static bool detect_package() {
    char buf[PROP_VALUE_MAX] = {0};
    __system_property_get("ro.build.version.release", buf);
    return buf[0] == 0;
}

__attribute__((noinline))
static bool detect_proxy() {
    char host[PROP_VALUE_MAX] = {0};
    char port[PROP_VALUE_MAX] = {0};
    __system_property_get(NGZZ_OBF("http.proxyHost"), host);
    __system_property_get(NGZZ_OBF("http.proxyPort"), port);
    if (host[0] != 0) return true;

    // Check for VPN/Proxy interfaces
    return file_exists("/sys/class/net/tun0") || file_exists("/sys/class/net/ppp0");
}

__attribute__((noinline))
static bool detect_root() {
    const char* su[] = {
        NGZZ_OBF("/system/bin/su"),
        NGZZ_OBF("/system/xbin/su"),
        NGZZ_OBF("/sbin/su"),
        NGZZ_OBF("/data/local/bin/su"),
        NGZZ_OBF("/data/local/xbin/su"),
        NGZZ_OBF("/sbin/.magisk")
    };

    for (size_t i = 0; i < sizeof(su) / sizeof(su[0]); ++i) {
        if (file_exists(su[i])) return true;
    }

    FILE* f = fopen("/proc/self/mounts", "r");
    if (f) {
        char line[512];
        while (fgets(line, sizeof(line), f)) {
            if (strstr(line, "/system") && strstr(line, "rw,")) {
                fclose(f);
                return true;
            }
        }
        fclose(f);
    }
    return false;
}

__attribute__((noinline))
static bool detect_network() {
    DIR* d = opendir("/sys/class/net");
    if (!d) return true;
    struct dirent* e;
    bool up = false;
    while ((e = readdir(d))) {
        if (e->d_name[0] == '.') continue;
        if (strcmp(e->d_name, "lo") == 0) continue;
        char path[256];
        snprintf(path, sizeof(path), "/sys/class/net/%s/operstate", e->d_name);
        FILE* f = fopen(path, "r");
        if (f) {
            char state[16] = {0};
            fgets(state, sizeof(state), f);
            fclose(f);
            if (strncmp(state, "up", 2) == 0) up = true;
        }
    }
    closedir(d);
    return !up;
}

__attribute__((noinline))
static bool detect_expiry() {
    time_t now = time(nullptr);
    int64_t custom = g_expiry_epoch.load(std::memory_order_relaxed);
    if (custom > 0) {
        return now > static_cast<time_t>(custom);
    }
    struct tm cutoff = {0};
    cutoff.tm_year = 2026 - 1900;
    cutoff.tm_mon = 11;
    cutoff.tm_mday = 31;
    return now > mktime(&cutoff);
}

typedef bool (*Detector)();

// Opaque predicate helper - always returns true but compiler can't prove it
__attribute__((noinline))
static bool opaque_true(int x) {
    return (x * x + x) % 2 == 0;
}

static bool run_checks(uint32_t flags) {
    struct { uint32_t flag; Detector fn; } table[] = {
        {FLAG_SIGNATURE, detect_signature},
        {FLAG_ANTI_DEBUG, detect_anti_debug},
        {FLAG_PACKAGE, detect_package},
        {FLAG_PROXY, detect_proxy},
        {FLAG_ROOT, detect_root},
        {FLAG_NETWORK, detect_network},
        {FLAG_EXPIRY, detect_expiry}
    };

    volatile int obf_val = 42;
    if (opaque_true(obf_val)) {
        for (size_t i = 0; i < sizeof(table) / sizeof(table[0]); ++i) {
            if ((flags & table[i].flag)) {
                // Anti-hook check before each detector
                if (ngzz::detect_timing_anomaly()) return true;
                if (table[i].fn()) return true;
            }
        }
    }
    return false;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_ngzz_protect_NativePack_nativeDetect(JNIEnv* env, jobject thiz, jint flags) {
    (void)env; (void)thiz;
    return run_checks(static_cast<uint32_t>(flags)) ? 1 : 0;
}

extern "C" JNIEXPORT void JNICALL
Java_com_ngzz_protect_NativePack_nativeLoadConfig(JNIEnv* env, jobject thiz, jstring apkPath) {
    (void)thiz;
    if (apkPath == nullptr) return;
    const char* c = env->GetStringUTFChars(apkPath, nullptr);
    if (!c) return;
    std::string p(c);
    env->ReleaseStringUTFChars(apkPath, c);
    load_config_from_apk(p);
}

extern "C" JNIEXPORT void JNICALL
Java_com_ngzz_protect_NativePack_nativeGuard(JNIEnv* env, jobject thiz, jint flags) {
    (void)env; (void)thiz;

    if (g_guard_running.exchange(true)) return;

    std::thread([flags]() {
        uint32_t f = static_cast<uint32_t>(flags);

        // Startup check
        if (run_checks(f)) {
            fatal_exit();
        }

        // Continuous monitoring with varying intervals to avoid pattern detection
        int iteration = 0;
        while (iteration < 300) {
            int sleep_ms = 1500 + (iteration % 7) * 500;
            std::this_thread::sleep_for(std::chrono::milliseconds(sleep_ms));

            // Multiple check strategies
            bool violation = false;

            // 1. Standard detector check
            if (run_checks(f)) violation = true;

            // 2. Anti-hook check
            if (ngzz::check_all_hooks()) violation = true;

            // 3. Timing anomaly check
            if (ngzz::detect_timing_anomaly()) violation = true;

            if (violation) {
                g_violation_count++;
                // Exit on first detection (or after 3 consecutive)
                if (g_violation_count >= 1) {
                    fatal_exit();
                }
            } else {
                g_violation_count = 0;
            }

            iteration++;
        }
        g_guard_running = false;
    }).detach();
}

// Enhanced detection entry point
extern "C" JNIEXPORT jint JNICALL
Java_com_ngzz_protect_NativePack_nativeDetectEx(JNIEnv* env, jobject thiz, jint flags) {
    (void)env; (void)thiz;

    uint32_t f = static_cast<uint32_t>(flags);
    int result = 0;

    // Check all detectors
    if (run_checks(f)) result |= 0x01;

    // Check anti-hook
    if (ngzz::check_all_hooks()) result |= 0x02;

    // Check timing
    if (ngzz::detect_timing_anomaly()) result |= 0x04;

    // Check specific hook libraries
    if (ngzz::detect_frida_hooks()) result |= 0x08;

    return result;
}
