#include <jni.h>
#include <string.h>
#include <stdlib.h>
#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include <sys/socket.h>
#include <sys/stat.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <pthread.h>
#include <time.h>
#include <sys/system_properties.h>
#include <android/log.h>

#define LOG_TAG "NantianmenGuard"
#define LOGW(...) __android_log_print(ANDROID_LOG_WARN, LOG_TAG, __VA_ARGS__)

static JavaVM *g_vm = NULL;
static jclass g_cls = NULL;
static jmethodID g_onViolation = NULL;
static int g_features = 0;
static volatile int g_running = 0;
static char g_pkg[256] = {0};

static void report(JNIEnv *env, const char *type, const char *detail) {
    if (g_cls && g_onViolation) {
        jstring jt = (*env)->NewStringUTF(env, type);
        jstring jd = (*env)->NewStringUTF(env, detail);
        (*env)->CallStaticVoidMethod(env, g_cls, g_onViolation, jt, jd);
        (*env)->DeleteLocalRef(env, jt);
        (*env)->DeleteLocalRef(env, jd);
    }
    LOGW("violation: %s %s", type, detail);
}

static int file_exists(const char *path) {
    struct stat st;
    return stat(path, &st) == 0;
}

static int contains_in_file(const char *path, const char *needle) {
    FILE *f = fopen(path, "r");
    if (!f) return 0;
    char buf[512];
    int found = 0;
    while (fgets(buf, sizeof(buf), f)) {
        if (strstr(buf, needle)) { found = 1; break; }
    }
    fclose(f);
    return found;
}

static int dir_contains(const char *dir, const char *needle) {
    DIR *d = opendir(dir);
    if (!d) return 0;
    struct dirent *e;
    int found = 0;
    while ((e = readdir(d)) != NULL) {
        if (strstr(e->d_name, needle)) { found = 1; break; }
    }
    closedir(d);
    return found;
}

static int check_root(JNIEnv *env) {
    static const char *su_paths[] = {
        "/system/bin/su", "/system/xbin/su", "/sbin/su", "/su/bin/su",
        "/system/app/Superuser.apk", "/system/xbin/daemonsu",
        "/data/local/su", NULL
    };
    for (int i = 0; su_paths[i]; i++) {
        if (file_exists(su_paths[i])) {
            report(env, "root", su_paths[i]);
            return 1;
        }
    }
    if (contains_in_file("/system/build.tags", "test-keys")) {
        report(env, "root", "build.tags=test-keys");
        return 1;
    }
    if (dir_contains("/data", "magisk") || dir_contains("/sbin", "magisk")) {
        report(env, "root", "magisk detected");
        return 1;
    }
    return 0;
}

static int check_debug(JNIEnv *env) {
    FILE *f = fopen("/proc/self/status", "r");
    if (f) {
        char buf[256];
        while (fgets(buf, sizeof(buf), f)) {
            if (strncmp(buf, "TracerPid:", 10) == 0) {
                int pid = atoi(buf + 10);
                if (pid > 0) {
                    report(env, "anti_debug", "TracerPid attached");
                    fclose(f);
                    return 1;
                }
            }
        }
        fclose(f);
    }

    static const char *frida[] = {
        "/data/local/tmp/frida-server", "/data/local/tmp/re.frida.server",
        "/data/local/tmp/fs", NULL
    };
    for (int i = 0; frida[i]; i++) {
        if (file_exists(frida[i])) {
            report(env, "anti_debug", frida[i]);
            return 1;
        }
    }

    if (dir_contains("/proc/self/fd", "frida")) {
        report(env, "anti_debug", "frida fd");
        return 1;
    }

    static const char *xp[] = {
        "/system/framework/XposedBridge.jar", "/system/lib/libxposed_art.so",
        "/data/local/tmp/xposed", NULL
    };
    for (int i = 0; xp[i]; i++) {
        if (file_exists(xp[i])) {
            report(env, "anti_debug", xp[i]);
            return 1;
        }
    }

    if (contains_in_file("/proc/self/maps", "frida")) {
        report(env, "anti_debug", "frida module in maps");
        return 1;
    }
    if (contains_in_file("/proc/self/maps", "xposed")) {
        report(env, "anti_debug", "xposed module in maps");
        return 1;
    }
    return 0;
}

