package com.nantianmen.protect.core.protect

object GuardDexBuilder {

    const val T_APP = "Landroid/app/Application;"
    const val T_CONTEXT = "Landroid/content/Context;"
    const val T_OBJECT = "Ljava/lang/Object;"
    const val T_STRING = "Ljava/lang/String;"
    const val T_LOG = "Landroid/util/Log;"
    const val T_PROCESS = "Landroid/os/Process;"
    const val T_GUARD = "Lcom/nantianmen/guard/Guard;"
    const val T_GUARDINIT = "Lcom/nantianmen/guard/GuardInit;"
    const val T_GUARDAPP = "Lcom/nantianmen/guard/GuardApplication;"
    const val T_BOOTSTRAP = "Lcom/nantianmen/guard/Bootstrap;"

    private const val LOG_TAG_STR = "NantianmenGuard"

    class Ctx(val b: DexEncoder.Builder) {
        val protos = HashMap<String, Int>()
        val methods = HashMap<String, Int>()
        val fields = HashMap<String, Int>()

        fun proto(shorty: String, ret: String, vararg params: String): Int {
            val key = "$shorty|${params.joinToString(",")}"
            return protos.getOrPut(key) { b.proto(shorty, ret, params.toList()) }
        }

        fun method(cls: String, name: String, shorty: String, ret: String, vararg params: String): Int {
            val key = "$cls.$name$shorty"
            return methods.getOrPut(key) { b.method(cls, name, proto(shorty, ret, *params)) }
        }

        fun field(cls: String, name: String, type: String): Int {
            val key = "$cls.$name:$type"
            return fields.getOrPut(key) { b.field(cls, name, type) }
        }
    }

