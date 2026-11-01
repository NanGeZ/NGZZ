package com.yan.hook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.util.Log;

import java.io.DataInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
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
 *  2) hook 系统服务 ServiceManager，让它返回的 IPackageManager 被替换成
 *     FakeBinder（本类的 Binder 代理）。
 *  3) FakeBinder 拦截所有会返回「本应用签名」的 PMS 事务
 *     (getPackageInfo / getInstalledPackages / getInstalledApplications /
 *      getApplicationInfo...)，把返回 Parcel 里本包名对应的
 *     PackageInfo.signatures / SigningInfo / ApplicationInfo.signatures
 *     全部改写成 origSigs。
 *
 * 结果：无论 APK 被重签成什么证书，凡是「应用自查签名」（很多加固/支付/DRM
 * 用 getPackageInfo(GET_SIGNING_CERTIFICATES) 或 hasSigningCertificate 做
 * 自校验）拿到的都是原始证书 → 自校验通过，即所谓「过签」。
 *
 * 【为什么只有本包名才 patch】
 * patchInfo 里严格判断 appPkg.equals(packageInfo.packageName)，只改自己，
 * 避免影响系统里其它应用的签名，降低被察觉的概率。
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

    // 缓存的反射 Field（不同 Android 版本字段名不同，反射解析一次后缓存）
    private static Field CACHE_SI_DETAILS;    // SigningInfo.mSigningDetails
    private static Field CACHE_SD_SIGNATURES; // SigningDetails.signatures
    private static Field CACHE_AI_SIGNATURES; // ApplicationInfo.signatures

    // ServiceManager 的假 Binder（返回 FakeBinder 的中间层）
    private static IBinder g_smFake;

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
            p.install();
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
    //     int count
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
            int count = in.readInt();
            if (count <= 0 || count > 64) return null;
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
    // ServiceManager hook：把返回的 IPackageManager 换成 FakeBinder
    // ========================================================================
    private void install() {
        resolveTxCodes();
        try {
            // 真实 IPackageManager Binder：由 ServiceManager hook 取到后注入
            IBinder realPms = getRealPackageBinder();
            g_smFake = new FakeBinder(realPms).getFakeServiceManager();
        } catch (Throwable t) {
            Log.w(TAG, "install failed", t);
        }
    }

    /** 占位：从 ServiceManager 拿到真实的 "package" 服务 Binder。 */
    private static IBinder getRealPackageBinder() {
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            java.lang.reflect.Method m = sm.getDeclaredMethod("getService", String.class);
            m.setAccessible(true);
            return (IBinder) m.invoke(null, "package");
        } catch (Throwable t) {
            return null;
        }
    }

    /** 给上层用：拿到被替换后的 ServiceManager Binder。 */
    public static IBinder makeFakeSM() {
        return g_smFake;
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
    // FakeBinder：伪装成 IPackageManager，拦截返回本应用签名的事务并改写 reply
    // ========================================================================
    private class FakeBinder implements IBinder {

        private final IBinder real;   // 真实的 IPackageManager Binder（hook 时注入）

        FakeBinder(IBinder real) {
            this.real = real;
        }

        IBinder getFakeServiceManager() {
            // 由 ServiceManager hook 调用；返回一个代理 package 服务的 Binder。
            return this;
        }

        // 注意：IBinder 接口本身没有 onTransact；真实框架里它是 Binder 基类的
        // protected 方法。这里作为内部调度入口，由 transact() 转发进来。
        public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            if (real == null) return false;
            // 先让真实 PMS 处理，拿到真实 reply
            boolean ok = real.transact(code, data, reply, flags);
            if (!ok || reply == null) return ok;

            try {
                reply.setDataPosition(0);
                if (code == TX_getPackageInfo
                        || code == TX_getPackageArchiveInfo) {
                    patchOne(reply, PackageInfo.CREATOR);
                } else if (code == TX_getPackageInfoVersioned) {
                    // VersionedPackage 包了一层，内部仍带 PackageInfo
                    patchOne(reply, PackageInfo.CREATOR);
                } else if (code == TX_getApplicationInfo
                        || code == TX_getApplicationInfoVersioned) {
                    patchOne(reply, ApplicationInfo.CREATOR);
                } else if (code == TX_getInstalledPackages
                        || code == TX_getInstalledApplications) {
                    patchList(reply);
                }
            } catch (Throwable t) {
                Log.w(TAG, "onTransact patch failed", t);
                try { reply.setDataPosition(0); } catch (Throwable ignored) {}
            }
            reply.setDataPosition(0);
            return true;
        }

        /** 单对象 reply：读出 -> patch -> 原地重写。 */
        private void patchOne(Parcel reply, Parcelable.Creator<?> creator) {
            int pos = reply.dataPosition();
            Object obj = reply.readValue(creator.getClass().getClassLoader());
            if (obj == null) { reply.setDataPosition(pos); return; }
            patchByType(obj);
            // 用新对象覆盖：重置到对象起点后写回
            reply.setDataPosition(pos);
            reply.setDataSize(pos);
            reply.writeValue(obj);
            reply.setDataPosition(0);
        }

        /** 列表 reply（ParceledListSlice / List）：遍历每个元素分别 patch。 */
        private void patchList(Parcel reply) {
            int pos = reply.dataPosition();
            Object obj = reply.readValue(PackageInfo.class.getClassLoader());
            if (obj == null) { reply.setDataPosition(pos); return; }
            if (obj instanceof java.util.List) {
                for (Object o : (java.util.List<?>) obj) patchByType(o);
            } else {
                patchByType(obj);
            }
            reply.setDataPosition(pos);
            reply.setDataSize(pos);
            reply.writeValue(obj);
            reply.setDataPosition(0);
        }

        // ---- IBinder 其余方法全部委托给真实 Binder ----
        @Override public String getInterfaceDescriptor() throws RemoteException {
            return real != null ? real.getInterfaceDescriptor() : null;
        }
        @Override public boolean pingBinder() { return real != null && real.pingBinder(); }
        @Override public boolean isBinderAlive() { return real != null && real.isBinderAlive(); }
        @Override public IInterface queryLocalInterface(String d) { return null; }
        @Override public void dump(java.io.FileDescriptor fd, String[] args) {}
        @Override public void dumpAsync(java.io.FileDescriptor fd, String[] args) {}
        @Override public boolean transact(int code, Parcel data, Parcel reply, int flags)
                throws RemoteException {
            return onTransact(code, data, reply, flags);
        }
        @Override public void linkToDeath(DeathRecipient r, int flags) {}
        @Override public boolean unlinkToDeath(DeathRecipient r, int flags) { return true; }
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
