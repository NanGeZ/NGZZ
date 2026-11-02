package com.seedy.hook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.DataInputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Seedy 过签运行时载荷（免 root）。
 *
 * 由过签处理注入目标 APK：`_.s` 存原始签名证书，assets/hook.dex 为本载荷。
 * 运行于目标应用进程内，安装三层 hook：
 *   FakeCache —— 替换 ServiceManager.sCache 的 "package" 项；
 *   FakeSM    —— 替换 ActivityThread.sPackageManager / PackageManager.mPM
 *                内部缓存的 IBinder；
 *   FakeBinder—— 伪装 IPackageManager：转发真实 PMS 后把「本包名」的
 *                signatures / SigningInfo 改写为原始证书，
 *                hasSigningCertificate 强制返回 true。
 * 只改写自己包名的返回结果，不影响其它应用。
 */
public final class SigHook {

    private static final String TAG = "SeedyHook";
    private static final String SIG_ENTRY = "_.s";

    private static String sAppPkg;
    private static Signature[] sOrigSigs;

    // 反射缓存
    private static Field sSiDetailsField;    // SigningInfo.mSigningDetails
    private static Field sSdSignaturesField; // SigningDetails.signatures
    private static Object sPlsCreator;       // ParceledListSlice.CREATOR
    private static Method sPlsGetList;       // ParceledListSlice.getList()

    // 事务码（反射读取失败用 AOSP 默认值兜底）
    private static int TX_getPackageInfo = 3;
    private static int TX_getPackageInfoVersioned = 4;
    private static int TX_getInstalledPackages = 13;
    private static int TX_getInstalledApplications = 14;
    private static int TX_hasSigningCertificate = 106;

    private SigHook() {}

    /** 入口：尽早调用（Application.attachBaseContext / ContentProvider.onCreate）。 */
    public static void entry(Context context) {
        try {
            sAppPkg = context.getPackageName();
            Signature[] sigs = loadSignatures(context);
            if (sigs == null || sigs.length == 0) {
                Log.w(TAG, "entry: no " + SIG_ENTRY + " data, hook skipped");
                return;
            }
            sOrigSigs = sigs;
            resolveTxCodes();
            resolveReflectCaches();
            install(context);
            Log.i(TAG, "entry: ok pkg=" + sAppPkg + " sigs=" + sigs.length);
        } catch (Throwable t) {
            Log.w(TAG, "entry failed", t);
        }
    }

    // ------------------------------------------------------------ `_.s` 读取
    // 格式：1 字节 count + 每条 { int len; byte[len] cert }

    private static Signature[] loadSignatures(Context ctx) {
        try {
            ApplicationInfo ai = ctx.getApplicationInfo();
            ZipFile zip = new ZipFile(ai.sourceDir);
            try {
                ZipEntry e = zip.getEntry(SIG_ENTRY);
                if (e == null) return null;
                DataInputStream in = new DataInputStream(zip.getInputStream(e));
                int count = in.readUnsignedByte();
                if (count <= 0) return null;
                Signature[] out = new Signature[count];
                for (int i = 0; i < count; i++) {
                    int len = in.readInt();
                    if (len <= 0 || len > 16 * 1024) return null;
                    byte[] der = new byte[len];
                    in.readFully(der);
                    out[i] = new Signature(der);
                }
                return out;
            } finally {
                zip.close();
            }
        } catch (Throwable t) {
            Log.w(TAG, "loadSignatures failed", t);
            return null;
        }
    }

    // ------------------------------------------------------- 反射缓存初始化