    fun buildDex(features: Int, pkg: String, originalApp: String): ByteArray {
        val b = DexEncoder.Builder()
        val ctx = Ctx(b)

        val pkgStr = pkg.ifEmpty { "unknown" }
        val hasDelegate = originalApp.isNotEmpty() && originalApp != "android.app.Application" && originalApp != T_APP
        val delegateCls = if (hasDelegate) descriptor(originalApp) else T_APP

        val mAppAttBase = ctx.method(T_APP, "attachBaseContext", "VL", "V", T_CONTEXT)
        val mAppOnCreate = ctx.method(T_APP, "onCreate", "V", "V")
        val mLogW = ctx.method(T_LOG, "w", "ILL", "I", T_STRING, T_STRING)
        val mMyPid = ctx.method(T_PROCESS, "myPid", "I", "I")
        val mKill = ctx.method(T_PROCESS, "killProcess", "VI", "V", "I")
        val mExit = ctx.method("Ljava/lang/System;", "exit", "VI", "V", "I")
        val mLoadLib = ctx.method("Ljava/lang/System;", "loadLibrary", "VL", "V", T_STRING)
        val mStrEq = ctx.method(T_STRING, "equals", "ZL", "Z", T_OBJECT)
        val mGetPkgName = ctx.method(T_CONTEXT, "getPackageName", "L", T_STRING)
        val mInit = ctx.method(T_GUARDINIT, "init", "VLL", "V", T_CONTEXT, T_STRING)
        val mNativeInit = ctx.method(T_GUARD, "nativeInit", "VIL", "V", "I", T_STRING)
        val mOnViolation = ctx.method(T_GUARD, "onViolation", "VLL", "V", T_STRING, T_STRING)
        val mCheckAll = ctx.method(T_GUARD, "checkAll", "IZ", "I")
        val mBootstrapStart = ctx.method(T_BOOTSTRAP, "start", "VLL", "V", T_CONTEXT, T_STRING)
        val mSuperAttBase = ctx.method(T_GUARDAPP, "attachBaseContext", "VL", "V", T_CONTEXT)
        val mSuperOnCreate = ctx.method(T_GUARDAPP, "onCreate", "V", "V")
        val mDelegateAttBase = ctx.method(delegateCls, "attachBaseContext", "VL", "V", T_CONTEXT)
        val mDelegateOnCreate = ctx.method(delegateCls, "onCreate", "V", "V")

        val fInitialized = ctx.field(T_GUARDINIT, "initialized", "Z")
        val fFeatures = ctx.field(T_GUARD, "features", "I")
        val fTargetPkg = ctx.field(T_GUARD, "targetPackage", T_STRING)
        val fNativeLoaded = ctx.field(T_GUARD, "nativeLoaded", "Z")
        val fDelegate = ctx.field(T_GUARDAPP, "delegate", delegateCls)

        val sLibIdx = b.stringIndex("nantianmen_guard")
        val sViolation = b.stringIndex("violation")
        val sKill = b.stringIndex("kill")
        val sLogTag = b.stringIndex(LOG_TAG_STR)
        val sPkg = b.stringIndex(pkgStr)

        b.addClass(
            T_GUARD, T_OBJECT,
            staticFields = listOf(
                DexEncoder.EncodedField(fFeatures, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC),
                DexEncoder.EncodedField(fTargetPkg, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC),
                DexEncoder.EncodedField(fNativeLoaded, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC)
            ),
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    ctx.method(T_GUARD, "<init>", "V", "V"),
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(),
                    isFinal = true
                ),
                DexEncoder.EncodedMethod(
                    ctx.method(T_GUARD, "nativeInit", "VIL", "V", "I", T_STRING),
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC or DexEncoder.ACC_NATIVE,
                    ByteArray(0),
                    isFinal = true
                ),
                DexEncoder.EncodedMethod(
                    ctx.method(T_GUARD, "nativeCheck", "I", "I"),
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC or DexEncoder.ACC_NATIVE,
                    ByteArray(0),
                    isFinal = true
                ),
                DexEncoder.EncodedMethod(
                    mOnViolation,
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC,
                    codeOnViolation(mLogW, mMyPid, mKill, mExit, sLogTag, sViolation, sKill),
                    isFinal = true
                ),
                DexEncoder.EncodedMethod(
                    mCheckAll,
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC,
                    codeCheckAll(fTargetPkg, mGetPkgName, mStrEq, mOnViolation, sPkg),
                    isFinal = true
                )
            )
        )

        b.addClass(
            T_GUARDINIT, T_OBJECT,
            staticFields = listOf(
                DexEncoder.EncodedField(fInitialized, DexEncoder.ACC_PRIVATE or DexEncoder.ACC_STATIC)
            ),
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    ctx.method(T_GUARDINIT, "<init>", "V", "V"),
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(),
                    isFinal = true
                ),
                DexEncoder.EncodedMethod(
                    mInit,
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC or DexEncoder.ACC_FINAL,
                    codeGuardInit(fInitialized, fFeatures, fTargetPkg, fNativeLoaded, mLoadLib, mNativeInit, mBootstrapStart, sLibIdx, sPkg, features),
                    isFinal = true
                )
            )
        )

        b.addClass(
            T_BOOTSTRAP, T_OBJECT,
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    ctx.method(T_BOOTSTRAP, "<init>", "V", "V"),
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(),
                    isFinal = true
                ),
                DexEncoder.EncodedMethod(
                    mBootstrapStart,
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC,
                    codeBootstrapStart(mInit),
                    isFinal = true
                )
            )
        )

        b.addClass(
            T_GUARDAPP, T_APP,
            instanceFields = listOf(
                DexEncoder.EncodedField(fDelegate, DexEncoder.ACC_PRIVATE)
            ),
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    ctx.method(T_GUARDAPP, "<init>", "V", "V"),
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(),
                    isFinal = true
                ),
                DexEncoder.EncodedMethod(
                    mSuperAttBase,
                    DexEncoder.ACC_PROTECTED,
                    codeAttachBaseContext(mSuperAttBase, mInit, sPkg),
                    isFinal = true
                )
            ),
            virtualMethods = listOf(
                DexEncoder.EncodedMethod(
                    mSuperOnCreate,
                    DexEncoder.ACC_PUBLIC,
                    codeOnCreate(mSuperOnCreate, mInit, sPkg),
                    isFinal = true
                )
            )
        )

        return DexEncoder.build(b)
    }

    private fun descriptor(className: String): String {
        return if (className.startsWith("L") && className.endsWith(";")) className
        else "L" + className.replace('.', '/') + ";"
    }

    private fun codeDefaultCtor(): ByteArray {
        return Codegen.codeOf(
            Codegen.invokeDirect(0, listOf(0, 0)),
            Codegen.returnVoid()
        )
    }

    private fun codeOnViolation(
        mLogW: Int, mMyPid: Int, mKill: Int, mExit: Int,
        sLogTagIdx: Int, sViolationIdx: Int, sKillIdx: Int
    ): ByteArray {
        return Codegen.codeOf(
            Codegen.constString(0, sLogTagIdx),
            Codegen.constString(1, sViolationIdx),
            Codegen.invokeStatic(mLogW, listOf(0, 1)),
            Codegen.constString(1, sKillIdx),
            Codegen.invokeStatic(mLogW, listOf(0, 1)),
            Codegen.invokeStatic(mMyPid, emptyList()),
            Codegen.moveResult(2),
            Codegen.invokeStatic(mKill, listOf(2)),
            Codegen.const4(3, 1),
            Codegen.invokeStatic(mExit, listOf(3)),
            Codegen.returnVoid()
        )
    }

    private fun codeCheckAll(
        fTargetPkg: Int, mGetPkgName: Int, mStrEq: Int, mOnViolation: Int,
        sPkgIdx: Int
    ): ByteArray {
        return Codegen.codeOf(
            Codegen.const4(0, 0),
            Codegen.invokeStatic(mGetPkgName, listOf(0)),
            Codegen.moveResultObject(1),
            Codegen.sgetTo(2, fTargetPkg),
            Codegen.invokeVirtual(mStrEq, listOf(1, 2)),
            Codegen.moveResult(3),
            Codegen.ifNez(3, 8),
            Codegen.constString(4, sPkgIdx),
            Codegen.invokeStatic(mOnViolation, listOf(4, 1)),
            Codegen.const4(5, 1),
            Codegen.returnObject(5),
            Codegen.const4(5, 0),
            Codegen.returnObject(5)
        )
    }

    private fun codeGuardInit(
        fInitialized: Int, fFeatures: Int, fTargetPkg: Int, fNativeLoaded: Int,
        mLoadLib: Int, mNativeInit: Int, mBootstrapStart: Int,
        sLibIdx: Int, sPkgIdx: Int, features: Int
    ): ByteArray {
        return Codegen.codeOf(
            Codegen.sgetTo(0, fInitialized),
            Codegen.ifNez(0, 20),
            Codegen.const4(1, 1),
            Codegen.sputFrom(1, fInitialized),
            Codegen.constString(2, sLibIdx),
            Codegen.invokeStatic(mLoadLib, listOf(2)),
            Codegen.const4(3, 1),
            Codegen.sputFrom(3, fNativeLoaded),
            Codegen.const(4, features),
            Codegen.sputFrom(4, fFeatures),
            Codegen.constString(5, sPkgIdx),
            Codegen.sputFrom(5, fTargetPkg),
            Codegen.invokeStatic(mNativeInit, listOf(4, 5)),
            Codegen.invokeStatic(mBootstrapStart, listOf(6, 5)),
            Codegen.returnVoid()
        )
    }

    private fun codeBootstrapStart(mInit: Int): ByteArray {
        return Codegen.codeOf(
            Codegen.invokeStatic(mInit, listOf(0, 1)),
            Codegen.returnVoid()
        )
    }

    private fun codeAttachBaseContext(
        mSuperAttBase: Int, mInit: Int, sPkgIdx: Int
    ): ByteArray {
        return Codegen.codeOf(
            Codegen.invokeSuper(mSuperAttBase, listOf(0, 1)),
            Codegen.constString(2, sPkgIdx),
            Codegen.invokeStatic(mInit, listOf(1, 2)),
            Codegen.returnVoid()
        )
    }

    private fun codeOnCreate(
        mSuperOnCreate: Int, mInit: Int, sPkgIdx: Int
    ): ByteArray {
        return Codegen.codeOf(
            Codegen.invokeSuper(mSuperOnCreate, listOf(0)),
            Codegen.constString(1, sPkgIdx),
            Codegen.invokeStatic(mInit, listOf(0, 1)),
            Codegen.returnVoid()
        )
    }
}
