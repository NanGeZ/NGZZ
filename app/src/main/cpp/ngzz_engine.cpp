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

enum {
    FLAG_SIGNATURE = 1 << 0,
    FLAG_ANTI_DEBUG = 1 << 1,
    FLAG_PACKAGE = 1 << 2,
    FLAG_PROXY = 1 << 3,
    FLAG_ROOT = 1 << 4,
    FLAG_NETWORK = 1 << 5,
    FLAG_EXPIRY = 1 << 6
};

static void fatal_exit() {
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

static bool detect_signature() {
    return file_exists("/proc/self/maps") && tracer_pid();
}

static bool detect_anti_debug() {
    const char* markers[] = {
        "/data/local/tmp/frida-server",
        "/system/lib/libfrida-gadget.so",
        "/system/lib64/libfrida-gadget.so",
        "/system/lib/libxposed.so",
        "/system/framework/lspd.jar",
        "/system/lib/libgg.so",
        "/proc/self/fd/0"
    };
    for (auto m : markers) {
        if (file_exists(m)) return true;
    }
    DIR* d = opendir("/proc/self/fd");
    if (d) {
        struct dirent* e;
        while ((e = readdir(d))) {
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

static bool detect_package() {
    char buf[PROP_VALUE_MAX] = {0};
    __system_property_get("ro.build.version.release", buf);
    return buf[0] == 0;
}

static bool detect_proxy() {
    char host[PROP_VALUE_MAX] = {0};
    char port[PROP_VALUE_MAX] = {0};
    __system_property_get("http.proxyHost", host);
    __system_property_get("http.proxyPort", port);
    if (host[0] != 0) return true;
    return file_exists("/sys/class/net/tun0") || file_exists("/sys/class/net/ppp0");
}

static bool detect_root() {
    const char* su[] = {
        "/system/bin/su", "/system/xbin/su", "/sbin/su",
        "/data/local/bin/su", "/data/local/xbin/su", "/sbin/.magisk"
    };
    for (auto p : su) {
        if (file_exists(p)) return true;
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

static bool detect_expiry() {
    time_t now = time(nullptr);
    struct tm cutoff = {0};
    cutoff.tm_year = 2026 - 1900;
    cutoff.tm_mon = 11;
    cutoff.tm_mday = 31;
    return now > mktime(&cutoff);
}

typedef bool (*Detector)();

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
    for (auto& t : table) {
        if ((flags & t.flag) && t.fn()) return true;
    }
    return false;
}

extern "C" JNIEXPORT jint JNICALL
Java_com_ngzz_protect_NativePack_nativeDetect(JNIEnv* env, jobject thiz, jint flags) {
    (void)env; (void)thiz;
    return run_checks(static_cast<uint32_t>(flags)) ? 1 : 0;
}

extern "C" JNIEXPORT void JNICALL
Java_com_ngzz_protect_NativePack_nativeGuard(JNIEnv* env, jobject thiz, jint flags) {
    (void)env; (void)thiz;
    std::thread([flags]() {
        for (int i = 0; i < 60; ++i) {
            if (run_checks(static_cast<uint32_t>(flags))) {
                fatal_exit();
            }
            std::this_thread::sleep_for(std::chrono::seconds(2));
        }
    }).detach();
}
