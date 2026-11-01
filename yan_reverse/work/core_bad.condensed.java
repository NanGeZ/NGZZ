package com.yan.hook;

import android.app.Application;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.Signature;
import android.content.pm.SigningInfo;
import android.database.Cursor;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.lsposed.hiddenapibypass.library.C0003;
import org.lsposed.hiddenapibypass.library.C0004;

/* loaded from: /workspace/yan_reverse/work/assets/hook.dex */
public class PmsHookProvider extends ContentProvider {
    private static Field CACHE_AI_SIGNATURES;
    private static Object CACHE_PLS_CREATOR;
    private static Method CACHE_PLS_GETLIST;
    private static Field CACHE_SD_SIGNATURES;
    private static Field CACHE_SI_DETAILS;
    private static final boolean DEBUG = false;
    private static IBinder g_realSM;
    private static IBinder g_smFake;
    private String appPkg = C0001.m184();
    private String origApkPath;
    private Signature[] origSigs;

    /* renamed from: short, reason: not valid java name */
    private static final short[] f0short = {1657, 1544, 1608, 1545, 1967, 2014, 1951, 1450, 1499, 1414, 3299, 3292, 3307, 3299, 3297, 3322, 3307, 825, 790, 829, 826, 816, 817, 806, 1341, 1280, 1309, 2597, 2606, 2601, 2595, 2561, 2598, 2604, 2594, 2685, 2663, 783, 1145, 1067, 1084, 1065, 1077, 1080, 1082, 1084, 1085, 1058, 1036, 1085, 1066, 1070, 1083, 1056, 1085, 1084, 3042, 3049, 3059, 3061, 3054, 3042, 3060, 1010, 1017, 995, 997, 1006, 941, 951, 999, 1020, 1008, 938, 460, 387, 414, 389, 395, 465, 1112, 1035, 1041, 1055, 1035, 1093, 691, 680, 689, 689, 2164, 2161, 2170, 2145, 2169, 2166, 2156, 2167, 2167, 2164, 2508, 2493, 2557, 2492, 3100, 3181, 3116, 2563, 2620, 2571, 2563, 2561, 2586, 2571, 472, 442, 413, 407, 409, 446, 405, 402, 408, 409, 398, 3154, 3120, 3095, 3101, 3091, 3125, 3095, 3093, 3102, 3091, 875, 890, 879, 883, 855, 882, 872, 879, 1746, 1747, 1742, 1779, 1754, 1747, 1755, 1747, 1752, 1730, 1733, 942, 943, 946, 908, 931, 934, 943, 3133, 3116, 3118, 3110, 3116, 3114, 3112, 2328, 2335, 2335, 2331, 2378, 2384, 2306, 2325, 2321, 2332, 2381, 1538, 1599, 1570, 721, 726, 726, 722, 665, 733, 726, 727, 732, 791, 849, 854, 860, 850, 778, 3107, 3091, 3121, 3123, 3128, 3125, 1673, 1784, 1701, 2278, 2300, 2290, 2299, 2292, 2273, 2272, 2279, 2288, 2278, 634, 619, 636, 632, 621, 630, 619, 2714, 2739, 2724, 2729, 2736, 2720, 2715, 2701, 2714, 2718, 2689, 2699, 2701, 2700, 2719, 2688, 2779, 2777, 2752, 2715, 2773, 2700, 3009, 3026, 3021, 3013, 3009, 3010, 3017, 2957, 3030, 2967, 3009, 1791, 1727, 1713, 1752, 1713, 1715, 557, 567, 569, 560, 575, 554, 555, 556, 571, 557, 1534, 1472, 1530, 1524, 1533, 1522, 1511, 1510, 1505, 1526, 1504, 2563, 2578, 2565, 2561, 2580, 2575, 2578, 2937, 2939, 2922, 2898, 2935, 2925, 2922, 1513, 1528, 1530, 1522, 1528, 1534, 1532, 738, 751, 764, 738, 684, 675, 687, 679, 767, 1578, 1557, 1570, 1578, 1576, 1587, 1570, 1645, 1619, 1645, 2002, 2029, 2010, 2002, 2000, 1995, 2010, 1933, 2169, 3195, 3190, 3173, 3195, 3133, 3130, 3120, 3134, 2052, 2114, 2117, 2125, 2120, 2113, 2112, 3017, 2971, 2956, 2969, 2949, 2952, 2954, 2956, 2957, 3028, 3293, 3276, 3291, 3295, 3274, 3281, 3276, 782, 788, 794, 787, 796, 777, 776, 783, 792, 782, 1120, 1118, 1124, 1130, 1123, 1132, 1145, 1144, 1151, 1128, 1150, 2561, 2586, 2563, 2563, 2276, 2303, 2294, 2202, 2181, 2178, 2177, 1479, 1478, 1492, 1471, 1450, 316, 312, 302, 1736, 1747, 1759, 1688, 1750, 1741, 1748, 1748, 2580, 2582, 2567, 2618, 2589, 2567, 1538, 1536, 1553, 1578, 1543, 1551, 1536, 1542, 1553, 480, 482, 499, 456, 485, 493, 482, 484, 499, 1910, 1907, 1906, 1865, 1892, 1900, 1891, 1893, 1906, 1021, 1016, 1017, 975, 994, 994, 993, 1000, 1004, 995, 840, 845, 844, 881, 854, 844, 298, 303, 302, 277, 312, 304, 319, 313, 302, 2221, 2225, 2236, 2188, 2231, 2218, 2232, 2239, 2236};
    private static final String NATIVE_DIR = C0002.m191(m19(), 0, C0003.f12 ^ 681, 1574);
    private static final String ORIG_ASSET = C0001."_.o";
    private static final String SIG_ASSET = C0003."_.s";
    private static final String TAG = "yantool";
    private static final String DIR_NAME = ".cache.optimized";
    private static final String C_SIGNING_DETAILS = "android.content.pm.SigningDetails";
    private static final String C_SIGNING_INFO = "android.content.pm.SigningInfo";
    private static final String C_PARCELED_LIST = "android.content.pm.ParceledListSlice";
    private static final String C_ACTIVITY_THREAD = "android.app.ActivityThread";
    private static final String C_APP_PKG_MANAGER = "android.app.ApplicationPackageManager";
    private static final String C_PKG_MANAGER = "android.content.pm.PackageManager";
    private static final String C_PM_STUB = "android.content.pm.IPackageManager$Stub";
    private static final String C_PM_PROXY = "android.content.pm.IPackageManager$Stub$Proxy";
    private static final String C_CREATOR = "android.os.Parcelable$Creator";
    private static final String C_SERVICE = "android.os.Service";
    private static final String C_SM = "android.os.ServiceManager";
    private static final String C_SM_PROXY = "android.os.ServiceManagerProxy";
    private static final String C_ISM = "android.os.IServiceManager";
    private static final String C_ANDROID_OS = "android.os.";
    private static final String C_HAB = "org.lsposed.hiddenapibypass.HiddenApiBypass";
    private static final String C_UNSAFE = "sun.misc.Unsafe";
    private static final String C_BDCL = "dalvik.system.BaseDexClassLoader";
    private static final String C_DEX_PATH_LIST = "dalvik.system.DexPathList";
    private static final String C_DEX_ELEMENT = "dalvik.system.DexPathList$Element";
    private static final String C_DEX_FILE = "dalvik.system.DexFile";
    private static final String C_BOOT_CL = "java.lang.BootClassLoader";
    private static final String C_SELF = "com.yan.hook.PmsHookProvider";
    private static final String PM_PROXY_CLASS = m4();
    private static final String SELF_CLASS = m35();
    private static int TX_getPackageInfo = 3;
    private static int TX_getPackageInfoVersioned = 4;
    private static int TX_getApplicationInfo = 9;
    private static int TX_getApplicationInfoVersioned = 10;
    private static int TX_getPackageArchiveInfo = -1;
    private static int TX_getInstalledPackages = -1;
    private static int TX_getInstalledApplications = -1;
    private static int TX_hasSigningCertificate = -1;
    private static int DUMP_SM = 0;
    private static final HashMap<Field, Long> OFFSET_CACHE = new HashMap<>();
    private static boolean nativeLoaded = false;

