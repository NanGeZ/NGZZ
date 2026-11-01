/* bionic 兼容桩库：提供 libandroid.so(liblog.so) 及 bionic 专有符号 */
#define _GNU_SOURCE
#include <stdio.h>
#include <stdarg.h>
#include <string.h>
#include <stdlib.h>
#include <fcntl.h>
#include <unistd.h>
#include <setjmp.h>
#undef sigsetjmp
int __sigsetjmp(jmp_buf env, int savemask);

/* ---- liblog ---- */
int __android_log_print(int prio, const char* tag, const char* fmt, ...) {
    va_list ap; va_start(ap, fmt);
    fprintf(stderr, "[log:%s] ", tag);
    vfprintf(stderr, fmt, ap);
    fputc('\n', stderr);
    va_end(ap);
    return 0;
}
int __android_log_write(int prio, const char* tag, const char* text) {
    fprintf(stderr, "[logw:%s] %s\n", tag, text); return 0;
}

/* ---- bionic libc 专有符号 ---- */
void __assert2(const char* f, int l, const char* fn, const char* expr) {
    fprintf(stderr, "ASSERT %s:%d %s: %s\n", f, l, fn, expr);
    abort();
}
int __system_property_get(const char* name, char* value) {
    /* 模拟 ro.build.version.sdk = 33 */
    if (strstr(name, "sdk")) strcpy(value, "33");
    else value[0] = 0;
    return (int)strlen(value);
}
void android_set_abort_message(const char* msg) {
    fprintf(stderr, "abort_msg: %s\n", msg);
}
long __open_2(const char* p, int f) { return open(p, f); }
size_t strlcpy(char* d, const char* s, size_t n) {
    size_t l = strlen(s);
    if (n) { size_t c = l >= n ? n-1 : l; memcpy(d, s, c); d[c] = 0; }
    return l;
}
size_t __strlcpy_chk(char* d, const char* s, size_t n, size_t ds) {
    (void)ds; return strlcpy(d, s, n);
}
int* __errno(void) { static int e; return &e; }
char __sF[3][1024]; /* bionic FILE 栈 */

/* bionic *_chk 族 */
size_t __strlen_chk(const char* s, size_t n) { (void)n; return strlen(s); }
long __read_chk(int fd, void* b, size_t n, size_t bn) { (void)bn; return read(fd, b, n); }
char* __strrchr_chk(const char* s, int c, size_t n) { (void)n; return strrchr(s, c); }
void* __memmove_chk(void* d, const void* s, size_t n, size_t dn) { (void)dn; return memmove(d, s, n); }
int __vsnprintf_chk(char* b, size_t n, int f, size_t bn, const char* fmt, va_list ap) {
    (void)bn; (void)f; return vsnprintf(b, n, fmt, ap);
}
int sigsetjmp(jmp_buf env, int savemask) { return __sigsetjmp(env, savemask); }

/* ---- libandroid: AAsset 最小实现 (从本地目录读取) ---- */
typedef struct AAssetManager AAssetManager;
typedef struct AAsset AAsset;
static const char* g_asset_dir = "assets_dir";
AAsset* AAssetManager_open(AAssetManager* m, const char* fn, int mode) {
    (void)m; (void)mode;
    char path[512];
    snprintf(path, sizeof(path), "%s/%s", g_asset_dir, fn);
    FILE* f = fopen(path, "rb");
    fprintf(stderr, "[AAsset_open] %s -> %p\n", path, (void*)f);
    if (!f) return NULL;
    return (AAsset*)f;
}
int AAsset_read(AAsset* a, void* buf, size_t count) {
    return (int)fread(buf, 1, count, (FILE*)a);
}
off_t AAsset_seek(AAsset* a, off_t offset, int whence) {
    return fseeko((FILE*)a, offset, whence);
}
void AAsset_close(AAsset* a) { fclose((FILE*)a); }
long AAsset_getLength(AAsset* a) {
    FILE* f = (FILE*)a; long cur = ftello(f);
    fseeko(f, 0, SEEK_END); long len = ftello(f); fseeko(f, cur, SEEK_SET);
    return len;
}
long AAsset_getRemainingLength(AAsset* a) {
    return AAsset_getLength(a) - ftello((FILE*)a);
}
const void* AAsset_getBuffer(AAsset* a) { (void)a; return NULL; }
void set_asset_dir(const char* d) { g_asset_dir = d; }
