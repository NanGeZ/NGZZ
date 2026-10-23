#include "ngzz_anti_hook.h"
#include "ngzz_obfuscate.h"

#include <unistd.h>
#include <fcntl.h>
#include <dirent.h>
#include <sys/ptrace.h>
#include <sys/mman.h>
#include <chrono>
#include <cstring>
#include <cstdio>
#include <link.h>

namespace ngzz {

static bool file_contains(const char* path, const char* pattern) {
    FILE* f = fopen(path, "r");
    if (!f) return false;
    char line[512];
    bool found = false;
    while (fgets(line, sizeof(line), f)) {
        if (strstr(line, pattern)) {
            found = true;
            break;
        }
    }
    fclose(f);
    return found;
}

bool detect_inline_hook(const void* func_addr) {
    if (!func_addr) return false;

    const uint8_t* code = static_cast<const uint8_t*>(func_addr);

    // ARM64: check for B/BR/BLR instruction (0x14xxxxxx, 0x16xxxxxx, 0xD6xxxxxx)
    // x86: check for JMP (0xE9), CALL (0xE8), INT3 (0xCC)
#if defined(__aarch64__)
    // Branch instruction: 000101 imm26 (B) = 0x14000000 mask 0xFC000000
    uint32_t insn = *reinterpret_cast<const uint32_t*>(code);
    if ((insn & 0xFC000000) == 0x14000000) return true;  // B
    if ((insn & 0xFC000000) == 0x94000000) return true;  // BL
    if ((insn & 0xFFFFFC1F) == 0xD61F0000) return true;  // BR Xn
    if ((insn & 0xFFFFFC1F) == 0xD63F0000) return true;  // BLR Xn
#elif defined(__arm__)
    uint32_t insn = *reinterpret_cast<const uint32_t*>(code);
    if ((insn & 0x0F000000) == 0x0A000000) return true;  // B
    if ((insn & 0x0F000000) == 0x0B000000) return true;  // BL
#elif defined(__x86_64__) || defined(__i386__)
    if (code[0] == 0xE9 || code[0] == 0xE8) return true;  // JMP/CALL rel
    if (code[0] == 0xFF && (code[1] & 0x38) == 0x20) return true;  // JMP/CALL indirect
    if (code[0] == 0xCC) return true;  // INT3
#endif

    return false;
}

static int check_lib_callback(struct dl_phdr_info* info, size_t size, void* data) {
    (void)size;
    const char* target = static_cast<const char*>(data);
    if (info->dlpi_name && strstr(info->dlpi_name, target)) {
        return 1;
    }
    return 0;
}

bool detect_plt_hook(const char* lib_name) {
    // Check if library is loaded in suspicious order or has modified GOT
    return dl_iterate_phdr(check_lib_callback, const_cast<char*>(lib_name)) != 0;
}

bool detect_frida_hooks() {
    // Check for Frida agent in memory maps
    if (file_contains("/proc/self/maps", "frida")) return true;
    if (file_contains("/proc/self/maps", "gadget")) return true;
    if (file_contains("/proc/self/maps", "linjector")) return true;

    // Check for Frida-specific thread names
    DIR* d = opendir("/proc/self/task");
    if (d) {
        struct dirent* e;
        while ((e = readdir(d))) {
            if (e->d_name[0] == '.') continue;
            char path[256];
            snprintf(path, sizeof(path), "/proc/self/task/%s/comm", e->d_name);
            if (file_contains(path, "frida") || file_contains(path, "gum-js")) {
                closedir(d);
                return true;
            }
        }
        closedir(d);
    }

    // Check for Xposed
    if (file_contains("/proc/self/maps", "XposedBridge")) return true;
    if (file_contains("/proc/self/maps", "edxp")) return true;

    return false;
}

bool detect_timing_anomaly() {
    auto start = std::chrono::high_resolution_clock::now();

    // Perform a quick computation
    volatile int sum = 0;
    for (int i = 0; i < 1000; ++i) {
        sum += i;
    }
    (void)sum;

    auto end = std::chrono::high_resolution_clock::now();
    auto duration = std::chrono::duration_cast<std::chrono::microseconds>(end - start).count();

    // If this simple loop takes too long, debugger is single-stepping
    return duration > 10000;  // > 10ms is suspicious
}

bool detect_hardware_breakpoints() {
#if defined(__aarch64__)
    // ARM64: read debug registers via ptrace
    // Simplified: check /proc/self/status for "TracerPid"
    FILE* f = fopen("/proc/self/status", "r");
    if (!f) return false;
    char line[256];
    bool traced = false;
    while (fgets(line, sizeof(line), f)) {
        if (strncmp(line, "TracerPid", 9) == 0) {
            int v = atoi(line + 10);
            traced = (v != 0);
            break;
        }
    }
    fclose(f);
    return traced;
#else
    return false;
#endif
}

bool check_all_hooks() {
    if (detect_frida_hooks()) return true;
    if (detect_timing_anomaly()) return true;
    if (detect_hardware_breakpoints()) return true;
    return false;
}

}  // namespace ngzz
