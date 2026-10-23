package com.ngzz.protect

class NativePack {

    init {
        System.loadLibrary("ngzzpack")
        System.loadLibrary("ngzz")
    }

    external fun protect(
        inputApk: String,
        config: ByteArray,
        outputApk: String,
        payload: Map<String, ByteArray>
    ): String

    external fun nativeDetect(flags: Int): Int

    external fun nativeGuard(flags: Int)
}