    private static void resolveTxCodes() {
        try {
            Class<?> stub = Class.forName("android.content.pm.IPackageManager$Stub");
            TX_getPackageInfo = readTx(stub, "TRANSACTION_getPackageInfo", TX_getPackageInfo);
            TX_getPackageInfoVersioned = readTx(stub, "TRANSACTION_getPackageInfoVersioned", TX_getPackageInfoVersioned);
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

    private static void resolveReflectCaches() {
        try {
            Class<?> si = Class.forName("android.content.pm.SigningInfo");
            sSiDetailsField = si.getDeclaredField("mSigningDetails");
            sSiDetailsField.setAccessible(true);
        } catch (Throwable ignored) {
        }
        for (String name : new String[]{
                "android.content.pm.SigningDetails",
                "android.content.pm.PackageParser$SigningDetails"}) {
            try {
                Class<?> sd = Class.forName(name);
                sSdSignaturesField = sd.getDeclaredField("signatures");
                sSdSignaturesField.setAccessible(true);
                break;
            } catch (Throwable ignored) {
            }
        }
        try {
            Class<?> pls = Class.forName("android.content.pm.ParceledListSlice");
            Field creator = pls.getDeclaredField("CREATOR");
            creator.setAccessible(true);
            sPlsCreator = creator.get(null);
            sPlsGetList = pls.getDeclaredMethod("getList");
        } catch (Throwable ignored) {
        }
    }

    // ------------------------------------------------------------- hook 安装

    private static void install(Context ctx) {
        IBinder realPms = getServiceBinder("package");
        if (realPms == null) {
            Log.w(TAG, "install: real PMS binder not found");
            return;
        }
        IBinder fakePms = new FakeBinder(realPms);

        // FakeSM 层：替换进程内缓存的 IPackageManager 底层 IBinder
        try {
            Class<?> at = Class.forName("android.app.ActivityThread");
            Method cur = at.getDeclaredMethod("currentActivityThread");
            cur.setAccessible(true);
            bindFake(Reflect.fieldValue(cur.invoke(null), "sPackageManager"), fakePms);
            bindFake(Reflect.fieldValue(ctx.getPackageManager(), "mPM"), fakePms);
        } catch (Throwable t) {
            Log.w(TAG, "bindFake failed", t);
        }

        // FakeCache 层：替换 ServiceManager.sCache 的 "package" 项
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            Field f = sm.getDeclaredField("sCache");
            f.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, IBinder> cache = (Map<String, IBinder>) f.get(null);
            if (cache != null) cache.put("package", fakePms);
        } catch (Throwable t) {
            Log.w(TAG, "sCache hook failed", t);
        }

        clearPackageInfoCache();
    }

    /** 从 ServiceManager 取真实 "package" 服务 Binder。 */
    private static IBinder getServiceBinder(String name) {
        try {
            Class<?> sm = Class.forName("android.os.ServiceManager");
            Method m = sm.getDeclaredMethod("getService", String.class);
            m.setAccessible(true);
            return (IBinder) m.invoke(null, name);
        } catch (Throwable t) {
            return null;
        }
    }

    /** 把代理对象内部缓存的 IBinder 字段替换成 FakeBinder。 */
    private static void bindFake(Object proxy, IBinder fake) {
        if (proxy == null) return;
        Class<?> c = proxy.getClass();
        while (c != null && c != Object.class) {
            for (Field f : c.getDeclaredFields()) {
                if (IBinder.class.isAssignableFrom(f.getType())) {
                    try {
                        f.setAccessible(true);
                        if (f.get(proxy) != null) f.set(proxy, fake);
                    } catch (Throwable ignored) {
                    }
                }
            }
            c = c.getSuperclass();
        }
    }

    /** 清空 PMS 进程内 PackageInfo 缓存，避免改写结果被旧缓存污染。 */
    private static void clearPackageInfoCache() {
        try {
            Class<?> at = Class.forName("android.app.ActivityThread");
            Method cur = at.getDeclaredMethod("currentActivityThread");
            cur.setAccessible(true);
            Object atObj = cur.invoke(null);
            Field sPackages = at.getDeclaredField("sPackages");
            sPackages.setAccessible(true);
            clearCacheMap(sPackages.get(null));
            Field sResourcePackages = at.getDeclaredField("sResourcePackages");
            sResourcePackages.setAccessible(true);
            clearCacheMap(sResourcePackages.get(null));
        } catch (Throwable ignored) {
        }
    }

    private static void clearCacheMap(Object maybeMap) {
        if (maybeMap instanceof Map) {
            ((Map<?, ?>) maybeMap).values().forEach(v -> {
                if (v instanceof Map) ((Map<?, ?>) v).clear();
            });
        }
    }

    // ----------------------------------------------------- FakeBinder 代理

    private static final class FakeBinder extends Binder {
        private final IBinder real;

        FakeBinder(IBinder real) {
            this.real = real;
        }

        @Override
        protected boolean onTransact(int code, Parcel data, Parcel reply, int flags)
                throws android.os.RemoteException {
            // 强制通过：应用自查签名证书一律视为匹配
            if (code == TX_hasSigningCertificate && reply != null) {
                reply.setDataPosition(0);
                reply.setDataSize(0);
                reply.setDataPosition(0);
                reply.writeInt(0);   // 无异常
                reply.writeInt(1);   // 强制返回 true
                return true;
            }

            data.setDataPosition(0);
            boolean ok = real.transact(code, data, reply, flags);
            if (ok && reply != null) {
                if (code == TX_getPackageInfo || code == TX_getPackageInfoVersioned) {
                    patchPackageInfoReply(reply);
                } else if (code == TX_getInstalledPackages || code == TX_getInstalledApplications) {
                    patchListReply(reply);
                }
            }
            return ok;
        }
    }

    // ------------------------------------------------------------ reply 改写

    private static void patchPackageInfoReply(Parcel reply) {
        try {
            reply.setDataPosition(0);
            int ex = reply.readInt();
            if (ex != 0) {
                reply.setDataPosition(0);
                return;
            }
            PackageInfo pi = PackageInfo.CREATOR.createFromParcel(reply);
            if (pi == null || !sAppPkg.equals(pi.packageName)) {
                reply.setDataPosition(0);
                return;
            }
            patchPackageInfo(pi);
            writeReply(reply, pi);
        } catch (Throwable t) {
            Log.w(TAG, "patchPackageInfoReply failed", t);
            reply.setDataPosition(0);
        }
    }

    /** getInstalledPackages / getInstalledApplications：ParceledListSlice 内逐项改写。 */
    @SuppressWarnings("unchecked")
    private static void patchListReply(Parcel reply) {
        try {
            if (sPlsCreator == null || sPlsGetList == null) return;
            reply.setDataPosition(0);
            int ex = reply.readInt();
            if (ex != 0) {
                reply.setDataPosition(0);
                return;
            }
            Object slice = ((Parcelable.Creator<Object>) sPlsCreator).createFromParcel(reply);
            if (slice == null) {
                reply.setDataPosition(0);
                return;
            }
            List<Object> list = (List<Object>) sPlsGetList.invoke(slice);
            if (list == null) {
                reply.setDataPosition(0);
                return;
            }
            boolean patched = false;
            for (Object item : list) {
                if (item instanceof PackageInfo) {
                    PackageInfo pi = (PackageInfo) item;
                    if (sAppPkg.equals(pi.packageName)) {
                        patchPackageInfo(pi);
                        patched = true;
                    }
                }
            }
            if (patched) writeReply(reply, (Parcelable) slice);
            else reply.setDataPosition(0);
        } catch (Throwable t) {
            Log.w(TAG, "patchListReply failed", t);
            reply.setDataPosition(0);
        }
    }

    /** 用改写后的对象重写 reply：writeNoException + writeTypedObject。 */
    private static void writeReply(Parcel reply, Parcelable patched) {
        reply.setDataPosition(0);
        reply.setDataSize(0);
        reply.setDataPosition(0);
        reply.writeInt(0);
        reply.writeTypedObject(patched, Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
    }

    /** 把 PackageInfo 的签名信息改写为原始证书。 */
    private static void patchPackageInfo(PackageInfo pi) {
        Signature[] forged = sOrigSigs.clone();
        pi.signatures = forged;
        try {
            Object si = Reflect.fieldValue(pi, "signingInfo");
            if (si != null && sSiDetailsField != null && sSdSignaturesField != null) {
                Object details = sSiDetailsField.get(si);
                if (details != null) sSdSignaturesField.set(details, forged.clone());
            }
        } catch (Throwable ignored) {
        }
    }
}
