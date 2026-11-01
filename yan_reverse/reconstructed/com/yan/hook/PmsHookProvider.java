package com.yan.hook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * ============================================================================
 * YanTool「签名校验 / 过签」核心 —— 从 DPT 加固(hook.dex)中还原的可读源码
 * ============================================================================
 *
 * 【这套过签到底做了什么】
 * 重打包/加固工具在给 APK 重新签名之前，先把「原始 APK 的签名证书」序列化后
 * 存进 APK 内的一个 ZIP 条目 `_.s` 里。运行时本类做三件事：
 *
 *  1) 启动时 entry(ctx) 读出包名 appPkg、原始签名 origSigs（来自 `_.s`）。
 *  2) 三层 hook 系统服务（FakeCache → FakeSM → FakeBinder），让应用拿到的
 *     IPackageManager 被替换成 FakeBinder（本类的 Binder 代理）。
 *  3) FakeBinder 拦截所有会返回「本应用签名」的 PMS 事务
 *     (getPackageInfo / getInstalledPackages / getInstalledApplications /
 *      getApplicationInfo / hasSigningCertificate...)，把返回 Parcel 里
 *     本包名对应的 PackageInfo.signatures / SigningInfo /
 *     ApplicationInfo.signatures 全部改写成 origSigs，hasSigningCertificate
 *     则强制返回 true。
 *
 * 结果：无论 APK 被重签成什么证书，凡是「应用自查签名」（很多加固/支付/DRM
 * 用 getPackageInfo(GET_SIGNING_CERTIFICATES) 或 hasSigningCertificate 做
 * 自校验）拿到的都是原始证书 → 自校验通过，即所谓「过签」。
 *
 * 【为什么只有本包名才 patch】
 * patchInfo 里严格判断 appPkg.equals(packageInfo.packageName)，只改自己，
 * 避免影响系统里其它应用的签名，降低被察觉的概率。
 *
 * 【三层 hook 架构】
 *   FakeCache —— 替换 ServiceManager.sCache，"package" 缓存项直接命中 FakeBinder；
 *   FakeSM    —— 伪装 ServiceManager Binder，转发真实 SM，把 "package" 的
 *                getService / checkService 结果替换成 FakeBinder；
 *   FakeBinder—— 伪装 IPackageManager Binder，转发真实 PMS 后改写 reply。
 * ============================================================================
 */
public class PmsHookProvider {

    private static final String TAG = "YanHook";

    // `_.s`：APK zip 里存放「原始签名」的条目名；`_.o` 是被抽出的原始 APK。
    private static final String SIG_ENTRY = "_.s";
    private static final String ORIG_ENTRY = "_.o";

    // 运行期状态
    private String appPkg;              // 本应用包名（getPackageName()）
    private String origApkPath;         // 抽出的原始 APK 路径
    private Signature[] origSigs;       // 从 `_.s` 读出的原始签名（过签 payload）

    // 缓存的反射 Field / Method（不同 Android 版本字段名不同，反射解析一次后缓存）
    private static Field CACHE_SI_DETAILS;    // SigningInfo.mSigningDetails
    private static Field CACHE_SD_SIGNATURES; // SigningDetails.signatures
    private static Field CACHE_AI_SIGNATURES; // ApplicationInfo.signatures
    private static Object CACHE_PLS_CREATOR;  // ParceledListSlice.CREATOR
    private static Method CACHE_PLS_GETLIST;  // ParceledListSlice.getList()

    // 三层 hook 的运行期句柄：
    //   g_smFake —— 伪装的 IPackageManager Binder（FakeBinder），sCache/SM 查询都指向它
    //   g_realSM —— 真实的 ServiceManager Binder，FakeSM 把请求转发给它
    private static IBinder g_smFake;
    private static IBinder g_realSM;

    // ---- IPackageManager 事务码：优先反射读 IPackageManager$Stub.TRANSACTION_*，
    // 反射失败时用下列 AOSP 默认值兜底 ----
    private static int TX_getPackageInfo = 3;
    private static int TX_getPackageInfoVersioned = 4;
    private static int TX_getApplicationInfo = 9;
    private static int TX_getApplicationInfoVersioned = 10;
    private static int TX_getPackageArchiveInfo = -1;
    private static int TX_getInstalledPackages = -1;
    private static int TX_getInstalledApplications = -1;
    private static int TX_hasSigningCertificate = -1;