static int check_package(JNIEnv *env, const char *expected_pkg) {
    if (!expected_pkg || !expected_pkg[0]) return 0;
    FILE *f = fopen("/proc/self/cmdline", "r");
    if (!f) return 0;
    char buf[256] = {0};
    size_t n = fread(buf, 1, sizeof(buf) - 1, f);
    fclose(f);
    if (n == 0) return 0;
    if (strstr(buf, expected_pkg) == NULL) {
        report(env, "package", buf);
        return 1;
    }
    return 0;
}

static int check_proxy(JNIEnv *env) {
    const char *http_proxy = getenv("http_proxy");
    const char *https_proxy = getenv("https_proxy");
    if ((http_proxy && http_proxy[0]) || (https_proxy && https_proxy[0])) {
        report(env, "packet_capture", "proxy env set");
        return 1;
    }

    char value[PROP_VALUE_MAX] = {0};
    __system_property_get("http.proxyHost", value);
    if (value[0]) {
        report(env, "packet_capture", value);
        return 1;
    }

    if (access("/sys/class/net/tun0", F_OK) == 0 || access("/sys/class/net/ppp0", F_OK) == 0) {
        report(env, "packet_capture", "vpn interface up");
        return 1;
    }

    DIR *d = opendir("/sys/class/net");
    if (d) {
        struct dirent *e;
        while ((e = readdir(d)) != NULL) {
            if (strncmp(e->d_name, "tun", 3) == 0 || strncmp(e->d_name, "tap", 3) == 0) {
                report(env, "packet_capture", e->d_name);
                closedir(d);
                return 1;
            }
        }
        closedir(d);
    }
    return 0;
}

static int check_network(JNIEnv *env) {
    struct hostent *he = gethostbyname("www.baidu.com");
    if (!he) {
        report(env, "network", "no internet");
        return 1;
    }
    return 0;
}

static void *monitor_thread(void *arg) {
    JNIEnv *env = NULL;
    if ((*g_vm)->AttachCurrentThread(g_vm, &env, NULL) != JNI_OK) {
        return NULL;
    }

    while (g_running) {
        sleep(3);
        if (!g_running) break;

        if ((g_features & (1 << 4)) && check_network(env)) break;
        if ((g_features & (1 << 2)) && check_proxy(env)) break;
        if ((g_features & (1 << 0)) && check_debug(env)) break;
        if ((g_features & (1 << 3)) && check_root(env)) break;
        if ((g_features & (1 << 1)) && check_package(env, g_pkg)) break;
    }

    (*g_vm)->DetachCurrentThread(g_vm);
    return NULL;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void *reserved) {
    g_vm = vm;
    JNIEnv *env = NULL;
    if ((*vm)->GetEnv(vm, (void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return JNI_ERR;
    }
    jclass cls = (*env)->FindClass(env, "com/nantianmen/guard/Guard");
    if (cls) {
        g_cls = (*env)->NewGlobalRef(env, cls);
        g_onViolation = (*env)->GetStaticMethodID(env, cls, "onViolation", "(Ljava/lang/String;Ljava/lang/String;)V");
    }
    return JNI_VERSION_1_6;
}

JNIEXPORT void JNICALL
Java_com_nantianmen_guard_Guard_nativeInit(JNIEnv *env, jclass cls, jint features, jstring pkg) {
    g_features = features;
    const char *pkgChars = (*env)->GetStringUTFChars(env, pkg, NULL);
    strncpy(g_pkg, pkgChars ? pkgChars : "", sizeof(g_pkg) - 1);
    if (pkgChars) (*env)->ReleaseStringUTFChars(env, pkg, pkgChars);

    if ((g_features & (1 << 0)) && check_debug(env)) return;
    if ((g_features & (1 << 1)) && check_package(env, g_pkg)) return;
    if ((g_features & (1 << 2)) && check_proxy(env)) return;
    if ((g_features & (1 << 3)) && check_root(env)) return;
    if ((g_features & (1 << 4)) && check_network(env)) return;

    if (g_features != 0 && !g_running) {
        g_running = 1;
        pthread_t tid;
        pthread_create(&tid, NULL, monitor_thread, NULL);
        pthread_detach(tid);
    }
}

JNIEXPORT jint JNICALL
Java_com_nantianmen_guard_Guard_nativeCheck(JNIEnv *env, jclass cls) {
    int violations = 0;
    if (g_features & (1 << 0)) violations += check_debug(env);
    if (g_features & (1 << 1)) violations += check_package(env, g_pkg);
    if (g_features & (1 << 2)) violations += check_proxy(env);
    if (g_features & (1 << 3)) violations += check_root(env);
    if (g_features & (1 << 4)) violations += check_network(env);
    return violations;
}
