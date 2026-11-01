/* aarch64 脱壳 harness：qemu-user 下加载壳so，转储解密后的内存 */
#define _GNU_SOURCE
#include <dlfcn.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <link.h>

static unsigned char* g_base;
static size_t g_dumpsize;

static void dump_region(const char* out, const void* p, size_t n) {
    FILE* f = fopen(out, "wb");
    fwrite(p, 1, n, f);
    fclose(f);
    fprintf(stderr, "[dump] %s %p +0x%zx\n", out, p, n);
}

static int cb(struct dl_phdr_info* info, size_t size, void* data) {
    (void)size; (void)data;
    if (!info->dlpi_name || !strstr(info->dlpi_name, "packer")) return 0;
    g_base = (unsigned char*)info->dlpi_addr;
    Elf64_Addr maxv = 0;
    for (int i = 0; i < info->dlpi_phnum; i++) {
        const Elf64_Phdr* ph = &info->dlpi_phdr[i];
        if (ph->p_type == PT_LOAD) {
            Elf64_Addr end = ph->p_vaddr + ph->p_memsz;
            if (end > maxv) maxv = end;
        }
    }
    g_dumpsize = maxv;
    fprintf(stderr, "[phdr] base=%p imgsize=0x%zx\n", (void*)g_base, g_dumpsize);
    return 0;
}

/* 通用 JNI 桩：记录调用索引 */
static long g_log[512];
static long log_stub(void) {
    long self = (long)__builtin_return_address(0);
    (void)self;
    /* 无法直接知道索引，用全局计数 */
    static long cnt; cnt++;
    if (cnt < 400) fprintf(stderr, "[jni] call #%ld\n", cnt);
    return 0;
}

int main(int argc, char** argv) {
    const char* so = argc > 1 ? argv[1] : "./packer_patched.so";
    void* h = dlopen(so, RTLD_NOW);
    if (!h) { fprintf(stderr, "dlopen failed: %s\n", dlerror()); return 1; }
    fprintf(stderr, "[ok] dlopen %s -> %p\n", so, h);

    dl_iterate_phdr(cb, NULL);
    if (!g_base) { g_base = h; g_dumpsize = 0xC0000; }

    dump_region("dump_after_dlopen.bin", g_base, g_dumpsize);

    void* jni = dlsym(h, "JNI_OnLoad");
    fprintf(stderr, "[ok] JNI_OnLoad sym = %p (offset 0x%lx)\n", jni,
            jni ? (unsigned long)((char*)jni - (char*)g_base) : 0);

    void* un = dlsym(h, "JNI_OnUnload");
    fprintf(stderr, "[ok] JNI_OnUnload sym = %p\n", un);

    if (argc > 2 && strcmp(argv[2], "call") == 0 && jni) {
        /* 构造假 JavaVM：表内全部指向 log_stub */
        static void* vm_table[16];
        static void* env_table[300];
        for (int i = 0; i < 16; i++) vm_table[i] = (void*)log_stub;
        for (int i = 0; i < 300; i++) env_table[i] = (void*)log_stub;
        /* GetEnv(env idx 7) 需要写入 env 指针并返回 0 —— 用专门桩 */
        static void* vm[16];
        for (int i = 0; i < 16; i++) vm[i] = vm_table[i];
        static void* env[300];
        for (int i = 0; i < 300; i++) env[i] = env_table[i];
        vm[0] = env; /* JNIEnv* 在 JavaVM 表首 (reserved0 槽即 env 指针惯例) */

        fprintf(stderr, "[call] JNI_OnLoad(%p, ...)\n", vm);
        long r = ((long(*)(void*, void*))jni)((void*)vm, NULL);
        fprintf(stderr, "[call] JNI_OnLoad returned %ld\n", r);
        dump_region("dump_after_onload.bin", g_base, g_dumpsize);
    }
    return 0;
}