    // ========================================================================
    // 入口：hook 安装点（由加固壳 / Application.onCreate 调用）
    // ========================================================================
    public static void entry(Context context) {
        PmsHookProvider p = new PmsHookProvider();
        p.appPkg = context.getPackageName();

        // 尽力而为：任一步失败都不致命，只是失去过签能力。
        try {
            p.origApkPath = extractOrigApk(context);
            Signature[] sigs = p.loadSignatures(context);
            Log.i(TAG, "entry: pkg=" + p.appPkg + " orig=" + p.origApkPath
                    + " sigs=" + (sigs == null ? "null" : sigs.length));

            if (sigs != null && sigs.length > 0) {
                p.origSigs = sigs;
            }
            // 解析事务码 + 安装 ServiceManager hook
            p.install(context);
        } catch (Throwable t) {
            Log.w(TAG, "entry failed", t);
        }
    }

    /** 反射解析 IPackageManager$Stub.TRANSACTION_*，失败则保留默认值。 */
    private static void resolveTxCodes() {
        try {
            Class<?> stub = Class.forName("android.content.pm.IPackageManager$Stub");
            TX_getPackageInfo = readTx(stub, "TRANSACTION_getPackageInfo", TX_getPackageInfo);
            TX_getPackageInfoVersioned = readTx(stub, "TRANSACTION_getPackageInfoVersioned", TX_getPackageInfoVersioned);
            TX_getApplicationInfo = readTx(stub, "TRANSACTION_getApplicationInfo", TX_getApplicationInfo);
            TX_getApplicationInfoVersioned = readTx(stub, "TRANSACTION_getApplicationInfoVersioned", TX_getApplicationInfoVersioned);
            TX_getPackageArchiveInfo = readTx(stub, "TRANSACTION_getPackageArchiveInfo", TX_getPackageArchiveInfo);
            TX_getInstalledPackages = readTx(stub, "TRANSACTION_getInstalledPackages", TX_getInstalledPackages);
            TX_getInstalledApplications = readTx(stub, "TRANSACTION_getInstalledApplications", TX_getInstalledApplications);
            TX_hasSigningCertificate = readTx(stub, "TRANSACTION_hasSigningCertificate", TX_hasSigningCertificate);
        } catch (Throwable ignored) {
        }
    }

    private static int readTx(Class<?> stub, String name, int def) {
        try {
            Field f = stub.getDeclaredField(name);
            f.setAccessible(true);
            return f.getInt(null);
        } catch (Throwable t) {
            return def;
        }
    }

    // ========================================================================
    // 读取原始签名：从本 APK 的 zip 条目 `_.s`
    //   序列化格式（与加固工具的写入端一致）：
    //     byte count（无符号）
    //     重复 count 次 { int len; byte[len] certBytes }
    // ========================================================================
    private Signature[] loadSignatures(Context ctx) {
        ZipFile zip = null;
        DataInputStream in = null;
        try {
            ApplicationInfo ai = ctx.getApplicationInfo();
            zip = new ZipFile(ai.sourceDir);
            ZipEntry e = zip.getEntry(SIG_ENTRY);
            if (e == null) {
                Log.w(TAG, "loadSignatures: no " + SIG_ENTRY + " entry");
                return null;
            }
            in = new DataInputStream(zip.getInputStream(e));
            int count = in.readUnsignedByte();    // 计数是 1 字节无符号数，不是 int
            if (count <= 0) return null;
            Signature[] out = new Signature[count];
            for (int i = 0; i < count; i++) {
                int len = in.readInt();
                if (len <= 0 || len > 16 * 1024) return null;
                byte[] cert = new byte[len];
                in.readFully(cert);
                out[i] = new Signature(cert);
            }
            return out;
        } catch (Throwable t) {
            Log.w(TAG, "loadSignatures failed", t);
            return null;
        } finally {
            try { if (in != null) in.close(); } catch (Throwable ignored) {}
            try { if (zip != null) zip.close(); } catch (Throwable ignored) {}
        }
    }

    /** 返回原始签名的克隆（外部不能直接改内部缓存）。 */
    public Signature[] sigs() {
        if (origSigs == null) return null;
        return origSigs.clone();
    }