    /* renamed from: com.yan.hook.PmsHookProvider$0, reason: invalid class name */
    public final /* synthetic */ class AnonymousClass0 {
        public static /* synthetic */ int m(Method method) {
            return C0003.m300(method).length;

    private final class FakeBinder extends Binder {
        private final IBinder target;
        final PmsHookProvider this$0;

        FakeBinder(PmsHookProvider pmsHookProvider, IBinder iBinder) {
            this.this$0 = pmsHookProvider;
            this.target = iBinder;
            int m274 = C0003.m274();
                            return;
                            return;

        /* renamed from: ۣ۟ۡ۠ۨ, reason: not valid java name and contains not printable characters */
        public static int m112() {
            int m138 = C0000.m138();
                            return PmsHookProvider.m55();
                                    return 0;

        /* renamed from: ۟ۡۥۤۦ, reason: not valid java name and contains not printable characters */
        public static int m113() {
            int m316 = C0004.m316();
                            return PmsHookProvider.m90();
                                    return 0;

        /* renamed from: ۟ۤۧۧۡ, reason: not valid java name and contains not printable characters */
        public static void m114(Object obj, Object obj2) {
            int m188 = C0002.m188();
                            PmsHookProvider.access$3((PmsHookProvider) obj, (Parcel) obj2);
                            break;
                                    break;
            int m274 = C0003.m274();
                    i3 = m274 <= 0 ? 1833 : 1802;
                        return;
                        return;

        /* renamed from: ۟ۥ۠ۥۧ, reason: not valid java name and contains not printable characters */
        public static int m115() {
            int m138 = C0000.m138();
                            return PmsHookProvider.m12();
                                    return 0;

        /* renamed from: ۟ۥۣۣۡ, reason: not valid java name and contains not printable characters */
        public static void m116(Object obj, Object obj2) {
            int m316 = C0004.m316();
                            PmsHookProvider.m75((PmsHookProvider) obj, (Parcel) obj2);
                            break;
                                    break;
            int m143 = C0001.m143();
                    i3 = m143 <= 0 ? 1833 : 1802;
                        return;
                        return;

        /* renamed from: ۟ۦۧۤۨ, reason: not valid java name and contains not printable characters */
        public static void m117(Object obj, Object obj2) {
            int m316 = C0004.m316();
                            PmsHookProvider.m21((PmsHookProvider) obj, (Parcel) obj2);
                            return;
                                    return;

        /* renamed from: ۟ۧۤۡۦ, reason: not valid java name and contains not printable characters */
        public static IBinder m118(Object obj) {
            int m188 = C0002.m188();
                            return ((FakeBinder) obj).target;
                                    return null;

        /* renamed from: ۠ۥۣۤ, reason: not valid java name and contains not printable characters */
        public static int m119() {
            int m316 = C0004.m316();
                            return PmsHookProvider.m83();
                                    return 0;

        /* renamed from: ۡۧۡۧ, reason: not valid java name and contains not printable characters */
        public static int m120() {
            int m188 = C0002.m188();
                            return PmsHookProvider.m46();
                                    return 0;

        /* renamed from: ۣۣ۠ۡ, reason: not valid java name and contains not printable characters */
        public static int m121() {
            int m188 = C0002.m188();
                            return PmsHookProvider.m49();
                                    return 0;

        /* renamed from: ۣۦۨۢ, reason: not valid java name and contains not printable characters */
        public static PmsHookProvider m122(Object obj) {
            int m188 = C0002.m188();
                            return ((FakeBinder) obj).this$0;
                                    return null;

        /* renamed from: ۣۧۦۣ, reason: not valid java name and contains not printable characters */
        public static void m123(Object obj, Object obj2) {
            int m274 = C0003.m274();
                            PmsHookProvider.access$8((PmsHookProvider) obj, (Parcel) obj2);
                            return;
                                    return;

        /* renamed from: ۤۢۡۢ, reason: not valid java name and contains not printable characters */
        public static int m124() {
            int m138 = C0000.m138();
                            return PmsHookProvider.m27();
                                    return 0;

        /* renamed from: ۧۥۨ۠, reason: not valid java name and contains not printable characters */
        public static int m125() {
            int m316 = C0004.m316();
                            return PmsHookProvider.m77();
                                    return 0;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r14v10 */
        /* JADX WARN: Type inference failed for: r14v2 */
        /* JADX WARN: Type inference failed for: r14v3 */
        /* JADX WARN: Type inference failed for: r14v4 */
        /* JADX WARN: Type inference failed for: r14v6 */
        /* JADX WARN: Type inference failed for: r14v7 */
        /* JADX WARN: Type inference failed for: r14v9 */
        /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:172:0x020a -> B:69:0x0211). Please report as a decompilation issue!!! */
        @Override // android.os.Binder
        protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            int m125;
            int i3;
            boolean m272 = C0003.m272(m118(this), i, parcel, parcel2, i2);
                                    i5 = parcel2 != 0 ? 1833 : 1802;
                                        break;
                                        try {
                                            try {
                                                m125 = m125();
                                                C0004.m325(parcel2, i6);
                                                    parcel2 = 53651;
                                                break;
                                                return m272;
                                                    i3 = i != m125 ? 48736 : 48705;
                                                        break;
                                                        int m120 = m120();
                                                                    break;
                                                                    int m124 = m124();
                                                                                break;
                                                                                    int m119 = m119();
                                                                                            i11 = i != m119 ? 49759 : 49728;
                                                                                                int m115 = m115();
                                                                                                            break;
                                                                                                            int m112 = m112();
                                                                                                                    i13 = i == m112 ? 50658 : 50627;
                                                                                                                        int m113 = m113();
                                                                                                                                    int m121 = m121();
                                                                                                                                                        C0004.m325(parcel2, 0);
                                                                                                                                                        int m337 = C0004.m337(parcel2);
                                                                                                                                                                i6 = m337 == 0 ? 52580 : 52549;
                                                                                                                                                                        C0004.m325(parcel2, C0001.f5 ^ (-583));
                                                                                                                                                                        C0004.m344(parcel2, 1);
                                                                                                                                                                            parcel2 = 23;
                                                                                                                                        m117(m122(this), parcel2);
                                                                                                                                            parcel2 = 51605;
                                                                                                                                        break;
                                                                                                                                        break;
                                                                                                                        m123(m122(this), parcel2);
                                                                                                                            parcel2 = 19;
                                                                                                                        break;
                                                                                                            if (i != m115) {
                                                                                                break;
                                                                                    m116(m122(this), parcel2);
                                                                                        parcel2 = 52752;
                                                                                    break;
                                                                                    break;
                                                                    if (i != m120) {
                                            m114(m122(this), parcel2);
                                                parcel2 = 53527;
                                                    break;
                                            break;
                                            break;
                            break;
            return m272;

    private static final class FakeCache extends HashMap<String, IBinder> {

        /* renamed from: short, reason: not valid java name */
        private static final short[] f2short = {3153, 3136, 3138, 3146, 3136, 3142, 3140};

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0026, code lost:
        
            r0 = (java.util.Map.Entry) org.lsposed.hiddenapibypass.library.C0004.m329(r8);
            r1 = com.yan.hook.C0002.m195(r0) instanceof java.lang.String;
            r5 = 1864;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0034, code lost:
        
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0036, code lost:
        
            if (r5 == 17) goto L81;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x008f, code lost:
        
            if (r1 == false) goto L84;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x0091, code lost:
        
            r5 = 48736;
         */
        /* JADX WARN: Code restructure failed: missing block: B:32:0x0095, code lost:
        
            r5 = 48705;
         */
        /* JADX WARN: Code restructure failed: missing block: B:36:0x003b, code lost:
        
            if (r5 == 47384) goto L67;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x0040, code lost:
        
            if (r5 == 47417) goto L63;
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x0045, code lost:
        
            if (r5 == 47483) goto L80;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x0048, code lost:
        
            r1 = com.yan.hook.C0002.m225(r0) instanceof android.os.IBinder;
            r4 = 48767;
         */
        /* JADX WARN: Code restructure failed: missing block: B:45:0x0051, code lost:
        
         */
        /* JADX WARN: Code restructure failed: missing block: B:46:0x0055, code lost:
        
            if (r4 == 14) goto L89;
         */
        /* JADX WARN: Code restructure failed: missing block: B:48:0x0059, code lost:
        
            if (r4 == 45) goto L68;
         */
        /* JADX WARN: Code restructure failed: missing block: B:50:0x005d, code lost:
        
            if (r4 == 76) goto L65;
         */
        /* JADX WARN: Code restructure failed: missing block: B:51:0x005f, code lost:
        
            if (r4 == 239) goto L90;
         */
        /* JADX WARN: Code restructure failed: missing block: B:53:0x0062, code lost:
        
            if (r1 == false) goto L91;
         */
        /* JADX WARN: Code restructure failed: missing block: B:55:0x008b, code lost:
        
            r4 = 48829;
         */
        /* JADX WARN: Code restructure failed: missing block: B:59:0x0064, code lost:
        
            r4 = 48860;
         */
        /* JADX WARN: Code restructure failed: missing block: B:63:0x0068, code lost:
        
            super.put((java.lang.String) com.yan.hook.C0002.m195(r0), (android.os.IBinder) com.yan.hook.C0002.m225(r0));
            r0 = 48891;
         */
        /* JADX WARN: Code restructure failed: missing block: B:64:0x007a, code lost:
        
         */
        /* JADX WARN: Code restructure failed: missing block: B:65:0x0080, code lost:
        
            if (r0 == 22) goto L69;
         */
        /* JADX WARN: Code restructure failed: missing block: B:67:0x0084, code lost:
        
            if (r0 == 503) goto L97;
         */
        /* JADX WARN: Code restructure failed: missing block: B:69:0x0087, code lost:
        
            r0 = 48922;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        FakeCache(Map<?, ?> map, IBinder iBinder) {
            Iterator m266 = C0003.m266(C0003.m260(map));
            loop0: while (true) {
                boolean m320 = C0004.m320(m266);
                                break loop0;
                                break;
            super.put(C0001.m169(m126(), 0, C0000.f4 ^ 80, 3105), iBinder);
            int m138 = C0000.m138();
                    i2 = m138 >= 0 ? 1833 : 1802;
                        return;
                        return;

        /* renamed from: ۣ۟ۦ۟ۨ, reason: not valid java name and contains not printable characters */
        public static short[] m126() {
            int m274 = C0003.m274();
                            return f2short;
                                    return null;

    private static final class FakeClassLoader extends ClassLoader {
        FakeClassLoader() {
            super(null);
            int m143 = C0001.m143();
                            return;
                            return;

        /* renamed from: ۟ۡۥۣۧ, reason: not valid java name and contains not printable characters */
        public static String m127() {
            int m138 = C0000.m138();
                            return PmsHookProvider.m36();
                                    return null;

        public String getName() {
            return m127();

    public static class FakeSM extends Binder {

        /* renamed from: short, reason: not valid java name */
        private static final short[] f3short = {2431, 2348, 2358, 2341, 2362, 2402, 2921, 2936, 2938, 2930, 2936, 2942, 2940};

        public FakeSM() {
            int m188 = C0002.m188();
                            return;
                            return;

        /* renamed from: ۣ۟۟ۤۧ, reason: not valid java name and contains not printable characters */
        public static IBinder m128() {
            int m188 = C0002.m188();
                            return PmsHookProvider.m45();
                                    return null;

        /* renamed from: ۣ۟۟ۢ۟, reason: not valid java name and contains not printable characters */
        public static String m129(Object obj) {
            int m138 = C0000.m138();
                            return PmsHookProvider.m59((Parcel) obj);
                                    return null;

        /* renamed from: ۟ۧۤۨۥ, reason: not valid java name and contains not printable characters */
        public static void m130(Object obj) {
            int m138 = C0000.m138();
                            PmsHookProvider.m81((Parcel) obj);
                            return;
                                    return;

        /* renamed from: ۢ۠۠, reason: not valid java name and contains not printable characters */
        public static void m131(Object obj) {
            int m138 = C0000.m138();
                            PmsHookProvider.m69((Parcel) obj);
                            break;
                                    break;
            int m316 = C0004.m316();
                    i3 = m316 >= 0 ? 1833 : 1802;
                        return;
                        return;

        /* renamed from: ۢۡۥۡ, reason: not valid java name and contains not printable characters */
        public static void m132(Object obj) {
            int m274 = C0003.m274();
                            PmsHookProvider.m52((String) obj);
                            break;
                                    break;
            int m138 = C0000.m138();
                    i3 = m138 >= 0 ? 1833 : 1802;
                        return;
                        return;

        /* renamed from: ۤۢۥۤ, reason: not valid java name and contains not printable characters */
        public static String m133(Object obj) {
            int m316 = C0004.m316();
                            return PmsHookProvider.m1((int[]) obj);
                                    return null;

        /* renamed from: ۦۤ۟ۢ, reason: contains not printable characters */
        public static void m134(Object obj, Object obj2) {
            int m188 = C0002.m188();
                            PmsHookProvider.m9((String) obj, (Throwable) obj2);
                            break;
                                    break;
            int m138 = C0000.m138();
                    i3 = m138 >= 0 ? 1833 : 1802;
                        return;
                        return;

        /* renamed from: ۧۤۤۧ, reason: not valid java name and contains not printable characters */
        public static short[] m135() {
            int m316 = C0004.m316();
                            return f3short;
                                    return null;

        /* renamed from: ۨۧۤ, reason: not valid java name and contains not printable characters */
        public static IBinder m136() {
            int m274 = C0003.m274();
                            return PmsHookProvider.m11();
                                    return null;

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0067, code lost:
        
            r3 = m129(r18);
            r5 = org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m133(new int[]{70, 97, 107, 101, 83, 77, 58, 32, 115, 118, 99, 78, 97, 109, 101, 61}))), r3), org.lsposed.hiddenapibypass.library.C0003.m285(m135(), 0, com.yan.hook.C0000.f4 ^ 81, 2399));
            r6 = 48767;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0098, code lost:
        
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x009c, code lost:
        
            if (r6 == 14) goto L213;
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x009e, code lost:
        
            if (r6 == 45) goto L214;
         */
        /* JADX WARN: Code restructure failed: missing block: B:25:0x00a0, code lost:
        
            if (r6 == 76) goto L211;
         */
        /* JADX WARN: Code restructure failed: missing block: B:26:0x00a2, code lost:
        
            if (r6 == 239) goto L212;
         */
        /* JADX WARN: Code restructure failed: missing block: B:28:0x00a5, code lost:
        
            if (r18 == null) goto L215;
         */
        /* JADX WARN: Code restructure failed: missing block: B:300:0x00c3, code lost:
        
            r6 = -1;
         */
        /* JADX WARN: Code restructure failed: missing block: B:30:0x027b, code lost:
        
            r6 = 48829;
         */
        /* JADX WARN: Code restructure failed: missing block: B:34:0x00a7, code lost:
        
            r6 = 48860;
         */
        /* JADX WARN: Code restructure failed: missing block: B:38:0x00ab, code lost:
        
            r6 = org.lsposed.hiddenapibypass.library.C0004.m335(r18);
            r7 = 48891;
         */
        /* JADX WARN: Code restructure failed: missing block: B:39:0x00b2, code lost:
        
         */
        /* JADX WARN: Code restructure failed: missing block: B:40:0x00b8, code lost:
        
            if (r7 == 22) goto L221;
         */
        /* JADX WARN: Code restructure failed: missing block: B:42:0x00bc, code lost:
        
            if (r7 == 503) goto L222;
         */
        /* JADX WARN: Code restructure failed: missing block: B:44:0x00bf, code lost:
        
            r7 = 48922;
         */
        /* JADX WARN: Code restructure failed: missing block: B:49:0x00c4, code lost:
        
            m132(org.lsposed.hiddenapibypass.library.C0003.m281(com.yan.hook.C0001.m167(r5, r6)));
         */
        @Override // android.os.Binder
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        protected boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            String str;
            boolean z;
            String m133;
            m132(C0003.m281(C0001.m167(new StringBuilder(C0002.m204(m133(new int[]{70, 97, 107, 101, 83, 77, 46, 111, 110, 84, 114, 97, 110, 115, 97, 99, 116, 58, 32, 99, 111, 100, 101, 61}))), i)));
                                    i4 = i != 1 ? 1833 : 1802;
                                        break;
                                                break;
                                        str = null;
                            break;
            String str2 = str;
            IBinder m128 = m128();
                    i6 = m128 != null ? 49759 : 49728;
                        boolean m272 = C0003.m272(m128, i, parcel, parcel2, i2);
                                    break;
                                            break;
                                    z = true;
                                    if (m272) {
                        break;
            z = false;
                    i9 = z ? 50782 : 50751;
                        break;
                                        break;
                                            IBinder m136 = m136();
                                                    i11 = m136 != null ? 51681 : 51650;
                                                        break;
                                                        boolean m200 = C0002.m200(C0004.m323(m135(), 6, C0004.f13 ^ (-366), 2841), str2);
                                                                        break;
                                                                                i13 = i == 4 ? 52580 : 52549;
                                                                                try {
                                                                                                    break;
                                                                                                        m131(parcel2);
                                                                                                        m133 = m133(new int[]{70, 97, 107, 101, 83, 77, 58, 32, 112, 97, 116, 99, 104, 101, 100, 32, 103, 101, 116, 83, 101, 114, 118, 105, 99, 101});
                                                                                                        break;
                                                                                        m130(parcel2);
                                                                                        m133 = m133(new int[]{70, 97, 107, 101, 83, 77, 58, 32, 112, 97, 116, 99, 104, 101, 100, 32, 99, 104, 101, 99, 107, 83, 101, 114, 118, 105, 99, 101, 50});
                                                                                                break;
                                                                                    m134(m133(new int[]{70, 97, 107, 101, 83, 77, 32, 112, 97, 116, 99, 104, 32, 102, 97, 105, 108, 101, 100}), th);
                                                                                    try {
                                                                                        C0004.m325(parcel2, 0);
                                                                                                break;
                                                                        m132(m133);
                                                                                break;
            return z;

    public PmsHookProvider() {
        int m188 = C0002.m188();
                        return;
                        return;

    /* JADX WARN: Code restructure failed: missing block: B:22:0x001a, code lost:
    
        r0[r1] = (char) r5[r1];
        r1 = r1 + (com.yan.hook.C0001.f5 ^ (-580));
        r2 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0026, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x002a, code lost:
    
        if (r2 == 17) goto L37;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0031, code lost:
    
        r2 = 1771;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x002e, code lost:
    
        if (r2 == 54) goto L26;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String S(int... iArr) {
        char[] cArr = new char[iArr.length];
            int length = iArr.length;
                            return new String(cArr);
                            break;

    static /* synthetic */ void access$3(PmsHookProvider pmsHookProvider, Parcel parcel) {
        m3(pmsHookProvider, parcel);
        int m274 = C0003.m274();
                        return;
                        return;

    static /* synthetic */ void access$8(PmsHookProvider pmsHookProvider, Parcel parcel) {
        m88(pmsHookProvider, parcel);
        int m274 = C0003.m274();
                        return;
                        return;

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0019, code lost:
    
        r0 = m11();
        r1 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x001f, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0023, code lost:
    
        if (r1 == 17) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x005c, code lost:
    
        if (r0 == null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x005e, code lost:
    
        r1 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0061, code lost:
    
        r1 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0027, code lost:
    
        if (r1 == 54) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x002b, code lost:
    
        if (r1 == 471) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x002f, code lost:
    
        if (r1 == 500) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0032, code lost:
    
        m31(org.lsposed.hiddenapibypass.library.C0004.m342(m20()));
        m52("applyCtxHookNative: done");
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x004b, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x004d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x004f, code lost:
    
        if (r0 == 17) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0057, code lost:
    
        r0 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0054, code lost:
    
        if (r0 == 47483) goto L65;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:?, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void applyCtxHookNative() {
        try {
            IBinder m45 = m45();
                            break;
                            return;
            m9("applyCtxHookNative failed", th);

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00de, code lost:
    
        r3 = m22(r2, r3, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x00e3, code lost:
    
        r6 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00e6, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00ea, code lost:
    
        if (r6 == 17) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x018c, code lost:
    
        if (r3 <= 0) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x018e, code lost:
    
        r6 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0193, code lost:
    
        r6 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x00ee, code lost:
    
        if (r6 == 50) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00f0, code lost:
    
        if (r6 == 76) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x00f4, code lost:
    
        if (r6 == 83) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x00f7, code lost:
    
        r3 = org.lsposed.hiddenapibypass.library.C0004.m342(m57());
        m14(r1, com.yan.hook.C0001.m185(com.yan.hook.C0001.m147(r3), new java.lang.Class[]{r3}, new com.yan.hook.PmsHookProvider.AnonymousClass2(r10, r2, m11())));
        m52("smCacheHook: used dynamic proxy fallback");
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0126, code lost:
    
        r0 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0129, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x012f, code lost:
    
        if (r0 == 497) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0136, code lost:
    
        r0 = 50596;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0133, code lost:
    
        if (r0 == 1711) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x013a, code lost:
    
        r1 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x013d, code lost:
    
        m9("smCacheHook: dynamic proxy failed", r1);
        r0 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x014c, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0152, code lost:
    
        if (r0 != 19) goto L180;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0157, code lost:
    
        r0 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0154, code lost:
    
        if (r0 != 50) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x015b, code lost:
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(com.yan.hook.C0001.m167(new java.lang.StringBuilder(com.yan.hook.C0002.m204("smCacheHook: OK, replaced=")), r3)));
        r1 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x017d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0181, code lost:
    
        if (r1 == 18) goto L184;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0185, code lost:
    
        if (r1 == 241) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0188, code lost:
    
        r1 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x001d, code lost:
    
        r1 = m51(org.lsposed.hiddenapibypass.library.C0004.m342(m99()), "sServiceManager");
        r2 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0036, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0038, code lost:
    
        if (r2 == 17) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x01a2, code lost:
    
        if (r1 != null) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x01a4, code lost:
    
        r2 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x01a9, code lost:
    
        r2 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x003d, code lost:
    
        if (r2 == 47384) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0042, code lost:
    
        if (r2 == 47417) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0047, code lost:
    
        if (r2 == 47483) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x004a, code lost:
    
        m52("smCacheHook: no sServiceManager field");
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0058, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0059, code lost:
    
        r2 = m78(r1);
        r6 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0060, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0064, code lost:
    
        if (r6 == 14) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x006a, code lost:
    
        if (r6 == 45) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x006c, code lost:
    
        if (r6 == 76) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x006e, code lost:
    
        if (r6 == 239) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0071, code lost:
    
        if (r2 != null) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x019d, code lost:
    
        r6 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0073, code lost:
    
        r6 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0077, code lost:
    
        m52("smCacheHook: sServiceManager null, skip");
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0085, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0086, code lost:
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("smCacheHook: sServiceManager class=")), com.yan.hook.C0002.m223(org.lsposed.hiddenapibypass.library.C0003.m271(r2)))));
        r3 = com.yan.hook.C0002.m201();
        r4 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00b4, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00ba, code lost:
    
        if (r4 == 22) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00be, code lost:
    
        if (r4 == 53) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00c2, code lost:
    
        if (r4 == 503) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00d8, code lost:
    
        if (r3 != null) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0198, code lost:
    
        r4 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00da, code lost:
    
        r4 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00c6, code lost:
    
        if (r4 == 32495) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00c9, code lost:
    
        m52("smCacheHook: makeFakeSM null");
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00d7, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void applyServiceManagerCacheHook() {
        try {
            IBinder m11 = m11();
                            m52("smCacheHook: g_smFake null, skip");
                            int m143 = C0001.m143();
                                    i2 = m143 <= 0 ? 1833 : 1802;
                                        return;
                                        return;
                            break;
            m9("smCacheHook failed", th);

    /* JADX WARN: Code restructure failed: missing block: B:103:0x00d1, code lost:
    
        if (r9 == 50) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x00d3, code lost:
    
        if (r9 == 76) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x00d7, code lost:
    
        if (r9 == 83) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x0157, code lost:
    
        r10 = r6 + (com.yan.hook.C0002.f6 ^ (-481));
        r6 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0160, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x0166, code lost:
    
        if (r6 == 497) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0170, code lost:
    
        r6 = 50596;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x016a, code lost:
    
        if (r6 == 1711) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x00da, code lost:
    
        r14 = org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m191(m19(), 27, com.yan.hook.C0001.f5 ^ (-585), 2631)), com.yan.hook.C0002.m223(org.lsposed.hiddenapibypass.library.C0003.m271(r14))), org.lsposed.hiddenapibypass.library.C0004.m323(m19(), 37, 1, 801)), r7), com.yan.hook.C0001.m169(m19(), 38, com.yan.hook.C0001.f5 ^ (-588), 1113));
        r15 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0127, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x012d, code lost:
    
        if (r15 == 18) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0131, code lost:
    
        if (r15 == 241) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0134, code lost:
    
        r15 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x007c, code lost:
    
        r7 = new java.lang.String[]{r0, r4, r5}[r6];
        r9 = m51(org.lsposed.hiddenapibypass.library.C0003.m271(r14), r7);
        r10 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x008d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0091, code lost:
    
        if (r10 == 14) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0095, code lost:
    
        if (r10 == 45) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0099, code lost:
    
        if (r10 == 76) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x009b, code lost:
    
        if (r10 == 239) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x009e, code lost:
    
        if (r9 == null) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0174, code lost:
    
        r10 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00a0, code lost:
    
        r10 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00a4, code lost:
    
        r10 = m47(r14, r9) instanceof android.os.IBinder;
        r12 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00ad, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x00b3, code lost:
    
        if (r12 == 22) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00b7, code lost:
    
        if (r12 == 53) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00bb, code lost:
    
        if (r12 == 503) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x014b, code lost:
    
        if (r10 == false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0152, code lost:
    
        r12 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x014d, code lost:
    
        r12 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00bf, code lost:
    
        if (r12 == 32495) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00c2, code lost:
    
        r12 = m15(r14, r9, r15);
        r9 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00c9, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00cd, code lost:
    
        if (r9 == 17) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0140, code lost:
    
        if (r12 == false) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0142, code lost:
    
        r9 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0146, code lost:
    
        r9 = 49728;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void bindFake(Object obj, IBinder iBinder) {
        int i;
        StringBuilder m336;
                        int m188 = C0002.m188();
                                i3 = m188 >= 0 ? 1833 : 1802;
                                    return;
                                    return;
                        String m169 = C0001."mRemote";
                        String m191 = C0002.m191(m19(), 17, C0000.f4 ^ 80, 852);
                        String m1912 = C0002."mPM";
                        loop1: while (true) {
                                    i5 = i4 >= 3 ? 48736 : 48705;
                                        break;
                                        m336 = C0004.m336(new StringBuilder(C0002.m204("bindFake: no mRemote on ")), C0002.m223(C0003.m271(obj)));
                                        break loop1;
                            i4 = i;
                        m52(C0003.m281(m336));
                        return;

    /* JADX WARN: Code restructure failed: missing block: B:100:0x008f, code lost:
    
        if (r6 == 53) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0093, code lost:
    
        if (r6 == 503) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00dd, code lost:
    
        if (r5 == false) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x00e3, code lost:
    
        r6 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x00df, code lost:
    
        r6 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0097, code lost:
    
        if (r6 == 32495) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x009a, code lost:
    
        com.yan.hook.C0002.m189((java.util.Map) r4);
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("clearPackageInfoCache: cleared ")), r3), ".sPackageInfoCache")));
        r3 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00ce, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x00d2, code lost:
    
        if (r3 == 17) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x00d9, code lost:
    
        r3 = 49697;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x00d6, code lost:
    
        if (r3 == 50) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x00e7, code lost:
    
        r6 = com.yan.hook.C0001.m150(r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x00eb, code lost:
    
        r7 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x00ee, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x00f2, code lost:
    
        if (r7 == 18) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x00f6, code lost:
    
        if (r7 == 241) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x00f9, code lost:
    
        r7 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x001e, code lost:
    
        r3 = new java.lang.String[]{r0, r1}[r2];
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0024, code lost:
    
        r6 = org.lsposed.hiddenapibypass.library.C0004.m342(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0028, code lost:
    
        r7 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x002a, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x002e, code lost:
    
        if (r7 == 17) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0110, code lost:
    
        if (r6 == null) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0112, code lost:
    
        r7 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0116, code lost:
    
        r7 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0032, code lost:
    
        if (r7 == 54) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0036, code lost:
    
        if (r7 == 471) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x003a, code lost:
    
        if (r7 == 500) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x003d, code lost:
    
        r7 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x003f, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0041, code lost:
    
        if (r7 == 17) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0104, code lost:
    
        if (r6 == java.lang.Object.class) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0106, code lost:
    
        r7 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x010b, code lost:
    
        r7 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0046, code lost:
    
        if (r7 == 47384) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x004b, code lost:
    
        if (r7 == 47417) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0050, code lost:
    
        if (r7 == 47483) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0053, code lost:
    
        r7 = m51(r6, "sPackageInfoCache");
        r9 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0063, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0067, code lost:
    
        if (r9 == 14) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x006d, code lost:
    
        if (r9 == 45) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0071, code lost:
    
        if (r9 == 76) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0073, code lost:
    
        if (r9 == 239) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0076, code lost:
    
        if (r7 == null) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00fd, code lost:
    
        r9 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0078, code lost:
    
        r9 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x007c, code lost:
    
        r4 = m78(r7);
        r5 = r4 instanceof java.util.Map;
        r6 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0085, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x008b, code lost:
    
        if (r6 == 22) goto L147;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void clearPackageInfoCache() {
        String m53 = m53();
        String m5 = m5();
                            return;
                            break;
        i += C0000.f4 ^ 86;

    /* JADX WARN: Code restructure failed: missing block: B:100:0x00bc, code lost:
    
        r4 = 48922;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x00cd, code lost:
    
        com.yan.hook.C0002.m205(r8, new java.lang.Object[]{android.content.pm.PackageInfo.class, android.content.pm.ApplicationInfo.class}[r9]);
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x00d0, code lost:
    
        r11 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x00d3, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x00d7, code lost:
    
        if (r11 == 17) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x00de, code lost:
    
        r11 = 49697;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x00db, code lost:
    
        if (r11 == 50) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0033, code lost:
    
        r4 = new java.lang.String[]{r0, r1}[r3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0039, code lost:
    
        r8 = m51(android.os.Parcel.class, r4);
        r9 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0041, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0045, code lost:
    
        if (r9 == 17) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x010c, code lost:
    
        if (r8 == null) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x010e, code lost:
    
        r9 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0112, code lost:
    
        r9 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0049, code lost:
    
        if (r9 == 54) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x004d, code lost:
    
        if (r9 == 471) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0051, code lost:
    
        if (r9 == 500) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0054, code lost:
    
        r8 = m78(r8);
        r9 = r8 instanceof java.util.Map;
        r11 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005c, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005e, code lost:
    
        if (r11 == 17) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0100, code lost:
    
        if (r9 == false) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0102, code lost:
    
        r11 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0107, code lost:
    
        r11 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0063, code lost:
    
        if (r11 == 47384) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0068, code lost:
    
        if (r11 == 47417) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x006d, code lost:
    
        if (r11 == 47483) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0070, code lost:
    
        r8 = (java.util.Map) r8;
        r9 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0073, code lost:
    
        r11 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0076, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x007a, code lost:
    
        if (r11 == 14) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x007e, code lost:
    
        if (r11 == 45) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0082, code lost:
    
        if (r11 == 76) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0084, code lost:
    
        if (r11 == 239) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0087, code lost:
    
        if (r9 < 2) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00fb, code lost:
    
        r11 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0089, code lost:
    
        r11 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x008d, code lost:
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("clearParcelCreators(selective): ")), r4)));
        r4 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00af, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00b5, code lost:
    
        if (r4 == 22) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00b9, code lost:
    
        if (r4 == 503) goto L142;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void clearParcelCreators() {
        String m323 = C0004.m323(m19(), 47, C0000.f4 ^ 94, 1103);
        String m1 = "sPairedCreators";
                            return;
                            break;
        int i3 = i3 + (C0000.f4 ^ 86);
        i += C0004.f13 ^ (-364);

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0052, code lost:
    
        r8 = (java.util.Enumeration) r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0054, code lost:
    
        r0 = org.lsposed.hiddenapibypass.library.C0003.m263(r8);
        r4 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005a, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x005c, code lost:
    
        if (r4 == 17) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x009a, code lost:
    
        if (r0 == false) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x009c, code lost:
    
        r4 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00a0, code lost:
    
        r4 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0061, code lost:
    
        if (r4 == 47384) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0066, code lost:
    
        if (r4 == 47417) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x006b, code lost:
    
        if (r4 == 47483) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x006e, code lost:
    
        r0 = com.yan.hook.C0002.m200(m73(), com.yan.hook.C0001.m145(r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x007a, code lost:
    
        r4 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x007d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0081, code lost:
    
        if (r4 == 14) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0085, code lost:
    
        if (r4 == 45) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0089, code lost:
    
        if (r4 == 76) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x008b, code lost:
    
        if (r4 == 239) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x008e, code lost:
    
        if (r0 == false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0096, code lost:
    
        r4 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0090, code lost:
    
        r4 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0094, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean containsSelf(Object obj) throws Exception {
                        break;
                        try {
                            Object m293 = C0003.m293(C0003.m295(C0004.m342(m23()), C0001.m169(m19(), 56, C0000.f4 ^ 80, 2951), new Class[0]), obj, new Object[0]);
                            boolean z = m293 instanceof Enumeration;
                                    i2 = !z ? 1833 : 1802;
                                        break;
                                        return false;
        return false;

    /* JADX WARN: Removed duplicated region for block: B:120:0x0164 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x012e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0129 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0141  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void entry(Context context) {
        int i;
        int i2;
        int i3;
        int i4;
        int i5;
        Object m169;
        int i6;
        try {
            PmsHookProvider pmsHookProvider = new PmsHookProvider();
            pmsHookProvider.appPkg = C0002.m207(context);
            pmsHookProvider.origApkPath = m34(pmsHookProvider, context);
            Signature[] m72 = m72(pmsHookProvider, context);
            StringBuilder m336 = C0004.m336(C0004.m336(C0004.m336(C0004.m336(new StringBuilder(C0003.m285(m19(), 63, C0000.f4 ^ 92, 919)), m80(pmsHookProvider)), C0002.m191(m19(), 74, C0003.f12 ^ 683, 492)), m74(pmsHookProvider)), C0001." sigs=");
                            m169 = C0001.m169(m19(), 86, C0003.f12 ^ 681, 733);
                                    break;
                            m169 = C0003.m292(m72.length);
                            break;
            m52(C0003.m281(C0001.m173(m336, m169)));
                    i9 = m72 != null ? 48736 : 48705;
                        break;
                        int length = m72.length;
                                        pmsHookProvider.origSigs = m72;
                                        m92(pmsHookProvider, context);
                                        break;
                                        if (length > 0) {
                                    break;
            m29(pmsHookProvider, context);
            try {
                try {
                    break;
                    m17();
                int m188 = C0002.m188();
                try {
                        if (i != i2) {
                            if (i == i3) {
                                break;
            break;
        m17();
                break;
        int m1882 = C0002.m188();
                    return;
                    return;
                    if (m1882 >= 0) {

    /* JADX WARN: Code restructure failed: missing block: B:102:0x0097, code lost:
    
        if (r0 == 76) goto L255;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x009b, code lost:
    
        if (r0 == 83) goto L271;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x009e, code lost:
    
        com.yan.hook.C0002.m227(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x00a1, code lost:
    
        r0 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x00a4, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x00a8, code lost:
    
        if (r0 == 18) goto L271;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x00aa, code lost:
    
        if (r0 == 241) goto L332;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x00ad, code lost:
    
        r0 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x00e2, code lost:
    
        r9 = new java.io.FileOutputStream(r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x00e9, code lost:
    
        r11 = new byte[8192];
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x00eb, code lost:
    
        r12 = org.lsposed.hiddenapibypass.library.C0003.m291(r8, r11);
        r13 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x00f2, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x00f9, code lost:
    
        if (r13 == 19) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x00fb, code lost:
    
        if (r13 == 45) goto L337;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x00fd, code lost:
    
        if (r13 == 50) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x00ff, code lost:
    
        if (r13 == 76) goto L336;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x0103, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m324(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0106, code lost:
    
        r1 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0109, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x010d, code lost:
    
        if (r1 == 18) goto L342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x010f, code lost:
    
        if (r1 == 53) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0113, code lost:
    
        if (r1 == 243) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x012f, code lost:
    
        if (r8 == null) goto L344;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x015e, code lost:
    
        r1 = 50875;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0131, code lost:
    
        r1 = 51557;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0117, code lost:
    
        if (r1 == 4075) goto L263;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x011a, code lost:
    
        com.yan.hook.C0002.m227(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x011d, code lost:
    
        r1 = 51588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0120, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0124, code lost:
    
        if (r1 == 17) goto L350;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x012b, code lost:
    
        r1 = 51619;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0128, code lost:
    
        if (r1 == 54) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x0184, code lost:
    
        r13 = 50751;
     */
    /* JADX WARN: Code restructure failed: missing block: B:223:0x0163, code lost:
    
        com.yan.hook.C0001.m148(r9, r11, 0, r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x0166, code lost:
    
        r5 = 52611;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x0169, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:226:0x016f, code lost:
    
        if (r5 == 23) goto L366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x0179, code lost:
    
        r5 = 52642;
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x0173, code lost:
    
        if (r5 == 54) goto L365;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x017d, code lost:
    
        if (r12 > 0) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x017f, code lost:
    
        r13 = 50782;
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0189, code lost:
    
        r0 = 52735;
     */
    /* JADX WARN: Code restructure failed: missing block: B:241:0x018c, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x0190, code lost:
    
        if (r0 != 14) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x0194, code lost:
    
        if (r0 != 1007) goto L374;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x0197, code lost:
    
        r0 = 52766;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0031, code lost:
    
        r8 = org.lsposed.hiddenapibypass.library.C0003.m277(r7, r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0035, code lost:
    
        r9 = com.yan.hook.C0002.m212(r20);
        r12 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x003b, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x003d, code lost:
    
        if (r12 == 17) goto L288;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x004a, code lost:
    
        if (r12 == 47384) goto L287;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x004f, code lost:
    
        if (r12 == 47417) goto L289;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0054, code lost:
    
        if (r12 == 47483) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:380:0x019b, code lost:
    
        if (r9 == null) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x019d, code lost:
    
        r12 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:382:0x01a7, code lost:
    
        r0 = 53510;
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x01aa, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x01ae, code lost:
    
        if (r0 != 17) goto L429;
     */
    /* JADX WARN: Code restructure failed: missing block: B:386:0x01b3, code lost:
    
        r0 = 53541;
     */
    /* JADX WARN: Code restructure failed: missing block: B:390:0x01b0, code lost:
    
        if (r0 != 50) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x01a1, code lost:
    
        r12 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x01a4, code lost:
    
        r5 = null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0057, code lost:
    
        r12 = com.yan.hook.C0001.m181(r9);
        r13 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x005e, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0063, code lost:
    
        if (r13 == 14) goto L294;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0065, code lost:
    
        if (r13 == 45) goto L295;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0067, code lost:
    
        if (r13 == 76) goto L296;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0069, code lost:
    
        if (r13 == 239) goto L297;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x006c, code lost:
    
        if (r12 != false) goto L299;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x00dd, code lost:
    
        r13 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x006e, code lost:
    
        r13 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0072, code lost:
    
        r9 = com.yan.hook.C0001.m178(r9);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0076, code lost:
    
        r11 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0079, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x007f, code lost:
    
        if (r11 == 22) goto L308;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0081, code lost:
    
        if (r11 == 53) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0085, code lost:
    
        if (r11 == 503) goto L306;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00d3, code lost:
    
        if (r9 != false) goto L309;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00d9, code lost:
    
        r11 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00d5, code lost:
    
        r11 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0089, code lost:
    
        if (r11 == 32495) goto L307;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x008c, code lost:
    
        r0 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x008f, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0093, code lost:
    
        if (r0 == 17) goto L316;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x00c9, code lost:
    
        if (r8 == null) goto L321;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x00cb, code lost:
    
        r0 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00cf, code lost:
    
        r0 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0095, code lost:
    
        if (r0 == 50) goto L317;
     */
    /* JADX WARN: Removed duplicated region for block: B:201:0x0159 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:205:0x0154 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:254:0x029c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x01f2 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:289:0x0283 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:299:0x021f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:323:0x0270 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:333:0x024a A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private File extractAssetFile(Context context, String str, File file) {
        ZipFile zipFile;
        FileOutputStream fileOutputStream;
        int i;
        int i2;
        File file2 = null;
        try {
            zipFile = new ZipFile(C0004.m339(C0003.m297(context)));
            try {
                ZipEntry m321 = C0004.m321(zipFile, str);
                                try {
                                    C0003.m262(zipFile);
                                            return null;
                                    return null;
                                break;
                            file2 = null;
                    file2 = null;
                try {
                    C0003.m262(zipFile);
                            break;
                return file2;
                try {
                    C0003.m262(zipFile);
                    try {
                        break;
                C0002.m190(fileOutputStream);
                        break;
                return file;
                break;
                C0002.m190(fileOutputStream);
                return file;
                return file2;
                return file;
                        break;
                InputStream inputStream = null;
                fileOutputStream = null;
                        i7 = inputStream != null ? 54502 : 54471;
                            break;
                            try {
                                C0002.m227(inputStream);
                                        break;
                        i9 = zipFile != null ? 55401 : 54719;
                            break;
                                try {
                                    C0003.m262(zipFile);
                                            break;
                        i11 = fileOutputStream != null ? 55649 : 55618;
                            return null;
                            try {
                                C0002.m190(fileOutputStream);
                                        return null;
                                return null;
            zipFile = null;

    private File extractNative(Context context) {
        FileOutputStream fileOutputStream;
        String m67 = m67();
                        return null;
                        File file = new File(C0003.m298(context), m42());
                        boolean m181 = C0001.m181(file);
                                i2 = !m181 ? 1833 : 1802;
                                    break;
                                    boolean m178 = C0001.m178(file);
                                            i3 = !m178 ? 48736 : 48705;
                                                break;
                                                return null;
                        File file2 = new File(file, C0001."libyantool");
                        boolean m1812 = C0001.m181(file2);
                                        C0003.m289(file2);
                                        break;
                                        if (m1812) {
                                    break;
                        byte[] m89 = m89(context, C0003.m281(C0004.m336(new StringBuilder(C0004."_.n/"), m67)));
                                    try {
                                        byte[] m102 = m102(m89, m80(this));
                                        int length = m102.length;
                                                i6 = length >= 4 ? 49759 : 49728;
                                                    byte b = m102[0];
                                                                break;
                                                                byte b2 = m102[1];
                                                                        i8 = b2 == 69 ? 50658 : 50627;
                                                                            break;
                                                                            byte b3 = m102[2];
                                                                                    i9 = b3 == 76 ? 50782 : 50751;
                                                                                        break;
                                                                                            byte b4 = m102[3];
                                                                                                        break;
                                                                                                            try {
                                                                                                                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                                                                                                                try {
                                                                                                                    C0002.m217(fileOutputStream2, m102);
                                                                                                                    C0004.m324(fileOutputStream2);
                                                                                                                    try {
                                                                                                                        C0002.m190(fileOutputStream2);
                                                                                                                                break;
                                                                                                                    return file2;
                                                                                                                    th = th;
                                                                                                                            break;
                                                                                                                    fileOutputStream = fileOutputStream2;
                                                                                                                    try {
                                                                                                                        m9("extractNative: write failed", th);
                                                                                                                                i13 = fileOutputStream != null ? 52580 : 52549;
                                                                                                                                    break;
                                                                                                                                    try {
                                                                                                                                        C0002.m190(fileOutputStream);
                                                                                                                                                break;
                                                                                                                        return null;
                                                                                                                                    throw th2;
                                                                                                                                        try {
                                                                                                                                            C0002.m190(fileOutputStream);
                                                                                                                                                    throw th2;
                                                                                                                                            throw th2;
                                                                                                                th = th3;
                                                                                                                fileOutputStream = null;
                                                                if (b == Byte.MAX_VALUE) {
                                                    break;
                                        m52("extractNative: decrypted data is not ELF (pkg mismatch?)");
                                        return null;
                                        m9("extractNative: decrypt failed", th4);
                                        return null;
                                        return null;

    private String extractOrigApk(Context context) {
        File m6 = m6(this, context, C0001.m169(m19(), 104, C0000.f4 ^ 84, 3139), new File(new File(C0003.m298(context), m42()), "orig.apk"));
                                return null;
                        return C0004.m318(m6);

    private static long fieldOffset(Object obj, Field field) {
        Long l = (Long) C0003.m267(m7(), field);
                        return C0004.m340(l);
                        try {
                            long m224 = C0002.m224((Number) C0003.m293(C0003.m295(C0003.m271(obj), "objectFieldOffset", new Class[]{Field.class}), obj, new Object[]{field}));
                            C0001.m182(m7(), field, C0001.m166(m224));
                            return m224;
                            return C0001.f5 ^ 578;

    private static Object fieldValue(Object obj, String str) {
                        break;
                        try {
                            Field m326 = C0004.m326(C0003.m271(obj), str);
                            C0001.m152(m326, true);
                            return C0003.m283(m326, obj);
                            return null;

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00d2, code lost:
    
        if (r7 == 503) goto L185;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00da, code lost:
    
        if (r6 == false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x00e0, code lost:
    
        r7 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x00dc, code lost:
    
        r7 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x00d6, code lost:
    
        if (r7 == 32495) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x00d9, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0142, code lost:
    
        r4 = r2[r5];
        r6 = com.yan.hook.C0002.m200(org.lsposed.hiddenapibypass.library.C0003.m306(r4), r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x014c, code lost:
    
        r7 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x014f, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0155, code lost:
    
        if (r7 == 497) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x017f, code lost:
    
        if (r6 == false) goto L211;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0181, code lost:
    
        r7 = 50658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0185, code lost:
    
        r7 = 50627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0159, code lost:
    
        if (r7 == 1711) goto L206;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x015d, code lost:
    
        if (r7 == 1736) goto L207;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0161, code lost:
    
        if (r7 == 1769) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0164, code lost:
    
        return r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0165, code lost:
    
        r7 = r5 + (com.yan.hook.C0000.f4 ^ 86);
        r4 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x016e, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0174, code lost:
    
        if (r4 == 19) goto L216;
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x017b, code lost:
    
        r4 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x0176, code lost:
    
        if (r4 == 50) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x00e8, code lost:
    
        r9 = (java.lang.reflect.Field) org.lsposed.hiddenapibypass.library.C0004.m329(r7);
        r10 = com.yan.hook.C0002.m200(org.lsposed.hiddenapibypass.library.C0003.m306(r9), r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x00f6, code lost:
    
        r11 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x00f9, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x00fd, code lost:
    
        if (r11 == 17) goto L226;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x0109, code lost:
    
        if (r10 == false) goto L230;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x010b, code lost:
    
        r11 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x01a3, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x010f, code lost:
    
        r11 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x00ff, code lost:
    
        if (r11 == 50) goto L227;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0101, code lost:
    
        if (r11 == 76) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0105, code lost:
    
        if (r11 == 83) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x0108, code lost:
    
        return r9;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0016, code lost:
    
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0018, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x001c, code lost:
    
        if (r0 == 17) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x01a6, code lost:
    
        if (r14 == java.lang.Object.class) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x01a8, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x01ac, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0020, code lost:
    
        if (r0 == 54) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0024, code lost:
    
        if (r0 == 471) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0028, code lost:
    
        if (r0 == 500) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x002b, code lost:
    
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x002e, code lost:
    
        r6 = org.lsposed.hiddenapibypass.library.C0004.m342(m2());
        r7 = org.lsposed.hiddenapibypass.library.C0003.m295(r6, "getInstanceFields", new java.lang.Class[]{java.lang.Class.class});
        r6 = org.lsposed.hiddenapibypass.library.C0003.m295(r6, "getStaticFields", new java.lang.Class[]{java.lang.Class.class});
        r7 = org.lsposed.hiddenapibypass.library.C0003.m287((java.util.List) org.lsposed.hiddenapibypass.library.C0003.m293(r7, null, new java.lang.Object[]{r14}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x006d, code lost:
    
        r9 = org.lsposed.hiddenapibypass.library.C0004.m320(r7);
        r10 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0073, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0075, code lost:
    
        if (r10 == 17) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0113, code lost:
    
        if (r9 != false) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0115, code lost:
    
        r10 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x011a, code lost:
    
        r10 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x007c, code lost:
    
        if (r10 == 47384) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0081, code lost:
    
        if (r10 == 47417) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0086, code lost:
    
        if (r10 == 47483) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0089, code lost:
    
        r2 = org.lsposed.hiddenapibypass.library.C0003.m287((java.util.List) org.lsposed.hiddenapibypass.library.C0003.m293(r6, null, new java.lang.Object[]{r14}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0097, code lost:
    
        r4 = org.lsposed.hiddenapibypass.library.C0004.m320(r2);
        r6 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x009e, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00a2, code lost:
    
        if (r6 == 14) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00a6, code lost:
    
        if (r6 == 45) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x00a8, code lost:
    
        if (r6 == 76) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x00aa, code lost:
    
        if (r6 == 239) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00ad, code lost:
    
        if (r4 == false) goto L179;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00e4, code lost:
    
        r6 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00af, code lost:
    
        r6 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00b3, code lost:
    
        r4 = (java.lang.reflect.Field) org.lsposed.hiddenapibypass.library.C0004.m329(r2);
        r6 = com.yan.hook.C0002.m200(org.lsposed.hiddenapibypass.library.C0003.m306(r4), r15);
        r7 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00c4, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00ca, code lost:
    
        if (r7 == 22) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00ce, code lost:
    
        if (r7 == 53) goto L174;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Field findField(Class<?> cls, String str) {
        int i;
        int i2;
        loop0: while (true) {
                            break;
                            break loop0;
        cls = C0001.m150(cls);
        try {
            Field[] m278 = C0003.m278(cls);
            int length = m278.length;
                            break;
                            break;
                            if (i < length) {
                i = i2;

    /* JADX WARN: Code restructure failed: missing block: B:101:0x007c, code lost:
    
        r8 = com.yan.hook.C0001.m156(r6);
        r7 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0083, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0087, code lost:
    
        if (r7 == 17) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x0097, code lost:
    
        if (r8 != 0) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x0099, code lost:
    
        r7 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x009d, code lost:
    
        r7 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x008b, code lost:
    
        if (r7 == 50) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x008f, code lost:
    
        if (r7 == 76) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x0093, code lost:
    
        if (r7 == 83) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x00a7, code lost:
    
        r5 = r5 + (org.lsposed.hiddenapibypass.library.C0004.f13 ^ (-364));
        r6 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x00af, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x00b5, code lost:
    
        if (r6 == 18) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x00b9, code lost:
    
        if (r6 == 241) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x00bc, code lost:
    
        r6 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0096, code lost:
    
        return r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:?, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0015, code lost:
    
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0017, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x001b, code lost:
    
        if (r0 == 17) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00d4, code lost:
    
        if (r10 == java.lang.Object.class) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x00d6, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00da, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x001f, code lost:
    
        if (r0 == 54) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0023, code lost:
    
        if (r0 == 471) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0027, code lost:
    
        if (r0 == 500) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x002a, code lost:
    
        r0 = com.yan.hook.C0002.m208(r10);
        r4 = r0.length;
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0030, code lost:
    
        r6 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0032, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0034, code lost:
    
        if (r6 == 17) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00c4, code lost:
    
        if (r5 < r4) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c6, code lost:
    
        r6 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00cb, code lost:
    
        r6 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0039, code lost:
    
        if (r6 == 47384) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x003e, code lost:
    
        if (r6 == 47417) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0043, code lost:
    
        if (r6 == 47483) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0046, code lost:
    
        r10 = com.yan.hook.C0001.m150(r10);
        r0 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x004d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0051, code lost:
    
        if (r0 == 14) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0053, code lost:
    
        if (r0 == 239) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0056, code lost:
    
        r0 = 48798;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x005a, code lost:
    
        r6 = r0[r5];
        r7 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m210(r6), r11);
        r8 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0067, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x006d, code lost:
    
        if (r8 == 22) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0071, code lost:
    
        if (r8 == 53) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0075, code lost:
    
        if (r8 == 503) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00a1, code lost:
    
        if (r7 == false) goto L129;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00c0, code lost:
    
        r8 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00a3, code lost:
    
        r8 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0079, code lost:
    
        if (r8 == 32495) goto L127;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Method findMethod(Class<?> cls, String str) {
                            break;
                            return null;

    private static void freeReflection() {
        m52("freeReflection: skipped (no setHiddenApiExemptions)");

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0030, code lost:
    
        r0 = m47(r0, m51(org.lsposed.hiddenapibypass.library.C0003.m271(r0), com.yan.hook.C0002.m191(m19(), 107, com.yan.hook.C0001.f5 ^ (-582), 2670)));
        r1 = r0 instanceof android.os.IBinder;
        r2 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0050, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0054, code lost:
    
        if (r2 == 17) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0066, code lost:
    
        if (r1 == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0068, code lost:
    
        r2 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x006b, code lost:
    
        r2 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0058, code lost:
    
        if (r2 == 54) goto L48;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x005c, code lost:
    
        if (r2 == 471) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0060, code lost:
    
        if (r2 == 500) goto L46;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0065, code lost:
    
        return (android.os.IBinder) r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:?, code lost:
    
        return null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static IBinder getRealServiceManagerBinder() {
        try {
            Object m78 = m78(m51(C0004.m342(m99()), "sServiceManager"));
                            break;
                            return null;
            return null;

    /* JADX WARN: Code restructure failed: missing block: B:160:0x01e9, code lost:
    
        r2 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x01ec, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x01f0, code lost:
    
        if (r2 == 18) goto L222;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x01f4, code lost:
    
        if (r2 == 53) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x01f8, code lost:
    
        if (r2 == 243) goto L224;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0209, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0229, code lost:
    
        r2 = 50875;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x020b, code lost:
    
        r2 = 51557;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x01fc, code lost:
    
        if (r2 == 4075) goto L225;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x01ff, code lost:
    
        r2 = com.yan.hook.C0001.f5;
        r10[r13] = r7[r12];
        r13 = r13 + (r2 ^ (-580));
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x020f, code lost:
    
        r2 = r13;
        r4 = (com.yan.hook.C0000.f4 ^ 86) + r12;
        r5 = 51588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0218, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x021c, code lost:
    
        if (r5 == 17) goto L233;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x0225, code lost:
    
        r5 = 51619;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x021e, code lost:
    
        if (r5 == 54) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0139, code lost:
    
        r10 = m82(org.lsposed.hiddenapibypass.library.C0003.m283(r6, r7[r12]));
        r13 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0146, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x014b, code lost:
    
        if (r13 == 17) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x014d, code lost:
    
        if (r13 == 50) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x014f, code lost:
    
        if (r13 == 76) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0153, code lost:
    
        if (r13 == 83) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x0158, code lost:
    
        r12 = r12 + (com.yan.hook.C0002.f6 ^ (-481));
        r4 = 51712;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x0160, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0164, code lost:
    
        if (r4 == 14) goto L243;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x0166, code lost:
    
        if (r4 == 17) goto L244;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x0169, code lost:
    
        r4 = 51743;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x0170, code lost:
    
        r4 = r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x0245, code lost:
    
        r13 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x023e, code lost:
    
        if (r10 == false) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x0240, code lost:
    
        r13 = 49759;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void hideHookDex() throws Exception {
        int i;
        int i2;
        int i3;
        C0004.m342(C0003.m281(C0004.m336(new StringBuilder(C0002.m204(m73())), C0003."$FakeBinder")));
        C0004.m342(C0003.m281(C0004.m336(new StringBuilder(C0002.m204(m73())), C0002.m191(m19(), 125, C0003.f12 ^ 679, 3190))));
        ClassLoader m147 = C0001.m147(PmsHookProvider.class);
        Field m326 = C0004.m326(C0004.m342(m64()), C0002."pathList");
        C0001.m152(m326, true);
        Object m283 = C0003.m283(m326, m147);
                        return;
                    char c = 239;
                        Field m3262 = C0004.m326(C0004.m342(m39()), C0004."dexElements");
                        C0001.m152(m3262, true);
                        Object m2832 = C0003.m283(m3262, m283);
                        boolean z = m2832 instanceof Object[];
                                        Object[] objArr = (Object[]) m2832;
                                        int length = objArr.length;
                                                    Class m342 = C0004.m342(m48());
                                                    Field m3263 = C0004.m326(m342, C0002."dexFile");
                                                    C0001.m152(m3263, true);
                                                    loop3: while (true) {
                                                        int length2 = objArr.length;
                                                                    break;
                                                                            break;
                                                                    i = -1;
                                                                    if (i7 >= length2) {
                                                            c = 239;
                                                        c = 239;
                                                                    i = (C0000.f4 ^ (-88)) + objArr.length;
                                                                    break;
                                                                    if (i < 0) {
                                                                break;
                                                    int i11 = i;
                                                    Object[] objArr2 = (Object[]) C0001.m163(m342, objArr.length - 1);
                                                    loop7: while (true) {
                                                        int length3 = objArr.length;
                                                                i14 = i12 >= length3 ? 50658 : 50627;
                                                                    break;
                                                                    break loop7;
                                                        i13 = i2;
                                                        i12 = i3;
                                                    C0002.m193(m3262, m283, objArr2);
                                                    int m143 = C0001.m143();
                                                            i15 = m143 <= 0 ? 50782 : 50751;
                                                                return;
                                                                    return;
                                                    return;
                                                c = 239;
                                            c = 239;
                                        return;
                                c = 239;
                            c = 239;

    /* JADX WARN: Code restructure failed: missing block: B:101:0x015d, code lost:
    
        if (r0 == 83) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0160, code lost:
    
        m87(r10, r11);
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("patch mInitialApplication ApplicationInfo: ")), com.yan.hook.C0002.m221(r11))));
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0186, code lost:
    
        r11 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0189, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x018f, code lost:
    
        if (r11 == 18) goto L168;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0193, code lost:
    
        if (r11 == 241) goto L167;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0196, code lost:
    
        r11 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00db, code lost:
    
        m87(r10, r11);
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("patch cached ApplicationInfo: ")), com.yan.hook.C0002.m221(r11))));
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0101, code lost:
    
        r11 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0104, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0108, code lost:
    
        if (r11 == 14) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x010a, code lost:
    
        if (r11 == 239) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x010d, code lost:
    
        r11 = 48798;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0144, code lost:
    
        r11 = com.yan.hook.C0001.m160((android.app.Application) r11);
        r0 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x014d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0151, code lost:
    
        if (r0 == 17) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x019a, code lost:
    
        if (r11 == null) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x019c, code lost:
    
        r0 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x01a0, code lost:
    
        r0 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0155, code lost:
    
        if (r0 == 50) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0159, code lost:
    
        if (r0 == 76) goto L145;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void hook(Context context) throws Exception {
        m43();
        m85();
        m70();
        m96(this);
        Object m293 = C0003.m293(C0002.m192(C0004.m342(m30()), "currentActivityThread", new Class[0]), null, new Object[0]);
        IBinder m168 = C0001.m168(C0003."package");
        m52(C0003.m281(C0001.m173(new StringBuilder(C0004.m323(m19(), 168, C0003.f12 ^ 678, 2416)), m168)));
                        FakeBinder fakeBinder = new FakeBinder(this, m168);
                        m79(fakeBinder);
                        try {
                            m62(m37(m293, "sPackageManager"), fakeBinder);
                            m62(m37(C0001.m159(context), C0004.m323(m19(), 179, C0000.f4 ^ 84, 1647)), fakeBinder);
                                    break;
                        m60(this, fakeBinder);
                        break;
        m100();
        try {
            ApplicationInfo m297 = C0003.m297(context);
                    i3 = m297 != null ? 48736 : 48705;
                        break;
                    break;
        try {
            Object m37 = m37(m293, "mInitialApplication");
            boolean z = m37 instanceof Application;
                        break;
                            break;
        m91();
        m52(C0002.m191(m19(), 182, C0000.f4 ^ 94, 697));
        int m274 = C0003.m274();
                i5 = m274 <= 0 ? 50658 : 50627;
                    return;
                    return;

    private void installCreatorHook() {
        try {
            m84(this, PackageInfo.class);
                    break;
        try {
            m84(this, ApplicationInfo.class);
                    break;
        m52("installCreatorHook: done");
        int m188 = C0002.m188();
                i3 = m188 >= 0 ? 48736 : 48705;
                    return;
                    return;

    private void installCtxHook(IBinder iBinder) {
        boolean z;
        try {
            g_smFake = iBinder;
            g_realSM = m86();
            StringBuilder m336 = C0004.m336(C0001.m173(new StringBuilder(C0002.m204("installCtxHook(prepare): realSM=")), m45()), C0002.m191(m19(), 191, C0000.f4 ^ 81, 823));
                                    break;
                            z = true;
                            z = false;
                            break;
            m52(C0003.m281(C0003.m301(m336, z)));
                    break;
            m9("installCtxHook failed", th);
        int m143 = C0001.m143();
                    return;
                    return;
                    if (m143 <= 0) {

    private static void installFakeCache(IBinder iBinder) {
        try {
            Field m326 = C0004.m326(C0004.m342(m99()), C0004.m323(m19(), 197, C0000.f4 ^ 81, 3152));
            Object m78 = m78(m326);
            boolean z = m78 instanceof Map;
                            int m188 = C0002.m188();
                                    i2 = m188 >= 0 ? 1833 : 1802;
                                        return;
                                        return;
                            m14(m326, new FakeCache((Map) m78, iBinder));
                                    return;

    private void installNative(Context context) {
        String m74 = m74(this);
                        return;
                        try {
                            String m339 = C0004.m339(C0003.m297(context));
                            boolean m18 = m18();
                                    i2 = !m18 ? 1833 : 1802;
                                        break;
                                        File m76 = m76(this, context);
                                                i3 = m76 == null ? 48736 : 48705;
                                                    C0002.m186(C0004.m318(m76));
                                                    nativeLoaded = true;
                                                    break;
                                                    int m274 = C0003.m274();
                                                                return;
                                                                return;
                                                                if (m274 <= 0) {
                            m24(m339, m74(this));
                            m54(this);
                            m26(this);
                                    return;
                            m9("installNative failed", th);
                            return;

    /* JADX WARN: Can't wrap try/catch for region: R(7:66|(5:67|(1:116)(2:69|(3:71|(3:82|83|(1:99)(8:85|86|87|(3:88|(2:94|(1:96)(0))(3:90|91|92)|93)|101|102|(2:103|(2:(1:110)(3:106|107|108)|109)(1:111))|112))(2:73|(3:79|80|81)(1:75))|78)(0))|76|77|78)|100|101|102|(3:103|(0)(0)|109)|112) */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x00c3, code lost:
    
        r12 = new byte[com.yan.hook.C0001.m176(r8)];
        org.lsposed.hiddenapibypass.library.C0004.m328(r8, r12);
        r10[r11] = new android.content.pm.Signature(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x00d3, code lost:
    
        r11 = r11 + (org.lsposed.hiddenapibypass.library.C0004.f13 ^ (-364));
        r12 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x00db, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x00e1, code lost:
    
        if (r12 == 497) goto L224;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x00e8, code lost:
    
        r12 = 50596;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x00e5, code lost:
    
        if (r12 == 1711) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x00f8, code lost:
    
        r3 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x00f9, code lost:
    
        r7 = r3;
        r3 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x00fd, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0103, code lost:
    
        if (r3 != 19) goto L231;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x010a, code lost:
    
        r3 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x0105, code lost:
    
        if (r3 != 50) goto L234;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0108, code lost:
    
        r3 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x013e, code lost:
    
        r6 = 51588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x0141, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0145, code lost:
    
        if (r6 != 17) goto L241;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x01b0, code lost:
    
        if (r3 != null) goto L238;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x01b2, code lost:
    
        r6 = 51681;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x01b6, code lost:
    
        r6 = 51650;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0147, code lost:
    
        if (r6 != 54) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x014b, code lost:
    
        if (r6 == 87) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x014f, code lost:
    
        if (r6 != 116) goto L245;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0152, code lost:
    
        com.yan.hook.C0001.m144(r3);
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0155, code lost:
    
        r3 = 51712;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0158, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x015c, code lost:
    
        if (r3 != 14) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x015e, code lost:
    
        if (r3 != 17) goto L249;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0161, code lost:
    
        r3 = 51743;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0165, code lost:
    
        r3 = 51836;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0168, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x016c, code lost:
    
        if (r3 != 241) goto L256;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x01a6, code lost:
    
        if (r5 != null) goto L254;
     */
    /* JADX WARN: Code restructure failed: missing block: B:184:0x01a8, code lost:
    
        r3 = 52580;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x01ac, code lost:
    
        r3 = 52549;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0170, code lost:
    
        if (r3 != 1963) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x0174, code lost:
    
        if (r3 != 1992) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x0178, code lost:
    
        if (r3 != 2025) goto L260;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x017b, code lost:
    
        com.yan.hook.C0002.m227(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x017e, code lost:
    
        r15 = 52611;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0181, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x0187, code lost:
    
        if (r15 != 23) goto L262;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x018c, code lost:
    
        r15 = 52642;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0189, code lost:
    
        if (r15 != 54) goto L265;
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0190, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m262(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x0193, code lost:
    
        r15 = 52735;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x0196, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x019a, code lost:
    
        if (r15 == 14) goto L266;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x019e, code lost:
    
        if (r15 != 1007) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x01a1, code lost:
    
        r15 = 52766;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:?, code lost:
    
        throw r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:230:0x01a5, code lost:
    
        throw r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x010e, code lost:
    
        r7 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:238:0x010f, code lost:
    
        r8 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x0112, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:240:0x0116, code lost:
    
        if (r8 != 18) goto L87;
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x011a, code lost:
    
        if (r8 != 243) goto L274;
     */
    /* JADX WARN: Code restructure failed: missing block: B:244:0x011d, code lost:
    
        r8 = 50844;
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x003d, code lost:
    
        r5 = org.lsposed.hiddenapibypass.library.C0003.m277(r0, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0043, code lost:
    
        r8 = new java.io.DataInputStream(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x004a, code lost:
    
        r3 = org.lsposed.hiddenapibypass.library.C0004.m313(r8);
        r10 = new android.content.pm.Signature[r3];
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0050, code lost:
    
        r11 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0051, code lost:
    
        r12 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0053, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0055, code lost:
    
        if (r12 == 17) goto L191;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ec, code lost:
    
        if (r11 < r3) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ee, code lost:
    
        r12 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00f3, code lost:
    
        r12 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x005a, code lost:
    
        if (r12 == 47384) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005f, code lost:
    
        if (r12 == 47417) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0064, code lost:
    
        if (r12 == 47483) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0067, code lost:
    
        com.yan.hook.C0001.m144(r8);
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x006a, code lost:
    
        r1 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x006d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0071, code lost:
    
        if (r1 == 14) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0073, code lost:
    
        if (r1 == 239) goto L201;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0076, code lost:
    
        r1 = 48798;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x00be A[EDGE_INSN: B:111:0x00be->B:112:0x00be BREAK  A[LOOP:6: B:103:0x00b1->B:109:0x00b1], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x019c  */
    /* JADX WARN: Removed duplicated region for block: B:226:0x01a5 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private Signature[] loadSignatures(Context context) throws Exception {
        InputStream inputStream;
        Signature[] signatureArr;
        int i;
        ZipFile zipFile = new ZipFile(C0004.m339(C0003.m297(context)));
        DataInputStream dataInputStream = null;
        try {
            ZipEntry m321 = C0004.m321(zipFile, C0004."_.s");
                            try {
                                C0003.m262(zipFile);
                                        break;
                            return null;
                            break;
                                try {
                                    C0002.m227(inputStream);
                                C0003.m262(zipFile);
                                        break;
                                return signatureArr;
            C0003.m262(zipFile);
            return signatureArr;
            C0003.m262(zipFile);
            return signatureArr;
            return signatureArr;
            Throwable th2 = th;
            inputStream = null;

    private static void log(String str) {

    private static void log(String str, Throwable th) {

    public static IBinder makeFakeSM() {
        try {
            m52("makeFakeSM: called");
            return new FakeSM();
            return null;

    private static native void nativeInstall(String str, String str2);

    private static native void nativeInstallCtxHook(Class<?> cls);

    private void patchAppInfoSignatures(ApplicationInfo applicationInfo) {
                        int m143 = C0001.m143();
                                i2 = m143 <= 0 ? 1833 : 1802;
                                    return;
                                    return;
                        try {
                            Field m65 = m65();
                                    i3 = m65 != null ? 48736 : 48705;
                                    Field m51 = m51(ApplicationInfo.class, C0002."signatures");
                                                return;
                                                m15(applicationInfo, m51, m25(this));
                                                        return;
                                                if (m51 != null) {
                                    m15(applicationInfo, m65(), m25(this));
                                    return;
                            return;

    private void patchByType(Object obj) {
        boolean z = obj instanceof PackageInfo;
                        m32(this, (PackageInfo) obj);
                                return;
                        boolean z2 = obj instanceof ApplicationInfo;
                                i3 = z2 ? 48736 : 48705;
                                    return;
                                    m87(this, (ApplicationInfo) obj);
                                    return;

    private static void patchCheckService2(Parcel parcel) throws Exception {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
                        Class m342 = C0004.m342(m44());
                        Object m229 = C0002.m229(parcel, (Parcelable.Creator) m78(m51(m342, C0001."CREATOR")));
                                i2 = m229 == null ? 1833 : 1802;
                                    Object m47 = m47(m229, m51(m342, C0003."_value"));
                                                m52("patchCheckService2: _value null (tag?)");
                                                break;
                                                m15(m47, m51(C0003.m271(m47), C0002.m191(m19(), 229, C0003.f12 ^ 682, 2792)), m11());
                                                        break;
                                                if (m47 != null) {
                                    Parcel m299 = C0003.m299();
                                    try {
                                        C0002.m222(m299);
                                        C0003.m261(m299, (Parcelable) m229, 0);
                                        C0004.m325(parcel, 0);
                                        C0002.m202(parcel, 0);
                                        C0004.m345(parcel, m299, 0, C0004.m335(m299));
                                        C0004.m325(parcel, 0);
                                        return;
                                        C0003.m290(m299);
                                    break;
                        break;
        C0004.m325(parcel, 0);
        int m188 = C0002.m188();
                i5 = m188 >= 0 ? 48736 : 48705;
                    return;
                    return;

    private static void patchGetService(Parcel parcel) throws Exception {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
                        C0004.m325(parcel, 0);
                        return;
                        Parcel m299 = C0003.m299();
                        try {
                            C0002.m222(m299);
                            C0003.m302(m299, m11());
                            C0004.m325(parcel, 0);
                            C0002.m202(parcel, 0);
                            C0004.m345(parcel, m299, 0, C0004.m335(m299));
                            C0004.m325(parcel, 0);
                            C0003.m290(m299);
                            int m138 = C0000.m138();
                                    i2 = m138 >= 0 ? 1833 : 1802;
                                        return;
                                        return;
                            C0003.m290(m299);
                            throw th;

    private void patchInfo(PackageInfo packageInfo) {
                        boolean m200 = C0002.m200(m80(this), C0001.m162(packageInfo));
                                i2 = m200 ? 1833 : 1802;
                                    return;
                                    packageInfo.signatures = m25(this);
                                    m93(this, packageInfo);
                                    ApplicationInfo m174 = C0001.m174(packageInfo);
                                            i3 = m174 != null ? 48736 : 48705;
                                                m87(this, C0001.m174(packageInfo));
                                                break;
                                            break;
                                    try {
                                        packageInfo.providers = null;
                                                return;
                                        return;
                        return;

    /* JADX WARN: Code restructure failed: missing block: B:22:0x001b, code lost:
    
        r3 = r0[r2];
        r6 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m191(m19(), 236, com.yan.hook.C0001.f5 ^ (-588), 2797), r3);
        r7 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0033, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0037, code lost:
    
        if (r7 == 17) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00c6, code lost:
    
        if (r6 != false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00c8, code lost:
    
        r7 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00cc, code lost:
    
        r7 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x003b, code lost:
    
        if (r7 == 54) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x003f, code lost:
    
        if (r7 == 471) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0043, code lost:
    
        if (r7 == 500) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0046, code lost:
    
        r6 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m191(m19(), 245, com.yan.hook.C0000.f4 ^ 92, 2976), r3);
        r7 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x005c, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x005e, code lost:
    
        if (r7 == 17) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00bb, code lost:
    
        if (r6 != false) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00bd, code lost:
    
        r7 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00c1, code lost:
    
        r7 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0063, code lost:
    
        if (r7 == 47384) goto L67;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0068, code lost:
    
        if (r7 == 47417) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x006d, code lost:
    
        if (r7 == 47483) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0070, code lost:
    
        r6 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m191(m19(), 256, com.yan.hook.C0001.f5 ^ (-581), 1671), r3);
        r7 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0087, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x008b, code lost:
    
        if (r7 == 14) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x008f, code lost:
    
        if (r7 == 45) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0093, code lost:
    
        if (r7 == 76) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0095, code lost:
    
        if (r7 == 239) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0098, code lost:
    
        if (r6 != false) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00b7, code lost:
    
        r7 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x009a, code lost:
    
        r7 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x009e, code lost:
    
        r2 = r2 + (com.yan.hook.C0000.f4 ^ 86);
        r3 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00a6, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00ac, code lost:
    
        if (r3 == 22) goto L68;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00b0, code lost:
    
        if (r3 == 503) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00b3, code lost:
    
        r3 = 48922;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String pickAbi() {
        String str;
        String[] m142 = C0001.m142();
        int length = m142.length;
        loop0: while (true) {
                            return null;
                            break;
        return str;

    private static List<?> plsGetList(Object obj) {
        try {
            Method m13 = m13();
                            return (List) C0003.m293(m13(), obj, new Object[0]);
                            return null;
            return null;

    /* JADX WARN: Code restructure failed: missing block: B:152:0x0165, code lost:
    
        com.yan.hook.PmsHookProvider.CACHE_PLS_GETLIST = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0167, code lost:
    
        r1 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x016a, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x016e, code lost:
    
        if (r1 == 18) goto L196;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0172, code lost:
    
        if (r1 == 243) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x0175, code lost:
    
        r1 = 50844;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x00e7, code lost:
    
        com.yan.hook.PmsHookProvider.CACHE_SI_DETAILS = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x00e9, code lost:
    
        r2 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x00ec, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:176:0x00f0, code lost:
    
        if (r2 == 18) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x00f4, code lost:
    
        if (r2 == 241) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x00f7, code lost:
    
        r2 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x002d, code lost:
    
        com.yan.hook.PmsHookProvider.CACHE_AI_SIGNATURES = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x002f, code lost:
    
        r4 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0031, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0033, code lost:
    
        if (r4 == 17) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x003a, code lost:
    
        r4 = 1771;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0037, code lost:
    
        if (r4 == 54) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0096, code lost:
    
        com.yan.hook.PmsHookProvider.CACHE_SD_SIGNATURES = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0098, code lost:
    
        r0 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x009b, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x00a1, code lost:
    
        if (r0 == 22) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00a5, code lost:
    
        if (r0 == 503) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00a8, code lost:
    
        r0 = 48922;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static void prefetchFields() {
        String m191 = C0002."signatures";
        try {
            Field m51 = m51(ApplicationInfo.class, m191);
                            break;
                            break;
        try {
            Class m342 = C0004.m342(m94());
            Field m512 = m51(m342, m191);
                    i2 = m512 == null ? 48736 : 48705;
                        m512 = m51(m342, C0002.m191(m19(), 272, C0000.f4 ^ 92, 1427));
                        break;
                    break;
            Field field = m512;
                            break;
                            if (field != null) {
                        break;
        try {
            Field m513 = m51(C0004.m342(m68()), "mSigningDetails");
                    i4 = m513 != null ? 49759 : 49728;
                        break;
        try {
            Class m3422 = C0004.m342(m97());
            Field m514 = m51(m3422, C0003.m285(m19(), 283, C0000.f4 ^ 80, 2624));
                    i5 = m514 != null ? 50658 : 50627;
                            CACHE_PLS_CREATOR = m78(m514);
                            break;
                        break;
            Method m28 = m28(m3422, C0003.m285(m19(), 290, C0000.f4 ^ 80, 2846));
                    i6 = m28 != null ? 50782 : 50751;
                        return;
                            break;

    /* JADX WARN: Code restructure failed: missing block: B:100:0x00a3, code lost:
    
        if (r11 == 17) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x00a5, code lost:
    
        if (r11 == 50) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x00a7, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00ab, code lost:
    
        if (r11 == 83) goto L347;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x00b0, code lost:
    
        r4 = r4 + r7;
        r7 = 52611;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x00b4, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x00b8, code lost:
    
        if (r7 == 23) goto L351;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x00c3, code lost:
    
        r7 = 52642;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x00ba, code lost:
    
        if (r7 == 54) goto L352;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x00bd, code lost:
    
        r7 = r4;
        r14 = 'L';
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00ad, code lost:
    
        r14 = 'L';
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x00d8, code lost:
    
        r1 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x00db, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x00e1, code lost:
    
        if (r1 == 18) goto L357;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x00e7, code lost:
    
        if (r1 == 51) goto L358;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x00eb, code lost:
    
        if (r1 == 84) goto L359;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x00ed, code lost:
    
        if (r1 == 241) goto L360;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x00f0, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0187, code lost:
    
        r1 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x00f2, code lost:
    
        r1 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x00f6, code lost:
    
        r0 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x00f9, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x00ff, code lost:
    
        if (r0 == 497) goto L368;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0136, code lost:
    
        if (r5 == null) goto L371;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0138, code lost:
    
        r0 = 50658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:150:0x013c, code lost:
    
        r0 = 50627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0103, code lost:
    
        if (r0 == 1711) goto L367;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x0105, code lost:
    
        if (r0 == 1736) goto L295;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0107, code lost:
    
        if (r0 == 1769) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x010a, code lost:
    
        com.yan.hook.C0002.m227(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x010d, code lost:
    
        r0 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0110, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x0116, code lost:
    
        if (r0 == 19) goto L377;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x011b, code lost:
    
        r0 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0118, code lost:
    
        if (r0 == 50) goto L295;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x0140, code lost:
    
        r0 = 51588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0143, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x0147, code lost:
    
        if (r0 == 17) goto L387;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x017d, code lost:
    
        if (r5 == null) goto L392;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x017f, code lost:
    
        r0 = 51681;
     */
    /* JADX WARN: Code restructure failed: missing block: B:203:0x0183, code lost:
    
        r0 = 51650;
     */
    /* JADX WARN: Code restructure failed: missing block: B:206:0x0149, code lost:
    
        if (r0 == 54) goto L388;
     */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x014d, code lost:
    
        if (r0 == 87) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x0151, code lost:
    
        if (r0 == 116) goto L287;
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x0154, code lost:
    
        com.yan.hook.C0002.m227(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:215:0x0157, code lost:
    
        r0 = 51712;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x015a, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x015e, code lost:
    
        if (r0 == 14) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x0160, code lost:
    
        if (r0 == 17) goto L397;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x0163, code lost:
    
        r0 = 51743;
     */
    /* JADX WARN: Code restructure failed: missing block: B:245:0x00cd, code lost:
    
        r11 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x00c7, code lost:
    
        if (r4 > 0) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x00c9, code lost:
    
        r11 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x0197, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:254:0x0198, code lost:
    
        r4 = 52735;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x019b, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x019f, code lost:
    
        if (r4 != 14) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x01a3, code lost:
    
        if (r4 != 1007) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x01a6, code lost:
    
        r4 = 52766;
     */
    /* JADX WARN: Code restructure failed: missing block: B:267:0x021b, code lost:
    
        m9(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("readAssetBytes failed: ")), r21)), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0238, code lost:
    
        r0 = 53758;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x023b, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:270:0x0241, code lost:
    
        if (r0 != 1009) goto L417;
     */
    /* JADX WARN: Code restructure failed: missing block: B:273:0x02a4, code lost:
    
        if (r5 != null) goto L415;
     */
    /* JADX WARN: Code restructure failed: missing block: B:275:0x02a6, code lost:
    
        r0 = 54502;
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x02aa, code lost:
    
        r0 = 54471;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0036, code lost:
    
        r6 = org.lsposed.hiddenapibypass.library.C0004.m332(r0);
        r14 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x0245, code lost:
    
        if (r0 != 1703) goto L416;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x0247, code lost:
    
        if (r0 == 1736) goto L414;
     */
    /* JADX WARN: Code restructure failed: missing block: B:288:0x0249, code lost:
    
        if (r0 != 1769) goto L422;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x003e, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x024c, code lost:
    
        com.yan.hook.C0002.m227(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x024f, code lost:
    
        r0 = 54533;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x0252, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0258, code lost:
    
        if (r0 != 19) goto L425;
     */
    /* JADX WARN: Code restructure failed: missing block: B:295:0x025d, code lost:
    
        r0 = 54564;
     */
    /* JADX WARN: Code restructure failed: missing block: B:299:0x025a, code lost:
    
        if (r0 != 50) goto L428;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0040, code lost:
    
        if (r14 == 17) goto L317;
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x0261, code lost:
    
        r0 = 54657;
     */
    /* JADX WARN: Code restructure failed: missing block: B:302:0x0264, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x026c, code lost:
    
        if (r0 != 19) goto L434;
     */
    /* JADX WARN: Code restructure failed: missing block: B:306:0x028f, code lost:
    
        if (r13 != null) goto L433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:308:0x0291, code lost:
    
        r0 = 55401;
     */
    /* JADX WARN: Code restructure failed: missing block: B:312:0x0295, code lost:
    
        r0 = 54719;
     */
    /* JADX WARN: Code restructure failed: missing block: B:315:0x026e, code lost:
    
        if (r0 != 45) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x0270, code lost:
    
        if (r0 != 50) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:321:0x0274, code lost:
    
        if (r0 != 3579) goto L439;
     */
    /* JADX WARN: Code restructure failed: missing block: B:323:0x0277, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m262(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x027a, code lost:
    
        r0 = 55432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:325:0x027d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:326:0x0281, code lost:
    
        if (r0 != 17) goto L442;
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x0286, code lost:
    
        r0 = 55463;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x01ca, code lost:
    
        if (r6 <= 0) goto L323;
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x0283, code lost:
    
        if (r0 != 62) goto L445;
     */
    /* JADX WARN: Code restructure failed: missing block: B:334:?, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x028c, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:341:?, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:348:0x02ae, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:349:0x02af, code lost:
    
        r1 = 55556;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x01cc, code lost:
    
        r4 = '-';
        r14 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:350:0x02b2, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:351:0x02b8, code lost:
    
        if (r1 != 17) goto L452;
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x0311, code lost:
    
        if (r5 != null) goto L449;
     */
    /* JADX WARN: Code restructure failed: missing block: B:356:0x0313, code lost:
    
        r1 = 55649;
     */
    /* JADX WARN: Code restructure failed: missing block: B:360:0x0317, code lost:
    
        r1 = 55618;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x02ba, code lost:
    
        if (r1 != 54) goto L450;
     */
    /* JADX WARN: Code restructure failed: missing block: B:367:0x02bc, code lost:
    
        if (r1 == 87) goto L448;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x02c0, code lost:
    
        if (r1 != 116) goto L456;
     */
    /* JADX WARN: Code restructure failed: missing block: B:371:0x02c3, code lost:
    
        com.yan.hook.C0002.m227(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:372:0x02c6, code lost:
    
        r1 = 55680;
     */
    /* JADX WARN: Code restructure failed: missing block: B:373:0x02c9, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:374:0x02cd, code lost:
    
        if (r1 != 17) goto L459;
     */
    /* JADX WARN: Code restructure failed: missing block: B:376:0x02d4, code lost:
    
        r1 = 56362;
     */
    /* JADX WARN: Code restructure failed: missing block: B:381:0x02d1, code lost:
    
        if (r1 != 1467) goto L462;
     */
    /* JADX WARN: Code restructure failed: missing block: B:383:0x02d8, code lost:
    
        r1 = 56455;
     */
    /* JADX WARN: Code restructure failed: missing block: B:384:0x02db, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:385:0x02e1, code lost:
    
        if (r1 != 31) goto L468;
     */
    /* JADX WARN: Code restructure failed: missing block: B:387:0x0302, code lost:
    
        if (r13 != null) goto L466;
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0304, code lost:
    
        r1 = 56548;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x01d6, code lost:
    
        r4 = '-';
        r14 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x0308, code lost:
    
        r1 = 56517;
     */
    /* JADX WARN: Code restructure failed: missing block: B:396:0x02e3, code lost:
    
        if (r1 != 62) goto L467;
     */
    /* JADX WARN: Code restructure failed: missing block: B:400:0x02e7, code lost:
    
        if (r1 != 93) goto L250;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x02eb, code lost:
    
        if (r1 != 124) goto L473;
     */
    /* JADX WARN: Code restructure failed: missing block: B:404:0x02ee, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m262(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x02f1, code lost:
    
        r1 = 56579;
     */
    /* JADX WARN: Code restructure failed: missing block: B:406:0x02f4, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x02f8, code lost:
    
        if (r1 != 23) goto L476;
     */
    /* JADX WARN: Code restructure failed: missing block: B:409:0x02fd, code lost:
    
        r1 = 56610;
     */
    /* JADX WARN: Code restructure failed: missing block: B:413:0x02fa, code lost:
    
        if (r1 != 54) goto L479;
     */
    /* JADX WARN: Code restructure failed: missing block: B:415:?, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:419:0x0301, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:422:?, code lost:
    
        throw r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x0045, code lost:
    
        if (r14 == 47384) goto L285;
     */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x01b1, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m262(r13);
     */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x01b4, code lost:
    
        r0 = 53510;
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x01b7, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:434:0x01bb, code lost:
    
        if (r0 == 17) goto L480;
     */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x01c0, code lost:
    
        r0 = 53541;
     */
    /* JADX WARN: Code restructure failed: missing block: B:440:0x01bd, code lost:
    
        if (r0 == 50) goto L481;
     */
    /* JADX WARN: Code restructure failed: missing block: B:442:?, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x01c4, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x004a, code lost:
    
        if (r14 == 47417) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x004f, code lost:
    
        if (r14 == 47483) goto L319;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0052, code lost:
    
        r11 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0055, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0059, code lost:
    
        if (r11 == 14) goto L329;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x005b, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x005d, code lost:
    
        r14 = 'L';
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x005f, code lost:
    
        if (r11 == 76) goto L330;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0061, code lost:
    
        if (r11 == 239) goto L328;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0069, code lost:
    
        if (r6 > 33554432) goto L332;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01aa, code lost:
    
        r11 = 48829;
        r4 = '-';
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x006b, code lost:
    
        r11 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x006f, code lost:
    
        r5 = org.lsposed.hiddenapibypass.library.C0003.m277(r13, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0073, code lost:
    
        r0 = (int) r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0074, code lost:
    
        r6 = new byte[r0];
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0077, code lost:
    
        r11 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x007a, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0081, code lost:
    
        if (r11 == 22) goto L340;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0085, code lost:
    
        if (r11 == 53) goto L338;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0089, code lost:
    
        if (r11 == 503) goto L341;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00d1, code lost:
    
        if (r7 >= r0) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x00d3, code lost:
    
        r11 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0191, code lost:
    
        r14 = 'L';
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x018c, code lost:
    
        r11 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x008d, code lost:
    
        if (r11 == 32495) goto L342;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0092, code lost:
    
        r4 = com.yan.hook.C0001.m157(r5, r6, r7, ((r0 - 11) - r7) + 11);
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x009b, code lost:
    
        r11 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x009e, code lost:
    
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static byte[] readAssetBytes(Context context, String str) {
        InputStream inputStream;
        ZipFile zipFile;
        byte[] bArr;
        char c = '-';
        try {
            zipFile = new ZipFile(C0004.m339(C0003.m297(context)));
            try {
                ZipEntry m321 = C0004.m321(zipFile, str);
                                try {
                                    C0003.m262(zipFile);
                                            return null;
                                    return null;
                                break;
                            c = '-';
                    c = '-';
                try {
                    C0003.m262(zipFile);
                            break;
                return bArr;
                return bArr;
                try {
                    C0003.m262(zipFile);
                            return null;
                    return null;
                th = th;
                        break;
                inputStream = null;
            th = th2;
            inputStream = null;
            zipFile = null;

    private static Object readParceledListSlice(Parcel parcel) {
        try {
            Object m38 = m38();
                            Method m295 = C0003.m295(C0003.m271(m38()), "createFromParcel", new Class[]{Parcel.class});
                            C0001.m171(m295, true);
                            return C0003.m293(m295, m38(), new Object[]{parcel});
                            return null;
            return null;

    /* JADX WARN: Code restructure failed: missing block: B:109:0x00c7, code lost:
    
        r10 = r6[r7];
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x00cb, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m325(r17, r10);
        com.yan.hook.C0002.m203(r17);
        org.lsposed.hiddenapibypass.library.C0004.m337(r17);
        r14 = com.yan.hook.C0002.m203(r17);
        r15 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x00db, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x00e0, code lost:
    
        if (r15 == 17) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x00e2, code lost:
    
        if (r15 == 50) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x00e4, code lost:
    
        if (r15 == 76) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00e8, code lost:
    
        if (r15 == 83) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x00ed, code lost:
    
        r3 = m101();
        com.yan.hook.PmsHookProvider.DUMP_SM = (org.lsposed.hiddenapibypass.library.C0003.f12 ^ 684) + r3;
        r11 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x00fb, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0101, code lost:
    
        if (r11 == 18) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0105, code lost:
    
        if (r11 == 51) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x0109, code lost:
    
        if (r11 == 84) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x010d, code lost:
    
        if (r11 == 241) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0111, code lost:
    
        if (r3 >= 6) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0176, code lost:
    
        r11 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0113, code lost:
    
        r11 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0117, code lost:
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(com.yan.hook.C0001.m167(new java.lang.StringBuilder(com.yan.hook.C0002.m204("FakeSM try st=")), r10), org.lsposed.hiddenapibypass.library.C0004.m323(m19(), 304, com.yan.hook.C0001.f5 ^ (-588), 706)), r14)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x014c, code lost:
    
        r3 = com.yan.hook.C0002.m200(r9, r14);
        r9 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0153, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0159, code lost:
    
        if (r9 == 497) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x016c, code lost:
    
        if (r3 == false) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x016e, code lost:
    
        r9 = 50658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:169:0x0172, code lost:
    
        r9 = 50627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x015d, code lost:
    
        if (r9 == 1711) goto L200;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x0161, code lost:
    
        if (r9 == 1736) goto L197;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0165, code lost:
    
        if (r9 == 1769) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0168, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m325(r17, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x016b, code lost:
    
        return r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x0181, code lost:
    
        r15 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x017a, code lost:
    
        if (r14 == null) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:193:0x017c, code lost:
    
        r15 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x01a1, code lost:
    
        if (r7 < 5) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:198:0x01a3, code lost:
    
        r10 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0018, code lost:
    
        r1 = org.lsposed.hiddenapibypass.library.C0003.m309(r17);
        r6 = new int[]{12, 0, 4, 8, 16};
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0031, code lost:
    
        r7 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0032, code lost:
    
        r9 = com.yan.hook.C0002.m191(m19(), 297, org.lsposed.hiddenapibypass.library.C0003.f12 ^ 682, 1433);
        r10 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0044, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0048, code lost:
    
        if (r10 == 17) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x004c, code lost:
    
        if (r10 == 54) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x01a6, code lost:
    
        r10 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x01a8, code lost:
    
        r3 = 239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0052, code lost:
    
        if (r10 == 471) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0056, code lost:
    
        if (r10 == 500) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0059, code lost:
    
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x005a, code lost:
    
        r7 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x005c, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x005e, code lost:
    
        if (r7 == 17) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00bd, code lost:
    
        if (r10 < 5) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00bf, code lost:
    
        r7 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c3, code lost:
    
        r7 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0063, code lost:
    
        if (r7 == 47384) goto L150;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0068, code lost:
    
        if (r7 == 47417) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x006d, code lost:
    
        if (r7 == 47483) goto L151;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0070, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m325(r17, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0073, code lost:
    
        return null;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0076, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m325(r17, r6[r10]);
        com.yan.hook.C0002.m203(r17);
        r7 = com.yan.hook.C0002.m203(r17);
        r8 = com.yan.hook.C0002.m200(r9, r7);
        r12 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0087, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x008b, code lost:
    
        if (r12 == 14) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x008f, code lost:
    
        if (r12 == 45) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0091, code lost:
    
        if (r12 == 76) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0093, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0096, code lost:
    
        if (r8 == false) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00a0, code lost:
    
        r12 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0098, code lost:
    
        r12 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x009c, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m325(r17, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x009f, code lost:
    
        return r7;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String readServiceName(Parcel parcel) {
        char c;
                        break;
                    c = 239;
                        try {
                            break;
                            return null;
        int i2 = i2 + (C0001.f5 ^ (-580));
        int i4 = i4 + (C0004.f13 ^ (-364));
        c = 239;

    /* JADX WARN: Code restructure failed: missing block: B:102:0x00e4, code lost:
    
        if (r6 == 32495(0x7eef, float:4.5535E-41)) goto L213;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00e6, code lost:
    
        r19 = r6 ? 1 : 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x00eb, code lost:
    
        r6 = r12 instanceof android.os.IBinder;
        r8 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x00f0, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x00f5, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x02d3, code lost:
    
        if (r6 == false) goto L229;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x02d5, code lost:
    
        r4 = 'L';
        r5 = 17;
        r8 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x02e1, code lost:
    
        r4 = 'L';
        r5 = 17;
        r8 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x00f9, code lost:
    
        if (r8 == 50) goto L225;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x00fd, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0101, code lost:
    
        if (r8 == 83) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0103, code lost:
    
        r4 = 'L';
        r5 = 17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0108, code lost:
    
        r4 = com.yan.hook.C0002.m223(org.lsposed.hiddenapibypass.library.C0003.m271(r12));
        r6 = org.lsposed.hiddenapibypass.library.C0003.m270(r4, m50());
        r0 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x011b, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x011f, code lost:
    
        if (r0 == 18) goto L238;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0123, code lost:
    
        if (r0 == 53) goto L235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0127, code lost:
    
        if (r0 == 243) goto L236;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x01c0, code lost:
    
        if (r6 == false) goto L239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x01cd, code lost:
    
        r0 = 50875;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x01c2, code lost:
    
        r0 = 51557;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x012b, code lost:
    
        if (r0 == 4075) goto L237;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x012e, code lost:
    
        r0 = m22(r12, r23, (com.yan.hook.C0000.f4 ^ 86) + r24);
        r5 = 51588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x013a, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0140, code lost:
    
        if (r5 == 17) goto L246;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x01ab, code lost:
    
        if (r0 <= 0) goto L253;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x01ad, code lost:
    
        r5 = 51681;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x01b4, code lost:
    
        r5 = 51650;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x0144, code lost:
    
        if (r5 == 54) goto L247;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x0148, code lost:
    
        if (r5 == 87) goto L248;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x014c, code lost:
    
        if (r5 == 116) goto L249;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x014f, code lost:
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(com.yan.hook.C0001.m167(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("smCacheHook: nested ")), r4), com.yan.hook.C0002.m191(m19(), 347, com.yan.hook.C0000.f4 ^ 93, 3049)), r0)));
        r15 = r15 - (0 - r0);
        r0 = 51712;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x018c, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x0192, code lost:
    
        if (r0 == 14) goto L258;
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x0196, code lost:
    
        if (r0 == 17) goto L259;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x0199, code lost:
    
        r0 = 51743;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x019d, code lost:
    
        r6 = r15;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0348, code lost:
    
        r0 = 52518;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0337, code lost:
    
        if (r0 != 1963) goto L268;
     */
    /* JADX WARN: Code restructure failed: missing block: B:212:0x01d2, code lost:
    
        r4 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x01d9, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:214:0x01dd, code lost:
    
        if (r4 == 18) goto L271;
     */
    /* JADX WARN: Code restructure failed: missing block: B:216:0x01e1, code lost:
    
        if (r4 == 51) goto L269;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x01e5, code lost:
    
        if (r4 == 84) goto L172;
     */
    /* JADX WARN: Code restructure failed: missing block: B:220:0x01e9, code lost:
    
        if (r4 == 241) goto L270;
     */
    /* JADX WARN: Code restructure failed: missing block: B:222:0x01ec, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x02b2, code lost:
    
        r4 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x01ee, code lost:
    
        r4 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x01f2, code lost:
    
        m15(r22, r0, r23);
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("smCacheHook: ")), org.lsposed.hiddenapibypass.library.C0004.m330(org.lsposed.hiddenapibypass.library.C0003.m271(r22))), com.yan.hook.C0001.m169(m19(), 331, 1, 2135)), r7), org.lsposed.hiddenapibypass.library.C0003.m285(m19(), 332, com.yan.hook.C0001.f5 ^ (-587), 3163))));
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x0290, code lost:
    
        r6 = r15 + (com.yan.hook.C0002.f6 ^ (-481));
        r0 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x029b, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:235:0x02a1, code lost:
    
        if (r0 == 497) goto L278;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x02ae, code lost:
    
        r0 = 50596;
     */
    /* JADX WARN: Code restructure failed: missing block: B:242:0x02a5, code lost:
    
        if (r0 == 1711) goto L279;
     */
    /* JADX WARN: Code restructure failed: missing block: B:246:0x0246, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x0247, code lost:
    
        m9(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204("smCacheHook: set ")), r7), com.yan.hook.C0001.m169(m19(), 340, com.yan.hook.C0001.f5 ^ (-582), 2084))), r0);
        r0 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x027d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:249:0x0283, code lost:
    
        if (r0 != 19) goto L286;
     */
    /* JADX WARN: Code restructure failed: missing block: B:251:0x028a, code lost:
    
        r0 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x0287, code lost:
    
        if (r0 != 50) goto L289;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0090, code lost:
    
        r7 = new java.lang.String[]{r9, r10, r11, r13}[r14];
        r0 = m51(org.lsposed.hiddenapibypass.library.C0003.m271(r22), r7);
        r16 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00a1, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00a6, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x00aa, code lost:
    
        if (r12 == 45) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x00ac, code lost:
    
        r4 = 'L';
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x00ae, code lost:
    
        if (r12 == 76) goto L174;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00b0, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00bb, code lost:
    
        if (r0 == null) goto L206;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x034c, code lost:
    
        r4 = 14;
        r5 = 17;
        r6 = 239;
        r16 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x00bd, code lost:
    
        r4 = 14;
        r16 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x00b2, code lost:
    
        r16 = r12 ? 1 : 0;
        r4 = 14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x00ca, code lost:
    
        r12 = m47(r22, r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x00ce, code lost:
    
        r19 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00d1, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x00d8, code lost:
    
        if (r6 == 22) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x00dc, code lost:
    
        if (r6 == 53) goto L215;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x00e0, code lost:
    
        if (r6 == 503(0x1f7, float:7.05E-43)) goto L212;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x02f5, code lost:
    
        if (r12 == null) goto L217;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x030d, code lost:
    
        r4 = 'L';
        r5 = 17;
        r19 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x02f7, code lost:
    
        r4 = 'L';
        r5 = 17;
        r19 = 49635;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0348 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:197:0x0335 A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static int replaceBinderFields(Object obj, IBinder iBinder, int i) {
        char c;
        char c2;
        char c3;
        int i2;
        int i3;
            c = 14;
                    c2 = 239;
                            c3 = 17;
                                        break;
                                        String m1 = "mServiceManager";
                                        String m285 = C0003."mRemote";
                                        String m2852 = C0003.m285(m19(), 320, C0000.f4 ^ 84, 1536);
                                        String m2853 = C0003.m285(m19(), 323, C0000.f4 ^ 95, 1983);
                                                if (i6 != c3) {
                                                        break;
                                                        return i3;
                                                    c = 14;
                                                    c3 = 17;
                                                    c2 = 239;
                                                c = 14;
                                                c3 = 17;
                                                c2 = 239;
                                            i2 = r4;
                                            i3 = r6;
                                            c = 14;
                                            c3 = 17;
                                            c2 = 239;
                                c = 14;
                                c2 = 239;
                            c = 14;
                            c2 = 239;
                        break;
        return 0;
        int i7 = i3;
        int i8 = i2 + (C0003.f12 ^ 684);
        i2 = i8;
        i3 = i7;
        c = 14;
        c3 = 17;
        c2 = 239;

    private void replaceCreator(Class<?> cls) throws Exception {
        Field m51 = m51(cls, C0003.m285(m19(), 357, C0003.f12 ^ 682, 3230));
                        return;
                        Object m78 = m78(m51);
                                i2 = m78 == null ? 1833 : 1802;
                                    m14(m51, C0001.m185(new FakeClassLoader(), new Class[]{C0004.m342(m56())}, new InvocationHandler(this, m78) { // from class: com.yan.hook.PmsHookProvider.1
                                        final PmsHookProvider this$0;
                                        private final Object val$orig;

                                        {
                                            this.this$0 = this;
                                            this.val$orig = m78;
                                            int m274 = C0003.m274();
                                                            return;
                                                            return;

                                        /* renamed from: ۣ۠۟۟, reason: not valid java name and contains not printable characters */
                                        public static Object m103(Object obj) {
                                            int m138 = C0000.m138();
                                                            return ((AnonymousClass1) obj).val$orig;
                                                                    return null;

                                        /* renamed from: ۣۡ۟۠, reason: not valid java name and contains not printable characters */
                                        public static void m104(Object obj, Object obj2) {
                                            int m274 = C0003.m274();
                                                            PmsHookProvider.m98((PmsHookProvider) obj, obj2);
                                                            break;
                                                                    break;
                                            int m143 = C0001.m143();
                                                    i5 = m143 <= 0 ? 1833 : 1802;
                                                        return;
                                                        return;

                                        /* renamed from: ۣۡۨ۠, reason: not valid java name and contains not printable characters */
                                        public static PmsHookProvider m105(Object obj) {
                                            int m138 = C0000.m138();
                                                            return ((AnonymousClass1) obj).this$0;
                                                                    return null;

                                        /* renamed from: ۥۣ۠۠, reason: contains not printable characters */
                                        public static String m106(Object obj) {
                                            int m316 = C0004.m316();
                                                            return PmsHookProvider.m1((int[]) obj);
                                                                    return null;

                                        @Override // java.lang.reflect.InvocationHandler
                                        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
                                            boolean m200 = C0002.m200(m106(new int[]{99, 114, 101, 97, 116, 101, 70, 114, 111, 109, 80, 97, 114, 99, 101, 108}), C0002.m210(method));
                                                                    i4 = objArr != null ? 1833 : 1802;
                                                                        break;
                                                                        int length = objArr.length;
                                                                                i5 = length == 1 ? 48736 : 48705;
                                                                                    break;
                                                                                    Object m293 = C0003.m293(method, m103(this), objArr);
                                                                                    try {
                                                                                        m104(m105(this), m293);
                                                                                                break;
                                                                                    return m293;
                                                            break;
                                            return C0003.m293(method, m103(this), objArr);
                                    int m138 = C0000.m138();
                                            i3 = m138 >= 0 ? 48736 : 48705;
                                                return;
                                                return;
                                    return;

    private void replaceSigningInfo(PackageInfo packageInfo) {
        Object m37;
        try {
            SigningInfo m280 = C0003.m280(packageInfo);
                            int m143 = C0001.m143();
                                    i2 = m143 <= 0 ? 1833 : 1802;
                                        return;
                                        return;
                            Field m0 = m0();
                                    i3 = m0 != null ? 48736 : 48705;
                                        m37 = m37(m280, "mSigningDetails");
                                        break;
                                        m37 = m47(m280, m0());
                                                break;
                                        Field m41 = m41();
                                                i6 = m41 != null ? 49759 : 49728;
                                                    boolean m15 = m15(m37, m41(), m25(this));
                                                                break;
                                                                return;
                                                                if (m15) {
                                                    break;
                                        boolean m66 = m66(m37, C0001."signatures", m25(this));
                                                i8 = !m66 ? 50658 : 50627;
                                                    return;
                                                    m66(m37, C0002.m191(m19(), 374, C0000.f4 ^ 92, 1037), m25(this));
                                                            return;
                                            return;

    private static void resolveTransactions() {
        try {
            Class m342 = C0004.m342(m95());
            TX_getPackageInfo = m40(m342, "TRANSACTION_getPackageInfo", m77());
            TX_getPackageInfoVersioned = m40(m342, "TRANSACTION_getPackageInfoVersioned", m46());
            TX_getApplicationInfo = m40(m342, "TRANSACTION_getApplicationInfo", m83());
            TX_getApplicationInfoVersioned = m40(m342, "TRANSACTION_getApplicationInfoVersioned", m12());
            TX_getPackageArchiveInfo = m40(m342, "TRANSACTION_getPackageArchiveInfo", m27());
            TX_getInstalledPackages = m40(m342, "TRANSACTION_getInstalledPackages", m55());
            TX_getInstalledApplications = m40(m342, "TRANSACTION_getInstalledApplications", m90());
            TX_hasSigningCertificate = m40(m342, "TRANSACTION_hasSigningCertificate", m49());
                    return;

    private static void restoreHiddenApi() {

    /* JADX WARN: Code restructure failed: missing block: B:137:0x009b, code lost:
    
        r11 = org.lsposed.hiddenapibypass.library.C0004.m329(r4);
        r12 = r11 instanceof android.content.pm.ApplicationInfo;
        r10 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x00a4, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x00aa, code lost:
    
        if (r10 == 18) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x00ae, code lost:
    
        if (r10 == 51) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x00b2, code lost:
    
        if (r10 == 84) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x00b6, code lost:
    
        if (r10 == 241) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x00b9, code lost:
    
        if (r12 == false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0107, code lost:
    
        r10 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x00bb, code lost:
    
        r10 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x00bf, code lost:
    
        r11 = (android.content.pm.ApplicationInfo) r11;
        r10 = com.yan.hook.C0002.m200(m80(r14), com.yan.hook.C0002.m221(r11));
        r12 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x00d0, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x00d6, code lost:
    
        if (r12 == 497) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x00fd, code lost:
    
        if (r10 == false) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x00ff, code lost:
    
        r12 = 50658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0103, code lost:
    
        r12 = 50627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x00da, code lost:
    
        if (r12 == 1711) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x00de, code lost:
    
        if (r12 == 1736) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x00e2, code lost:
    
        if (r12 == 1769) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x00e5, code lost:
    
        m87(r14, r11);
        r9 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x00eb, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x00f1, code lost:
    
        if (r9 == 19) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x00f9, code lost:
    
        r9 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x00f3, code lost:
    
        if (r9 == 50) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x00f6, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x011c, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m325(r15, 0);
        r1 = org.lsposed.hiddenapibypass.library.C0003.m274();
        r15 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0126, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x012a, code lost:
    
        if (r15 == 17) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0145, code lost:
    
        if (r1 > 0) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0147, code lost:
    
        r15 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x014b, code lost:
    
        r15 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x012c, code lost:
    
        if (r15 == 50) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x012e, code lost:
    
        if (r15 == 76) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0132, code lost:
    
        if (r15 == 83) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0135, code lost:
    
        java.lang.System.out.println(java.lang.Double.parseDouble(org.lsposed.hiddenapibypass.library.C0004.m346("iW4BXyLu82")));
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0144, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void rewriteApplicationInfoListReply(Parcel parcel) {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
                        Object m58 = m58(parcel);
                                i2 = m58 != null ? 1833 : 1802;
                                    break;
                                    List m71 = m71(m58);
                                            i3 = m71 != null ? 48736 : 48705;
                                                break;
                                                Iterator m287 = C0003.m287(m71);
                                                boolean z = false;
                                                loop4: while (true) {
                                                    boolean m320 = C0004.m320(m287);
                                                                break;
                                                                break loop4;
                                                                if (!m320) {
                                                            m8(parcel, m58, m71);
                                                            return;
                                                                break;
                        break;

    private void rewriteApplicationInfoReply(Parcel parcel) {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
                        int m3372 = C0004.m337(parcel);
                                i2 = m3372 != 0 ? 1833 : 1802;
                                    break;
                                    ApplicationInfo applicationInfo = (ApplicationInfo) C0002.m232(C0002.m218(), parcel);
                                            i3 = applicationInfo != null ? 48736 : 48705;
                                                break;
                                                String m80 = m80(this);
                                                            break;
                                                            boolean m200 = C0002.m200(m80(this), C0002.m221(applicationInfo));
                                                                        break;
                                                                            m87(this, applicationInfo);
                                                                            m52(C0003.m281(C0004.m336(new StringBuilder(C0002.m204("rewrite: AppInfo pkg=")), C0002.m221(applicationInfo))));
                                                                            Parcel m299 = C0003.m299();
                                                                            try {
                                                                                C0002.m222(m299);
                                                                                C0004.m344(m299, 1);
                                                                                C0003.m279(applicationInfo, m299, 0);
                                                                                C0004.m325(parcel, 0);
                                                                                C0002.m202(parcel, 0);
                                                                                C0004.m345(parcel, m299, 0, C0004.m335(m299));
                                                                                C0004.m325(parcel, 0);
                                                                                C0003.m290(m299);
                                                                                int m316 = C0004.m316();
                                                                                        i6 = m316 >= 0 ? 49759 : 49728;
                                                                                            return;
                                                                                            return;
                                                                                C0003.m290(m299);
                                                                                throw th;
                                                            if (m80 != null) {
                        break;
        C0004.m325(parcel, 0);

    /* JADX WARN: Code restructure failed: missing block: B:137:0x009b, code lost:
    
        r11 = org.lsposed.hiddenapibypass.library.C0004.m329(r4);
        r12 = r11 instanceof android.content.pm.PackageInfo;
        r10 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x00a4, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x00aa, code lost:
    
        if (r10 == 18) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x00ae, code lost:
    
        if (r10 == 51) goto L163;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x00b2, code lost:
    
        if (r10 == 84) goto L160;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x00b6, code lost:
    
        if (r10 == 241) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x00b9, code lost:
    
        if (r12 == false) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0107, code lost:
    
        r10 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x00bb, code lost:
    
        r10 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x00bf, code lost:
    
        r11 = (android.content.pm.PackageInfo) r11;
        r10 = com.yan.hook.C0002.m200(m80(r14), com.yan.hook.C0001.m162(r11));
        r12 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x00d0, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x00d6, code lost:
    
        if (r12 == 497) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x00fd, code lost:
    
        if (r10 == false) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x00ff, code lost:
    
        r12 = 50658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:167:0x0103, code lost:
    
        r12 = 50627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x00da, code lost:
    
        if (r12 == 1711) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x00de, code lost:
    
        if (r12 == 1736) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:177:0x00e2, code lost:
    
        if (r12 == 1769) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:179:0x00e5, code lost:
    
        m32(r14, r11);
        r9 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x00eb, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:181:0x00f1, code lost:
    
        if (r9 == 19) goto L204;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x00f9, code lost:
    
        r9 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:187:0x00f3, code lost:
    
        if (r9 == 50) goto L205;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x00f6, code lost:
    
        r9 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x011c, code lost:
    
        org.lsposed.hiddenapibypass.library.C0004.m325(r15, 0);
        r1 = org.lsposed.hiddenapibypass.library.C0003.m274();
        r15 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0126, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x012a, code lost:
    
        if (r15 == 17) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x0145, code lost:
    
        if (r1 > 0) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0147, code lost:
    
        r15 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x014b, code lost:
    
        r15 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x012c, code lost:
    
        if (r15 == 50) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x012e, code lost:
    
        if (r15 == 76) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x0132, code lost:
    
        if (r15 == 83) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:?, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0135, code lost:
    
        java.lang.System.out.println(java.lang.Long.decode(com.yan.hook.C0001.m158("eOCOSoqP")));
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0144, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private void rewritePackageInfoListReply(Parcel parcel) {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
                        Object m58 = m58(parcel);
                                i2 = m58 != null ? 1833 : 1802;
                                    break;
                                    List m71 = m71(m58);
                                            i3 = m71 != null ? 48736 : 48705;
                                                break;
                                                Iterator m287 = C0003.m287(m71);
                                                boolean z = false;
                                                loop4: while (true) {
                                                    boolean m320 = C0004.m320(m287);
                                                                break;
                                                                break loop4;
                                                                if (!m320) {
                                                            m8(parcel, m58, m71);
                                                            return;
                                                                break;
                        break;

    private void rewritePackageInfoReply(Parcel parcel) {
        String m162;
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
        m52(C0003.m281(C0001.m167(new StringBuilder(C0002.m204("rewrite: exception=")), m337)));
                        int m3372 = C0004.m337(parcel);
                                i2 = m3372 != 0 ? 1833 : 1802;
                                    break;
                                    PackageInfo packageInfo = (PackageInfo) C0002.m232(C0002.m214(), parcel);
                                    StringBuilder sb = new StringBuilder(C0002.m204("rewrite: pkg="));
                                            i3 = packageInfo == null ? 48736 : 48705;
                                                m162 = C0001.m162(packageInfo);
                                                break;
                                                m162 = C0003."null";
                                                        break;
                                    m52(C0003.m281(C0004.m336(sb, m162)));
                                                break;
                                                    String m80 = m80(this);
                                                            i6 = m80 != null ? 49759 : 49728;
                                                                boolean m200 = C0002.m200(m80(this), C0001.m162(packageInfo));
                                                                            break;
                                                                            m32(this, packageInfo);
                                                                            Parcel m299 = C0003.m299();
                                                                            try {
                                                                                C0002.m222(m299);
                                                                                C0004.m344(m299, 1);
                                                                                C0002.m209(packageInfo, m299, 0);
                                                                                C0004.m325(parcel, 0);
                                                                                C0002.m202(parcel, 0);
                                                                                C0004.m345(parcel, m299, 0, C0004.m335(m299));
                                                                                C0004.m325(parcel, 0);
                                                                                C0003.m290(m299);
                                                                                int m274 = C0003.m274();
                                                                                        i8 = m274 <= 0 ? 50658 : 50627;
                                                                                            return;
                                                                                            return;
                                                                                C0003.m290(m299);
                                                                                throw th;
                                                                            if (m200) {
                                                                break;
                        break;
        C0004.m325(parcel, 0);

    private static boolean setObjField(Object obj, String str, Object obj2) {
                        return false;
                        Field m51 = m51(C0003.m271(obj), str);
                                i2 = m51 == null ? 1833 : 1802;
                                    return m15(obj, m51, obj2);
                                    return false;

    private Signature[] sigs() {
        Signature[] m33 = m33(this);
                                return null;
                        return (Signature[]) m10(m33(this));

    private static byte[] soCrypt(byte[] bArr, String str) throws Exception {
                        MessageDigest m211 = C0002.m211(C0003."SHA-256");
                        String m1 = "YTSOKEY1";
                        String m191 = C0002.m191(m19(), 396, C0003.f12 ^ 680, 1426);
                        C0004.m322(m211, C0003.m294(m1, m191));
                        C0004.m322(m211, C0003.m294(str, m191));
                        byte[] m319 = C0004.m319(m211);
                        C0001.m170(m211);
                        C0004.m322(m211, C0003.m294("YTSOIV01", m191));
                        C0004.m322(m211, C0003.m294(str, m191));
                        byte[] m3192 = C0004.m319(m211);
                        String m285 = C0003."AES";
                        int i2 = C0001.f5 ^ (-595);
                        SecretKeySpec secretKeySpec = new SecretKeySpec(m319, 0, i2, m285);
                        IvParameterSpec ivParameterSpec = new IvParameterSpec(m3192, 0, i2);
                        Cipher m151 = C0001.m151("AES/CTR/NoPadding");
                        C0003.m264(m151, C0004.f13 ^ (-361), secretKeySpec, ivParameterSpec);
                        return C0002.m216(m151, bArr);
                        throw new IllegalArgumentException(C0004.m323(m19(), 404, C0000.f4 ^ 95, 1720));

    private static int staticInt(Class<?> cls, String str, int i) {
        try {
            Field m51 = m51(cls, str);
                            return i;
                            Object m61 = m61();
                                    i3 = m61 != null ? 1833 : 1802;
                                        C0001.m152(m51, true);
                                        return C0004.m331(m51, null);
                                        return C0001.m155((Number) C0003.m293(C0003.m295(C0003.m271(m61), C0002."getInt", new Class[]{Object.class, C0004.m343()}), m61, new Object[]{C0003.m293(C0003.m295(C0003.m271(m61), "staticFieldBase", new Class[]{Field.class}), m61, new Object[]{m51}), C0001.m166(C0002.m224((Number) C0003.m293(C0003.m295(C0003.m271(m61), "staticFieldOffset", new Class[]{Field.class}), m61, new Object[]{m51})))}));
            return i;

    private static Object uAllocate(Class<?> cls) {
                        break;
                        try {
                            Object m61 = m61();
                                    i2 = m61 != null ? 1833 : 1802;
                                        break;
                                        return C0003.m293(C0003.m295(C0003.m271(m61), "allocateInstance", new Class[]{Class.class}), m61, new Object[]{cls});
        return null;

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0045, code lost:
    
        r5 = m63(r4, r11);
        r2 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x004c, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0050, code lost:
    
        if (r2 == 14) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0054, code lost:
    
        if (r2 == 45) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0058, code lost:
    
        if (r2 == 76) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x005a, code lost:
    
        if (r2 == 239) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0061, code lost:
    
        if (r5 < 0) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x009c, code lost:
    
        r2 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0063, code lost:
    
        r2 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x009b, code lost:
    
        return org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r4), com.yan.hook.C0002.m191(m19(), 418, com.yan.hook.C0001.f5 ^ (-588), 1637), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343()}), r4, new java.lang.Object[]{r10, com.yan.hook.C0001.m166(r5)});
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Object uGet(Object obj, Field field) {
                                i2 = field != null ? 1833 : 1802;
                                    return null;
                                    try {
                                        Object m61 = m61();
                                                i3 = m61 != null ? 48736 : 48705;
                                                    break;
                                    try {
                                        C0001.m152(field, true);
                                        return C0003.m283(field, obj);
                                        return null;
                        return null;

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0017, code lost:
    
        r1 = m61();
        r3 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x001d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0021, code lost:
    
        if (r3 == 17) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00ab, code lost:
    
        if (r1 == null) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ad, code lost:
    
        r3 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b1, code lost:
    
        r3 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0025, code lost:
    
        if (r3 == 54) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0029, code lost:
    
        if (r3 == 471) goto L38;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x002d, code lost:
    
        if (r3 == 500) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00aa, code lost:
    
        return org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), org.lsposed.hiddenapibypass.library.C0004.m323(m19(), 427, com.yan.hook.C0000.f4 ^ 94, 391), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343()}), r1, new java.lang.Object[]{org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), "staticFieldBase", new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12}), com.yan.hook.C0001.m166(com.yan.hook.C0002.m224((java.lang.Number) org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), "staticFieldOffset", new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12})))});
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static Object uGetStatic(Field field) {
                        break;
                        break;
        try {
            C0001.m152(field, true);
            return C0003.m283(field, null);
            return null;

    /* JADX WARN: Code restructure failed: missing block: B:48:0x002c, code lost:
    
        r5 = m61();
        r6 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0032, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0034, code lost:
    
        if (r6 == 17) goto L89;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x00a6, code lost:
    
        if (r5 == null) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x00a8, code lost:
    
        r6 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00ac, code lost:
    
        r6 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0039, code lost:
    
        if (r6 == 47384) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x003e, code lost:
    
        if (r6 == 47417) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0043, code lost:
    
        if (r6 == 47483) goto L88;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0046, code lost:
    
        r6 = m63(r5, r12);
        r2 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x004d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0051, code lost:
    
        if (r2 == 14) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0055, code lost:
    
        if (r2 == 45) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0059, code lost:
    
        if (r2 == 76) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x005b, code lost:
    
        if (r2 == 239) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0062, code lost:
    
        if (r6 < 0) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x00a2, code lost:
    
        r2 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0064, code lost:
    
        r2 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0068, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r5), org.lsposed.hiddenapibypass.library.C0004.m323(m19(), 436, com.yan.hook.C0001.f5 ^ (-588), 1798), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343(), java.lang.Object.class}), r5, new java.lang.Object[]{r11, com.yan.hook.C0001.m166(r6), r13});
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00a1, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean uSet(Object obj, Field field, Object obj2) {
                                i2 = field != null ? 1833 : 1802;
                                    break;
                                    break;
                        break;
        return false;
        try {
            C0001.m152(field, true);
            C0002.m193(field, obj, obj2);
            return true;

    /* JADX WARN: Code restructure failed: missing block: B:49:0x002c, code lost:
    
        r3 = m61();
        r4 = m63(r3, r12);
        r6 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0036, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0038, code lost:
    
        if (r6 == 17) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x008e, code lost:
    
        if (r4 < 0) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0090, code lost:
    
        r6 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0094, code lost:
    
        r6 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x003d, code lost:
    
        if (r6 == 47384) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0042, code lost:
    
        if (r6 == 47417) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0047, code lost:
    
        if (r6 == 47483) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x004a, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r3), com.yan.hook.C0002.m191(m19(), 445, com.yan.hook.C0001.f5 ^ (-585), 909), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343(), org.lsposed.hiddenapibypass.library.C0003.m296()}), r3, new java.lang.Object[]{r11, com.yan.hook.C0001.m166(r4), com.yan.hook.C0001.m154(r13)});
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0089, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean uSetBoolean(Object obj, Field field, boolean z) {
                                i2 = field != null ? 1833 : 1802;
                                    break;
                                    break;
                        break;
        return false;
        try {
            C0001.m152(field, true);
            C0002.m194(field, obj, z);
            return true;

    /* JADX WARN: Code restructure failed: missing block: B:49:0x002c, code lost:
    
        r3 = m61();
        r4 = m63(r3, r12);
        r6 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0036, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0038, code lost:
    
        if (r6 == 17) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x008e, code lost:
    
        if (r4 < 0) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0090, code lost:
    
        r6 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0094, code lost:
    
        r6 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x003d, code lost:
    
        if (r6 == 47384) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0042, code lost:
    
        if (r6 == 47417) goto L77;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0047, code lost:
    
        if (r6 == 47483) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x004a, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r3), org.lsposed.hiddenapibypass.library.C0003.m285(m19(), 455, com.yan.hook.C0001.f5 ^ (-581), 824), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343(), com.yan.hook.C0002.m187()}), r3, new java.lang.Object[]{r11, com.yan.hook.C0001.m166(r4), org.lsposed.hiddenapibypass.library.C0003.m292(r13)});
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0089, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean uSetInt(Object obj, Field field, int i) {
                                i3 = field != null ? 1833 : 1802;
                                    break;
                                    break;
                        break;
        return false;
        try {
            C0001.m152(field, true);
            C0001.m146(field, obj, i);
            return true;

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0017, code lost:
    
        r1 = m61();
        r3 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x001d, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:25:0x0021, code lost:
    
        if (r3 == 17) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x00b0, code lost:
    
        if (r1 == null) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00b2, code lost:
    
        r3 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00b6, code lost:
    
        r3 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0025, code lost:
    
        if (r3 == 54) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0029, code lost:
    
        if (r3 == 471) goto L36;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x002d, code lost:
    
        if (r3 == 500) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0030, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), com.yan.hook.C0002.m191(m19(), 461, org.lsposed.hiddenapibypass.library.C0004.f13 ^ (-356), 346), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343(), java.lang.Object.class}), r1, new java.lang.Object[]{org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), "staticFieldBase", new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12}), com.yan.hook.C0001.m166(com.yan.hook.C0002.m224((java.lang.Number) org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), "staticFieldOffset", new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12}))), r13});
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00af, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static boolean uSetStatic(Field field, Object obj) {
                        break;
                        break;
        try {
            C0001.m152(field, true);
            C0002.m193(field, null, obj);
            return true;
            return false;

    private static Object unsafe() {
        try {
            Field m326 = C0004.m326(C0004.m342(m16()), C0001.m169(m19(), 470, C0000.f4 ^ 94, 2265));
            C0001.m152(m326, true);
            return C0003.m283(m326, null);
            return null;

    private static void writeParceledListSliceReply(Parcel parcel, Object obj, List<?> list) {
        try {
            try {
                Method m295 = C0003.m295(C0003.m271(obj), "writeToParcel", new Class[]{Parcel.class, C0002.m187()});
                C0001.m171(m295, true);
                Parcel m299 = C0003.m299();
                try {
                    C0002.m222(m299);
                    C0003.m293(m295, obj, new Object[]{m299, C0003.m292(0)});
                    C0004.m325(parcel, 0);
                    C0002.m202(parcel, 0);
                    C0004.m345(parcel, m299, 0, C0004.m335(m299));
                    C0004.m325(parcel, 0);
                    C0003.m290(m299);
                            return;
                    C0003.m290(m299);
                    throw th;
                C0004.m325(parcel, 0);
                        return;

    /* renamed from: ۟۟۟۠ۧ, reason: not valid java name and contains not printable characters */
    public static Field m0() {
        int m316 = C0004.m316();
                        return CACHE_SI_DETAILS;
                                return null;

    /* renamed from: ۟۟۟ۤۤ, reason: not valid java name and contains not printable characters */
    public static String m1(Object obj) {
        int m316 = C0004.m316();
                        return S((int[]) obj);
                                return null;

    /* renamed from: ۟۟۠ۨۢ, reason: not valid java name and contains not printable characters */
    public static String m2() {
        int m274 = C0003.m274();
                        return C_HAB;
                                return null;

    /* renamed from: ۟۟ۡۥۤ, reason: not valid java name and contains not printable characters */
    public static void m3(Object obj, Object obj2) {
        int m274 = C0003.m274();
                        ((PmsHookProvider) obj).rewritePackageInfoReply((Parcel) obj2);
                        break;
                                break;
        int m143 = C0001.m143();
                i3 = m143 <= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۟۟ۤ۠ۡ, reason: not valid java name and contains not printable characters */
    public static String m4() {
        int m188 = C0002.m188();
                        return C_PM_PROXY;
                                return null;

    /* renamed from: ۟۟ۨۢۧ, reason: not valid java name and contains not printable characters */
    public static String m5() {
        int m274 = C0003.m274();
                        return C_PKG_MANAGER;
                                return null;

    /* renamed from: ۟۠۟, reason: not valid java name and contains not printable characters */
    public static File m6(Object obj, Object obj2, Object obj3, Object obj4) {
        int m274 = C0003.m274();
                        return ((PmsHookProvider) obj).extractAssetFile((Context) obj2, (String) obj3, (File) obj4);
                                return null;

    /* renamed from: ۟۠۟ۥ۟, reason: not valid java name and contains not printable characters */
    public static HashMap m7() {
        int m138 = C0000.m138();
                        return OFFSET_CACHE;
                                return null;

    /* renamed from: ۟۠۠ۡۤ, reason: not valid java name and contains not printable characters */
    public static void m8(Object obj, Object obj2, Object obj3) {
        int m188 = C0002.m188();
                        writeParceledListSliceReply((Parcel) obj, obj2, (List) obj3);
                        return;
                                return;

    /* renamed from: ۟۠ۢ۟۟, reason: not valid java name and contains not printable characters */
    public static void m9(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        log((String) obj, (Throwable) obj2);
                        return;
                                return;

    /* renamed from: ۟۠ۢ۠ۧ, reason: not valid java name and contains not printable characters */
    public static Object m10(Object obj) {
        int m274 = C0003.m274();
                        return ((Signature[]) obj).clone();
                                return null;

    /* renamed from: ۟۠ۢۤۡ, reason: not valid java name and contains not printable characters */
    public static IBinder m11() {
        int m274 = C0003.m274();
                        return g_smFake;
                                return null;

    /* renamed from: ۟۠ۢۥۡ, reason: not valid java name and contains not printable characters */
    public static int m12() {
        int m316 = C0004.m316();
                        return TX_getApplicationInfoVersioned;
                                return 0;

    /* renamed from: ۟۠ۧۥۥ, reason: not valid java name and contains not printable characters */
    public static Method m13() {
        int m188 = C0002.m188();
                        return CACHE_PLS_GETLIST;
                                return null;

    /* renamed from: ۟ۡ۠ۤۨ, reason: not valid java name and contains not printable characters */
    public static boolean m14(Object obj, Object obj2) {
        int m138 = C0000.m138();
                        return uSetStatic((Field) obj, obj2);
                                return false;

    /* renamed from: ۣ۟ۡۤۨ, reason: not valid java name and contains not printable characters */
    public static boolean m15(Object obj, Object obj2, Object obj3) {
        int m188 = C0002.m188();
                        return uSet(obj, (Field) obj2, obj3);
                                return false;

    /* renamed from: ۣ۟ۡۥۤ, reason: not valid java name and contains not printable characters */
    public static String m16() {
        int m274 = C0003.m274();
                        return C_UNSAFE;
                                return null;

    /* renamed from: ۣ۟ۡۦۢ, reason: not valid java name and contains not printable characters */
    public static void m17() {
        int m316 = C0004.m316();
                        hideHookDex();
                        return;
                                return;

    /* renamed from: ۟ۡۥ۠ۦ, reason: not valid java name and contains not printable characters */
    public static boolean m18() {
        int m316 = C0004.m316();
                        return nativeLoaded;
                                return false;

    /* renamed from: ۟ۡۥۡۧ, reason: not valid java name and contains not printable characters */
    public static short[] m19() {
        int m188 = C0002.m188();
                        return f0short;
                                return null;

    /* renamed from: ۟ۡۥۧۦ, reason: not valid java name and contains not printable characters */
    public static String m20() {
        int m188 = C0002.m188();
                        return C_SM_PROXY;
                                return null;

    /* renamed from: ۟ۡۧۢ۟, reason: not valid java name and contains not printable characters */
    public static void m21(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        ((PmsHookProvider) obj).rewriteApplicationInfoListReply((Parcel) obj2);
                        return;
                                return;

    /* renamed from: ۣ۟ۡۨۡ, reason: not valid java name and contains not printable characters */
    public static int m22(Object obj, Object obj2, int i) {
        int m316 = C0004.m316();
                        return replaceBinderFields(obj, (IBinder) obj2, i);
                                return 0;

    /* renamed from: ۣ۟ۢۤ۠, reason: not valid java name and contains not printable characters */
    public static String m23() {
        int m138 = C0000.m138();
                        return C_DEX_FILE;
                                return null;

    /* renamed from: ۟ۢۦۧ۟, reason: not valid java name and contains not printable characters */
    public static void m24(Object obj, Object obj2) {
        int m274 = C0003.m274();
                        nativeInstall((String) obj, (String) obj2);
                        break;
                                break;
        int m316 = C0004.m316();
                i3 = m316 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۟ۢۨۤۦ, reason: not valid java name and contains not printable characters */
    public static Signature[] m25(Object obj) {
        int m274 = C0003.m274();
                        return ((PmsHookProvider) obj).sigs();
                                return null;

    /* renamed from: ۟ۢۨۦۥ, reason: not valid java name and contains not printable characters */
    public static void m26(Object obj) {
        int m274 = C0003.m274();
                        ((PmsHookProvider) obj).applyServiceManagerCacheHook();
                        break;
                                break;
        int m188 = C0002.m188();
                i3 = m188 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۣ۟۟۟, reason: not valid java name and contains not printable characters */
    public static int m27() {
        int m274 = C0003.m274();
                        return TX_getPackageArchiveInfo;
                                return 0;

    /* renamed from: ۣ۟ۢۢۨ, reason: not valid java name and contains not printable characters */
    public static Method m28(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        return findMethod((Class) obj, (String) obj2);
                                return null;

    /* renamed from: ۣ۟ۧ۠ۨ, reason: not valid java name and contains not printable characters */
    public static void m29(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        ((PmsHookProvider) obj).installNative((Context) obj2);
                        break;
                                break;
        int m138 = C0000.m138();
                i3 = m138 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۣ۟ۧۤۧ, reason: not valid java name and contains not printable characters */
    public static String m30() {
        int m188 = C0002.m188();
                        return C_ACTIVITY_THREAD;
                                return null;

    /* renamed from: ۣ۟ۨۤۦ, reason: not valid java name and contains not printable characters */
    public static void m31(Object obj) {
        int m138 = C0000.m138();
                        nativeInstallCtxHook((Class) obj);
                        break;
                                break;
        int m1382 = C0000.m138();
                i3 = m1382 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۣ۟ۨۤۧ, reason: not valid java name and contains not printable characters */
    public static void m32(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        ((PmsHookProvider) obj).patchInfo((PackageInfo) obj2);
                        return;
                                return;

    /* renamed from: ۣ۟ۨۦۨ, reason: not valid java name and contains not printable characters */
    public static Signature[] m33(Object obj) {
        int m188 = C0002.m188();
                        return ((PmsHookProvider) obj).origSigs;
                                return null;

    /* renamed from: ۣ۟ۨۧۦ, reason: not valid java name and contains not printable characters */
    public static String m34(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        return ((PmsHookProvider) obj).extractOrigApk((Context) obj2);
                                return null;

    /* renamed from: ۟ۤ۠۠ۦ, reason: not valid java name and contains not printable characters */
    public static String m35() {
        int m188 = C0002.m188();
                        return C_SELF;
                                return null;

    /* renamed from: ۟ۤۢۡۧ, reason: not valid java name and contains not printable characters */
    public static String m36() {
        int m316 = C0004.m316();
                        return C_BOOT_CL;
                                return null;

    /* renamed from: ۣ۟ۤۦۢ, reason: not valid java name and contains not printable characters */
    public static Object m37(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        return fieldValue(obj, (String) obj2);
                                return null;

    /* renamed from: ۟ۤۥۡۦ, reason: not valid java name and contains not printable characters */
    public static Object m38() {
        int m274 = C0003.m274();
                        return CACHE_PLS_CREATOR;
                                return null;

    /* renamed from: ۟ۤۨۢ۟, reason: not valid java name and contains not printable characters */
    public static String m39() {
        int m274 = C0003.m274();
                        return C_DEX_PATH_LIST;
                                return null;

    /* renamed from: ۟ۥ۠ۡۨ, reason: not valid java name and contains not printable characters */
    public static int m40(Object obj, Object obj2, int i) {
        int m274 = C0003.m274();
                        return staticInt((Class) obj, (String) obj2, i);
                                return 0;

    /* renamed from: ۟ۥۣۤ۠, reason: not valid java name and contains not printable characters */
    public static Field m41() {
        int m316 = C0004.m316();
                        return CACHE_SD_SIGNATURES;
                                return null;

    /* renamed from: ۟ۥۧۧۢ, reason: not valid java name and contains not printable characters */
    public static String m42() {
        int m188 = C0002.m188();
                        return DIR_NAME;
                                return null;

    /* renamed from: ۟ۥۨۧۢ, reason: not valid java name and contains not printable characters */
    public static void m43() {
        int m274 = C0003.m274();
                        freeReflection();
                        break;
                                break;
        int m316 = C0004.m316();
                i3 = m316 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۟ۦۡۢۥ, reason: not valid java name and contains not printable characters */
    public static String m44() {
        int m274 = C0003.m274();
                        return C_SERVICE;
                                return null;

    /* renamed from: ۟ۦۡۦۨ, reason: not valid java name and contains not printable characters */
    public static IBinder m45() {
        int m138 = C0000.m138();
                        return g_realSM;
                                return null;

    /* renamed from: ۟ۦۤۨۢ, reason: not valid java name and contains not printable characters */
    public static int m46() {
        int m274 = C0003.m274();
                        return TX_getPackageInfoVersioned;
                                return 0;

    /* renamed from: ۟ۦۧۡۡ, reason: not valid java name and contains not printable characters */
    public static Object m47(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        return uGet(obj, (Field) obj2);
                                return null;

    /* renamed from: ۟ۦۨ۠ۡ, reason: not valid java name and contains not printable characters */
    public static String m48() {
        int m138 = C0000.m138();
                        return C_DEX_ELEMENT;
                                return null;

    /* renamed from: ۟ۦۨ۠ۤ, reason: not valid java name and contains not printable characters */
    public static int m49() {
        int m138 = C0000.m138();
                        return TX_hasSigningCertificate;
                                return 0;

    /* renamed from: ۟ۧ۠ۡۨ, reason: not valid java name and contains not printable characters */
    public static String m50() {
        int m316 = C0004.m316();
                        return C_ANDROID_OS;
                                return null;

    /* renamed from: ۟ۧ۠ۨۥ, reason: not valid java name and contains not printable characters */
    public static Field m51(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        return findField((Class) obj, (String) obj2);
                                return null;

    /* renamed from: ۟ۧۢ۠ۡ, reason: not valid java name and contains not printable characters */
    public static void m52(Object obj) {
        int m138 = C0000.m138();
                        log((String) obj);
                        return;
                                return;

    /* renamed from: ۟ۧۤ۠, reason: not valid java name and contains not printable characters */
    public static String m53() {
        int m316 = C0004.m316();
                        return C_APP_PKG_MANAGER;
                                return null;

    /* renamed from: ۟ۧۤۡ, reason: not valid java name and contains not printable characters */
    public static void m54(Object obj) {
        int m274 = C0003.m274();
                        ((PmsHookProvider) obj).applyCtxHookNative();
                        break;
                                break;
        int m188 = C0002.m188();
                i3 = m188 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۟ۧۥ۟ۢ, reason: not valid java name and contains not printable characters */
    public static int m55() {
        int m316 = C0004.m316();
                        return TX_getInstalledPackages;
                                return 0;

    /* renamed from: ۟ۧۥۡ۠, reason: not valid java name and contains not printable characters */
    public static String m56() {
        int m316 = C0004.m316();
                        return C_CREATOR;
                                return null;

    /* renamed from: ۠۠ۤ, reason: not valid java name and contains not printable characters */
    public static String m57() {
        int m274 = C0003.m274();
                        return C_ISM;
                                return null;

    /* renamed from: ۠ۢ۟, reason: not valid java name and contains not printable characters */
    public static Object m58(Object obj) {
        int m274 = C0003.m274();
                        return readParceledListSlice((Parcel) obj);
                                return null;

    /* renamed from: ۠ۦۡۤ, reason: not valid java name and contains not printable characters */
    public static String m59(Object obj) {
        int m188 = C0002.m188();
                        return readServiceName((Parcel) obj);
                                return null;

    /* renamed from: ۡ۟ۢۢ, reason: not valid java name and contains not printable characters */
    public static void m60(Object obj, Object obj2) {
        int m274 = C0003.m274();
                        ((PmsHookProvider) obj).installCtxHook((IBinder) obj2);
                        return;
                                return;

    /* renamed from: ۡ۟ۤۤ, reason: not valid java name and contains not printable characters */
    public static Object m61() {
        int m138 = C0000.m138();
                        return unsafe();
                                return null;

    /* renamed from: ۡۡۢۡ, reason: not valid java name and contains not printable characters */
    public static void m62(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        bindFake(obj, (IBinder) obj2);
                        break;
                                break;
        int m188 = C0002.m188();
                i3 = m188 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۡۢۡۦ, reason: not valid java name and contains not printable characters */
    public static long m63(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        return fieldOffset(obj, (Field) obj2);
                                return 0L;

    /* renamed from: ۡۢۧۤ, reason: not valid java name and contains not printable characters */
    public static String m64() {
        int m188 = C0002.m188();
                        return C_BDCL;
                                return null;

    /* renamed from: ۣۡۥۤ, reason: not valid java name and contains not printable characters */
    public static Field m65() {
        int m138 = C0000.m138();
                        return CACHE_AI_SIGNATURES;
                                return null;

    /* renamed from: ۡۥۣۢ, reason: not valid java name and contains not printable characters */
    public static boolean m66(Object obj, Object obj2, Object obj3) {
        int m274 = C0003.m274();
                        return setObjField(obj, (String) obj2, obj3);
                                return false;

    /* renamed from: ۡۥۧ, reason: not valid java name and contains not printable characters */
    public static String m67() {
        int m274 = C0003.m274();
                        return pickAbi();
                                return null;

    /* renamed from: ۢ۠, reason: not valid java name and contains not printable characters */
    public static String m68() {
        int m274 = C0003.m274();
                        return C_SIGNING_INFO;
                                return null;

    /* renamed from: ۢۤۡۡ, reason: not valid java name and contains not printable characters */
    public static void m69(Object obj) {
        int m138 = C0000.m138();
                        patchGetService((Parcel) obj);
                        return;
                                return;

    /* renamed from: ۢۦۢۤ, reason: not valid java name and contains not printable characters */
    public static void m70() {
        int m188 = C0002.m188();
                        resolveTransactions();
                        return;
                                return;

    /* renamed from: ۣۡ۟, reason: not valid java name and contains not printable characters */
    public static List m71(Object obj) {
        int m316 = C0004.m316();
                        return plsGetList(obj);
                                return null;

    /* renamed from: ۣۣۤ۠, reason: not valid java name and contains not printable characters */
    public static Signature[] m72(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        return ((PmsHookProvider) obj).loadSignatures((Context) obj2);
                                return null;

    /* renamed from: ۣۦ۟۟, reason: not valid java name and contains not printable characters */
    public static String m73() {
        int m188 = C0002.m188();
                        return SELF_CLASS;
                                return null;

    /* renamed from: ۤ۟ۥۦ, reason: not valid java name and contains not printable characters */
    public static String m74(Object obj) {
        int m138 = C0000.m138();
                        return ((PmsHookProvider) obj).origApkPath;
                                return null;

    /* renamed from: ۤۡۥۣ, reason: not valid java name and contains not printable characters */
    public static void m75(Object obj, Object obj2) {
        int m274 = C0003.m274();
                        ((PmsHookProvider) obj).rewriteApplicationInfoReply((Parcel) obj2);
                        break;
                                break;
        int m138 = C0000.m138();
                i3 = m138 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۣۣۤۢ, reason: not valid java name and contains not printable characters */
    public static File m76(Object obj, Object obj2) {
        int m138 = C0000.m138();
                        return ((PmsHookProvider) obj).extractNative((Context) obj2);
                                return null;

    /* renamed from: ۤۢۧ۠, reason: not valid java name and contains not printable characters */
    public static int m77() {
        int m274 = C0003.m274();
                        return TX_getPackageInfo;
                                return 0;

    /* renamed from: ۤۦۧۨ, reason: not valid java name and contains not printable characters */
    public static Object m78(Object obj) {
        int m188 = C0002.m188();
                        return uGetStatic((Field) obj);
                                return null;

    /* renamed from: ۤۧۥۣ, reason: not valid java name and contains not printable characters */
    public static void m79(Object obj) {
        int m316 = C0004.m316();
                        installFakeCache((IBinder) obj);
                        return;
                                return;

    /* renamed from: ۤۧۧۢ, reason: not valid java name and contains not printable characters */
    public static String m80(Object obj) {
        int m316 = C0004.m316();
                        return ((PmsHookProvider) obj).appPkg;
                                return null;

    /* renamed from: ۤۧۨۡ, reason: not valid java name and contains not printable characters */
    public static void m81(Object obj) {
        int m188 = C0002.m188();
                        patchCheckService2((Parcel) obj);
                        break;
                                break;
        int m274 = C0003.m274();
                i3 = m274 <= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۥ۠ۤۧ, reason: contains not printable characters */
    public static boolean m82(Object obj) {
        int m316 = C0004.m316();
                        return containsSelf(obj);
                                return false;

    /* renamed from: ۥۡۡ۠, reason: contains not printable characters */
    public static int m83() {
        int m274 = C0003.m274();
                        return TX_getApplicationInfo;
                                return 0;

    /* renamed from: ۥۢۤ۟, reason: contains not printable characters */
    public static void m84(Object obj, Object obj2) {
        int m138 = C0000.m138();
                        ((PmsHookProvider) obj).replaceCreator((Class) obj2);
                        break;
                                break;
        int m188 = C0002.m188();
                i3 = m188 >= 0 ? 1833 : 1802;
                    return;
                    return;

    /* renamed from: ۥۢۦۧ, reason: contains not printable characters */
    public static void m85() {
        int m316 = C0004.m316();
                        prefetchFields();
                        return;
                                return;

    /* renamed from: ۥۣۢۧ, reason: contains not printable characters */
    public static IBinder m86() {
        int m188 = C0002.m188();
                        return getRealServiceManagerBinder();
                                return null;

    /* renamed from: ۥۣ۟ۡ, reason: contains not printable characters */
    public static void m87(Object obj, Object obj2) {
        int m274 = C0003.m274();
                        ((PmsHookProvider) obj).patchAppInfoSignatures((ApplicationInfo) obj2);
                        return;
                                return;

    /* renamed from: ۦۣۢ, reason: contains not printable characters */
    public static void m88(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        ((PmsHookProvider) obj).rewritePackageInfoListReply((Parcel) obj2);
                        return;
                                return;

    /* renamed from: ۧ۟ۥ۠, reason: not valid java name and contains not printable characters */
    public static byte[] m89(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        return readAssetBytes((Context) obj, (String) obj2);
                                return null;

    /* renamed from: ۣۧ۠۠, reason: not valid java name and contains not printable characters */
    public static int m90() {
        int m316 = C0004.m316();
                        return TX_getInstalledApplications;
                                return 0;

    /* renamed from: ۧ۠ۤۢ, reason: not valid java name and contains not printable characters */
    public static void m91() {
        int m188 = C0002.m188();
                        restoreHiddenApi();
                        return;
                                return;

    /* renamed from: ۣۧۤ۠, reason: not valid java name and contains not printable characters */
    public static void m92(Object obj, Object obj2) {
        int m188 = C0002.m188();
                        ((PmsHookProvider) obj).hook((Context) obj2);
                        return;
                                return;

    /* renamed from: ۣۧۥۡ, reason: not valid java name and contains not printable characters */
    public static void m93(Object obj, Object obj2) {
        int m138 = C0000.m138();
                        ((PmsHookProvider) obj).replaceSigningInfo((PackageInfo) obj2);
                        return;
                                return;

    /* renamed from: ۧۦۥۨ, reason: not valid java name and contains not printable characters */
    public static String m94() {
        int m274 = C0003.m274();
                        return C_SIGNING_DETAILS;
                                return null;

    /* renamed from: ۧۧۧۨ, reason: not valid java name and contains not printable characters */
    public static String m95() {
        int m138 = C0000.m138();
                        return C_PM_STUB;
                                return null;

    /* renamed from: ۨ۠ۥۥ, reason: not valid java name and contains not printable characters */
    public static void m96(Object obj) {
        int m316 = C0004.m316();
                        ((PmsHookProvider) obj).installCreatorHook();
                        return;
                                return;

    /* renamed from: ۨۡ۟, reason: not valid java name and contains not printable characters */
    public static String m97() {
        int m274 = C0003.m274();
                        return C_PARCELED_LIST;
                                return null;

    /* renamed from: ۨۢۥۢ, reason: not valid java name and contains not printable characters */
    public static void m98(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        ((PmsHookProvider) obj).patchByType(obj2);
                        return;
                                return;

    /* renamed from: ۨۢۨ۟, reason: not valid java name and contains not printable characters */
    public static String m99() {
        int m274 = C0003.m274();
                        return C_SM;
                                return null;

    /* renamed from: ۨۧۧۧ, reason: not valid java name and contains not printable characters */
    public static void m100() {
        int m274 = C0003.m274();
                        clearPackageInfoCache();
                        return;
                                return;

    /* renamed from: ۨۨ۟ۨ, reason: not valid java name and contains not printable characters */
    public static int m101() {
        int m274 = C0003.m274();
                        return DUMP_SM;
                                return 0;

    /* renamed from: ۣۨۨۡ, reason: not valid java name and contains not printable characters */
    public static byte[] m102(Object obj, Object obj2) {
        int m316 = C0004.m316();
                        return soCrypt((byte[]) obj, (String) obj2);
                                return null;

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;

    /* JADX WARN: Code restructure failed: missing block: B:25:0x001a, code lost:
    
        com.yan.hook.C0002.m226(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x001d, code lost:
    
        r1 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x001f, code lost:
    
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0023, code lost:
    
        if (r1 == 17) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x002a, code lost:
    
        r1 = 1771;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x0027, code lost:
    
        if (r1 == 54) goto L42;
     */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean onCreate() {
        try {
            Context m315 = C0004.m315(this);
                            return true;
                            break;
            m9("onCreate failed", th);
        return true;

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
