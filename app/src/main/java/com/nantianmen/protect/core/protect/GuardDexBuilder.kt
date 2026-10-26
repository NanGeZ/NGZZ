package com.nantianmen.protect.core.protect

/**
 * 生成注入到目标 APK 的保护 DEX。
 *
 * 每个方法的寄存器布局都必须显式约定：DEX 中入参占据 `registers_size - ins_size .. registers_size - 1`，
 * 因此 `regs` 与字节码里用到的寄存器编号必须一并给出，否则参数会被本地变量覆盖。
 */
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
            val key = "$shorty|$ret|${params.joinToString(",")}"
            return protos.getOrPut(key) { b.proto(shorty, ret, params.toList()) }
        }

        fun method(cls: String, name: String, shorty: String, ret: String, vararg params: String): Int {
            // 缓存键必须带上参数类型：短描述符无法区分 String 与 Object 这类同首字母的参数
            val key = "$cls.$name|$shorty|${ret}|${params.joinToString(",")}"
            val reg = methods.getOrPut(key) { b.method(cls, name, proto(shorty, ret, *params)) }
            // freeze() 之后方法表已按 (类, 名, proto) 定序，字节码与 EncodedMethod 都要用表内最终下标
            return if (b.isFrozen) b.finalMethodIdx(reg) else reg
        }

        fun field(cls: String, name: String, type: String): Int {
            val key = "$cls.$name:$type"
            val reg = fields.getOrPut(key) { b.field(cls, name, type) }
            return if (b.isFrozen) b.finalFieldIdx(reg) else reg
        }
    }

    /**
     * 阶段一注册的全部外部符号。
     * [registerAll] 会跑两遍：freeze 前一遍只做注册（注册序下标在定序后会整体重排，不能写进字节码），
     * freeze 后一遍由 [Ctx] 换算成表内最终下标。两遍同一份注册表，规格不一致会直接抛错。
     */
    private class Sym(
        val mObjCtor: Int, val mSuperCtor: Int, val mSuperAttBase: Int, val mSuperOnCreate: Int,
        val mLogW: Int, val mMyPid: Int, val mKill: Int, val mExit: Int, val mLoadLib: Int,
        val mGuardCtor: Int, val mNativeInit: Int, val mNativeCheck: Int, val mOnViolation: Int,
        val mGuardInitCtor: Int, val mGuardInit: Int,
        val mBootstrapCtor: Int, val mBootstrapStart: Int,
        val mAppCtor: Int, val mAppAttBase: Int, val mAppOnCreate: Int,
        val fInitialized: Int, val fFeatures: Int, val fTargetPkg: Int, val fNativeLoaded: Int
    )

    /**
     * 注册全部外部符号。所有 method/field 必须在 freeze() 之前登记完，
     * 否则定序后的表与已写入的字节码会互相错位。
     */
    private fun registerAll(ctx: Ctx, superDesc: String): Sym {
        val mObjCtor = ctx.method(T_OBJECT, "<init>", "V", "V")
        val mSuperCtor = ctx.method(superDesc, "<init>", "V", "V")
        val mSuperAttBase = ctx.method(superDesc, "attachBaseContext", "VL", "V", T_CONTEXT)
        val mSuperOnCreate = ctx.method(superDesc, "onCreate", "V", "V")

        val mLogW = ctx.method(T_LOG, "w", "ILL", "I", T_STRING, T_STRING)
        val mMyPid = ctx.method(T_PROCESS, "myPid", "I", "I")
        val mKill = ctx.method(T_PROCESS, "killProcess", "VI", "V", "I")
        val mExit = ctx.method("Ljava/lang/System;", "exit", "VI", "V", "I")
        val mLoadLib = ctx.method("Ljava/lang/System;", "loadLibrary", "VL", "V", T_STRING)

        val mGuardCtor = ctx.method(T_GUARD, "<init>", "V", "V")
        val mNativeInit = ctx.method(T_GUARD, "nativeInit", "VIL", "V", "I", T_STRING)
        val mNativeCheck = ctx.method(T_GUARD, "nativeCheck", "I", "I")
        val mOnViolation = ctx.method(T_GUARD, "onViolation", "VLL", "V", T_STRING, T_STRING)

        val mGuardInitCtor = ctx.method(T_GUARDINIT, "<init>", "V", "V")
        val mGuardInit = ctx.method(T_GUARDINIT, "init", "VLL", "V", T_CONTEXT, T_STRING)

        val mBootstrapCtor = ctx.method(T_BOOTSTRAP, "<init>", "V", "V")
        val mBootstrapStart = ctx.method(T_BOOTSTRAP, "start", "VLL", "V", T_CONTEXT, T_STRING)

        // 自己声明的 override 与父类的被调方法是两个不同的 method_id，必须分开注册
        val mAppCtor = ctx.method(T_GUARDAPP, "<init>", "V", "V")
        val mAppAttBase = ctx.method(T_GUARDAPP, "attachBaseContext", "VL", "V", T_CONTEXT)
        val mAppOnCreate = ctx.method(T_GUARDAPP, "onCreate", "V", "V")

        val fInitialized = ctx.field(T_GUARDINIT, "initialized", "Z")
        val fFeatures = ctx.field(T_GUARD, "features", "I")
        val fTargetPkg = ctx.field(T_GUARD, "targetPackage", T_STRING)
        val fNativeLoaded = ctx.field(T_GUARD, "nativeLoaded", "Z")

        return Sym(
            mObjCtor, mSuperCtor, mSuperAttBase, mSuperOnCreate,
            mLogW, mMyPid, mKill, mExit, mLoadLib,
            mGuardCtor, mNativeInit, mNativeCheck, mOnViolation,
            mGuardInitCtor, mGuardInit,
            mBootstrapCtor, mBootstrapStart,
            mAppCtor, mAppAttBase, mAppOnCreate,
            fInitialized, fFeatures, fTargetPkg, fNativeLoaded
        )
    }

    /**
     * @param superApp GuardApplication 的父类描述符（形如 `Lcom/demo/App;`）。
     *   传原 Application 类可保留其生命周期回调；调用方在原类不可继承时回退为 [T_APP]。
     */
    fun buildDex(features: Int, pkg: String, superApp: String): ByteArray {
        val b = DexEncoder.Builder()
        val ctx = Ctx(b)

        val superDesc = descriptor(superApp)
        val pkgStr = pkg.ifEmpty { "unknown" }

        // ---- 阶段一：注册全部 proto / method / field（此时只登记，下标还会重排）----
        registerAll(ctx, superDesc)

        // ---- 阶段二：字符串入表，然后一次性定稿全部下标 ----
        // 字符串表每新增一条就整体重排，先取的下标会被后续插入顶掉；
        // proto/field/method 的段内升序排序也在此定稿，冻结后不再允许注册符号。
        b.addString("nantianmen_guard")
        b.addString(LOG_TAG_STR)
        b.addString(pkgStr)
        b.freeze()
        val sLibIdx = b.stringIndex("nantianmen_guard")
        val sLogTagIdx = b.stringIndex(LOG_TAG_STR)
        val sPkgIdx = b.stringIndex(pkgStr)

        // ---- 阶段三：同一份注册表再跑一遍，取到表内最终下标 ----
        val s = registerAll(ctx, superDesc)

        // ---- 阶段三：类定义与字节码（s.xxx 是表内最终下标，字节码与 Encoded* 统一使用）----
        b.addClass(
            T_GUARD, T_OBJECT,
            staticFields = listOf(
                DexEncoder.EncodedField(s.fFeatures, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC),
                DexEncoder.EncodedField(s.fTargetPkg, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC),
                DexEncoder.EncodedField(s.fNativeLoaded, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC)
            ),
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    s.mGuardCtor, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(s.mObjCtor), regs = 1
                ),
                DexEncoder.EncodedMethod(
                    s.mNativeInit,
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC or DexEncoder.ACC_NATIVE,
                    ByteArray(0)
                ),
                DexEncoder.EncodedMethod(
                    s.mNativeCheck,
                    DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC or DexEncoder.ACC_NATIVE,
                    ByteArray(0)
                ),
                DexEncoder.EncodedMethod(
                    s.mOnViolation, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC,
                    codeOnViolation(s.mLogW, s.mMyPid, s.mKill, s.mExit, sLogTagIdx), regs = 5
                )
            )
        )

        b.addClass(
            T_GUARDINIT, T_OBJECT,
            staticFields = listOf(
                DexEncoder.EncodedField(s.fInitialized, DexEncoder.ACC_PRIVATE or DexEncoder.ACC_STATIC)
            ),
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    s.mGuardInitCtor, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(s.mObjCtor), regs = 1
                ),
                DexEncoder.EncodedMethod(
                    s.mGuardInit, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC or DexEncoder.ACC_FINAL,
                    codeGuardInit(
                        s.fInitialized, s.fFeatures, s.fTargetPkg, s.fNativeLoaded,
                        s.mLoadLib, s.mNativeInit, sLibIdx, features
                    ),
                    regs = 6
                )
            )
        )

        b.addClass(
            T_BOOTSTRAP, T_OBJECT,
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    s.mBootstrapCtor, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(s.mObjCtor), regs = 1
                ),
                DexEncoder.EncodedMethod(
                    s.mBootstrapStart, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_STATIC,
                    codeBootstrapStart(s.mGuardInit), regs = 2
                )
            )
        )

        b.addClass(
            T_GUARDAPP, superDesc,
            directMethods = listOf(
                DexEncoder.EncodedMethod(
                    s.mAppCtor, DexEncoder.ACC_PUBLIC or DexEncoder.ACC_CONSTRUCTOR,
                    codeDefaultCtor(s.mSuperCtor), regs = 1
                )
            ),
            virtualMethods = listOf(
                // attachBaseContext 是 protected 的非构造实例方法，属于 virtual 方法组
                DexEncoder.EncodedMethod(
                    s.mAppAttBase, DexEncoder.ACC_PROTECTED,
                    codeAttachBaseContext(s.mSuperAttBase, s.mBootstrapStart, sPkgIdx), regs = 4
                ),
                DexEncoder.EncodedMethod(
                    s.mAppOnCreate, DexEncoder.ACC_PUBLIC,
                    codeOnCreate(s.mSuperOnCreate), regs = 1
                )
            )
        )

        return DexEncoder.build(b)
    }

    private fun descriptor(className: String): String =
        if (className.startsWith("L") && className.endsWith(";")) className
        else "L" + className.replace('.', '/') + ";"

    /** `public <init>() { super(); }` —— regs=1，入参 v0 = this */
    private fun codeDefaultCtor(mSuperCtor: Int): ByteArray = Codegen.codeOf(
        Codegen.invokeDirect(mSuperCtor, listOf(0)),
        Codegen.returnVoid()
    )

    /**
     * 静态 `onViolation(String type, String detail)`，regs=5 → 入参 p0=v3、p1=v4。
     * 记录后直接结束进程，这是保护的响应动作。
     */
    private fun codeOnViolation(
        mLogW: Int, mMyPid: Int, mKill: Int, mExit: Int, sLogTagIdx: Int
    ): ByteArray = Codegen.codeOf(
        Codegen.constString(0, sLogTagIdx),
        Codegen.invokeStatic(mLogW, listOf(0, 3)),
        Codegen.invokeStatic(mLogW, listOf(0, 4)),
        Codegen.invokeStatic(mMyPid, emptyList()),
        Codegen.moveResult(1),
        Codegen.invokeStatic(mKill, listOf(1)),
        Codegen.const4(1, 1),
        Codegen.invokeStatic(mExit, listOf(1)),
        Codegen.returnVoid()
    )

    /**
     * 静态 `init(Context ctx, String pkg)`，regs=6 → 入参 p0(ctx)=v4、p1(pkg)=v5。
     * 可重入保护 + 加载原生库 + 启动检测。
     */
    private fun codeGuardInit(
        fInitialized: Int, fFeatures: Int, fTargetPkg: Int, fNativeLoaded: Int,
        mLoadLib: Int, mNativeInit: Int, sLibIdx: Int, features: Int
    ): ByteArray {
        val tail = Codegen.codeOf(
            Codegen.const4(0, 1),
            Codegen.sputBool(0, fInitialized),
            Codegen.constString(1, sLibIdx),
            Codegen.invokeStatic(mLoadLib, listOf(1)),
            Codegen.const4(0, 1),
            Codegen.sputBool(0, fNativeLoaded),
            Codegen.const(0, features),
            Codegen.sputInt(0, fFeatures),
            Codegen.sputObject(5, fTargetPkg),
            Codegen.invokeStatic(mNativeInit, listOf(0, 5)),
            Codegen.returnVoid()
        )
        // 分支目标 = 当前指令下标 + 偏移；sget-boolean 占 2 个 code unit，
        // if-nez 占 2 个，故 if-nez 下标为 2、tail 从 4 开始，return-void 是 tail 最后一条。
        val branchIdx = 2
        val returnIdx = 4 + tail.size / 2 - 1
        return Codegen.codeOf(
            Codegen.sgetBool(0, fInitialized),
            Codegen.ifNez(0, returnIdx - branchIdx),
            tail
        )
    }

    /** 静态 `start(Context, String)`，regs=2 → 入参 p0=v0、p1=v1 */
    private fun codeBootstrapStart(mInit: Int): ByteArray = Codegen.codeOf(
        Codegen.invokeStatic(mInit, listOf(0, 1)),
        Codegen.returnVoid()
    )

    /**
     * 实例 `attachBaseContext(Context base)`，regs=4 → 入参 this=v2、base=v3。
     * 先走原 Application 的实现，再引导保护逻辑。
     */
    private fun codeAttachBaseContext(
        mSuperAttBase: Int, mBootstrapStart: Int, sPkgIdx: Int
    ): ByteArray = Codegen.codeOf(
        Codegen.invokeSuper(mSuperAttBase, listOf(2, 3)),
        Codegen.constString(0, sPkgIdx),
        Codegen.invokeStatic(mBootstrapStart, listOf(3, 0)),
        Codegen.returnVoid()
    )

    /** 实例 `onCreate()`，regs=1 → 入参 this=v0；保护已在 attachBaseContext 中启动 */
    private fun codeOnCreate(mSuperOnCreate: Int): ByteArray = Codegen.codeOf(
        Codegen.invokeSuper(mSuperOnCreate, listOf(0)),
        Codegen.returnVoid()
    )
}