    // ========================================================================
    // hook 安装：三层结构
    //   第一层 FakeBinder —— 伪装 IPackageManager：转发真实 PMS，改写 reply
    //   第二层 FakeSM     —— 伪装 ServiceManager：转发真实 SM，把 "package"
    //                        的 getService/checkService 结果换成 FakeBinder
    //   第三层 FakeCache  —— 替换 ServiceManager.sCache：缓存里 "package"
    //                        直接命中 FakeBinder
    // ========================================================================
    private void install(Context context) {
        resolveTxCodes();
        try {
            // 第一层：伪装 IPackageManager（真实 PMS Binder 从 ServiceManager 取）
            IBinder fakePms = new FakeBinder(getRealPackageBinder());
            // 原版 hook(Context)：把代理对象内部缓存的 IBinder 字段换成 FakeBinder，
            // 即 bindFake(ActivityThread.sPackageManager 的值, fake) 与
            //    bindFake(context.getPackageManager().mPM 的值, fake)
            try {
                Class<?> atCls = Class.forName("android.app.ActivityThread");
                Method cur = atCls.getDeclaredMethod("currentActivityThread");
                cur.setAccessible(true);
                Object at = cur.invoke(null);
                bindFake(Reflect.fieldValue(at, "sPackageManager"), fakePms);
                bindFake(Reflect.fieldValue(context.getPackageManager(), "mPM"), fakePms);
            } catch (Throwable unused) {
            }
            // 记录 g_smFake / g_realSM（原版 installCtxHook）
            installCtxHook(fakePms);
            // 第三层：替换 ServiceManager.sCache（原版 applyServiceManagerCacheHook）
            applyServiceManagerCacheHook();
            // 原版 hook(Context) 在 installCtxHook 之后调用 m100()：
            // 清空 PM 内部的 sPackageInfoCache 静态缓存
            clearPackageInfoCache();
        } catch (Throwable t) {
            Log.w(TAG, "install failed", t);
        }
    }

