#ifndef NGZZ_ANTI_HOOK_H
#define NGZZ_ANTI_HOOK_H

#include <jni.h>
#include <cstdint>

namespace ngzz {

// Inline hook detection (check function prologue for JMP/BRK)
bool detect_inline_hook(const void* func_addr);

// PLT/GOT hook detection
bool detect_plt_hook(const char* lib_name);

// Detect Frida/Xposed inline hooks
bool detect_frida_hooks();

// Timing-based anti-debug (detect single-step)
bool detect_timing_anomaly();

// Check for hardware breakpoints (x86/ARM debug registers)
bool detect_hardware_breakpoints();

// Comprehensive anti-hook check
bool check_all_hooks();

}  // namespace ngzz

#endif  // NGZZ_ANTI_HOOK_H