    /** 从 ServiceManager 拿到真实的 "package" 服务 Binder。 */
    private static IBinder getRealPackageBinder() {
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            Method m = sm.getDeclaredMethod("getService", String.class);
            m.setAccessible(true);
            return (IBinder) m.invoke(null, "package");
        } catch (Throwable t) {
            return null;
        }
    }

    /** 反射取真实的 ServiceManager Binder（原版 getRealServiceManagerBinder：读 sServiceManager 字段）。 */
    private static IBinder getRealServiceManagerBinder() {
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            Field f = sm.getDeclaredField("sServiceManager");
            f.setAccessible(true);
            Object v = f.get(null);
            return v instanceof IBinder ? (IBinder) v : null;
        } catch (Throwable unused) {
            return null;
        }
    }

    /** D4 接线：登记真假 Binder（原版 installCtxHook）。 */
    private void installCtxHook(IBinder fakePms) {
        g_smFake = fakePms;
        g_realSM = getRealServiceManagerBinder();
    }

    /**
     * 原版 bindFake：把代理对象内部缓存的 IBinder 字段换成 fake binder。
     * 按 mRemote / mBinder / mPM 三个名字在对象的类继承链（直到 Object.class）上
     * 查找，找到「值是 IBinder」的字段即替换并返回；三个名字全部落空则记日志
     * "bindFake: no mRemote on <类名>"。
     */
    private static void bindFake(Object obj, IBinder fake) {
        if (obj == null) return;
        String[] names = {"mRemote", "mBinder", "mPM"};
        for (String name : names) {
            Field f = Reflect.findField(obj.getClass(), name);
            if (f == null) continue;
            try {
                if (f.get(obj) instanceof IBinder) {
                    f.set(obj, fake);
                    return;
                }
            } catch (Throwable ignored) {
            }
        }
        Log.w(TAG, "bindFake: no mRemote on " + obj.getClass().getName());
    }

    /**
     * 原版 replaceBinderFields：把 obj 内按 mServiceManager / mRemote / mSm / mRemote2
     * 命名的 IBinder 字段替换成 fake binder；字段值不是 IBinder、但属于 android.os.*
     * 类型时递归下钻替换（深度 +1，depth<=2 保护，字段值已是 fake 时跳过）。
     * 返回替换计数（含递归）。
     */
    private static int replaceBinderFields(Object obj, IBinder fake, int depth) {
        if (obj == null || depth > 2) return 0;
        String[] names = {"mServiceManager", "mRemote", "mSm", "mRemote2"};
        int count = 0;
        for (String name : names) {
            Field f = Reflect.findField(obj.getClass(), name);
            if (f == null) continue;
            try {
                Object v = f.get(obj);
                if (v == null || v == fake) continue;
                if (v instanceof IBinder) {
                    f.set(obj, fake);
                    count++;
                    Log.i(TAG, "smCacheHook: " + obj.getClass().getName() + " " + name + " -> fake");
                } else if (v.getClass().getName().startsWith("android.os.")) {
                    int n = replaceBinderFields(v, fake, depth + 1);
                    if (n > 0) {
                        Log.i(TAG, "smCacheHook: nested " + v.getClass().getName() + ": " + n);
                        count += n;
                    }
                }
            } catch (Throwable t) {
                Log.w(TAG, "smCacheHook: set " + name + " failed", t);
            }
        }
        return count;
    }

    /** D4 接线：用 FakeCache 替换 ServiceManager.sCache（原版 applyServiceManagerCacheHook）。 */
    private void applyServiceManagerCacheHook() {
        try {
            if (g_smFake == null) {
                Log.w(TAG, "smCacheHook: g_smFake null, skip");
                return;
            }
            Class<?> sm = Class.forName("android.os.ServiceManager");
            // 原版 smCacheHook 主路径：replaceBinderFields(sServiceManager 的值, fakeSm, 0)，
            // 把 SM 侧对象里的 binder 字段（mServiceManager/mRemote/mSm/mRemote2）指向
            // FakeSM；替换计数 <=0 时原版回退 dynamic proxy 方案（见下方 else 分支）。
            try {
                Field fsm = sm.getDeclaredField("sServiceManager");
                fsm.setAccessible(true);
                Object smObj = fsm.get(null);
                if (smObj == null) {
                    Log.w(TAG, "smCacheHook: sServiceManager null, skip");
                } else {
                    IBinder fakeSm = makeFakeSM();
                    if (fakeSm == null) {
                        Log.w(TAG, "smCacheHook: makeFakeSM null");
                    } else {
                        int n = replaceBinderFields(smObj, fakeSm, 0);
                        if (n > 0) {
                            Log.i(TAG, "smCacheHook: OK, replaced=" + n);
                        } else {
                            // 原版回退（AnonymousClass2）：replaceBinderFields 计数 <=0 时，
                            // 用 dynamic proxy 包一层 IServiceManager 静态写回 sServiceManager
                            try {
                                Class<?> ism = Class.forName("android.os.IServiceManager");
                                Object proxy = Proxy.newProxyInstance(ism.getClassLoader(),
                                        new Class<?>[]{ism},
                                        new SmFallbackHandler(smObj, g_smFake));
                                fsm.set(null, proxy);
                                Log.i(TAG, "smCacheHook: used dynamic proxy fallback");
                            } catch (Throwable t) {
                                Log.w(TAG, "smCacheHook: dynamic proxy failed", t);
                            }
                        }
                    }
                }
            } catch (Throwable unused) {
            }
            Field f = sm.getDeclaredField("sCache");
            f.setAccessible(true);
            Object cur = f.get(null);
            if (cur instanceof Map) {
                f.set(null, new FakeCache((Map<?, ?>) cur, g_smFake));
            }
        } catch (Throwable t) {
            Log.w(TAG, "smCacheHook failed", t);
        }
    }

    /**
     * 原版 clearPackageInfoCache（m100）：清空 PM 两级类的 sPackageInfoCache
     * 静态缓存（android.app.ApplicationPackageManager / android.content.pm.PackageManager），
     * 避免旧的 PackageInfo（带原签名）被缓存命中。每类异常吞掉。
     */
    private static void clearPackageInfoCache() {
        String[] names = {
                "android.app.ApplicationPackageManager",
                "android.content.pm.PackageManager"
        };
        for (String name : names) {
            try {
                Field f = Reflect.findField(Class.forName(name), "sPackageInfoCache");
                if (f == null) continue;
                Object v = f.get(null);
                if (v instanceof Map) {
                    ((Map<?, ?>) v).clear();
                    Log.i(TAG, "clearPackageInfoCache: cleared " + name + ".sPackageInfoCache");
                }
            } catch (Throwable ignored) {
            }
        }
    }

    /**
     * 原版 AnonymousClass2：dynamic proxy 回退的 InvocationHandler。
     * 先无条件转发给真实 IServiceManager；仅当 getService("package") 单参调用
     * 且 FakeBinder 就绪时，替换返回值为 FakeBinder（即 g_smFake）。
     */
    private static class SmFallbackHandler implements InvocationHandler {
        private final Object real;       // 原 sServiceManager 的值（真实 IServiceManager）
        private final IBinder pkgFake;   // g_smFake（伪装的 IPackageManager Binder）

        SmFallbackHandler(Object real, IBinder pkgFake) {
            this.real = real;
            this.pkgFake = pkgFake;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object result = method.invoke(real, args);
            if ("getService".equals(method.getName())
                    && args != null && args.length == 1
                    && "package".equals(args[0])
                    && pkgFake != null) {
                Log.i(TAG, "smCacheHook(dyn): return FakeBinder for package");
                return pkgFake;
            }
            return result;
        }
    }

    /** D4：创建伪装的 ServiceManager Binder（原版 makeFakeSM）。 */
    public static IBinder makeFakeSM() {
        try {
            Log.i(TAG, "makeFakeSM: called");
            return new FakeSM();
        } catch (Throwable unused) {
            return null;
        }
    }

    // ========================================================================
    // 核心 patch：按对象类型分发
    // ========================================================================
    private void patchByType(Object obj) {
        if (obj instanceof PackageInfo) {
            patchInfo((PackageInfo) obj);
        } else if (obj instanceof ApplicationInfo) {
            patchAppInfoSignatures((ApplicationInfo) obj);
        }
    }

    /**
     * 过签核心：把本包名的 PackageInfo 里的所有签名字段改写成原始签名。
     * 只处理 appPkg 自身，其它包原样放行。
     */
    private void patchInfo(PackageInfo pi) {
        if (pi == null) return;
        if (!appPkg.equals(pi.packageName)) return;   // 只 patch 自己

        Signature[] s = sigs();
        if (s == null) return;

        pi.signatures = s;                 // 旧字段：GET_SIGNATURES 用
        replaceSigningInfo(pi);            // 新字段：SigningInfo -> SigningDetails

        if (pi.applicationInfo != null) {
            patchAppInfoSignatures(pi.applicationInfo);
        }
        // 反侦察：抹掉 providers，避免泄露重打包后的组件信息
        try { pi.providers = null; } catch (Throwable ignored) {}
    }

    /**
     * 改写 PackageInfo.signingInfo（Android 9+ 的签名容器）。
     * SigningInfo 内部持有一个 SigningDetails 对象，真正存放签名数组的字段是
     * SigningDetails.signatures（老版本叫 mSignatures）。这里用 Unsafe 直接写。
     */
    private void replaceSigningInfo(PackageInfo pi) {
        try {
            SigningInfo si = pi.signingInfo;
            if (si == null) return;

            // 1) 拿到 SigningInfo.mSigningDetails
            Field detailsF = CACHE_SI_DETAILS;
            if (detailsF == null) {
                detailsF = Reflect.findField(SigningInfo.class, "mSigningDetails");
                CACHE_SI_DETAILS = detailsF;
            }
            Object details = (detailsF != null)
                    ? Reflect.uGet(si, detailsF)
                    : Reflect.fieldValue(si, "mSigningDetails");
            if (details == null) return;

            Signature[] s = sigs();

            // 2) 写 SigningDetails.signatures（缓存字段优先）
            Field sdSigs = CACHE_SD_SIGNATURES;
            if (sdSigs == null) {
                sdSigs = Reflect.findField(details.getClass(), "signatures");
                CACHE_SD_SIGNATURES = sdSigs;
            }
            if (sdSigs != null) Reflect.uSet(details, sdSigs, s);

            // 3) 兼容不同版本字段名：signatures / mSignatures 都尝试写一遍
            setNamedField(details, "signatures", s);
            setNamedField(details, "mSignatures", s);
        } catch (Throwable t) {
            Log.w(TAG, "replaceSigningInfo failed", t);
        }
    }

    /** 改写 ApplicationInfo.signatures（部分老流程读这里）。 */
    private void patchAppInfoSignatures(ApplicationInfo ai) {
        if (ai == null) return;
        try {
            Signature[] s = sigs();
            if (s == null) return;
            Field direct = Reflect.findField(ApplicationInfo.class, "signatures");
            if (direct != null) Reflect.uSet(ai, direct, s);
            Field cached = CACHE_AI_SIGNATURES;
            if (cached == null) {
                cached = direct;
                CACHE_AI_SIGNATURES = cached;
            }
            if (cached != null) Reflect.uSet(ai, cached, s);
        } catch (Throwable t) {
            Log.w(TAG, "patchAppInfoSignatures failed", t);
        }
    }

    private static void setNamedField(Object obj, String name, Object value) {
        Field f = Reflect.findField(obj.getClass(), name);
        if (f != null) Reflect.uSet(obj, f, value);
    }

    // ========================================================================
    // D3/D5 reply 改写协议
    //
    // 单对象（getPackageInfo / getApplicationInfo...）：
    //   reply = writeNoException + writeInt(1)（存在标志） + obj.writeToParcel
    // 列表（getInstalledPackages / getInstalledApplications...）：
    //   reply = writeNoException + ParceledListSlice.writeToParcel（无存在标志）
    //
    // 改写方式都是：把对象读出来 patch 后，写进一个新 Parcel，再整体替换 reply
    // （setDataPosition(0) + setDataSize(0) + appendFrom）。
    // ========================================================================

    /** D3：单对象 reply 改写 —— PackageInfo 版。 */
    private void rewritePackageInfoReply(Parcel reply) {
        Parcel tmp = null;
        try {
            reply.setDataPosition(0);
            if (reply.readInt() != 0) return;      // 异常码非 0：原样放行
            if (reply.readInt() == 0) return;      // 存在标志 0：没有对象
            PackageInfo pi = PackageInfo.CREATOR.createFromParcel(reply);
            if (pi == null || !appPkg.equals(pi.packageName)) return;
            patchInfo(pi);
            Log.i(TAG, "rewrite: PkgInfo pkg=" + pi.packageName);

            tmp = Parcel.obtain();
            tmp.writeNoException();
            tmp.writeInt(1);                       // 存在标志
            pi.writeToParcel(tmp, 0);
            replaceReply(reply, tmp);
        } catch (Throwable t) {
            Log.w(TAG, "rewritePackageInfoReply failed", t);
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        } finally {
            if (tmp != null) tmp.recycle();
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        }
    }

    /** D3：单对象 reply 改写 —— ApplicationInfo 版。 */
    private void rewriteApplicationInfoReply(Parcel reply) {
        Parcel tmp = null;
        try {
            reply.setDataPosition(0);
            if (reply.readInt() != 0) return;      // 异常码非 0：原样放行
            if (reply.readInt() == 0) return;      // 存在标志 0：没有对象
            ApplicationInfo ai = ApplicationInfo.CREATOR.createFromParcel(reply);
            if (ai == null || !appPkg.equals(ai.packageName)) return;
            patchAppInfoSignatures(ai);
            Log.i(TAG, "rewrite: AppInfo pkg=" + ai.packageName);

            tmp = Parcel.obtain();
            tmp.writeNoException();
            tmp.writeInt(1);                       // 存在标志
            ai.writeToParcel(tmp, 0);
            replaceReply(reply, tmp);
        } catch (Throwable t) {
            Log.w(TAG, "rewriteApplicationInfoReply failed", t);
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        } finally {
            if (tmp != null) tmp.recycle();
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        }
    }

    /** D5：列表 reply 改写（ParceledListSlice 协议）—— PackageInfo 列表版。 */
    private void rewritePackageInfoListReply(Parcel reply) {
        try {
            reply.setDataPosition(0);
            if (reply.readInt() != 0) return;      // 异常码非 0：原样放行
            Object slice = readParceledListSlice(reply);
            if (slice == null) return;
            List<?> list = plsGetList(slice);
            if (list == null) return;

            boolean changed = false;
            for (Object o : list) {
                if (o instanceof PackageInfo
                        && appPkg.equals(((PackageInfo) o).packageName)) {
                    patchInfo((PackageInfo) o);
                    changed = true;
                }
            }
            if (changed) writeParceledListSliceReply(reply, slice);
        } catch (Throwable t) {
            Log.w(TAG, "rewritePackageInfoListReply failed", t);
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        } finally {
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        }
    }

    /** D5：列表 reply 改写（ParceledListSlice 协议）—— ApplicationInfo 列表版。 */
    private void rewriteApplicationInfoListReply(Parcel reply) {
        try {
            reply.setDataPosition(0);
            if (reply.readInt() != 0) return;      // 异常码非 0：原样放行
            Object slice = readParceledListSlice(reply);
            if (slice == null) return;
            List<?> list = plsGetList(slice);
            if (list == null) return;

            boolean changed = false;
            for (Object o : list) {
                if (o instanceof ApplicationInfo
                        && appPkg.equals(((ApplicationInfo) o).packageName)) {
                    patchAppInfoSignatures((ApplicationInfo) o);
                    changed = true;
                }
            }
            if (changed) writeParceledListSliceReply(reply, slice);
        } catch (Throwable t) {
            Log.w(TAG, "rewriteApplicationInfoListReply failed", t);
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        } finally {
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        }
    }

    /** D5：从 reply 里读出 ParceledListSlice（隐藏类，走 CREATOR 反射）。 */
    private static Object readParceledListSlice(Parcel reply) {
        try {
            Object creator = CACHE_PLS_CREATOR;
            if (creator == null) {
                Class<?> cls = Class.forName("android.content.pm.ParceledListSlice");
                Field f = cls.getDeclaredField("CREATOR");
                f.setAccessible(true);
                creator = f.get(null);
                CACHE_PLS_CREATOR = creator;
            }
            Method m = creator.getClass().getMethod("createFromParcel", Parcel.class);
            m.setAccessible(true);
            return m.invoke(creator, reply);
        } catch (Throwable t) {
            return null;
        }
    }

    /** D5：反射取 ParceledListSlice 内部的 List（隐藏方法 getList()）。 */
    private static List<?> plsGetList(Object slice) {
        try {
            Method m = CACHE_PLS_GETLIST;
            if (m == null) {
                m = slice.getClass().getDeclaredMethod("getList");
                m.setAccessible(true);
                CACHE_PLS_GETLIST = m;
            }
            return (List<?>) m.invoke(slice);
        } catch (Throwable t) {
            return null;
        }
    }

    /**
     * D5：列表 reply 回写 —— writeNoException + slice.writeToParcel。
     * 注意与单对象协议不同：ParceledListSlice 版本没有 writeInt(1) 存在标志。
     */
    private static void writeParceledListSliceReply(Parcel reply, Object slice) {
        Parcel tmp = Parcel.obtain();
        try {
            Method m = slice.getClass().getMethod("writeToParcel", Parcel.class, int.class);
            m.setAccessible(true);
            tmp.writeNoException();
            m.invoke(slice, tmp, 0);
            replaceReply(reply, tmp);
        } catch (Throwable t) {
            try { reply.setDataPosition(0); } catch (Throwable ignored) {}
        } finally {
            tmp.recycle();
        }
    }

    /** reply 整体替换：清空原内容后把 tmp 的字节追加回来，读指针归零。 */
    private static void replaceReply(Parcel reply, Parcel tmp) {
        reply.setDataPosition(0);
        reply.setDataSize(0);
        reply.appendFrom(tmp, 0, tmp.dataSize());
        reply.setDataPosition(0);
    }

    // ========================================================================
    // D4 第二层 FakeCache / FakeSM
    // ========================================================================

    /** 第三层：替换 ServiceManager.sCache —— 复制原缓存，"package" 覆盖为 FakeBinder。 */
    private static class FakeCache extends HashMap<String, IBinder> {

        FakeCache(Map<?, ?> src, IBinder fakePms) {
            super();
            if (src != null) {
                for (Map.Entry<?, ?> e : src.entrySet()) {
                    if (e.getKey() instanceof String && e.getValue() instanceof IBinder) {
                        put((String) e.getKey(), (IBinder) e.getValue());
                    }
                }
            }
            // 关键：让 "package" 查询直接命中伪装的 IPackageManager
            put("package", fakePms);
        }
    }

    /** 第二层：伪装 ServiceManager Binder —— 转发真实 SM，把 "package" 的查询结果换成 FakeBinder。 */
    private static class FakeSM extends Binder {

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            Log.i(TAG, "FakeSM.onTransact: code=" + code);
            String svcName = readServiceName(data);
            Log.i(TAG, "FakeSM: svcName=" + svcName);

            // 先让真实的 ServiceManager 处理
            IBinder real = g_realSM;
            boolean ok = real != null && real.transact(code, data, reply, flags);

            // 只改 "package" 服务的查询结果：1=getService，4=checkService
            if ("package".equals(svcName) && reply != null) {
                try {
                    if (code == 1) {
                        patchGetService(reply);
                        Log.i(TAG, "FakeSM: patched getService");
                    } else if (code == 4) {
                        patchCheckService2(reply);
                        Log.i(TAG, "FakeSM: patched checkService2");
                    }
                } catch (Throwable t) {
                    Log.w(TAG, "FakeSM patch failed", t);
                    try { reply.setDataPosition(0); } catch (Throwable ignored) {}
                }
            }
            return ok;
        }
    }

    /**
     * 从 transact 的 data 里解析服务名（interface token 之后的第一个字符串）。
     * 原版 readServiceName 的混淆状态机在反编译时损坏，这里按
     * IServiceManager 事务布局（token + 服务名）做等价实现。
     */
    private static String readServiceName(Parcel data) {
        String first = null;
        String second = null;
        try {
            if (data == null) return null;
            int pos = data.dataPosition();
            data.setDataPosition(0);
            while (data.dataAvail() > 0 && second == null) {
                Object v = data.readValue(String.class.getClassLoader());
                if (v instanceof String) {
                    if (first == null) {
                        first = (String) v;      // interface descriptor
                    } else {
                        second = (String) v;     // 服务名
                    }
                }
            }
            data.setDataPosition(pos);
        } catch (Throwable unused) {
            return null;
        }
        return second != null ? second : first;
    }

    /** D4：getService("package") 的 reply 改写 —— 返回 g_smFake（FakeBinder）。 */
    private static void patchGetService(Parcel reply) {
        reply.setDataPosition(0);
        if (reply.readInt() != 0) {              // 异常码非 0：原样放行
            reply.setDataPosition(0);
            return;
        }
        Parcel tmp = Parcel.obtain();
        try {
            tmp.writeNoException();
            tmp.writeStrongBinder(g_smFake);
            replaceReply(reply, tmp);
        } finally {
            tmp.recycle();
            reply.setDataPosition(0);
        }
    }

    /** D4：checkService("package") 的 reply 改写 —— android.os.Service._value.service = g_smFake。 */
    private static void patchCheckService2(Parcel reply) throws Exception {
        try {
            reply.setDataPosition(0);
            if (reply.readInt() != 0) return;    // 异常码非 0：原样放行

            Class<?> cls = Class.forName("android.os.Service");
            Field creatorF = cls.getDeclaredField("CREATOR");
            creatorF.setAccessible(true);
            Parcelable.Creator<?> creator = (Parcelable.Creator<?>) creatorF.get(null);
            Object obj = reply.readTypedObject(creator);
            if (obj == null) return;

            Field valueF = cls.getDeclaredField("_value");
            valueF.setAccessible(true);
            Object value = valueF.get(obj);
            if (value != null) {
                Field svcF = value.getClass().getDeclaredField("service");
                svcF.setAccessible(true);
                svcF.set(value, g_smFake);
            } else {
                Log.w(TAG, "patchCheckService2: _value null (tag?)");
            }

            Parcel tmp = Parcel.obtain();
            try {
                tmp.writeNoException();
                tmp.writeTypedObject((Parcelable) obj, 0);
                replaceReply(reply, tmp);
            } finally {
                tmp.recycle();
            }
        } finally {
            reply.setDataPosition(0);
        }
    }

    // ========================================================================
    // D4 第一层 FakeBinder：伪装 IPackageManager
    //   转发真实 PMS 后按事务码改写 reply（D2/D3/D5）
    // ========================================================================
    private class FakeBinder extends Binder {

        private final IBinder target;   // 真实的 IPackageManager Binder

        FakeBinder(IBinder target) {
            this.target = target;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (target == null) return false;
            // 先让真实 PMS 处理，拿到真实 reply
            boolean ok = target.transact(code, data, reply, flags);
            if (!ok || reply == null) return ok;

            try {
                if (code == TX_getPackageInfo
                        || code == TX_getPackageInfoVersioned
                        || code == TX_getPackageArchiveInfo) {
                    // reply 就是裸 PackageInfo（VersionedPackage 只出现在请求参数里）
                    rewritePackageInfoReply(reply);
                } else if (code == TX_getApplicationInfo
                        || code == TX_getApplicationInfoVersioned) {
                    rewriteApplicationInfoReply(reply);
                } else if (code == TX_getInstalledPackages) {
                    rewritePackageInfoListReply(reply);
                } else if (code == TX_getInstalledApplications) {
                    rewriteApplicationInfoListReply(reply);
                } else if (code == TX_hasSigningCertificate) {
                    // D2：hasSigningCertificate 强制返回 true
                    reply.setDataPosition(4);   // 跳过 writeNoException 写的异常码
                    reply.writeInt(1);
                }
            } catch (Throwable t) {
                Log.w(TAG, "onTransact patch failed", t);
                try { reply.setDataPosition(0); } catch (Throwable ignored) {}
            }
            reply.setDataPosition(0);
            return true;
        }
    }

    // ========================================================================
    // soCrypt：AES/CTR/NoPadding 解密被加固加密的 libyan.so
    //   key = SHA256("YTSOKEY1" + pkg) 的前 16 字节
    //   iv  = SHA256("YTSOIV01" + pkg) 的前 16 字节
    // （这是加载被隐藏 native 库用的，与过签本身独立，一并保留）
    // ========================================================================
    public static byte[] soCrypt(byte[] data, String pkg) throws Exception {
        java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
        md.update("YTSOKEY1".getBytes("UTF-8"));
        md.update(pkg.getBytes("UTF-8"));
        byte[] key = md.digest();
        md.reset();
        md.update("YTSOIV01".getBytes("UTF-8"));
        md.update(pkg.getBytes("UTF-8"));
        byte[] iv = md.digest();

        int len = 16;
        javax.crypto.spec.SecretKeySpec sks = new javax.crypto.spec.SecretKeySpec(key, 0, len, "AES");
        javax.crypto.spec.IvParameterSpec ivps = new javax.crypto.spec.IvParameterSpec(iv, 0, len);
        javax.crypto.Cipher c = javax.crypto.Cipher.getInstance("AES/CTR/NoPadding");
        c.init(javax.crypto.Cipher.DECRYPT_MODE, sks, ivps);
        return c.doFinal(data);
    }

    // 从加固资产里抽出原始 APK（`_.o`）到 filesDir，返回其绝对路径。
    private static String extractOrigApk(Context ctx) {
        try {
            ApplicationInfo ai = ctx.getApplicationInfo();
            ZipFile zip = new ZipFile(ai.sourceDir);
            ZipEntry e = zip.getEntry(ORIG_ENTRY);
            if (e == null) { zip.close(); return null; }
            java.io.File out = new java.io.File(ctx.getFilesDir(), "orig.apk");
            InputStream in = zip.getInputStream(e);
            java.io.FileOutputStream fos = new java.io.FileOutputStream(out);
            byte[] buf = new byte[8192];
            int n;
            while ((n = in.read(buf)) > 0) fos.write(buf, 0, n);
            fos.close(); in.close(); zip.close();
            return out.getAbsolutePath();
        } catch (Throwable t) {
            return null;
        }
    }
}
