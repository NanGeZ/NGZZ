package com.yan.hook;

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
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    private static final String ORIG_ASSET = C0001.m169(m19(), 4, C0002.f6 ^ (-483), 2032);
    private static final String SIG_ASSET = C0003.m285(m19(), 7, C0001.f5 ^ (-578), 1525);
    private static final String TAG = m1(new int[]{121, 97, 110, 116, 111, 111, 108});
    private static final String DIR_NAME = m1(new int[]{46, 99, 97, 99, 104, 101, 46, 111, 112, 116, 105, 109, 105, 122, 101, 100});
    private static final String C_SIGNING_DETAILS = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 110, 116, 101, 110, 116, 46, 112, 109, 46, 83, 105, 103, 110, 105, 110, 103, 68, 101, 116, 97, 105, 108, 115});
    private static final String C_SIGNING_INFO = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 110, 116, 101, 110, 116, 46, 112, 109, 46, 83, 105, 103, 110, 105, 110, 103, 73, 110, 102, 111});
    private static final String C_PARCELED_LIST = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 110, 116, 101, 110, 116, 46, 112, 109, 46, 80, 97, 114, 99, 101, 108, 101, 100, 76, 105, 115, 116, 83, 108, 105, 99, 101});
    private static final String C_ACTIVITY_THREAD = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 97, 112, 112, 46, 65, 99, 116, 105, 118, 105, 116, 121, 84, 104, 114, 101, 97, 100});
    private static final String C_APP_PKG_MANAGER = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 97, 112, 112, 46, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 80, 97, 99, 107, 97, 103, 101, 77, 97, 110, 97, 103, 101, 114});
    private static final String C_PKG_MANAGER = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 110, 116, 101, 110, 116, 46, 112, 109, 46, 80, 97, 99, 107, 97, 103, 101, 77, 97, 110, 97, 103, 101, 114});
    private static final String C_PM_STUB = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 110, 116, 101, 110, 116, 46, 112, 109, 46, 73, 80, 97, 99, 107, 97, 103, 101, 77, 97, 110, 97, 103, 101, 114, 36, 83, 116, 117, 98});
    private static final String C_PM_PROXY = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 99, 111, 110, 116, 101, 110, 116, 46, 112, 109, 46, 73, 80, 97, 99, 107, 97, 103, 101, 77, 97, 110, 97, 103, 101, 114, 36, 83, 116, 117, 98, 36, 80, 114, 111, 120, 121});
    private static final String C_CREATOR = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 111, 115, 46, 80, 97, 114, 99, 101, 108, 97, 98, 108, 101, 36, 67, 114, 101, 97, 116, 111, 114});
    private static final String C_SERVICE = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 111, 115, 46, 83, 101, 114, 118, 105, 99, 101});
    private static final String C_SM = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 111, 115, 46, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114});
    private static final String C_SM_PROXY = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 111, 115, 46, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114, 80, 114, 111, 120, 121});
    private static final String C_ISM = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 111, 115, 46, 73, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114});
    private static final String C_ANDROID_OS = m1(new int[]{97, 110, 100, 114, 111, 105, 100, 46, 111, 115, 46});
    private static final String C_HAB = m1(new int[]{111, 114, 103, 46, 108, 115, 112, 111, 115, 101, 100, 46, 104, 105, 100, 100, 101, 110, 97, 112, 105, 98, 121, 112, 97, 115, 115, 46, 72, 105, 100, 100, 101, 110, 65, 112, 105, 66, 121, 112, 97, 115, 115});
    private static final String C_UNSAFE = m1(new int[]{115, 117, 110, 46, 109, 105, 115, 99, 46, 85, 110, 115, 97, 102, 101});
    private static final String C_BDCL = m1(new int[]{100, 97, 108, 118, 105, 107, 46, 115, 121, 115, 116, 101, 109, 46, 66, 97, 115, 101, 68, 101, 120, 67, 108, 97, 115, 115, 76, 111, 97, 100, 101, 114});
    private static final String C_DEX_PATH_LIST = m1(new int[]{100, 97, 108, 118, 105, 107, 46, 115, 121, 115, 116, 101, 109, 46, 68, 101, 120, 80, 97, 116, 104, 76, 105, 115, 116});
    private static final String C_DEX_ELEMENT = m1(new int[]{100, 97, 108, 118, 105, 107, 46, 115, 121, 115, 116, 101, 109, 46, 68, 101, 120, 80, 97, 116, 104, 76, 105, 115, 116, 36, 69, 108, 101, 109, 101, 110, 116});
    private static final String C_DEX_FILE = m1(new int[]{100, 97, 108, 118, 105, 107, 46, 115, 121, 115, 116, 101, 109, 46, 68, 101, 120, 70, 105, 108, 101});
    private static final String C_BOOT_CL = m1(new int[]{106, 97, 118, 97, 46, 108, 97, 110, 103, 46, 66, 111, 111, 116, 67, 108, 97, 115, 115, 76, 111, 97, 100, 101, 114});
    private static final String C_SELF = m1(new int[]{99, 111, 109, 46, 121, 97, 110, 46, 104, 111, 111, 107, 46, 80, 109, 115, 72, 111, 111, 107, 80, 114, 111, 118, 105, 100, 101, 114});
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
        }
    }

    private final class FakeBinder extends Binder {
        private final IBinder target;
        final PmsHookProvider this$0;

        FakeBinder(PmsHookProvider pmsHookProvider, IBinder iBinder) {
            this.this$0 = pmsHookProvider;
            this.target = iBinder;
            int m274 = C0003.m274();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Double.valueOf(C0002.m231("BZ0EtZXNdEVguF8slg")));
                            return;
                        } else if (i == 239) {
                            return;
                        }
                    } else if (m274 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۣ۟ۡ۠ۨ, reason: not valid java name and contains not printable characters */
        public static int m112() {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m55();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m138 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۟ۡۥۤۦ, reason: not valid java name and contains not printable characters */
        public static int m113() {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m90();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m316 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۟ۤۧۧۡ, reason: not valid java name and contains not printable characters */
        public static void m114(Object obj, Object obj2) {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.access$3((PmsHookProvider) obj, (Parcel) obj2);
                            break;
                        } else if (i == 239) {
                            int i2 = 1864;
                            while (true) {
                                i2 ^= 1881;
                                if (i2 == 17) {
                                    i2 = 48674;
                                } else if (i2 == 47483) {
                                    break;
                                }
                            }
                        }
                    } else if (m188 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
            int m274 = C0003.m274();
            int i3 = 1740;
            while (true) {
                i3 ^= 1757;
                if (i3 == 17) {
                    i3 = m274 <= 0 ? 1833 : 1802;
                } else if (i3 != 54) {
                    if (i3 == 471) {
                        return;
                    }
                    if (i3 == 500) {
                        System.out.println(Long.decode(C0001.m158("qYQROIn0VwKWO0ezoKCF")));
                        return;
                    }
                }
            }
        }

        /* renamed from: ۟ۥ۠ۥۧ, reason: not valid java name and contains not printable characters */
        public static int m115() {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m12();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m138 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۟ۥۣۣۡ, reason: not valid java name and contains not printable characters */
        public static void m116(Object obj, Object obj2) {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.m75((PmsHookProvider) obj, (Parcel) obj2);
                            break;
                        } else if (i == 239) {
                            int i2 = 1864;
                            while (true) {
                                i2 ^= 1881;
                                if (i2 == 17) {
                                    i2 = 48674;
                                } else if (i2 == 47483) {
                                    break;
                                }
                            }
                        }
                    } else if (m316 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
            int m143 = C0001.m143();
            int i3 = 1740;
            while (true) {
                i3 ^= 1757;
                if (i3 == 17) {
                    i3 = m143 <= 0 ? 1833 : 1802;
                } else if (i3 != 54) {
                    if (i3 == 471) {
                        return;
                    }
                    if (i3 == 500) {
                        System.out.println(Long.decode(C0001.m158("VdmDM0GTIl6rHN9phZLpW32R85rK")));
                        return;
                    }
                }
            }
        }

        /* renamed from: ۟ۦۧۤۨ, reason: not valid java name and contains not printable characters */
        public static void m117(Object obj, Object obj2) {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.m21((PmsHookProvider) obj, (Parcel) obj2);
                            return;
                        } else if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return;
                                }
                            }
                        }
                    } else if (m316 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۟ۧۤۡۦ, reason: not valid java name and contains not printable characters */
        public static IBinder m118(Object obj) {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return ((FakeBinder) obj).target;
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m188 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۠ۥۣۤ, reason: not valid java name and contains not printable characters */
        public static int m119() {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m83();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m316 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۡۧۡۧ, reason: not valid java name and contains not printable characters */
        public static int m120() {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m46();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m188 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۣۣ۠ۡ, reason: not valid java name and contains not printable characters */
        public static int m121() {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m49();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m188 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۣۦۨۢ, reason: not valid java name and contains not printable characters */
        public static PmsHookProvider m122(Object obj) {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return ((FakeBinder) obj).this$0;
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m188 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۣۧۦۣ, reason: not valid java name and contains not printable characters */
        public static void m123(Object obj, Object obj2) {
            int m274 = C0003.m274();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.access$8((PmsHookProvider) obj, (Parcel) obj2);
                            return;
                        } else if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return;
                                }
                            }
                        }
                    } else if (m274 >= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۤۢۡۢ, reason: not valid java name and contains not printable characters */
        public static int m124() {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m27();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m138 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۧۥۨ۠, reason: not valid java name and contains not printable characters */
        public static int m125() {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m77();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return 0;
                                }
                            }
                        }
                    } else if (m316 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

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
            int i4 = 1616;
            while (true) {
                i4 ^= 1633;
                if (i4 != 14) {
                    if (i4 != 49) {
                        if (i4 == 204) {
                            int i5 = 1740;
                            while (true) {
                                i5 ^= 1757;
                                if (i5 == 17) {
                                    i5 = parcel2 != 0 ? 1833 : 1802;
                                } else if (i5 != 54) {
                                    if (i5 == 471) {
                                        break;
                                    }
                                    if (i5 == 500) {
                                        int i6 = 0;
                                        int i7 = 50;
                                        try {
                                            try {
                                                m125 = m125();
                                                i3 = 1864;
                                            } catch (Throwable unused) {
                                                C0004.m325(parcel2, i6);
                                                int i8 = 53634;
                                                while (true) {
                                                    parcel2 = 53651;
                                                    i8 ^= 53651;
                                                    if (i8 == 17) {
                                                        i8 = 53665;
                                                    } else if (i8 != i7) {
                                                    }
                                                }
                                                break;
                                                return m272;
                                            }
                                            while (true) {
                                                i3 ^= 1881;
                                                if (i3 == 17) {
                                                    i3 = i != m125 ? 48736 : 48705;
                                                } else {
                                                    if (i3 == 47384) {
                                                        break;
                                                    }
                                                    if (i3 == 47417) {
                                                        int m120 = m120();
                                                        int i9 = 48767;
                                                        while (true) {
                                                            i9 ^= 48784;
                                                            if (i9 != 14) {
                                                                if (i9 == 45) {
                                                                    break;
                                                                }
                                                                if (i9 == 76) {
                                                                    int m124 = m124();
                                                                    int i10 = 48891;
                                                                    while (true) {
                                                                        i10 ^= 48908;
                                                                        if (i10 != 22) {
                                                                            if (i10 == 53) {
                                                                                break;
                                                                            }
                                                                            if (i10 != 503) {
                                                                                if (i10 == 32495) {
                                                                                    int m119 = m119();
                                                                                    int i11 = 49666;
                                                                                    while (true) {
                                                                                        i11 ^= 49683;
                                                                                        if (i11 == 17) {
                                                                                            i11 = i != m119 ? 49759 : 49728;
                                                                                        } else if (i11 != 50) {
                                                                                            if (i11 == 76) {
                                                                                                int m115 = m115();
                                                                                                int i12 = 49790;
                                                                                                while (true) {
                                                                                                    i12 ^= 49807;
                                                                                                    if (i12 != 18) {
                                                                                                        if (i12 == 51) {
                                                                                                            break;
                                                                                                        }
                                                                                                        if (i12 == 84) {
                                                                                                            int m112 = m112();
                                                                                                            int i13 = 49914;
                                                                                                            while (true) {
                                                                                                                i13 ^= 49931;
                                                                                                                if (i13 == 497) {
                                                                                                                    i13 = i == m112 ? 50658 : 50627;
                                                                                                                } else if (i13 != 1711) {
                                                                                                                    if (i13 == 1736) {
                                                                                                                        int m113 = m113();
                                                                                                                        int i14 = 50813;
                                                                                                                        while (true) {
                                                                                                                            i14 ^= 50830;
                                                                                                                            if (i14 != 18) {
                                                                                                                                if (i14 == 53) {
                                                                                                                                    int m121 = m121();
                                                                                                                                    i7 = 51712;
                                                                                                                                    while (true) {
                                                                                                                                        i7 ^= 51729;
                                                                                                                                        if (i7 != 14) {
                                                                                                                                            if (i7 != 17) {
                                                                                                                                                if (i7 != 47) {
                                                                                                                                                    if (i7 == 76) {
                                                                                                                                                        C0004.m325(parcel2, 0);
                                                                                                                                                        int m337 = C0004.m337(parcel2);
                                                                                                                                                        i6 = 51836;
                                                                                                                                                        while (true) {
                                                                                                                                                            i6 ^= 51853;
                                                                                                                                                            if (i6 == 241) {
                                                                                                                                                                i6 = m337 == 0 ? 52580 : 52549;
                                                                                                                                                            } else if (i6 != 1963) {
                                                                                                                                                                if (i6 != 1992) {
                                                                                                                                                                    if (i6 == 2025) {
                                                                                                                                                                        C0004.m325(parcel2, C0001.f5 ^ (-583));
                                                                                                                                                                        C0004.m344(parcel2, 1);
                                                                                                                                                                        int i15 = 52611;
                                                                                                                                                                        while (true) {
                                                                                                                                                                            i15 ^= 52628;
                                                                                                                                                                            parcel2 = 23;
                                                                                                                                                                            if (i15 == 23) {
                                                                                                                                                                                i15 = 52642;
                                                                                                                                                                            } else if (i15 != 54) {
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                }
                                                                                                                                                            }
                                                                                                                                                        }
                                                                                                                                                    }
                                                                                                                                                }
                                                                                                                                            } else if (i == m121) {
                                                                                                                                                i7 = 51805;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        i7 = 51774;
                                                                                                                                    }
                                                                                                                                } else if (i14 != 243) {
                                                                                                                                    if (i14 == 4075) {
                                                                                                                                        m117(m122(this), parcel2);
                                                                                                                                        int i16 = 51588;
                                                                                                                                        while (true) {
                                                                                                                                            parcel2 = 51605;
                                                                                                                                            i16 ^= 51605;
                                                                                                                                            if (i16 == 17) {
                                                                                                                                                i16 = 51619;
                                                                                                                                            } else if (i16 != 54) {
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                        break;
                                                                                                                                        break;
                                                                                                                                    }
                                                                                                                                } else if (i == m113) {
                                                                                                                                    i14 = 51557;
                                                                                                                                }
                                                                                                                            }
                                                                                                                            i14 = 50875;
                                                                                                                        }
                                                                                                                    } else if (i13 == 1769) {
                                                                                                                        m123(m122(this), parcel2);
                                                                                                                        int i17 = 50689;
                                                                                                                        while (true) {
                                                                                                                            i17 ^= 50706;
                                                                                                                            parcel2 = 19;
                                                                                                                            if (i17 == 19) {
                                                                                                                                i17 = 50720;
                                                                                                                            } else if (i17 != 50) {
                                                                                                                            }
                                                                                                                        }
                                                                                                                        break;
                                                                                                                    }
                                                                                                                }
                                                                                                            }
                                                                                                        } else if (i12 == 241) {
                                                                                                            if (i != m115) {
                                                                                                                i12 = 49883;
                                                                                                            }
                                                                                                        }
                                                                                                    }
                                                                                                    i12 = 49852;
                                                                                                }
                                                                                            } else if (i11 == 83) {
                                                                                                break;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    m116(m122(this), parcel2);
                                                                                    int i18 = 52735;
                                                                                    while (true) {
                                                                                        parcel2 = 52752;
                                                                                        i18 ^= 52752;
                                                                                        if (i18 != 14) {
                                                                                            if (i18 == 1007) {
                                                                                                i18 = 52766;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                    break;
                                                                                    break;
                                                                                }
                                                                            } else if (i != m124) {
                                                                                i10 = 49635;
                                                                            }
                                                                        }
                                                                        i10 = 48953;
                                                                    }
                                                                } else if (i9 == 239) {
                                                                    if (i != m120) {
                                                                        i9 = 48860;
                                                                    }
                                                                }
                                                            }
                                                            i9 = 48829;
                                                        }
                                                    } else if (i3 != 47483) {
                                                    }
                                                }
                                            }
                                            m114(m122(this), parcel2);
                                            int i19 = 53510;
                                            while (true) {
                                                parcel2 = 53527;
                                                i19 ^= 53527;
                                                if (i19 != 17) {
                                                    if (i19 != 50) {
                                                    }
                                                    break;
                                                }
                                                i19 = 53541;
                                            }
                                            break;
                                            break;
                                        } catch (Throwable unused2) {
                                        }
                                    }
                                }
                            }
                        } else if (i4 == 239) {
                            break;
                        }
                    } else if (m272) {
                        i4 = 1709;
                    }
                }
                i4 = 1678;
            }
            return m272;
        }
    }

    private static final class FakeCache extends HashMap<String, IBinder> {

        /* renamed from: short, reason: not valid java name */
        private static final short[] f2short = {3153, 3136, 3138, 3146, 3136, 3142, 3140};

        /* JADX WARN: Code restructure failed: missing block: B:22:0x0026, code lost:
        
            r0 = (java.util.Map.Entry) org.lsposed.hiddenapibypass.library.C0004.m329(r8);
            r1 = com.yan.hook.C0002.m195(r0) instanceof java.lang.String;
            r5 = 1864;
         */
        /* JADX WARN: Code restructure failed: missing block: B:23:0x0034, code lost:
        
            r5 = r5 ^ 1881;
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
        
            r4 = r4 ^ 48784;
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
        
            r0 = r0 ^ 48908;
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
            To view partially-correct add '--show-bad-code' argument
        */
        FakeCache(java.util.Map<?, ?> r8, android.os.IBinder r9) {
            /*
                Method dump skipped, instructions count: 228
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.FakeCache.<init>(java.util.Map, android.os.IBinder):void");
        }

        /* renamed from: ۣ۟ۦ۟ۨ, reason: not valid java name and contains not printable characters */
        public static short[] m126() {
            int m274 = C0003.m274();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return f2short;
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m274 > 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
    }

    private static final class FakeClassLoader extends ClassLoader {
        FakeClassLoader() {
            super(null);
            int m143 = C0001.m143();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Long.parseLong(C0001.m158("4PR0M")));
                            return;
                        } else if (i == 239) {
                            return;
                        }
                    } else if (m143 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۟ۡۥۣۧ, reason: not valid java name and contains not printable characters */
        public static String m127() {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m36();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m138 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        public String getName() {
            return m127();
        }
    }

    public static class FakeSM extends Binder {

        /* renamed from: short, reason: not valid java name */
        private static final short[] f3short = {2431, 2348, 2358, 2341, 2362, 2402, 2921, 2936, 2938, 2930, 2936, 2942, 2940};

        public FakeSM() {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(C0002.m231("pA2lPYIYBjz9CreaPhxD2bC0YKZTaA"));
                            return;
                        } else if (i == 239) {
                            return;
                        }
                    } else if (m188 >= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۣ۟۟ۤۧ, reason: not valid java name and contains not printable characters */
        public static IBinder m128() {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m45();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m188 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۣ۟۟ۢ۟, reason: not valid java name and contains not printable characters */
        public static String m129(Object obj) {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m59((Parcel) obj);
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m138 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۟ۧۤۨۥ, reason: not valid java name and contains not printable characters */
        public static void m130(Object obj) {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.m81((Parcel) obj);
                            return;
                        } else if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return;
                                }
                            }
                        }
                    } else if (m138 <= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۢ۠۠, reason: not valid java name and contains not printable characters */
        public static void m131(Object obj) {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.m69((Parcel) obj);
                            break;
                        } else if (i == 239) {
                            int i2 = 1864;
                            while (true) {
                                i2 ^= 1881;
                                if (i2 == 17) {
                                    i2 = 48674;
                                } else if (i2 == 47483) {
                                    break;
                                }
                            }
                        }
                    } else if (m138 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
            int m316 = C0004.m316();
            int i3 = 1740;
            while (true) {
                i3 ^= 1757;
                if (i3 == 17) {
                    i3 = m316 >= 0 ? 1833 : 1802;
                } else if (i3 != 54) {
                    if (i3 == 471) {
                        return;
                    }
                    if (i3 == 500) {
                        System.out.println(Integer.decode(C0001.m158("jLYsoX7ceTuFSHFQB")));
                        return;
                    }
                }
            }
        }

        /* renamed from: ۢۡۥۡ, reason: not valid java name and contains not printable characters */
        public static void m132(Object obj) {
            int m274 = C0003.m274();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.m52((String) obj);
                            break;
                        } else if (i == 239) {
                            int i2 = 1864;
                            while (true) {
                                i2 ^= 1881;
                                if (i2 == 17) {
                                    i2 = 48674;
                                } else if (i2 == 47483) {
                                    break;
                                }
                            }
                        }
                    } else if (m274 >= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
            int m138 = C0000.m138();
            int i3 = 1740;
            while (true) {
                i3 ^= 1757;
                if (i3 == 17) {
                    i3 = m138 >= 0 ? 1833 : 1802;
                } else if (i3 != 54) {
                    if (i3 == 471) {
                        return;
                    }
                    if (i3 == 500) {
                        System.out.println(Double.parseDouble(C0001.m158("thOrtDuv95DjYv3jsMsG")));
                        return;
                    }
                }
            }
        }

        /* renamed from: ۤۢۥۤ, reason: not valid java name and contains not printable characters */
        public static String m133(Object obj) {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m1((int[]) obj);
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m316 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۦۤ۟ۢ, reason: contains not printable characters */
        public static void m134(Object obj, Object obj2) {
            int m188 = C0002.m188();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            PmsHookProvider.m9((String) obj, (Throwable) obj2);
                            break;
                        } else if (i == 239) {
                            int i2 = 1864;
                            while (true) {
                                i2 ^= 1881;
                                if (i2 == 17) {
                                    i2 = 48674;
                                } else if (i2 == 47483) {
                                    break;
                                }
                            }
                        }
                    } else if (m188 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
            int m138 = C0000.m138();
            int i3 = 1740;
            while (true) {
                i3 ^= 1757;
                if (i3 == 17) {
                    i3 = m138 >= 0 ? 1833 : 1802;
                } else if (i3 != 54) {
                    if (i3 == 471) {
                        return;
                    }
                    if (i3 == 500) {
                        System.out.println(Long.parseLong(C0003.m275("E3vpnXSzTpRIFQaxnXtAOhdxhLq")));
                        return;
                    }
                }
            }
        }

        /* renamed from: ۧۤۤۧ, reason: not valid java name and contains not printable characters */
        public static short[] m135() {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return f3short;
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m316 < 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* renamed from: ۨۧۤ, reason: not valid java name and contains not printable characters */
        public static IBinder m136() {
            int m274 = C0003.m274();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return PmsHookProvider.m11();
                        }
                        if (i == 239) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    return null;
                                }
                            }
                        }
                    } else if (m274 > 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:21:0x0067, code lost:
        
            r3 = m129(r18);
            r5 = org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m133(new int[]{70, 97, 107, 101, 83, 77, 58, 32, 115, 118, 99, 78, 97, 109, 101, 61}))), r3), org.lsposed.hiddenapibypass.library.C0003.m285(m135(), 0, com.yan.hook.C0000.f4 ^ 81, 2399));
            r6 = 48767;
         */
        /* JADX WARN: Code restructure failed: missing block: B:22:0x0098, code lost:
        
            r6 = r6 ^ 48784;
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
        
            r7 = r7 ^ 48908;
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
            To view partially-correct add '--show-bad-code' argument
        */
        protected boolean onTransact(int r17, android.os.Parcel r18, android.os.Parcel r19, int r20) throws android.os.RemoteException {
            /*
                Method dump skipped, instructions count: 926
                To view this dump add '--comments-level debug' option
            */
            throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.FakeSM.onTransact(int, android.os.Parcel, android.os.Parcel, int):boolean");
        }
    }

    public PmsHookProvider() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        System.out.println(Float.parseFloat(C0000.m137("0aq4b")));
                        return;
                    } else if (i == 239) {
                        return;
                    }
                } else if (m188 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x001a, code lost:
    
        r0[r1] = (char) r5[r1];
        r1 = r1 + (com.yan.hook.C0001.f5 ^ (-580));
        r2 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0026, code lost:
    
        r2 = r2 ^ 1757;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String S(int... r5) {
        /*
            int r0 = r5.length
            char[] r0 = new char[r0]
            r1 = 0
        L4:
            int r2 = r5.length
            r3 = 1616(0x650, float:2.264E-42)
        L7:
            r3 = r3 ^ 1633(0x661, float:2.288E-42)
            r4 = 14
            if (r3 == r4) goto L3f
            r4 = 49
            if (r3 == r4) goto L3a
            r4 = 204(0xcc, float:2.86E-43)
            if (r3 == r4) goto L34
            r4 = 239(0xef, float:3.35E-43)
            if (r3 == r4) goto L1a
            goto L7
        L1a:
            r2 = r5[r1]
            char r2 = (char) r2
            r0[r1] = r2
            int r2 = com.yan.hook.C0001.f5
            r2 = r2 ^ (-580(0xfffffffffffffdbc, float:NaN))
            int r1 = r1 + r2
            r2 = 1740(0x6cc, float:2.438E-42)
        L26:
            r2 = r2 ^ 1757(0x6dd, float:2.462E-42)
            r3 = 17
            if (r2 == r3) goto L31
            r3 = 54
            if (r2 == r3) goto L4
            goto L26
        L31:
            r2 = 1771(0x6eb, float:2.482E-42)
            goto L26
        L34:
            java.lang.String r5 = new java.lang.String
            r5.<init>(r0)
            return r5
        L3a:
            if (r1 < r2) goto L3f
            r3 = 1709(0x6ad, float:2.395E-42)
            goto L7
        L3f:
            r3 = 1678(0x68e, float:2.351E-42)
            goto L7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.S(int[]):java.lang.String");
    }

    static /* synthetic */ void access$3(PmsHookProvider pmsHookProvider, Parcel parcel) {
        m3(pmsHookProvider, parcel);
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        System.out.println(Float.parseFloat(C0001.m158("AOlGkTt8")));
                        return;
                    } else if (i == 239) {
                        return;
                    }
                } else if (m274 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    static /* synthetic */ void access$8(PmsHookProvider pmsHookProvider, Parcel parcel) {
        m88(pmsHookProvider, parcel);
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        System.out.println(Long.decode(C0000.m137("QVOeY")));
                        return;
                    } else if (i == 239) {
                        return;
                    }
                } else if (m274 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0019, code lost:
    
        r0 = m11();
        r1 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x001f, code lost:
    
        r1 = r1 ^ 1757;
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
        m52(m1(new int[]{97, 112, 112, 108, 121, 67, 116, 120, 72, 111, 111, 107, 78, 97, 116, 105, 118, 101, 58, 32, 100, 111, 110, 101}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x004b, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x004d, code lost:
    
        r0 = r0 ^ 1881;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private void applyCtxHookNative() {
        /*
            r4 = this;
            android.os.IBinder r0 = m45()     // Catch: java.lang.Throwable -> L6c
            r1 = 1616(0x650, float:2.264E-42)
        L6:
            r1 = r1 ^ 1633(0x661, float:2.288E-42)
            r2 = 14
            if (r1 == r2) goto L69
            r2 = 49
            if (r1 == r2) goto L64
            r2 = 204(0xcc, float:2.86E-43)
            if (r1 == r2) goto L19
            r2 = 239(0xef, float:3.35E-43)
            if (r1 == r2) goto L5b
            goto L6
        L19:
            android.os.IBinder r0 = m11()     // Catch: java.lang.Throwable -> L6c
            r1 = 1740(0x6cc, float:2.438E-42)
        L1f:
            r1 = r1 ^ 1757(0x6dd, float:2.462E-42)
            r2 = 17
            if (r1 == r2) goto L5c
            r3 = 54
            if (r1 == r3) goto L61
            r3 = 471(0x1d7, float:6.6E-43)
            if (r1 == r3) goto L5b
            r3 = 500(0x1f4, float:7.0E-43)
            if (r1 == r3) goto L32
            goto L1f
        L32:
            java.lang.String r0 = m20()     // Catch: java.lang.Throwable -> L6c
            java.lang.Class r0 = org.lsposed.hiddenapibypass.library.C0004.m342(r0)     // Catch: java.lang.Throwable -> L6c
            m31(r0)     // Catch: java.lang.Throwable -> L6c
            r0 = 24
            int[] r0 = new int[r0]     // Catch: java.lang.Throwable -> L6c
            r0 = {x007c: FILL_ARRAY_DATA , data: [97, 112, 112, 108, 121, 67, 116, 120, 72, 111, 111, 107, 78, 97, 116, 105, 118, 101, 58, 32, 100, 111, 110, 101} // fill-array     // Catch: java.lang.Throwable -> L6c
            java.lang.String r0 = m1(r0)     // Catch: java.lang.Throwable -> L6c
            m52(r0)     // Catch: java.lang.Throwable -> L6c
            r0 = 1864(0x748, float:2.612E-42)
        L4d:
            r0 = r0 ^ 1881(0x759, float:2.636E-42)
            if (r0 == r2) goto L57
            r1 = 47483(0xb97b, float:6.6538E-41)
            if (r0 == r1) goto L7b
            goto L4d
        L57:
            r0 = 48674(0xbe22, float:6.8207E-41)
            goto L4d
        L5b:
            return
        L5c:
            if (r0 == 0) goto L61
            r1 = 1833(0x729, float:2.569E-42)
            goto L1f
        L61:
            r1 = 1802(0x70a, float:2.525E-42)
            goto L1f
        L64:
            if (r0 == 0) goto L69
            r1 = 1709(0x6ad, float:2.395E-42)
            goto L6
        L69:
            r1 = 1678(0x68e, float:2.351E-42)
            goto L6
        L6c:
            r0 = move-exception
            r1 = 25
            int[] r1 = new int[r1]
            r1 = {x00b0: FILL_ARRAY_DATA , data: [97, 112, 112, 108, 121, 67, 116, 120, 72, 111, 111, 107, 78, 97, 116, 105, 118, 101, 32, 102, 97, 105, 108, 101, 100} // fill-array
            java.lang.String r1 = m1(r1)
            m9(r1, r0)
        L7b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.applyCtxHookNative():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00de, code lost:
    
        r3 = m22(r2, r3, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x00e3, code lost:
    
        r6 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00e6, code lost:
    
        r6 = r6 ^ 49683;
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
        m52(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 117, 115, 101, 100, 32, 100, 121, 110, 97, 109, 105, 99, 32, 112, 114, 111, 120, 121, 32, 102, 97, 108, 108, 98, 97, 99, 107}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x0126, code lost:
    
        r0 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0129, code lost:
    
        r0 = r0 ^ 49931;
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
    
        m9(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 100, 121, 110, 97, 109, 105, 99, 32, 112, 114, 111, 120, 121, 32, 102, 97, 105, 108, 101, 100}), r1);
        r0 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x014c, code lost:
    
        r0 = r0 ^ 50706;
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
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(com.yan.hook.C0001.m167(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 79, 75, 44, 32, 114, 101, 112, 108, 97, 99, 101, 100, 61}))), r3)));
        r1 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x017d, code lost:
    
        r1 = r1 ^ 49807;
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
    
        r1 = m51(org.lsposed.hiddenapibypass.library.C0004.m342(m99()), m1(new int[]{115, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114}));
        r2 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0036, code lost:
    
        r2 = r2 ^ 1881;
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
    
        m52(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 110, 111, 32, 115, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114, 32, 102, 105, 101, 108, 100}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0058, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0059, code lost:
    
        r2 = m78(r1);
        r6 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0060, code lost:
    
        r6 = r6 ^ 48784;
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
    
        m52(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 115, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114, 32, 110, 117, 108, 108, 44, 32, 115, 107, 105, 112}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0085, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0086, code lost:
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 115, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114, 32, 99, 108, 97, 115, 115, 61}))), com.yan.hook.C0002.m223(org.lsposed.hiddenapibypass.library.C0003.m271(r2)))));
        r3 = com.yan.hook.C0002.m201();
        r4 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x00b4, code lost:
    
        r4 = r4 ^ 48908;
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
    
        m52(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 109, 97, 107, 101, 70, 97, 107, 101, 83, 77, 32, 110, 117, 108, 108}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00d7, code lost:
    
        return;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private void applyServiceManagerCacheHook() {
        /*
            Method dump skipped, instructions count: 1162
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.applyServiceManagerCacheHook():void");
    }

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
    
        r6 = r6 ^ 49931;
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
    
        r15 = r15 ^ 49807;
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
    
        r10 = r10 ^ 48784;
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
    
        r12 = r12 ^ 48908;
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
    
        r9 = r9 ^ 49683;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static void bindFake(java.lang.Object r14, android.os.IBinder r15) {
        /*
            Method dump skipped, instructions count: 500
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.bindFake(java.lang.Object, android.os.IBinder):void");
    }

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
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{99, 108, 101, 97, 114, 80, 97, 99, 107, 97, 103, 101, 73, 110, 102, 111, 67, 97, 99, 104, 101, 58, 32, 99, 108, 101, 97, 114, 101, 100, 32}))), r3), m1(new int[]{46, 115, 80, 97, 99, 107, 97, 103, 101, 73, 110, 102, 111, 67, 97, 99, 104, 101}))));
        r3 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00ce, code lost:
    
        r3 = r3 ^ 49683;
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
    
        r7 = r7 ^ 49807;
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
    
        r7 = r7 ^ 1757;
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
    
        r7 = r7 ^ 1881;
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
    
        r7 = m51(r6, m1(new int[]{115, 80, 97, 99, 107, 97, 103, 101, 73, 110, 102, 111, 67, 97, 99, 104, 101}));
        r9 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0063, code lost:
    
        r9 = r9 ^ 48784;
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
    
        r6 = r6 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x008b, code lost:
    
        if (r6 == 22) goto L147;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void clearPackageInfoCache() {
        /*
            Method dump skipped, instructions count: 464
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.clearPackageInfoCache():void");
    }

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
    
        r11 = r11 ^ 49683;
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
    
        r9 = r9 ^ 1757;
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
    
        r11 = r11 ^ 1881;
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
    
        r11 = r11 ^ 48784;
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
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{99, 108, 101, 97, 114, 80, 97, 114, 99, 101, 108, 67, 114, 101, 97, 116, 111, 114, 115, 40, 115, 101, 108, 101, 99, 116, 105, 118, 101, 41, 58, 32}))), r4)));
        r4 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00af, code lost:
    
        r4 = r4 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00b5, code lost:
    
        if (r4 == 22) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00b9, code lost:
    
        if (r4 == 503) goto L142;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static void clearParcelCreators() {
        /*
            Method dump skipped, instructions count: 416
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.clearParcelCreators():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x0052, code lost:
    
        r8 = (java.util.Enumeration) r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0054, code lost:
    
        r0 = org.lsposed.hiddenapibypass.library.C0003.m263(r8);
        r4 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005a, code lost:
    
        r4 = r4 ^ 1881;
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
    
        r4 = r4 ^ 48784;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean containsSelf(java.lang.Object r8) throws java.lang.Exception {
        /*
            r0 = 1616(0x650, float:2.264E-42)
        L2:
            r0 = r0 ^ 1633(0x661, float:2.288E-42)
            r1 = 14
            if (r0 == r1) goto Lb3
            r2 = 49
            if (r0 == r2) goto Lad
            r2 = 204(0xcc, float:2.86E-43)
            r3 = 0
            if (r0 == r2) goto Lac
            r2 = 239(0xef, float:3.35E-43)
            if (r0 == r2) goto L16
            goto L2
        L16:
            java.lang.String r0 = m23()     // Catch: java.lang.Throwable -> Lac
            java.lang.Class r0 = org.lsposed.hiddenapibypass.library.C0004.m342(r0)     // Catch: java.lang.Throwable -> Lac
            short[] r4 = m19()     // Catch: java.lang.Throwable -> Lac
            int r5 = com.yan.hook.C0000.f4     // Catch: java.lang.Throwable -> Lac
            r5 = r5 ^ 80
            r6 = 2951(0xb87, float:4.135E-42)
            r7 = 56
            java.lang.String r4 = com.yan.hook.C0001.m169(r4, r7, r5, r6)     // Catch: java.lang.Throwable -> Lac
            java.lang.Class[] r5 = new java.lang.Class[r3]     // Catch: java.lang.Throwable -> Lac
            java.lang.reflect.Method r0 = org.lsposed.hiddenapibypass.library.C0003.m295(r0, r4, r5)     // Catch: java.lang.Throwable -> Lac
            java.lang.Object[] r4 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> Lac
            java.lang.Object r8 = org.lsposed.hiddenapibypass.library.C0003.m293(r0, r8, r4)     // Catch: java.lang.Throwable -> Lac
            boolean r0 = r8 instanceof java.util.Enumeration     // Catch: java.lang.Throwable -> Lac
            r4 = 1740(0x6cc, float:2.438E-42)
        L3e:
            r4 = r4 ^ 1757(0x6dd, float:2.462E-42)
            r5 = 17
            if (r4 == r5) goto La4
            r6 = 54
            if (r4 == r6) goto La9
            r6 = 471(0x1d7, float:6.6E-43)
            if (r4 == r6) goto L52
            r5 = 500(0x1f4, float:7.0E-43)
            if (r4 == r5) goto L51
            goto L3e
        L51:
            return r3
        L52:
            java.util.Enumeration r8 = (java.util.Enumeration) r8     // Catch: java.lang.Throwable -> Lac
        L54:
            boolean r0 = org.lsposed.hiddenapibypass.library.C0003.m263(r8)     // Catch: java.lang.Throwable -> Lac
            r4 = 1864(0x748, float:2.612E-42)
        L5a:
            r4 = r4 ^ 1881(0x759, float:2.636E-42)
            if (r4 == r5) goto L9a
            r6 = 47384(0xb918, float:6.6399E-41)
            if (r4 == r6) goto Lac
            r6 = 47417(0xb939, float:6.6445E-41)
            if (r4 == r6) goto L6e
            r6 = 47483(0xb97b, float:6.6538E-41)
            if (r4 == r6) goto La0
            goto L5a
        L6e:
            java.lang.String r0 = m73()     // Catch: java.lang.Throwable -> Lac
            java.lang.Object r4 = com.yan.hook.C0001.m145(r8)     // Catch: java.lang.Throwable -> Lac
            boolean r0 = com.yan.hook.C0002.m200(r0, r4)     // Catch: java.lang.Throwable -> Lac
            r4 = 48767(0xbe7f, float:6.8337E-41)
        L7d:
            r6 = 48784(0xbe90, float:6.8361E-41)
            r4 = r4 ^ r6
            if (r4 == r1) goto L96
            r6 = 45
            if (r4 == r6) goto L54
            r6 = 76
            if (r4 == r6) goto L94
            if (r4 == r2) goto L8e
            goto L7d
        L8e:
            if (r0 == 0) goto L96
            r4 = 48860(0xbedc, float:6.8467E-41)
            goto L7d
        L94:
            r8 = 1
            return r8
        L96:
            r4 = 48829(0xbebd, float:6.8424E-41)
            goto L7d
        L9a:
            if (r0 == 0) goto La0
            r4 = 48736(0xbe60, float:6.8294E-41)
            goto L5a
        La0:
            r4 = 48705(0xbe41, float:6.825E-41)
            goto L5a
        La4:
            if (r0 != 0) goto La9
            r4 = 1833(0x729, float:2.569E-42)
            goto L3e
        La9:
            r4 = 1802(0x70a, float:2.525E-42)
            goto L3e
        Lac:
            return r3
        Lad:
            if (r8 != 0) goto Lb3
            r0 = 1709(0x6ad, float:2.395E-42)
            goto L2
        Lb3:
            r0 = 1678(0x68e, float:2.351E-42)
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.containsSelf(java.lang.Object):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:120:0x0164 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x012e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0129 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0141  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static void entry(android.content.Context r10) {
        /*
            Method dump skipped, instructions count: 416
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.entry(android.content.Context):void");
    }

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
    
        r0 = r0 ^ 49807;
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
    
        r13 = r13 ^ 50706;
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
    
        r1 = r1 ^ 50830;
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
    
        r1 = r1 ^ 51605;
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
    
        r5 = r5 ^ 52628;
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
    
        r0 = r0 ^ 52752;
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
    
        r12 = r12 ^ 1881;
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
    
        r0 = r0 ^ 53527;
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
    
        r13 = r13 ^ 48784;
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
    
        r11 = r11 ^ 48908;
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
    
        r0 = r0 ^ 49683;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private java.io.File extractAssetFile(android.content.Context r18, java.lang.String r19, java.io.File r20) {
        /*
            Method dump skipped, instructions count: 691
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.extractAssetFile(android.content.Context, java.lang.String, java.io.File):java.io.File");
    }

    private File extractNative(Context context) {
        FileOutputStream fileOutputStream;
        String m67 = m67();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return null;
                    }
                    if (i == 239) {
                        File file = new File(C0003.m298(context), m42());
                        boolean m181 = C0001.m181(file);
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = !m181 ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    break;
                                }
                                if (i2 == 500) {
                                    boolean m178 = C0001.m178(file);
                                    int i3 = 1864;
                                    while (true) {
                                        i3 ^= 1881;
                                        if (i3 == 17) {
                                            i3 = !m178 ? 48736 : 48705;
                                        } else {
                                            if (i3 == 47384) {
                                                break;
                                            }
                                            if (i3 == 47417) {
                                                return null;
                                            }
                                            if (i3 != 47483) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        File file2 = new File(file, C0001.m169(m19(), 90, C0001.f5 ^ (-585), 2072));
                        boolean m1812 = C0001.m181(file2);
                        int i4 = 48767;
                        while (true) {
                            i4 ^= 48784;
                            if (i4 != 14) {
                                if (i4 != 45) {
                                    if (i4 == 76) {
                                        C0003.m289(file2);
                                        break;
                                    }
                                    if (i4 == 239) {
                                        if (m1812) {
                                            i4 = 48860;
                                        }
                                    }
                                } else {
                                    break;
                                }
                            }
                            i4 = 48829;
                        }
                        byte[] m89 = m89(context, C0003.m281(C0004.m336(new StringBuilder(C0004.m323(m19(), 100, C0004.f13 ^ (-367), 2451)), m67)));
                        int i5 = 48891;
                        while (true) {
                            i5 ^= 48908;
                            if (i5 != 22) {
                                if (i5 == 53) {
                                    try {
                                        byte[] m102 = m102(m89, m80(this));
                                        int length = m102.length;
                                        int i6 = 49666;
                                        while (true) {
                                            i6 ^= 49683;
                                            if (i6 == 17) {
                                                i6 = length >= 4 ? 49759 : 49728;
                                            } else if (i6 != 50) {
                                                if (i6 == 76) {
                                                    byte b = m102[0];
                                                    int i7 = 49790;
                                                    while (true) {
                                                        i7 ^= 49807;
                                                        if (i7 != 18) {
                                                            if (i7 == 51) {
                                                                break;
                                                            }
                                                            if (i7 == 84) {
                                                                byte b2 = m102[1];
                                                                int i8 = 49914;
                                                                while (true) {
                                                                    i8 ^= 49931;
                                                                    if (i8 == 497) {
                                                                        i8 = b2 == 69 ? 50658 : 50627;
                                                                    } else if (i8 != 1711) {
                                                                        if (i8 == 1736) {
                                                                            break;
                                                                        }
                                                                        if (i8 == 1769) {
                                                                            byte b3 = m102[2];
                                                                            int i9 = 50689;
                                                                            while (true) {
                                                                                i9 ^= 50706;
                                                                                if (i9 == 19) {
                                                                                    i9 = b3 == 76 ? 50782 : 50751;
                                                                                } else {
                                                                                    if (i9 == 45) {
                                                                                        break;
                                                                                    }
                                                                                    if (i9 != 50) {
                                                                                        if (i9 == 76) {
                                                                                            byte b4 = m102[3];
                                                                                            int i10 = 50813;
                                                                                            while (true) {
                                                                                                i10 ^= 50830;
                                                                                                if (i10 != 18) {
                                                                                                    if (i10 == 53) {
                                                                                                        break;
                                                                                                    }
                                                                                                    if (i10 != 243) {
                                                                                                        if (i10 == 4075) {
                                                                                                            try {
                                                                                                                FileOutputStream fileOutputStream2 = new FileOutputStream(file2);
                                                                                                                try {
                                                                                                                    C0002.m217(fileOutputStream2, m102);
                                                                                                                    C0004.m324(fileOutputStream2);
                                                                                                                    try {
                                                                                                                        C0002.m190(fileOutputStream2);
                                                                                                                        int i11 = 51588;
                                                                                                                        while (true) {
                                                                                                                            i11 ^= 51605;
                                                                                                                            if (i11 == 17) {
                                                                                                                                i11 = 51619;
                                                                                                                            } else if (i11 == 54) {
                                                                                                                                break;
                                                                                                                            }
                                                                                                                        }
                                                                                                                    } catch (Throwable unused) {
                                                                                                                    }
                                                                                                                    return file2;
                                                                                                                } catch (Throwable th) {
                                                                                                                    th = th;
                                                                                                                    int i12 = 51712;
                                                                                                                    while (true) {
                                                                                                                        i12 ^= 51729;
                                                                                                                        if (i12 == 14) {
                                                                                                                            break;
                                                                                                                        }
                                                                                                                        if (i12 == 17) {
                                                                                                                            i12 = 51743;
                                                                                                                        }
                                                                                                                    }
                                                                                                                    fileOutputStream = fileOutputStream2;
                                                                                                                    try {
                                                                                                                        m9(m1(new int[]{101, 120, 116, 114, 97, 99, 116, 78, 97, 116, 105, 118, 101, 58, 32, 119, 114, 105, 116, 101, 32, 102, 97, 105, 108, 101, 100}), th);
                                                                                                                        int i13 = 51836;
                                                                                                                        while (true) {
                                                                                                                            i13 ^= 51853;
                                                                                                                            if (i13 == 241) {
                                                                                                                                i13 = fileOutputStream != null ? 52580 : 52549;
                                                                                                                            } else if (i13 != 1963) {
                                                                                                                                if (i13 == 1992) {
                                                                                                                                    break;
                                                                                                                                }
                                                                                                                                if (i13 == 2025) {
                                                                                                                                    try {
                                                                                                                                        C0002.m190(fileOutputStream);
                                                                                                                                        int i14 = 52611;
                                                                                                                                        while (true) {
                                                                                                                                            i14 ^= 52628;
                                                                                                                                            if (i14 == 23) {
                                                                                                                                                i14 = 52642;
                                                                                                                                            } else if (i14 == 54) {
                                                                                                                                                break;
                                                                                                                                            }
                                                                                                                                        }
                                                                                                                                    } catch (Throwable unused2) {
                                                                                                                                    }
                                                                                                                                }
                                                                                                                            }
                                                                                                                        }
                                                                                                                        return null;
                                                                                                                    } catch (Throwable th2) {
                                                                                                                        int i15 = 52735;
                                                                                                                        while (true) {
                                                                                                                            i15 ^= 52752;
                                                                                                                            if (i15 != 14) {
                                                                                                                                if (i15 == 45) {
                                                                                                                                    throw th2;
                                                                                                                                }
                                                                                                                                if (i15 != 1007) {
                                                                                                                                    if (i15 == 7927) {
                                                                                                                                        try {
                                                                                                                                            C0002.m190(fileOutputStream);
                                                                                                                                            int i16 = 53510;
                                                                                                                                            while (true) {
                                                                                                                                                i16 ^= 53527;
                                                                                                                                                if (i16 == 17) {
                                                                                                                                                    i16 = 53541;
                                                                                                                                                } else if (i16 == 50) {
                                                                                                                                                    throw th2;
                                                                                                                                                }
                                                                                                                                            }
                                                                                                                                        } catch (Throwable unused3) {
                                                                                                                                            throw th2;
                                                                                                                                        }
                                                                                                                                    }
                                                                                                                                } else if (fileOutputStream != null) {
                                                                                                                                    i15 = 53479;
                                                                                                                                }
                                                                                                                            }
                                                                                                                            i15 = 52797;
                                                                                                                        }
                                                                                                                    }
                                                                                                                }
                                                                                                            } catch (Throwable th3) {
                                                                                                                th = th3;
                                                                                                                fileOutputStream = null;
                                                                                                            }
                                                                                                        }
                                                                                                    } else if (b4 == 70) {
                                                                                                        i10 = 51557;
                                                                                                    }
                                                                                                }
                                                                                                i10 = 50875;
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            } else if (i7 == 241) {
                                                                if (b == Byte.MAX_VALUE) {
                                                                    i7 = 49883;
                                                                }
                                                            }
                                                        }
                                                        i7 = 49852;
                                                    }
                                                } else if (i6 == 83) {
                                                    break;
                                                }
                                            }
                                        }
                                        m52(m1(new int[]{101, 120, 116, 114, 97, 99, 116, 78, 97, 116, 105, 118, 101, 58, 32, 100, 101, 99, 114, 121, 112, 116, 101, 100, 32, 100, 97, 116, 97, 32, 105, 115, 32, 110, 111, 116, 32, 69, 76, 70, 32, 40, 112, 107, 103, 32, 109, 105, 115, 109, 97, 116, 99, 104, 63, 41}));
                                        return null;
                                    } catch (Throwable th4) {
                                        m9(m1(new int[]{101, 120, 116, 114, 97, 99, 116, 78, 97, 116, 105, 118, 101, 58, 32, 100, 101, 99, 114, 121, 112, 116, 32, 102, 97, 105, 108, 101, 100}), th4);
                                        return null;
                                    }
                                }
                                if (i5 != 503) {
                                    if (i5 == 32495) {
                                        return null;
                                    }
                                } else if (m89 == null) {
                                    i5 = 49635;
                                }
                            }
                            i5 = 48953;
                        }
                    }
                } else if (m67 == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private String extractOrigApk(Context context) {
        File m6 = m6(this, context, C0001.m169(m19(), 104, C0000.f4 ^ 84, 3139), new File(new File(C0003.m298(context), m42()), m1(new int[]{111, 114, 105, 103, 46, 97, 112, 107})));
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    } else if (i == 239) {
                        return C0004.m318(m6);
                    }
                } else if (m6 == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private static long fieldOffset(Object obj, Field field) {
        Long l = (Long) C0003.m267(m7(), field);
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C0004.m340(l);
                    }
                    if (i == 239) {
                        try {
                            long m224 = C0002.m224((Number) C0003.m293(C0003.m295(C0003.m271(obj), m1(new int[]{111, 98, 106, 101, 99, 116, 70, 105, 101, 108, 100, 79, 102, 102, 115, 101, 116}), new Class[]{Field.class}), obj, new Object[]{field}));
                            C0001.m182(m7(), field, C0001.m166(m224));
                            return m224;
                        } catch (Throwable unused) {
                            return C0001.f5 ^ 578;
                        }
                    }
                } else if (l != null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private static Object fieldValue(Object obj, String str) {
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        break;
                    }
                    if (i == 239) {
                        try {
                            Field m326 = C0004.m326(C0003.m271(obj), str);
                            C0001.m152(m326, true);
                            return C0003.m283(m326, obj);
                        } catch (Throwable unused) {
                            return null;
                        }
                    }
                } else if (obj == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

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
    
        r7 = r7 ^ 49931;
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
    
        r4 = r4 ^ 50706;
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
    
        r11 = r11 ^ 49683;
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
    
        r0 = r0 ^ 1757;
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
        r7 = org.lsposed.hiddenapibypass.library.C0003.m295(r6, m1(new int[]{103, 101, 116, 73, 110, 115, 116, 97, 110, 99, 101, 70, 105, 101, 108, 100, 115}), new java.lang.Class[]{java.lang.Class.class});
        r6 = org.lsposed.hiddenapibypass.library.C0003.m295(r6, m1(new int[]{103, 101, 116, 83, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 115}), new java.lang.Class[]{java.lang.Class.class});
        r7 = org.lsposed.hiddenapibypass.library.C0003.m287((java.util.List) org.lsposed.hiddenapibypass.library.C0003.m293(r7, null, new java.lang.Object[]{r14}));
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x006d, code lost:
    
        r9 = org.lsposed.hiddenapibypass.library.C0004.m320(r7);
        r10 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0073, code lost:
    
        r10 = r10 ^ 1881;
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
    
        r6 = r6 ^ 48784;
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
    
        r7 = r7 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00ca, code lost:
    
        if (r7 == 22) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00ce, code lost:
    
        if (r7 == 53) goto L174;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.reflect.Field findField(java.lang.Class<?> r14, java.lang.String r15) {
        /*
            Method dump skipped, instructions count: 514
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.findField(java.lang.Class, java.lang.String):java.lang.reflect.Field");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x007c, code lost:
    
        r8 = com.yan.hook.C0001.m156(r6);
        r7 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x0083, code lost:
    
        r7 = r7 ^ 49683;
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
    
        r6 = r6 ^ 49807;
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
    
        r0 = r0 ^ 1757;
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
    
        r6 = r6 ^ 1881;
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
    
        r0 = r0 ^ 48784;
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
    
        r8 = r8 ^ 48908;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.reflect.Method findMethod(java.lang.Class<?> r10, java.lang.String r11) {
        /*
            Method dump skipped, instructions count: 232
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.findMethod(java.lang.Class, java.lang.String):java.lang.reflect.Method");
    }

    private static void freeReflection() {
        m52(m1(new int[]{102, 114, 101, 101, 82, 101, 102, 108, 101, 99, 116, 105, 111, 110, 58, 32, 115, 107, 105, 112, 112, 101, 100, 32, 40, 110, 111, 32, 115, 101, 116, 72, 105, 100, 100, 101, 110, 65, 112, 105, 69, 120, 101, 109, 112, 116, 105, 111, 110, 115, 41}));
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0030, code lost:
    
        r0 = m47(r0, m51(org.lsposed.hiddenapibypass.library.C0003.m271(r0), com.yan.hook.C0002.m191(m19(), 107, com.yan.hook.C0001.f5 ^ (-582), 2670)));
        r1 = r0 instanceof android.os.IBinder;
        r2 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0050, code lost:
    
        r2 = r2 ^ 1757;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static android.os.IBinder getRealServiceManagerBinder() {
        /*
            java.lang.String r0 = m99()     // Catch: java.lang.Throwable -> L76
            java.lang.Class r0 = org.lsposed.hiddenapibypass.library.C0004.m342(r0)     // Catch: java.lang.Throwable -> L76
            r1 = 15
            int[] r1 = new int[r1]     // Catch: java.lang.Throwable -> L76
            r1 = {x0078: FILL_ARRAY_DATA , data: [115, 83, 101, 114, 118, 105, 99, 101, 77, 97, 110, 97, 103, 101, 114} // fill-array     // Catch: java.lang.Throwable -> L76
            java.lang.String r1 = m1(r1)     // Catch: java.lang.Throwable -> L76
            java.lang.reflect.Field r0 = m51(r0, r1)     // Catch: java.lang.Throwable -> L76
            java.lang.Object r0 = m78(r0)     // Catch: java.lang.Throwable -> L76
            r1 = 1616(0x650, float:2.264E-42)
        L1d:
            r1 = r1 ^ 1633(0x661, float:2.288E-42)
            r2 = 14
            if (r1 == r2) goto L73
            r2 = 49
            if (r1 == r2) goto L6e
            r2 = 204(0xcc, float:2.86E-43)
            if (r1 == r2) goto L30
            r2 = 239(0xef, float:3.35E-43)
            if (r1 == r2) goto L76
            goto L1d
        L30:
            java.lang.Class r1 = org.lsposed.hiddenapibypass.library.C0003.m271(r0)     // Catch: java.lang.Throwable -> L76
            short[] r2 = m19()     // Catch: java.lang.Throwable -> L76
            int r3 = com.yan.hook.C0001.f5     // Catch: java.lang.Throwable -> L76
            r3 = r3 ^ (-582(0xfffffffffffffdba, float:NaN))
            r4 = 2670(0xa6e, float:3.741E-42)
            r5 = 107(0x6b, float:1.5E-43)
            java.lang.String r2 = com.yan.hook.C0002.m191(r2, r5, r3, r4)     // Catch: java.lang.Throwable -> L76
            java.lang.reflect.Field r1 = m51(r1, r2)     // Catch: java.lang.Throwable -> L76
            java.lang.Object r0 = m47(r0, r1)     // Catch: java.lang.Throwable -> L76
            boolean r1 = r0 instanceof android.os.IBinder     // Catch: java.lang.Throwable -> L76
            r2 = 1740(0x6cc, float:2.438E-42)
        L50:
            r2 = r2 ^ 1757(0x6dd, float:2.462E-42)
            r3 = 17
            if (r2 == r3) goto L66
            r3 = 54
            if (r2 == r3) goto L6b
            r3 = 471(0x1d7, float:6.6E-43)
            if (r2 == r3) goto L76
            r3 = 500(0x1f4, float:7.0E-43)
            if (r2 == r3) goto L63
            goto L50
        L63:
            android.os.IBinder r0 = (android.os.IBinder) r0     // Catch: java.lang.Throwable -> L76
            return r0
        L66:
            if (r1 == 0) goto L6b
            r2 = 1833(0x729, float:2.569E-42)
            goto L50
        L6b:
            r2 = 1802(0x70a, float:2.525E-42)
            goto L50
        L6e:
            if (r0 == 0) goto L73
            r1 = 1709(0x6ad, float:2.395E-42)
            goto L1d
        L73:
            r1 = 1678(0x68e, float:2.351E-42)
            goto L1d
        L76:
            r0 = 0
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.getRealServiceManagerBinder():android.os.IBinder");
    }

    /* JADX WARN: Code restructure failed: missing block: B:160:0x01e9, code lost:
    
        r2 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x01ec, code lost:
    
        r2 = r2 ^ 50830;
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
    
        if (r12 == r3) goto L227;
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
    
        r5 = r5 ^ 51605;
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
    
        r13 = r13 ^ 49683;
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
    
        r4 = r4 ^ 51729;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static void hideHookDex() throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 628
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.hideHookDex():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x015d, code lost:
    
        if (r0 == 83) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0160, code lost:
    
        m87(r10, r11);
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{112, 97, 116, 99, 104, 32, 109, 73, 110, 105, 116, 105, 97, 108, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 32, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 73, 110, 102, 111, 58, 32}))), com.yan.hook.C0002.m221(r11))));
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0186, code lost:
    
        r11 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0189, code lost:
    
        r11 = r11 ^ 49807;
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
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{112, 97, 116, 99, 104, 32, 99, 97, 99, 104, 101, 100, 32, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 73, 110, 102, 111, 58, 32}))), com.yan.hook.C0002.m221(r11))));
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0101, code lost:
    
        r11 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0104, code lost:
    
        r11 = r11 ^ 48784;
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
    
        r0 = r0 ^ 49683;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private void hook(android.content.Context r11) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 792
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.hook(android.content.Context):void");
    }

    private void installCreatorHook() {
        try {
            m84(this, PackageInfo.class);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i == 14) {
                    break;
                } else if (i == 49) {
                    i = 1647;
                }
            }
        } catch (Throwable unused) {
        }
        try {
            m84(this, ApplicationInfo.class);
            int i2 = 1740;
            while (true) {
                i2 ^= 1757;
                if (i2 == 17) {
                    i2 = 1771;
                } else if (i2 == 54) {
                    break;
                }
            }
        } catch (Throwable unused2) {
        }
        m52(m1(new int[]{105, 110, 115, 116, 97, 108, 108, 67, 114, 101, 97, 116, 111, 114, 72, 111, 111, 107, 58, 32, 100, 111, 110, 101}));
        int m188 = C0002.m188();
        int i3 = 1864;
        while (true) {
            i3 ^= 1881;
            if (i3 == 17) {
                i3 = m188 >= 0 ? 48736 : 48705;
            } else {
                if (i3 == 47384) {
                    return;
                }
                if (i3 == 47417) {
                    System.out.println(Double.decode(C0003.m275("K6xGnVI3SzxuhqimPX7AUhkavZ")));
                    return;
                } else if (i3 != 47483) {
                }
            }
        }
    }

    private void installCtxHook(IBinder iBinder) {
        boolean z;
        try {
            g_smFake = iBinder;
            g_realSM = m86();
            StringBuilder m336 = C0004.m336(C0001.m173(new StringBuilder(C0002.m204(m1(new int[]{105, 110, 115, 116, 97, 108, 108, 67, 116, 120, 72, 111, 111, 107, 40, 112, 114, 101, 112, 97, 114, 101, 41, 58, 32, 114, 101, 97, 108, 83, 77, 61}))), m45()), C0002.m191(m19(), 191, C0000.f4 ^ 81, 823));
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = 1771;
                                } else if (i2 == 54) {
                                    break;
                                }
                            }
                            z = true;
                        } else if (i == 239) {
                            z = false;
                            break;
                        }
                    } else if (iBinder != null) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
            m52(C0003.m281(C0003.m301(m336, z)));
            int i3 = 1864;
            while (true) {
                i3 ^= 1881;
                if (i3 == 17) {
                    i3 = 48674;
                } else if (i3 == 47483) {
                    break;
                }
            }
        } catch (Throwable th) {
            m9(m1(new int[]{105, 110, 115, 116, 97, 108, 108, 67, 116, 120, 72, 111, 111, 107, 32, 102, 97, 105, 108, 101, 100}), th);
        }
        int m143 = C0001.m143();
        int i4 = 48767;
        while (true) {
            i4 ^= 48784;
            if (i4 != 14) {
                if (i4 == 45) {
                    return;
                }
                if (i4 == 76) {
                    System.out.println(C0001.m158("hdTorl4FEnLbO02"));
                    return;
                } else if (i4 == 239) {
                    if (m143 <= 0) {
                        i4 = 48860;
                    }
                }
            }
            i4 = 48829;
        }
    }

    private static void installFakeCache(IBinder iBinder) {
        try {
            Field m326 = C0004.m326(C0004.m342(m99()), C0004.m323(m19(), 197, C0000.f4 ^ 81, 3152));
            Object m78 = m78(m326);
            boolean z = m78 instanceof Map;
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            int m188 = C0002.m188();
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = m188 >= 0 ? 1833 : 1802;
                                } else if (i2 != 54) {
                                    if (i2 == 471) {
                                        return;
                                    }
                                    if (i2 == 500) {
                                        System.out.println(Double.decode(C0000.m137("9vgFXEZLUrmAQ7QuU04M8x6ELMVj")));
                                        return;
                                    }
                                }
                            }
                        } else if (i == 239) {
                            m14(m326, new FakeCache((Map) m78, iBinder));
                            int i3 = 1864;
                            while (true) {
                                i3 ^= 1881;
                                if (i3 == 17) {
                                    i3 = 48674;
                                } else if (i3 == 47483) {
                                    return;
                                }
                            }
                        }
                    } else if (!z) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        } catch (Throwable unused) {
        }
    }

    private void installNative(Context context) {
        String m74 = m74(this);
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return;
                    }
                    if (i == 239) {
                        try {
                            String m339 = C0004.m339(C0003.m297(context));
                            boolean m18 = m18();
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = !m18 ? 1833 : 1802;
                                } else if (i2 != 54) {
                                    if (i2 == 471) {
                                        break;
                                    }
                                    if (i2 == 500) {
                                        File m76 = m76(this, context);
                                        int i3 = 1864;
                                        while (true) {
                                            i3 ^= 1881;
                                            if (i3 == 17) {
                                                i3 = m76 == null ? 48736 : 48705;
                                            } else {
                                                if (i3 == 47384) {
                                                    C0002.m186(C0004.m318(m76));
                                                    nativeLoaded = true;
                                                    break;
                                                }
                                                if (i3 == 47417) {
                                                    int m274 = C0003.m274();
                                                    int i4 = 48767;
                                                    while (true) {
                                                        i4 ^= 48784;
                                                        if (i4 != 14) {
                                                            if (i4 == 45) {
                                                                return;
                                                            }
                                                            if (i4 == 76) {
                                                                System.out.println(Float.decode(C0001.m158("S8GYU8S0Wbabh")));
                                                                return;
                                                            } else if (i4 == 239) {
                                                                if (m274 <= 0) {
                                                                    i4 = 48860;
                                                                }
                                                            }
                                                        }
                                                        i4 = 48829;
                                                    }
                                                } else if (i3 != 47483) {
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            m24(m339, m74(this));
                            m54(this);
                            m26(this);
                            int i5 = 48891;
                            while (true) {
                                i5 ^= 48908;
                                if (i5 == 22) {
                                    return;
                                }
                                if (i5 == 503) {
                                    i5 = 48922;
                                }
                            }
                        } catch (Throwable th) {
                            m9(m1(new int[]{105, 110, 115, 116, 97, 108, 108, 78, 97, 116, 105, 118, 101, 32, 102, 97, 105, 108, 101, 100}), th);
                            return;
                        }
                    }
                } else if (m74 == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

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
    
        r12 = r12 ^ 49931;
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
    
        r3 = r3 ^ 50706;
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
    
        r6 = r6 ^ 51605;
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
    
        r3 = r3 ^ 51729;
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
    
        r3 = r3 ^ 51853;
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
    
        r15 = r15 ^ 52628;
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
    
        r15 = r15 ^ 52752;
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
    
        r8 = r8 ^ 50830;
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
    
        r12 = r12 ^ 1881;
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
    
        r1 = r1 ^ 48784;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private android.content.pm.Signature[] loadSignatures(android.content.Context r15) throws java.lang.Exception {
        /*
            Method dump skipped, instructions count: 442
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.loadSignatures(android.content.Context):android.content.pm.Signature[]");
    }

    private static void log(String str) {
    }

    private static void log(String str, Throwable th) {
    }

    public static IBinder makeFakeSM() {
        try {
            m52(m1(new int[]{109, 97, 107, 101, 70, 97, 107, 101, 83, 77, 58, 32, 99, 97, 108, 108, 101, 100}));
            return new FakeSM();
        } catch (Throwable unused) {
            return null;
        }
    }

    private static native void nativeInstall(String str, String str2);

    private static native void nativeInstallCtxHook(Class<?> cls);

    private void patchAppInfoSignatures(ApplicationInfo applicationInfo) {
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        int m143 = C0001.m143();
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = m143 <= 0 ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    return;
                                }
                                if (i2 == 500) {
                                    System.out.println(Integer.parseInt(C0002.m231("NYqxhw0UfeV523Gd76F")));
                                    return;
                                }
                            }
                        }
                    } else if (i == 239) {
                        try {
                            Field m65 = m65();
                            int i3 = 1864;
                            while (true) {
                                i3 ^= 1881;
                                if (i3 == 17) {
                                    i3 = m65 != null ? 48736 : 48705;
                                } else if (i3 == 47384) {
                                    Field m51 = m51(ApplicationInfo.class, C0002.m191(m19(), 206, C0004.f13 ^ (-353), 2197));
                                    int i4 = 48767;
                                    while (true) {
                                        i4 ^= 48784;
                                        if (i4 != 14) {
                                            if (i4 == 45) {
                                                return;
                                            }
                                            if (i4 == 76) {
                                                m15(applicationInfo, m51, m25(this));
                                                int i5 = 48891;
                                                while (true) {
                                                    i5 ^= 48908;
                                                    if (i5 == 22) {
                                                        return;
                                                    }
                                                    if (i5 == 503) {
                                                        i5 = 48922;
                                                    }
                                                }
                                            } else if (i4 == 239) {
                                                if (m51 != null) {
                                                    i4 = 48860;
                                                }
                                            }
                                        }
                                        i4 = 48829;
                                    }
                                } else if (i3 == 47417) {
                                    m15(applicationInfo, m65(), m25(this));
                                    return;
                                } else if (i3 != 47483) {
                                }
                            }
                        } catch (Throwable unused) {
                            return;
                        }
                    }
                } else if (applicationInfo == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private void patchByType(Object obj) {
        boolean z = obj instanceof PackageInfo;
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        m32(this, (PackageInfo) obj);
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    } else if (i == 239) {
                        boolean z2 = obj instanceof ApplicationInfo;
                        int i3 = 1864;
                        while (true) {
                            i3 ^= 1881;
                            if (i3 == 17) {
                                i3 = z2 ? 48736 : 48705;
                            } else {
                                if (i3 == 47384) {
                                    return;
                                }
                                if (i3 == 47417) {
                                    m87(this, (ApplicationInfo) obj);
                                    return;
                                } else if (i3 != 47483) {
                                }
                            }
                        }
                    }
                } else if (z) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private static void patchCheckService2(Parcel parcel) throws Exception {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        Class m342 = C0004.m342(m44());
                        Object m229 = C0002.m229(parcel, (Parcelable.Creator) m78(m51(m342, C0001.m169(m19(), 216, C0002.f6 ^ (-487), 569))));
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = m229 == null ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    Object m47 = m47(m229, m51(m342, C0003.m285(m19(), 223, C0002.f6 ^ (-488), 2757)));
                                    int i3 = 48767;
                                    while (true) {
                                        i3 ^= 48784;
                                        if (i3 != 14) {
                                            if (i3 == 45) {
                                                m52(m1(new int[]{112, 97, 116, 99, 104, 67, 104, 101, 99, 107, 83, 101, 114, 118, 105, 99, 101, 50, 58, 32, 95, 118, 97, 108, 117, 101, 32, 110, 117, 108, 108, 32, 40, 116, 97, 103, 63, 41}));
                                                break;
                                            }
                                            if (i3 == 76) {
                                                m15(m47, m51(C0003.m271(m47), C0002.m191(m19(), 229, C0003.f12 ^ 682, 2792)), m11());
                                                int i4 = 48891;
                                                while (true) {
                                                    i4 ^= 48908;
                                                    if (i4 == 22) {
                                                        break;
                                                    } else if (i4 == 503) {
                                                        i4 = 48922;
                                                    }
                                                }
                                            } else if (i3 == 239) {
                                                if (m47 != null) {
                                                    i3 = 48860;
                                                }
                                            }
                                        }
                                        i3 = 48829;
                                    }
                                    Parcel m299 = C0003.m299();
                                    try {
                                        C0002.m222(m299);
                                        C0003.m261(m299, (Parcelable) m229, 0);
                                        C0004.m325(parcel, 0);
                                        C0002.m202(parcel, 0);
                                        C0004.m345(parcel, m299, 0, C0004.m335(m299));
                                        C0004.m325(parcel, 0);
                                        return;
                                    } finally {
                                        C0003.m290(m299);
                                    }
                                }
                                if (i2 == 500) {
                                    break;
                                }
                            }
                        }
                    } else if (i == 239) {
                        break;
                    }
                } else if (m337 == 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        C0004.m325(parcel, 0);
        int m188 = C0002.m188();
        int i5 = 1864;
        while (true) {
            i5 ^= 1881;
            if (i5 == 17) {
                i5 = m188 >= 0 ? 48736 : 48705;
            } else {
                if (i5 == 47384) {
                    return;
                }
                if (i5 == 47417) {
                    System.out.println(Integer.valueOf(C0001.m158("zucLeRa98")));
                    return;
                } else if (i5 != 47483) {
                }
            }
        }
    }

    private static void patchGetService(Parcel parcel) throws Exception {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        C0004.m325(parcel, 0);
                        return;
                    }
                    if (i == 239) {
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
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = m138 >= 0 ? 1833 : 1802;
                                } else if (i2 != 54) {
                                    if (i2 == 471) {
                                        return;
                                    }
                                    if (i2 == 500) {
                                        System.out.println(Float.parseFloat(C0002.m231("GHvtzCg8bF0")));
                                        return;
                                    }
                                }
                            }
                        } catch (Throwable th) {
                            C0003.m290(m299);
                            throw th;
                        }
                    }
                } else if (m337 != 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private void patchInfo(PackageInfo packageInfo) {
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        boolean m200 = C0002.m200(m80(this), C0001.m162(packageInfo));
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = m200 ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    return;
                                }
                                if (i2 == 500) {
                                    packageInfo.signatures = m25(this);
                                    m93(this, packageInfo);
                                    ApplicationInfo m174 = C0001.m174(packageInfo);
                                    int i3 = 1864;
                                    while (true) {
                                        i3 ^= 1881;
                                        if (i3 == 17) {
                                            i3 = m174 != null ? 48736 : 48705;
                                        } else if (i3 != 47384) {
                                            if (i3 == 47417) {
                                                m87(this, C0001.m174(packageInfo));
                                                break;
                                            } else if (i3 != 47483) {
                                            }
                                        } else {
                                            break;
                                        }
                                    }
                                    try {
                                        packageInfo.providers = null;
                                        int i4 = 48767;
                                        while (true) {
                                            i4 ^= 48784;
                                            if (i4 == 14) {
                                                return;
                                            }
                                            if (i4 == 239) {
                                                i4 = 48798;
                                            }
                                        }
                                    } catch (Throwable unused) {
                                        return;
                                    }
                                }
                            }
                        }
                    } else if (i == 239) {
                        return;
                    }
                } else if (packageInfo != null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:22:0x001b, code lost:
    
        r3 = r0[r2];
        r6 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m191(m19(), 236, com.yan.hook.C0001.f5 ^ (-588), 2797), r3);
        r7 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x0033, code lost:
    
        r7 = r7 ^ 1757;
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
    
        r7 = r7 ^ 1881;
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
    
        r7 = r7 ^ 48784;
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
    
        r3 = r3 ^ 48908;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String pickAbi() {
        /*
            Method dump skipped, instructions count: 220
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.pickAbi():java.lang.String");
    }

    private static List<?> plsGetList(Object obj) {
        try {
            Method m13 = m13();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return (List) C0003.m293(m13(), obj, new Object[0]);
                        }
                        if (i == 239) {
                            return null;
                        }
                    } else if (m13 != null) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        } catch (Throwable unused) {
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:152:0x0165, code lost:
    
        com.yan.hook.PmsHookProvider.CACHE_PLS_GETLIST = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0167, code lost:
    
        r1 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x016a, code lost:
    
        r1 = r1 ^ 50830;
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
    
        r2 = r2 ^ 49807;
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
    
        r4 = r4 ^ 1757;
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
    
        r0 = r0 ^ 48908;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static void prefetchFields() {
        /*
            Method dump skipped, instructions count: 432
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.prefetchFields():void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x00a3, code lost:
    
        if (r11 == 17) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x00a5, code lost:
    
        if (r11 == 50) goto L66;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x00a7, code lost:
    
        if (r11 == r14) goto L339;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00ab, code lost:
    
        if (r11 == 83) goto L347;
     */
    /* JADX WARN: Code restructure failed: missing block: B:106:0x00b0, code lost:
    
        r4 = r4 + r7;
        r7 = 52611;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x00b4, code lost:
    
        r7 = r7 ^ 52628;
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
    
        r1 = r1 ^ 49807;
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
    
        if (r7 == r0) goto L361;
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
    
        r0 = r0 ^ 49931;
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
    
        r0 = r0 ^ 50706;
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
    
        r0 = r0 ^ 51605;
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
    
        r0 = r0 ^ 51729;
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
    
        r4 = r4 ^ 52752;
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
    
        m9(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{114, 101, 97, 100, 65, 115, 115, 101, 116, 66, 121, 116, 101, 115, 32, 102, 97, 105, 108, 101, 100, 58, 32}))), r21)), r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x0238, code lost:
    
        r0 = 53758;
     */
    /* JADX WARN: Code restructure failed: missing block: B:269:0x023b, code lost:
    
        r0 = r0 ^ 53775;
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
    
        r14 = r14 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x024c, code lost:
    
        com.yan.hook.C0002.m227(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:291:0x024f, code lost:
    
        r0 = 54533;
     */
    /* JADX WARN: Code restructure failed: missing block: B:292:0x0252, code lost:
    
        r0 = r0 ^ 54550;
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
    
        r0 = r0 ^ 54674;
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
    
        r0 = r0 ^ 55449;
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
    
        r1 = r1 ^ 55573;
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
    
        r1 = r1 ^ 55697;
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
    
        r1 = r1 ^ 56472;
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
    
        r1 = r1 ^ 56596;
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
    
        r0 = r0 ^ 53527;
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
    
        r11 = r11 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0059, code lost:
    
        if (r11 == 14) goto L329;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x005b, code lost:
    
        if (r11 == r4) goto L285;
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
    
        r11 = r11 ^ 48908;
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
    
        r11 = r11 ^ 49683;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static byte[] readAssetBytes(android.content.Context r20, java.lang.String r21) {
        /*
            Method dump skipped, instructions count: 846
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.readAssetBytes(android.content.Context, java.lang.String):byte[]");
    }

    private static Object readParceledListSlice(Parcel parcel) {
        try {
            Object m38 = m38();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            Method m295 = C0003.m295(C0003.m271(m38()), m1(new int[]{99, 114, 101, 97, 116, 101, 70, 114, 111, 109, 80, 97, 114, 99, 101, 108}), new Class[]{Parcel.class});
                            C0001.m171(m295, true);
                            return C0003.m293(m295, m38(), new Object[]{parcel});
                        }
                        if (i == 239) {
                            return null;
                        }
                    } else if (m38 != null) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        } catch (Throwable unused) {
            return null;
        }
    }

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
    
        r15 = r15 ^ 49683;
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
    
        r11 = r11 ^ 49807;
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
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(com.yan.hook.C0001.m167(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{70, 97, 107, 101, 83, 77, 32, 116, 114, 121, 32, 115, 116, 61}))), r10), org.lsposed.hiddenapibypass.library.C0004.m323(m19(), 304, com.yan.hook.C0001.f5 ^ (-588), 706)), r14)));
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x014c, code lost:
    
        r3 = com.yan.hook.C0002.m200(r9, r14);
        r9 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0153, code lost:
    
        r9 = r9 ^ 49931;
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
    
        r10 = r10 ^ 1757;
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
    
        r7 = r7 ^ 1881;
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
    
        r12 = r12 ^ 48784;
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
    
        if (r12 == r3) goto L161;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.String readServiceName(android.os.Parcel r17) {
        /*
            Method dump skipped, instructions count: 472
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.readServiceName(android.os.Parcel):java.lang.String");
    }

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
    
        r8 = r8 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x00f5, code lost:
    
        if (r8 == r5) goto L224;
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
    
        if (r8 == r4) goto L226;
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
    
        r0 = r0 ^ 50830;
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
    
        r5 = r5 ^ 51605;
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
    
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(com.yan.hook.C0001.m167(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 110, 101, 115, 116, 101, 100, 32}))), r4), com.yan.hook.C0002.m191(m19(), 347, com.yan.hook.C0000.f4 ^ 93, 3049)), r0)));
        r15 = r15 - (0 - r0);
        r0 = 51712;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x018c, code lost:
    
        r0 = r0 ^ 51729;
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
    
        r4 = r4 ^ 49807;
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
    
        if (r12 == r23) goto L272;
     */
    /* JADX WARN: Code restructure failed: missing block: B:224:0x02b2, code lost:
    
        r4 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:228:0x01ee, code lost:
    
        r4 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x01f2, code lost:
    
        m15(r22, r0, r23);
        m52(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32}))), org.lsposed.hiddenapibypass.library.C0004.m330(org.lsposed.hiddenapibypass.library.C0003.m271(r22))), com.yan.hook.C0001.m169(m19(), 331, 1, 2135)), r7), org.lsposed.hiddenapibypass.library.C0003.m285(m19(), 332, com.yan.hook.C0001.f5 ^ (-587), 3163))));
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x0290, code lost:
    
        r6 = r15 + (com.yan.hook.C0002.f6 ^ (-481));
        r0 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x029b, code lost:
    
        r0 = r0 ^ 49931;
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
    
        m9(org.lsposed.hiddenapibypass.library.C0003.m281(org.lsposed.hiddenapibypass.library.C0004.m336(org.lsposed.hiddenapibypass.library.C0004.m336(new java.lang.StringBuilder(com.yan.hook.C0002.m204(m1(new int[]{115, 109, 67, 97, 99, 104, 101, 72, 111, 111, 107, 58, 32, 115, 101, 116, 32}))), r7), com.yan.hook.C0001.m169(m19(), 340, com.yan.hook.C0001.f5 ^ (-582), 2084))), r0);
        r0 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:248:0x027d, code lost:
    
        r0 = r0 ^ 50706;
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
    
        r12 = r16 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00a6, code lost:
    
        if (r12 == r4) goto L203;
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
    
        if (r12 == r6) goto L202;
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
    
        r6 = r19 ^ 48908;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static int replaceBinderFields(java.lang.Object r22, android.os.IBinder r23, int r24) {
        /*
            Method dump skipped, instructions count: 1076
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.replaceBinderFields(java.lang.Object, android.os.IBinder, int):int");
    }

    private void replaceCreator(Class<?> cls) throws Exception {
        Field m51 = m51(cls, C0003.m285(m19(), 357, C0003.f12 ^ 682, 3230));
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return;
                    }
                    if (i == 239) {
                        Object m78 = m78(m51);
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = m78 == null ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    m14(m51, C0001.m185(new FakeClassLoader(), new Class[]{C0004.m342(m56())}, new InvocationHandler(this, m78) { // from class: com.yan.hook.PmsHookProvider.1
                                        final PmsHookProvider this$0;
                                        private final Object val$orig;

                                        {
                                            this.this$0 = this;
                                            this.val$orig = m78;
                                            int m274 = C0003.m274();
                                            int i3 = 1616;
                                            while (true) {
                                                i3 ^= 1633;
                                                if (i3 != 14) {
                                                    if (i3 != 49) {
                                                        if (i3 == 204) {
                                                            System.out.println(C0001.m158("AYEsP9y"));
                                                            return;
                                                        } else if (i3 == 239) {
                                                            return;
                                                        }
                                                    } else if (m274 <= 0) {
                                                        i3 = 1709;
                                                    }
                                                }
                                                i3 = 1678;
                                            }
                                        }

                                        /* renamed from: ۣ۠۟۟, reason: not valid java name and contains not printable characters */
                                        public static Object m103(Object obj) {
                                            int m138 = C0000.m138();
                                            int i3 = 1616;
                                            while (true) {
                                                i3 ^= 1633;
                                                if (i3 != 14) {
                                                    if (i3 != 49) {
                                                        if (i3 == 204) {
                                                            return ((AnonymousClass1) obj).val$orig;
                                                        }
                                                        if (i3 == 239) {
                                                            int i4 = 1740;
                                                            while (true) {
                                                                i4 ^= 1757;
                                                                if (i4 == 17) {
                                                                    i4 = 1771;
                                                                } else if (i4 == 54) {
                                                                    return null;
                                                                }
                                                            }
                                                        }
                                                    } else if (m138 < 0) {
                                                        i3 = 1709;
                                                    }
                                                }
                                                i3 = 1678;
                                            }
                                        }

                                        /* renamed from: ۣۡ۟۠, reason: not valid java name and contains not printable characters */
                                        public static void m104(Object obj, Object obj2) {
                                            int m274 = C0003.m274();
                                            int i3 = 1616;
                                            while (true) {
                                                i3 ^= 1633;
                                                if (i3 != 14) {
                                                    if (i3 != 49) {
                                                        if (i3 == 204) {
                                                            PmsHookProvider.m98((PmsHookProvider) obj, obj2);
                                                            break;
                                                        } else if (i3 == 239) {
                                                            int i4 = 1864;
                                                            while (true) {
                                                                i4 ^= 1881;
                                                                if (i4 == 17) {
                                                                    i4 = 48674;
                                                                } else if (i4 == 47483) {
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                    } else if (m274 >= 0) {
                                                        i3 = 1709;
                                                    }
                                                }
                                                i3 = 1678;
                                            }
                                            int m143 = C0001.m143();
                                            int i5 = 1740;
                                            while (true) {
                                                i5 ^= 1757;
                                                if (i5 == 17) {
                                                    i5 = m143 <= 0 ? 1833 : 1802;
                                                } else if (i5 != 54) {
                                                    if (i5 == 471) {
                                                        return;
                                                    }
                                                    if (i5 == 500) {
                                                        System.out.println(C0000.m137("nW2sUh9BMoD7WZs4S6mOStLIkfKMo"));
                                                        return;
                                                    }
                                                }
                                            }
                                        }

                                        /* renamed from: ۣۡۨ۠, reason: not valid java name and contains not printable characters */
                                        public static PmsHookProvider m105(Object obj) {
                                            int m138 = C0000.m138();
                                            int i3 = 1616;
                                            while (true) {
                                                i3 ^= 1633;
                                                if (i3 != 14) {
                                                    if (i3 != 49) {
                                                        if (i3 == 204) {
                                                            return ((AnonymousClass1) obj).this$0;
                                                        }
                                                        if (i3 == 239) {
                                                            int i4 = 1740;
                                                            while (true) {
                                                                i4 ^= 1757;
                                                                if (i4 == 17) {
                                                                    i4 = 1771;
                                                                } else if (i4 == 54) {
                                                                    return null;
                                                                }
                                                            }
                                                        }
                                                    } else if (m138 < 0) {
                                                        i3 = 1709;
                                                    }
                                                }
                                                i3 = 1678;
                                            }
                                        }

                                        /* renamed from: ۥۣ۠۠, reason: contains not printable characters */
                                        public static String m106(Object obj) {
                                            int m316 = C0004.m316();
                                            int i3 = 1616;
                                            while (true) {
                                                i3 ^= 1633;
                                                if (i3 != 14) {
                                                    if (i3 != 49) {
                                                        if (i3 == 204) {
                                                            return PmsHookProvider.m1((int[]) obj);
                                                        }
                                                        if (i3 == 239) {
                                                            int i4 = 1740;
                                                            while (true) {
                                                                i4 ^= 1757;
                                                                if (i4 == 17) {
                                                                    i4 = 1771;
                                                                } else if (i4 == 54) {
                                                                    return null;
                                                                }
                                                            }
                                                        }
                                                    } else if (m316 < 0) {
                                                        i3 = 1709;
                                                    }
                                                }
                                                i3 = 1678;
                                            }
                                        }

                                        @Override // java.lang.reflect.InvocationHandler
                                        public Object invoke(Object obj, Method method, Object[] objArr) throws Throwable {
                                            boolean m200 = C0002.m200(m106(new int[]{99, 114, 101, 97, 116, 101, 70, 114, 111, 109, 80, 97, 114, 99, 101, 108}), C0002.m210(method));
                                            int i3 = 1616;
                                            while (true) {
                                                i3 ^= 1633;
                                                if (i3 != 14) {
                                                    if (i3 != 49) {
                                                        if (i3 == 204) {
                                                            int i4 = 1740;
                                                            while (true) {
                                                                i4 ^= 1757;
                                                                if (i4 == 17) {
                                                                    i4 = objArr != null ? 1833 : 1802;
                                                                } else if (i4 != 54) {
                                                                    if (i4 == 471) {
                                                                        break;
                                                                    }
                                                                    if (i4 == 500) {
                                                                        int length = objArr.length;
                                                                        int i5 = 1864;
                                                                        while (true) {
                                                                            i5 ^= 1881;
                                                                            if (i5 == 17) {
                                                                                i5 = length == 1 ? 48736 : 48705;
                                                                            } else {
                                                                                if (i5 == 47384) {
                                                                                    break;
                                                                                }
                                                                                if (i5 == 47417) {
                                                                                    Object m293 = C0003.m293(method, m103(this), objArr);
                                                                                    try {
                                                                                        m104(m105(this), m293);
                                                                                        int i6 = 48767;
                                                                                        while (true) {
                                                                                            i6 ^= 48784;
                                                                                            if (i6 == 14) {
                                                                                                break;
                                                                                            }
                                                                                            if (i6 == 239) {
                                                                                                i6 = 48798;
                                                                                            }
                                                                                        }
                                                                                    } catch (Throwable unused) {
                                                                                    }
                                                                                    return m293;
                                                                                }
                                                                                if (i5 != 47483) {
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else if (i3 == 239) {
                                                            break;
                                                        }
                                                    } else if (m200) {
                                                        i3 = 1709;
                                                    }
                                                }
                                                i3 = 1678;
                                            }
                                            return C0003.m293(method, m103(this), objArr);
                                        }
                                    }));
                                    int m138 = C0000.m138();
                                    int i3 = 1864;
                                    while (true) {
                                        i3 ^= 1881;
                                        if (i3 == 17) {
                                            i3 = m138 >= 0 ? 48736 : 48705;
                                        } else {
                                            if (i3 == 47384) {
                                                return;
                                            }
                                            if (i3 == 47417) {
                                                System.out.println(Float.valueOf(C0004.m346("7OurGOKnLtbJ5Rw7terVDm")));
                                                return;
                                            } else if (i3 != 47483) {
                                            }
                                        }
                                    }
                                } else if (i2 == 500) {
                                    return;
                                }
                            }
                        }
                    }
                } else if (m51 == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private void replaceSigningInfo(PackageInfo packageInfo) {
        Object m37;
        try {
            SigningInfo m280 = C0003.m280(packageInfo);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            int m143 = C0001.m143();
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = m143 <= 0 ? 1833 : 1802;
                                } else if (i2 != 54) {
                                    if (i2 == 471) {
                                        return;
                                    }
                                    if (i2 == 500) {
                                        System.out.println(Double.valueOf(C0004.m346("9CG6Zo9zc9PSj")));
                                        return;
                                    }
                                }
                            }
                        } else if (i == 239) {
                            Field m0 = m0();
                            int i3 = 1864;
                            while (true) {
                                i3 ^= 1881;
                                if (i3 == 17) {
                                    i3 = m0 != null ? 48736 : 48705;
                                } else {
                                    if (i3 == 47384) {
                                        m37 = m37(m280, m1(new int[]{109, 83, 105, 103, 110, 105, 110, 103, 68, 101, 116, 97, 105, 108, 115}));
                                        break;
                                    }
                                    if (i3 == 47417) {
                                        m37 = m47(m280, m0());
                                        int i4 = 48767;
                                        while (true) {
                                            i4 ^= 48784;
                                            if (i4 == 14) {
                                                break;
                                            } else if (i4 == 239) {
                                                i4 = 48798;
                                            }
                                        }
                                    } else if (i3 != 47483) {
                                    }
                                }
                            }
                            int i5 = 48891;
                            while (true) {
                                i5 ^= 48908;
                                if (i5 != 22) {
                                    if (i5 == 53) {
                                        Field m41 = m41();
                                        int i6 = 49666;
                                        while (true) {
                                            i6 ^= 49683;
                                            if (i6 == 17) {
                                                i6 = m41 != null ? 49759 : 49728;
                                            } else if (i6 != 50) {
                                                if (i6 == 76) {
                                                    boolean m15 = m15(m37, m41(), m25(this));
                                                    int i7 = 49790;
                                                    while (true) {
                                                        i7 ^= 49807;
                                                        if (i7 != 18) {
                                                            if (i7 == 51) {
                                                                break;
                                                            }
                                                            if (i7 == 84) {
                                                                return;
                                                            }
                                                            if (i7 == 241) {
                                                                if (m15) {
                                                                    i7 = 49883;
                                                                }
                                                            }
                                                        }
                                                        i7 = 49852;
                                                    }
                                                } else if (i6 == 83) {
                                                    break;
                                                }
                                            }
                                        }
                                        boolean m66 = m66(m37, C0001.m169(m19(), 364, C0001.f5 ^ (-585), 893), m25(this));
                                        int i8 = 49914;
                                        while (true) {
                                            i8 ^= 49931;
                                            if (i8 == 497) {
                                                i8 = !m66 ? 50658 : 50627;
                                            } else if (i8 != 1711) {
                                                if (i8 == 1736) {
                                                    return;
                                                }
                                                if (i8 == 1769) {
                                                    m66(m37, C0002.m191(m19(), 374, C0000.f4 ^ 92, 1037), m25(this));
                                                    int i9 = 50689;
                                                    while (true) {
                                                        i9 ^= 50706;
                                                        if (i9 == 19) {
                                                            i9 = 50720;
                                                        } else if (i9 == 50) {
                                                            return;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    } else if (i5 != 503) {
                                        if (i5 == 32495) {
                                            return;
                                        }
                                    } else if (m37 == null) {
                                        i5 = 49635;
                                    }
                                }
                                i5 = 48953;
                            }
                        }
                    } else if (m280 == null) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        } catch (Throwable unused) {
        }
    }

    private static void resolveTransactions() {
        try {
            Class m342 = C0004.m342(m95());
            TX_getPackageInfo = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 103, 101, 116, 80, 97, 99, 107, 97, 103, 101, 73, 110, 102, 111}), m77());
            TX_getPackageInfoVersioned = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 103, 101, 116, 80, 97, 99, 107, 97, 103, 101, 73, 110, 102, 111, 86, 101, 114, 115, 105, 111, 110, 101, 100}), m46());
            TX_getApplicationInfo = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 103, 101, 116, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 73, 110, 102, 111}), m83());
            TX_getApplicationInfoVersioned = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 103, 101, 116, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 73, 110, 102, 111, 86, 101, 114, 115, 105, 111, 110, 101, 100}), m12());
            TX_getPackageArchiveInfo = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 103, 101, 116, 80, 97, 99, 107, 97, 103, 101, 65, 114, 99, 104, 105, 118, 101, 73, 110, 102, 111}), m27());
            TX_getInstalledPackages = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 103, 101, 116, 73, 110, 115, 116, 97, 108, 108, 101, 100, 80, 97, 99, 107, 97, 103, 101, 115}), m55());
            TX_getInstalledApplications = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 103, 101, 116, 73, 110, 115, 116, 97, 108, 108, 101, 100, 65, 112, 112, 108, 105, 99, 97, 116, 105, 111, 110, 115}), m90());
            TX_hasSigningCertificate = m40(m342, m1(new int[]{84, 82, 65, 78, 83, 65, 67, 84, 73, 79, 78, 95, 104, 97, 115, 83, 105, 103, 110, 105, 110, 103, 67, 101, 114, 116, 105, 102, 105, 99, 97, 116, 101}), m49());
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i == 14) {
                    return;
                }
                if (i == 49) {
                    i = 1647;
                }
            }
        } catch (Throwable unused) {
        }
    }

    private static void restoreHiddenApi() {
    }

    /* JADX WARN: Code restructure failed: missing block: B:137:0x009b, code lost:
    
        r11 = org.lsposed.hiddenapibypass.library.C0004.m329(r4);
        r12 = r11 instanceof android.content.pm.ApplicationInfo;
        r10 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x00a4, code lost:
    
        r10 = r10 ^ 49807;
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
    
        r12 = r12 ^ 49931;
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
    
        r9 = r9 ^ 50706;
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
    
        r15 = r15 ^ 49683;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private void rewriteApplicationInfoListReply(android.os.Parcel r15) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.rewriteApplicationInfoListReply(android.os.Parcel):void");
    }

    private void rewriteApplicationInfoReply(Parcel parcel) {
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        int m3372 = C0004.m337(parcel);
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = m3372 != 0 ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    break;
                                }
                                if (i2 == 500) {
                                    ApplicationInfo applicationInfo = (ApplicationInfo) C0002.m232(C0002.m218(), parcel);
                                    int i3 = 1864;
                                    while (true) {
                                        i3 ^= 1881;
                                        if (i3 == 17) {
                                            i3 = applicationInfo != null ? 48736 : 48705;
                                        } else {
                                            if (i3 == 47384) {
                                                break;
                                            }
                                            if (i3 == 47417) {
                                                String m80 = m80(this);
                                                int i4 = 48767;
                                                while (true) {
                                                    i4 ^= 48784;
                                                    if (i4 != 14) {
                                                        if (i4 == 45) {
                                                            break;
                                                        }
                                                        if (i4 == 76) {
                                                            boolean m200 = C0002.m200(m80(this), C0002.m221(applicationInfo));
                                                            int i5 = 48891;
                                                            while (true) {
                                                                i5 ^= 48908;
                                                                if (i5 != 22) {
                                                                    if (i5 == 53) {
                                                                        break;
                                                                    }
                                                                    if (i5 != 503) {
                                                                        if (i5 == 32495) {
                                                                            m87(this, applicationInfo);
                                                                            m52(C0003.m281(C0004.m336(new StringBuilder(C0002.m204(m1(new int[]{114, 101, 119, 114, 105, 116, 101, 58, 32, 65, 112, 112, 73, 110, 102, 111, 32, 112, 107, 103, 61}))), C0002.m221(applicationInfo))));
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
                                                                                int i6 = 49666;
                                                                                while (true) {
                                                                                    i6 ^= 49683;
                                                                                    if (i6 == 17) {
                                                                                        i6 = m316 >= 0 ? 49759 : 49728;
                                                                                    } else if (i6 != 50) {
                                                                                        if (i6 == 76) {
                                                                                            System.out.println(Integer.decode(C0000.m137("vRUOvewbdC5kVMbU")));
                                                                                            return;
                                                                                        } else if (i6 == 83) {
                                                                                            return;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } catch (Throwable th) {
                                                                                C0003.m290(m299);
                                                                                throw th;
                                                                            }
                                                                        }
                                                                    } else if (m200) {
                                                                        i5 = 49635;
                                                                    }
                                                                }
                                                                i5 = 48953;
                                                            }
                                                        } else if (i4 == 239) {
                                                            if (m80 != null) {
                                                                i4 = 48860;
                                                            }
                                                        }
                                                    }
                                                    i4 = 48829;
                                                }
                                            } else if (i3 != 47483) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else if (i == 239) {
                        break;
                    }
                } else if (m337 == 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        C0004.m325(parcel, 0);
    }

    /* JADX WARN: Code restructure failed: missing block: B:137:0x009b, code lost:
    
        r11 = org.lsposed.hiddenapibypass.library.C0004.m329(r4);
        r12 = r11 instanceof android.content.pm.PackageInfo;
        r10 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x00a4, code lost:
    
        r10 = r10 ^ 49807;
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
    
        r12 = r12 ^ 49931;
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
    
        r9 = r9 ^ 50706;
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
    
        r15 = r15 ^ 49683;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private void rewritePackageInfoListReply(android.os.Parcel r15) {
        /*
            Method dump skipped, instructions count: 355
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.rewritePackageInfoListReply(android.os.Parcel):void");
    }

    private void rewritePackageInfoReply(Parcel parcel) {
        String m162;
        C0004.m325(parcel, 0);
        int m337 = C0004.m337(parcel);
        m52(C0003.m281(C0001.m167(new StringBuilder(C0002.m204(m1(new int[]{114, 101, 119, 114, 105, 116, 101, 58, 32, 101, 120, 99, 101, 112, 116, 105, 111, 110, 61}))), m337)));
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        int m3372 = C0004.m337(parcel);
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = m3372 != 0 ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    break;
                                }
                                if (i2 == 500) {
                                    PackageInfo packageInfo = (PackageInfo) C0002.m232(C0002.m214(), parcel);
                                    StringBuilder sb = new StringBuilder(C0002.m204(m1(new int[]{114, 101, 119, 114, 105, 116, 101, 58, 32, 112, 107, 103, 61})));
                                    int i3 = 1864;
                                    while (true) {
                                        i3 ^= 1881;
                                        if (i3 == 17) {
                                            i3 = packageInfo == null ? 48736 : 48705;
                                        } else {
                                            if (i3 == 47384) {
                                                m162 = C0001.m162(packageInfo);
                                                break;
                                            }
                                            if (i3 == 47417) {
                                                m162 = C0003.m285(m19(), 385, C0002.f6 ^ (-486), 2671);
                                                int i4 = 48767;
                                                while (true) {
                                                    i4 ^= 48784;
                                                    if (i4 == 14) {
                                                        break;
                                                    } else if (i4 == 239) {
                                                        i4 = 48798;
                                                    }
                                                }
                                            } else if (i3 != 47483) {
                                            }
                                        }
                                    }
                                    m52(C0003.m281(C0004.m336(sb, m162)));
                                    int i5 = 48891;
                                    while (true) {
                                        i5 ^= 48908;
                                        if (i5 != 22) {
                                            if (i5 == 53) {
                                                break;
                                            }
                                            if (i5 != 503) {
                                                if (i5 == 32495) {
                                                    String m80 = m80(this);
                                                    int i6 = 49666;
                                                    while (true) {
                                                        i6 ^= 49683;
                                                        if (i6 == 17) {
                                                            i6 = m80 != null ? 49759 : 49728;
                                                        } else if (i6 != 50) {
                                                            if (i6 == 76) {
                                                                boolean m200 = C0002.m200(m80(this), C0001.m162(packageInfo));
                                                                int i7 = 49790;
                                                                while (true) {
                                                                    i7 ^= 49807;
                                                                    if (i7 != 18) {
                                                                        if (i7 == 51) {
                                                                            break;
                                                                        }
                                                                        if (i7 == 84) {
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
                                                                                int i8 = 49914;
                                                                                while (true) {
                                                                                    i8 ^= 49931;
                                                                                    if (i8 == 497) {
                                                                                        i8 = m274 <= 0 ? 50658 : 50627;
                                                                                    } else if (i8 != 1711) {
                                                                                        if (i8 == 1736) {
                                                                                            return;
                                                                                        }
                                                                                        if (i8 == 1769) {
                                                                                            System.out.println(Integer.valueOf(C0000.m137("bJVTeAs")));
                                                                                            return;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } catch (Throwable th) {
                                                                                C0003.m290(m299);
                                                                                throw th;
                                                                            }
                                                                        } else if (i7 == 241) {
                                                                            if (m200) {
                                                                                i7 = 49883;
                                                                            }
                                                                        }
                                                                    }
                                                                    i7 = 49852;
                                                                }
                                                            } else if (i6 == 83) {
                                                                break;
                                                            }
                                                        }
                                                    }
                                                }
                                            } else if (packageInfo != null) {
                                                i5 = 49635;
                                            }
                                        }
                                        i5 = 48953;
                                    }
                                }
                            }
                        }
                    } else if (i == 239) {
                        break;
                    }
                } else if (m337 == 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        C0004.m325(parcel, 0);
    }

    private static boolean setObjField(Object obj, String str, Object obj2) {
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return false;
                    }
                    if (i == 239) {
                        Field m51 = m51(C0003.m271(obj), str);
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = m51 == null ? 1833 : 1802;
                            } else if (i2 != 54) {
                                if (i2 == 471) {
                                    return m15(obj, m51, obj2);
                                }
                                if (i2 == 500) {
                                    return false;
                                }
                            }
                        }
                    }
                } else if (obj == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private Signature[] sigs() {
        Signature[] m33 = m33(this);
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    } else if (i == 239) {
                        return (Signature[]) m10(m33(this));
                    }
                } else if (m33 == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private static byte[] soCrypt(byte[] bArr, String str) throws Exception {
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        MessageDigest m211 = C0002.m211(C0003.m285(m19(), 389, C0001.f5 ^ (-582), 2231));
                        String m1 = m1(new int[]{89, 84, 83, 79, 75, 69, 89, 49});
                        String m191 = C0002.m191(m19(), 396, C0003.f12 ^ 680, 1426);
                        C0004.m322(m211, C0003.m294(m1, m191));
                        C0004.m322(m211, C0003.m294(str, m191));
                        byte[] m319 = C0004.m319(m211);
                        C0001.m170(m211);
                        C0004.m322(m211, C0003.m294(m1(new int[]{89, 84, 83, 79, 73, 86, 48, 49}), m191));
                        C0004.m322(m211, C0003.m294(str, m191));
                        byte[] m3192 = C0004.m319(m211);
                        String m285 = C0003.m285(m19(), 401, C0004.f13 ^ (-362), 381);
                        int i2 = C0001.f5 ^ (-595);
                        SecretKeySpec secretKeySpec = new SecretKeySpec(m319, 0, i2, m285);
                        IvParameterSpec ivParameterSpec = new IvParameterSpec(m3192, 0, i2);
                        Cipher m151 = C0001.m151(m1(new int[]{65, 69, 83, 47, 67, 84, 82, 47, 78, 111, 80, 97, 100, 100, 105, 110, 103}));
                        C0003.m264(m151, C0004.f13 ^ (-361), secretKeySpec, ivParameterSpec);
                        return C0002.m216(m151, bArr);
                    }
                    if (i == 239) {
                        throw new IllegalArgumentException(C0004.m323(m19(), 404, C0000.f4 ^ 95, 1720));
                    }
                } else if (str != null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    private static int staticInt(Class<?> cls, String str, int i) {
        try {
            Field m51 = m51(cls, str);
            int i2 = 1616;
            while (true) {
                i2 ^= 1633;
                if (i2 != 14) {
                    if (i2 != 49) {
                        if (i2 == 204) {
                            return i;
                        }
                        if (i2 == 239) {
                            Object m61 = m61();
                            int i3 = 1740;
                            while (true) {
                                i3 ^= 1757;
                                if (i3 == 17) {
                                    i3 = m61 != null ? 1833 : 1802;
                                } else if (i3 != 54) {
                                    if (i3 == 471) {
                                        C0001.m152(m51, true);
                                        return C0004.m331(m51, null);
                                    }
                                    if (i3 == 500) {
                                        return C0001.m155((Number) C0003.m293(C0003.m295(C0003.m271(m61), C0002.m191(m19(), 412, C0002.f6 ^ (-488), 2675), new Class[]{Object.class, C0004.m343()}), m61, new Object[]{C0003.m293(C0003.m295(C0003.m271(m61), m1(new int[]{115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 66, 97, 115, 101}), new Class[]{Field.class}), m61, new Object[]{m51}), C0001.m166(C0002.m224((Number) C0003.m293(C0003.m295(C0003.m271(m61), m1(new int[]{115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 79, 102, 102, 115, 101, 116}), new Class[]{Field.class}), m61, new Object[]{m51})))}));
                                    }
                                }
                            }
                        }
                    } else if (m51 == null) {
                        i2 = 1709;
                    }
                }
                i2 = 1678;
            }
        } catch (Throwable unused) {
            return i;
        }
    }

    private static Object uAllocate(Class<?> cls) {
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        break;
                    }
                    if (i == 239) {
                        try {
                            Object m61 = m61();
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = m61 != null ? 1833 : 1802;
                                } else if (i2 != 54) {
                                    if (i2 == 471) {
                                        break;
                                    }
                                    if (i2 == 500) {
                                        return C0003.m293(C0003.m295(C0003.m271(m61), m1(new int[]{97, 108, 108, 111, 99, 97, 116, 101, 73, 110, 115, 116, 97, 110, 99, 101}), new Class[]{Class.class}), m61, new Object[]{cls});
                                    }
                                }
                            }
                        } catch (Throwable unused) {
                        }
                    }
                } else if (cls == null) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x0045, code lost:
    
        r5 = m63(r4, r11);
        r2 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x004c, code lost:
    
        r2 = r2 ^ 48784;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.Object uGet(java.lang.Object r10, java.lang.reflect.Field r11) {
        /*
            r0 = 1616(0x650, float:2.264E-42)
        L2:
            r0 = r0 ^ 1633(0x661, float:2.288E-42)
            r1 = 14
            if (r0 == r1) goto Lc4
            r2 = 49
            if (r0 == r2) goto Lbe
            r2 = 204(0xcc, float:2.86E-43)
            r3 = 239(0xef, float:3.35E-43)
            if (r0 == r2) goto L15
            if (r0 == r3) goto Lb2
            goto L2
        L15:
            r0 = 1740(0x6cc, float:2.438E-42)
        L17:
            r0 = r0 ^ 1757(0x6dd, float:2.462E-42)
            r2 = 17
            if (r0 == r2) goto Lb4
            r4 = 54
            if (r0 == r4) goto Lba
            r4 = 471(0x1d7, float:6.6E-43)
            if (r0 == r4) goto Lb2
            r4 = 500(0x1f4, float:7.0E-43)
            if (r0 == r4) goto L2a
            goto L17
        L2a:
            r0 = 1
            java.lang.Object r4 = m61()     // Catch: java.lang.Throwable -> Laa
            r5 = 1864(0x748, float:2.612E-42)
        L31:
            r5 = r5 ^ 1881(0x759, float:2.636E-42)
            if (r5 == r2) goto La0
            r6 = 47384(0xb918, float:6.6399E-41)
            if (r5 == r6) goto Laa
            r6 = 47417(0xb939, float:6.6445E-41)
            if (r5 == r6) goto L45
            r6 = 47483(0xb97b, float:6.6538E-41)
            if (r5 == r6) goto La6
            goto L31
        L45:
            long r5 = m63(r4, r11)     // Catch: java.lang.Throwable -> Laa
            r2 = 48767(0xbe7f, float:6.8337E-41)
        L4c:
            r7 = 48784(0xbe90, float:6.8361E-41)
            r2 = r2 ^ r7
            if (r2 == r1) goto L9c
            r7 = 45
            if (r2 == r7) goto Laa
            r7 = 76
            if (r2 == r7) goto L67
            if (r2 == r3) goto L5d
            goto L4c
        L5d:
            r7 = 0
            int r2 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r2 < 0) goto L9c
            r2 = 48860(0xbedc, float:6.8467E-41)
            goto L4c
        L67:
            java.lang.Class r1 = org.lsposed.hiddenapibypass.library.C0003.m271(r4)     // Catch: java.lang.Throwable -> Laa
            short[] r2 = m19()     // Catch: java.lang.Throwable -> Laa
            int r3 = com.yan.hook.C0001.f5     // Catch: java.lang.Throwable -> Laa
            r3 = r3 ^ (-588(0xfffffffffffffdb4, float:NaN))
            r7 = 1637(0x665, float:2.294E-42)
            r8 = 418(0x1a2, float:5.86E-43)
            java.lang.String r2 = com.yan.hook.C0002.m191(r2, r8, r3, r7)     // Catch: java.lang.Throwable -> Laa
            r3 = 2
            java.lang.Class[] r7 = new java.lang.Class[r3]     // Catch: java.lang.Throwable -> Laa
            java.lang.Class<java.lang.Object> r8 = java.lang.Object.class
            r9 = 0
            r7[r9] = r8     // Catch: java.lang.Throwable -> Laa
            java.lang.Class r8 = org.lsposed.hiddenapibypass.library.C0004.m343()     // Catch: java.lang.Throwable -> Laa
            r7[r0] = r8     // Catch: java.lang.Throwable -> Laa
            java.lang.reflect.Method r1 = org.lsposed.hiddenapibypass.library.C0003.m295(r1, r2, r7)     // Catch: java.lang.Throwable -> Laa
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> Laa
            r2[r9] = r10     // Catch: java.lang.Throwable -> Laa
            java.lang.Long r3 = com.yan.hook.C0001.m166(r5)     // Catch: java.lang.Throwable -> Laa
            r2[r0] = r3     // Catch: java.lang.Throwable -> Laa
            java.lang.Object r10 = org.lsposed.hiddenapibypass.library.C0003.m293(r1, r4, r2)     // Catch: java.lang.Throwable -> Laa
            return r10
        L9c:
            r2 = 48829(0xbebd, float:6.8424E-41)
            goto L4c
        La0:
            if (r4 == 0) goto La6
            r5 = 48736(0xbe60, float:6.8294E-41)
            goto L31
        La6:
            r5 = 48705(0xbe41, float:6.825E-41)
            goto L31
        Laa:
            com.yan.hook.C0001.m152(r11, r0)     // Catch: java.lang.Throwable -> Lb2
            java.lang.Object r10 = org.lsposed.hiddenapibypass.library.C0003.m283(r11, r10)     // Catch: java.lang.Throwable -> Lb2
            return r10
        Lb2:
            r10 = 0
            return r10
        Lb4:
            if (r11 == 0) goto Lba
            r0 = 1833(0x729, float:2.569E-42)
            goto L17
        Lba:
            r0 = 1802(0x70a, float:2.525E-42)
            goto L17
        Lbe:
            if (r10 == 0) goto Lc4
            r0 = 1709(0x6ad, float:2.395E-42)
            goto L2
        Lc4:
            r0 = 1678(0x68e, float:2.351E-42)
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.uGet(java.lang.Object, java.lang.reflect.Field):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0017, code lost:
    
        r1 = m61();
        r3 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x001d, code lost:
    
        r3 = r3 ^ 1757;
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
    
        return org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), org.lsposed.hiddenapibypass.library.C0004.m323(m19(), 427, com.yan.hook.C0000.f4 ^ 94, 391), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343()}), r1, new java.lang.Object[]{org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), m1(new int[]{115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 66, 97, 115, 101}), new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12}), com.yan.hook.C0001.m166(com.yan.hook.C0002.m224((java.lang.Number) org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), m1(new int[]{115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 79, 102, 102, 115, 101, 116}), new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12})))});
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static java.lang.Object uGetStatic(java.lang.reflect.Field r12) {
        /*
            r0 = 1616(0x650, float:2.264E-42)
        L2:
            r0 = r0 ^ 1633(0x661, float:2.288E-42)
            r1 = 14
            if (r0 == r1) goto Lc4
            r1 = 49
            if (r0 == r1) goto Lbe
            r1 = 204(0xcc, float:2.86E-43)
            r2 = 0
            if (r0 == r1) goto Lbd
            r1 = 239(0xef, float:3.35E-43)
            if (r0 == r1) goto L16
            goto L2
        L16:
            r0 = 1
            java.lang.Object r1 = m61()     // Catch: java.lang.Throwable -> Lb5
            r3 = 1740(0x6cc, float:2.438E-42)
        L1d:
            r3 = r3 ^ 1757(0x6dd, float:2.462E-42)
            r4 = 17
            if (r3 == r4) goto Lab
            r5 = 54
            if (r3 == r5) goto Lb1
            r5 = 471(0x1d7, float:6.6E-43)
            if (r3 == r5) goto Lb5
            r5 = 500(0x1f4, float:7.0E-43)
            if (r3 == r5) goto L30
            goto L1d
        L30:
            java.lang.Class r3 = org.lsposed.hiddenapibypass.library.C0003.m271(r1)     // Catch: java.lang.Throwable -> Lb5
            r5 = 15
            int[] r5 = new int[r5]     // Catch: java.lang.Throwable -> Lb5
            r5 = {x00c8: FILL_ARRAY_DATA , data: [115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 66, 97, 115, 101} // fill-array     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r5 = m1(r5)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class<java.lang.reflect.Field> r7 = java.lang.reflect.Field.class
            r8 = 0
            r6[r8] = r7     // Catch: java.lang.Throwable -> Lb5
            java.lang.reflect.Method r3 = org.lsposed.hiddenapibypass.library.C0003.m295(r3, r5, r6)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> Lb5
            r5[r8] = r12     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object r3 = org.lsposed.hiddenapibypass.library.C0003.m293(r3, r1, r5)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class r5 = org.lsposed.hiddenapibypass.library.C0003.m271(r1)     // Catch: java.lang.Throwable -> Lb5
            int[] r4 = new int[r4]     // Catch: java.lang.Throwable -> Lb5
            r4 = {x00ea: FILL_ARRAY_DATA , data: [115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 79, 102, 102, 115, 101, 116} // fill-array     // Catch: java.lang.Throwable -> Lb5
            java.lang.String r4 = m1(r4)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class<java.lang.reflect.Field> r7 = java.lang.reflect.Field.class
            r6[r8] = r7     // Catch: java.lang.Throwable -> Lb5
            java.lang.reflect.Method r4 = org.lsposed.hiddenapibypass.library.C0003.m295(r5, r4, r6)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> Lb5
            r5[r8] = r12     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object r4 = org.lsposed.hiddenapibypass.library.C0003.m293(r4, r1, r5)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Number r4 = (java.lang.Number) r4     // Catch: java.lang.Throwable -> Lb5
            long r4 = com.yan.hook.C0002.m224(r4)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class r6 = org.lsposed.hiddenapibypass.library.C0003.m271(r1)     // Catch: java.lang.Throwable -> Lb5
            short[] r7 = m19()     // Catch: java.lang.Throwable -> Lb5
            int r9 = com.yan.hook.C0000.f4     // Catch: java.lang.Throwable -> Lb5
            r9 = r9 ^ 94
            r10 = 391(0x187, float:5.48E-43)
            r11 = 427(0x1ab, float:5.98E-43)
            java.lang.String r7 = org.lsposed.hiddenapibypass.library.C0004.m323(r7, r11, r9, r10)     // Catch: java.lang.Throwable -> Lb5
            r9 = 2
            java.lang.Class[] r10 = new java.lang.Class[r9]     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class<java.lang.Object> r11 = java.lang.Object.class
            r10[r8] = r11     // Catch: java.lang.Throwable -> Lb5
            java.lang.Class r11 = org.lsposed.hiddenapibypass.library.C0004.m343()     // Catch: java.lang.Throwable -> Lb5
            r10[r0] = r11     // Catch: java.lang.Throwable -> Lb5
            java.lang.reflect.Method r6 = org.lsposed.hiddenapibypass.library.C0003.m295(r6, r7, r10)     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object[] r7 = new java.lang.Object[r9]     // Catch: java.lang.Throwable -> Lb5
            r7[r8] = r3     // Catch: java.lang.Throwable -> Lb5
            java.lang.Long r3 = com.yan.hook.C0001.m166(r4)     // Catch: java.lang.Throwable -> Lb5
            r7[r0] = r3     // Catch: java.lang.Throwable -> Lb5
            java.lang.Object r12 = org.lsposed.hiddenapibypass.library.C0003.m293(r6, r1, r7)     // Catch: java.lang.Throwable -> Lb5
            return r12
        Lab:
            if (r1 == 0) goto Lb1
            r3 = 1833(0x729, float:2.569E-42)
            goto L1d
        Lb1:
            r3 = 1802(0x70a, float:2.525E-42)
            goto L1d
        Lb5:
            com.yan.hook.C0001.m152(r12, r0)     // Catch: java.lang.Throwable -> Lbd
            java.lang.Object r12 = org.lsposed.hiddenapibypass.library.C0003.m283(r12, r2)     // Catch: java.lang.Throwable -> Lbd
            return r12
        Lbd:
            return r2
        Lbe:
            if (r12 != 0) goto Lc4
            r0 = 1709(0x6ad, float:2.395E-42)
            goto L2
        Lc4:
            r0 = 1678(0x68e, float:2.351E-42)
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.uGetStatic(java.lang.reflect.Field):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:48:0x002c, code lost:
    
        r5 = m61();
        r6 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0032, code lost:
    
        r6 = r6 ^ 1881;
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
    
        r2 = r2 ^ 48784;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean uSet(java.lang.Object r11, java.lang.reflect.Field r12, java.lang.Object r13) {
        /*
            r0 = 1616(0x650, float:2.264E-42)
        L2:
            r0 = r0 ^ 1633(0x661, float:2.288E-42)
            r1 = 14
            if (r0 == r1) goto Lc8
            r2 = 49
            if (r0 == r2) goto Lc2
            r2 = 204(0xcc, float:2.86E-43)
            r3 = 239(0xef, float:3.35E-43)
            r4 = 0
            if (r0 == r2) goto L16
            if (r0 == r3) goto Lb7
            goto L2
        L16:
            r0 = 1740(0x6cc, float:2.438E-42)
        L18:
            r0 = r0 ^ 1757(0x6dd, float:2.462E-42)
            r2 = 17
            if (r0 == r2) goto Lb8
            r5 = 54
            if (r0 == r5) goto Lbe
            r5 = 471(0x1d7, float:6.6E-43)
            if (r0 == r5) goto Lb7
            r5 = 500(0x1f4, float:7.0E-43)
            if (r0 == r5) goto L2b
            goto L18
        L2b:
            r0 = 1
            java.lang.Object r5 = m61()     // Catch: java.lang.Throwable -> Lb0
            r6 = 1864(0x748, float:2.612E-42)
        L32:
            r6 = r6 ^ 1881(0x759, float:2.636E-42)
            if (r6 == r2) goto La6
            r7 = 47384(0xb918, float:6.6399E-41)
            if (r6 == r7) goto Lb0
            r7 = 47417(0xb939, float:6.6445E-41)
            if (r6 == r7) goto L46
            r7 = 47483(0xb97b, float:6.6538E-41)
            if (r6 == r7) goto Lac
            goto L32
        L46:
            long r6 = m63(r5, r12)     // Catch: java.lang.Throwable -> Lb0
            r2 = 48767(0xbe7f, float:6.8337E-41)
        L4d:
            r8 = 48784(0xbe90, float:6.8361E-41)
            r2 = r2 ^ r8
            if (r2 == r1) goto La2
            r8 = 45
            if (r2 == r8) goto Lb0
            r8 = 76
            if (r2 == r8) goto L68
            if (r2 == r3) goto L5e
            goto L4d
        L5e:
            r8 = 0
            int r2 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r2 < 0) goto La2
            r2 = 48860(0xbedc, float:6.8467E-41)
            goto L4d
        L68:
            java.lang.Class r1 = org.lsposed.hiddenapibypass.library.C0003.m271(r5)     // Catch: java.lang.Throwable -> Lb0
            short[] r2 = m19()     // Catch: java.lang.Throwable -> Lb0
            int r3 = com.yan.hook.C0001.f5     // Catch: java.lang.Throwable -> Lb0
            r3 = r3 ^ (-588(0xfffffffffffffdb4, float:NaN))
            r8 = 1798(0x706, float:2.52E-42)
            r9 = 436(0x1b4, float:6.11E-43)
            java.lang.String r2 = org.lsposed.hiddenapibypass.library.C0004.m323(r2, r9, r3, r8)     // Catch: java.lang.Throwable -> Lb0
            r3 = 3
            java.lang.Class[] r8 = new java.lang.Class[r3]     // Catch: java.lang.Throwable -> Lb0
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            r8[r4] = r9     // Catch: java.lang.Throwable -> Lb0
            java.lang.Class r9 = org.lsposed.hiddenapibypass.library.C0004.m343()     // Catch: java.lang.Throwable -> Lb0
            r8[r0] = r9     // Catch: java.lang.Throwable -> Lb0
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            r10 = 2
            r8[r10] = r9     // Catch: java.lang.Throwable -> Lb0
            java.lang.reflect.Method r1 = org.lsposed.hiddenapibypass.library.C0003.m295(r1, r2, r8)     // Catch: java.lang.Throwable -> Lb0
            java.lang.Object[] r2 = new java.lang.Object[r3]     // Catch: java.lang.Throwable -> Lb0
            r2[r4] = r11     // Catch: java.lang.Throwable -> Lb0
            java.lang.Long r3 = com.yan.hook.C0001.m166(r6)     // Catch: java.lang.Throwable -> Lb0
            r2[r0] = r3     // Catch: java.lang.Throwable -> Lb0
            r2[r10] = r13     // Catch: java.lang.Throwable -> Lb0
            org.lsposed.hiddenapibypass.library.C0003.m293(r1, r5, r2)     // Catch: java.lang.Throwable -> Lb0
            return r0
        La2:
            r2 = 48829(0xbebd, float:6.8424E-41)
            goto L4d
        La6:
            if (r5 == 0) goto Lac
            r6 = 48736(0xbe60, float:6.8294E-41)
            goto L32
        Lac:
            r6 = 48705(0xbe41, float:6.825E-41)
            goto L32
        Lb0:
            com.yan.hook.C0001.m152(r12, r0)     // Catch: java.lang.Throwable -> Lb7
            com.yan.hook.C0002.m193(r12, r11, r13)     // Catch: java.lang.Throwable -> Lb7
            return r0
        Lb7:
            return r4
        Lb8:
            if (r12 == 0) goto Lbe
            r0 = 1833(0x729, float:2.569E-42)
            goto L18
        Lbe:
            r0 = 1802(0x70a, float:2.525E-42)
            goto L18
        Lc2:
            if (r11 == 0) goto Lc8
            r0 = 1709(0x6ad, float:2.395E-42)
            goto L2
        Lc8:
            r0 = 1678(0x68e, float:2.351E-42)
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.uSet(java.lang.Object, java.lang.reflect.Field, java.lang.Object):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x002c, code lost:
    
        r3 = m61();
        r4 = m63(r3, r12);
        r6 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0036, code lost:
    
        r6 = r6 ^ 1881;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean uSetBoolean(java.lang.Object r11, java.lang.reflect.Field r12, boolean r13) {
        /*
            r0 = 1616(0x650, float:2.264E-42)
        L2:
            r0 = r0 ^ 1633(0x661, float:2.288E-42)
            r1 = 14
            if (r0 == r1) goto Lb0
            r1 = 49
            if (r0 == r1) goto Laa
            r1 = 204(0xcc, float:2.86E-43)
            r2 = 0
            if (r0 == r1) goto L16
            r1 = 239(0xef, float:3.35E-43)
            if (r0 == r1) goto L9f
            goto L2
        L16:
            r0 = 1740(0x6cc, float:2.438E-42)
        L18:
            r0 = r0 ^ 1757(0x6dd, float:2.462E-42)
            r1 = 17
            if (r0 == r1) goto La0
            r3 = 54
            if (r0 == r3) goto La6
            r3 = 471(0x1d7, float:6.6E-43)
            if (r0 == r3) goto L9f
            r3 = 500(0x1f4, float:7.0E-43)
            if (r0 == r3) goto L2b
            goto L18
        L2b:
            r0 = 1
            java.lang.Object r3 = m61()     // Catch: java.lang.Throwable -> L98
            long r4 = m63(r3, r12)     // Catch: java.lang.Throwable -> L98
            r6 = 1864(0x748, float:2.612E-42)
        L36:
            r6 = r6 ^ 1881(0x759, float:2.636E-42)
            if (r6 == r1) goto L8a
            r7 = 47384(0xb918, float:6.6399E-41)
            if (r6 == r7) goto L98
            r7 = 47417(0xb939, float:6.6445E-41)
            if (r6 == r7) goto L4a
            r7 = 47483(0xb97b, float:6.6538E-41)
            if (r6 == r7) goto L94
            goto L36
        L4a:
            java.lang.Class r1 = org.lsposed.hiddenapibypass.library.C0003.m271(r3)     // Catch: java.lang.Throwable -> L98
            short[] r6 = m19()     // Catch: java.lang.Throwable -> L98
            int r7 = com.yan.hook.C0001.f5     // Catch: java.lang.Throwable -> L98
            r7 = r7 ^ (-585(0xfffffffffffffdb7, float:NaN))
            r8 = 909(0x38d, float:1.274E-42)
            r9 = 445(0x1bd, float:6.24E-43)
            java.lang.String r6 = com.yan.hook.C0002.m191(r6, r9, r7, r8)     // Catch: java.lang.Throwable -> L98
            r7 = 3
            java.lang.Class[] r8 = new java.lang.Class[r7]     // Catch: java.lang.Throwable -> L98
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            r8[r2] = r9     // Catch: java.lang.Throwable -> L98
            java.lang.Class r9 = org.lsposed.hiddenapibypass.library.C0004.m343()     // Catch: java.lang.Throwable -> L98
            r8[r0] = r9     // Catch: java.lang.Throwable -> L98
            java.lang.Class r9 = org.lsposed.hiddenapibypass.library.C0003.m296()     // Catch: java.lang.Throwable -> L98
            r10 = 2
            r8[r10] = r9     // Catch: java.lang.Throwable -> L98
            java.lang.reflect.Method r1 = org.lsposed.hiddenapibypass.library.C0003.m295(r1, r6, r8)     // Catch: java.lang.Throwable -> L98
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L98
            r6[r2] = r11     // Catch: java.lang.Throwable -> L98
            java.lang.Long r4 = com.yan.hook.C0001.m166(r4)     // Catch: java.lang.Throwable -> L98
            r6[r0] = r4     // Catch: java.lang.Throwable -> L98
            java.lang.Boolean r4 = com.yan.hook.C0001.m154(r13)     // Catch: java.lang.Throwable -> L98
            r6[r10] = r4     // Catch: java.lang.Throwable -> L98
            org.lsposed.hiddenapibypass.library.C0003.m293(r1, r3, r6)     // Catch: java.lang.Throwable -> L98
            return r0
        L8a:
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 < 0) goto L94
            r6 = 48736(0xbe60, float:6.8294E-41)
            goto L36
        L94:
            r6 = 48705(0xbe41, float:6.825E-41)
            goto L36
        L98:
            com.yan.hook.C0001.m152(r12, r0)     // Catch: java.lang.Throwable -> L9f
            com.yan.hook.C0002.m194(r12, r11, r13)     // Catch: java.lang.Throwable -> L9f
            return r0
        L9f:
            return r2
        La0:
            if (r12 == 0) goto La6
            r0 = 1833(0x729, float:2.569E-42)
            goto L18
        La6:
            r0 = 1802(0x70a, float:2.525E-42)
            goto L18
        Laa:
            if (r11 == 0) goto Lb0
            r0 = 1709(0x6ad, float:2.395E-42)
            goto L2
        Lb0:
            r0 = 1678(0x68e, float:2.351E-42)
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.uSetBoolean(java.lang.Object, java.lang.reflect.Field, boolean):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x002c, code lost:
    
        r3 = m61();
        r4 = m63(r3, r12);
        r6 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0036, code lost:
    
        r6 = r6 ^ 1881;
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
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean uSetInt(java.lang.Object r11, java.lang.reflect.Field r12, int r13) {
        /*
            r0 = 1616(0x650, float:2.264E-42)
        L2:
            r0 = r0 ^ 1633(0x661, float:2.288E-42)
            r1 = 14
            if (r0 == r1) goto Lb0
            r1 = 49
            if (r0 == r1) goto Laa
            r1 = 204(0xcc, float:2.86E-43)
            r2 = 0
            if (r0 == r1) goto L16
            r1 = 239(0xef, float:3.35E-43)
            if (r0 == r1) goto L9f
            goto L2
        L16:
            r0 = 1740(0x6cc, float:2.438E-42)
        L18:
            r0 = r0 ^ 1757(0x6dd, float:2.462E-42)
            r1 = 17
            if (r0 == r1) goto La0
            r3 = 54
            if (r0 == r3) goto La6
            r3 = 471(0x1d7, float:6.6E-43)
            if (r0 == r3) goto L9f
            r3 = 500(0x1f4, float:7.0E-43)
            if (r0 == r3) goto L2b
            goto L18
        L2b:
            r0 = 1
            java.lang.Object r3 = m61()     // Catch: java.lang.Throwable -> L98
            long r4 = m63(r3, r12)     // Catch: java.lang.Throwable -> L98
            r6 = 1864(0x748, float:2.612E-42)
        L36:
            r6 = r6 ^ 1881(0x759, float:2.636E-42)
            if (r6 == r1) goto L8a
            r7 = 47384(0xb918, float:6.6399E-41)
            if (r6 == r7) goto L98
            r7 = 47417(0xb939, float:6.6445E-41)
            if (r6 == r7) goto L4a
            r7 = 47483(0xb97b, float:6.6538E-41)
            if (r6 == r7) goto L94
            goto L36
        L4a:
            java.lang.Class r1 = org.lsposed.hiddenapibypass.library.C0003.m271(r3)     // Catch: java.lang.Throwable -> L98
            short[] r6 = m19()     // Catch: java.lang.Throwable -> L98
            int r7 = com.yan.hook.C0001.f5     // Catch: java.lang.Throwable -> L98
            r7 = r7 ^ (-581(0xfffffffffffffdbb, float:NaN))
            r8 = 824(0x338, float:1.155E-42)
            r9 = 455(0x1c7, float:6.38E-43)
            java.lang.String r6 = org.lsposed.hiddenapibypass.library.C0003.m285(r6, r9, r7, r8)     // Catch: java.lang.Throwable -> L98
            r7 = 3
            java.lang.Class[] r8 = new java.lang.Class[r7]     // Catch: java.lang.Throwable -> L98
            java.lang.Class<java.lang.Object> r9 = java.lang.Object.class
            r8[r2] = r9     // Catch: java.lang.Throwable -> L98
            java.lang.Class r9 = org.lsposed.hiddenapibypass.library.C0004.m343()     // Catch: java.lang.Throwable -> L98
            r8[r0] = r9     // Catch: java.lang.Throwable -> L98
            java.lang.Class r9 = com.yan.hook.C0002.m187()     // Catch: java.lang.Throwable -> L98
            r10 = 2
            r8[r10] = r9     // Catch: java.lang.Throwable -> L98
            java.lang.reflect.Method r1 = org.lsposed.hiddenapibypass.library.C0003.m295(r1, r6, r8)     // Catch: java.lang.Throwable -> L98
            java.lang.Object[] r6 = new java.lang.Object[r7]     // Catch: java.lang.Throwable -> L98
            r6[r2] = r11     // Catch: java.lang.Throwable -> L98
            java.lang.Long r4 = com.yan.hook.C0001.m166(r4)     // Catch: java.lang.Throwable -> L98
            r6[r0] = r4     // Catch: java.lang.Throwable -> L98
            java.lang.Integer r4 = org.lsposed.hiddenapibypass.library.C0003.m292(r13)     // Catch: java.lang.Throwable -> L98
            r6[r10] = r4     // Catch: java.lang.Throwable -> L98
            org.lsposed.hiddenapibypass.library.C0003.m293(r1, r3, r6)     // Catch: java.lang.Throwable -> L98
            return r0
        L8a:
            r6 = 0
            int r8 = (r4 > r6 ? 1 : (r4 == r6 ? 0 : -1))
            if (r8 < 0) goto L94
            r6 = 48736(0xbe60, float:6.8294E-41)
            goto L36
        L94:
            r6 = 48705(0xbe41, float:6.825E-41)
            goto L36
        L98:
            com.yan.hook.C0001.m152(r12, r0)     // Catch: java.lang.Throwable -> L9f
            com.yan.hook.C0001.m146(r12, r11, r13)     // Catch: java.lang.Throwable -> L9f
            return r0
        L9f:
            return r2
        La0:
            if (r12 == 0) goto La6
            r0 = 1833(0x729, float:2.569E-42)
            goto L18
        La6:
            r0 = 1802(0x70a, float:2.525E-42)
            goto L18
        Laa:
            if (r11 == 0) goto Lb0
            r0 = 1709(0x6ad, float:2.395E-42)
            goto L2
        Lb0:
            r0 = 1678(0x68e, float:2.351E-42)
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.uSetInt(java.lang.Object, java.lang.reflect.Field, int):boolean");
    }

    /* JADX WARN: Code restructure failed: missing block: B:23:0x0017, code lost:
    
        r1 = m61();
        r3 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x001d, code lost:
    
        r3 = r3 ^ 1757;
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
    
        org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), com.yan.hook.C0002.m191(m19(), 461, org.lsposed.hiddenapibypass.library.C0004.f13 ^ (-356), 346), new java.lang.Class[]{java.lang.Object.class, org.lsposed.hiddenapibypass.library.C0004.m343(), java.lang.Object.class}), r1, new java.lang.Object[]{org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), m1(new int[]{115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 66, 97, 115, 101}), new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12}), com.yan.hook.C0001.m166(com.yan.hook.C0002.m224((java.lang.Number) org.lsposed.hiddenapibypass.library.C0003.m293(org.lsposed.hiddenapibypass.library.C0003.m295(org.lsposed.hiddenapibypass.library.C0003.m271(r1), m1(new int[]{115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 79, 102, 102, 115, 101, 116}), new java.lang.Class[]{java.lang.reflect.Field.class}), r1, new java.lang.Object[]{r12}))), r13});
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00af, code lost:
    
        return true;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    private static boolean uSetStatic(java.lang.reflect.Field r12, java.lang.Object r13) {
        /*
            r0 = 1616(0x650, float:2.264E-42)
        L2:
            r0 = r0 ^ 1633(0x661, float:2.288E-42)
            r1 = 14
            if (r0 == r1) goto Lc9
            r1 = 49
            if (r0 == r1) goto Lc3
            r1 = 204(0xcc, float:2.86E-43)
            r2 = 0
            if (r0 == r1) goto Lc2
            r1 = 239(0xef, float:3.35E-43)
            if (r0 == r1) goto L16
            goto L2
        L16:
            r0 = 1
            java.lang.Object r1 = m61()     // Catch: java.lang.Throwable -> Lba
            r3 = 1740(0x6cc, float:2.438E-42)
        L1d:
            r3 = r3 ^ 1757(0x6dd, float:2.462E-42)
            r4 = 17
            if (r3 == r4) goto Lb0
            r5 = 54
            if (r3 == r5) goto Lb6
            r5 = 471(0x1d7, float:6.6E-43)
            if (r3 == r5) goto Lba
            r5 = 500(0x1f4, float:7.0E-43)
            if (r3 == r5) goto L30
            goto L1d
        L30:
            java.lang.Class r3 = org.lsposed.hiddenapibypass.library.C0003.m271(r1)     // Catch: java.lang.Throwable -> Lba
            r5 = 15
            int[] r5 = new int[r5]     // Catch: java.lang.Throwable -> Lba
            r5 = {x00ce: FILL_ARRAY_DATA , data: [115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 66, 97, 115, 101} // fill-array     // Catch: java.lang.Throwable -> Lba
            java.lang.String r5 = m1(r5)     // Catch: java.lang.Throwable -> Lba
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch: java.lang.Throwable -> Lba
            java.lang.Class<java.lang.reflect.Field> r7 = java.lang.reflect.Field.class
            r6[r2] = r7     // Catch: java.lang.Throwable -> Lba
            java.lang.reflect.Method r3 = org.lsposed.hiddenapibypass.library.C0003.m295(r3, r5, r6)     // Catch: java.lang.Throwable -> Lba
            java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> Lba
            r5[r2] = r12     // Catch: java.lang.Throwable -> Lba
            java.lang.Object r3 = org.lsposed.hiddenapibypass.library.C0003.m293(r3, r1, r5)     // Catch: java.lang.Throwable -> Lba
            java.lang.Class r5 = org.lsposed.hiddenapibypass.library.C0003.m271(r1)     // Catch: java.lang.Throwable -> Lba
            int[] r4 = new int[r4]     // Catch: java.lang.Throwable -> Lba
            r4 = {x00f0: FILL_ARRAY_DATA , data: [115, 116, 97, 116, 105, 99, 70, 105, 101, 108, 100, 79, 102, 102, 115, 101, 116} // fill-array     // Catch: java.lang.Throwable -> Lba
            java.lang.String r4 = m1(r4)     // Catch: java.lang.Throwable -> Lba
            java.lang.Class[] r6 = new java.lang.Class[r0]     // Catch: java.lang.Throwable -> Lba
            java.lang.Class<java.lang.reflect.Field> r7 = java.lang.reflect.Field.class
            r6[r2] = r7     // Catch: java.lang.Throwable -> Lba
            java.lang.reflect.Method r4 = org.lsposed.hiddenapibypass.library.C0003.m295(r5, r4, r6)     // Catch: java.lang.Throwable -> Lba
            java.lang.Object[] r5 = new java.lang.Object[r0]     // Catch: java.lang.Throwable -> Lba
            r5[r2] = r12     // Catch: java.lang.Throwable -> Lba
            java.lang.Object r4 = org.lsposed.hiddenapibypass.library.C0003.m293(r4, r1, r5)     // Catch: java.lang.Throwable -> Lba
            java.lang.Number r4 = (java.lang.Number) r4     // Catch: java.lang.Throwable -> Lba
            long r4 = com.yan.hook.C0002.m224(r4)     // Catch: java.lang.Throwable -> Lba
            java.lang.Class r6 = org.lsposed.hiddenapibypass.library.C0003.m271(r1)     // Catch: java.lang.Throwable -> Lba
            short[] r7 = m19()     // Catch: java.lang.Throwable -> Lba
            int r8 = org.lsposed.hiddenapibypass.library.C0004.f13     // Catch: java.lang.Throwable -> Lba
            r8 = r8 ^ (-356(0xfffffffffffffe9c, float:NaN))
            r9 = 346(0x15a, float:4.85E-43)
            r10 = 461(0x1cd, float:6.46E-43)
            java.lang.String r7 = com.yan.hook.C0002.m191(r7, r10, r8, r9)     // Catch: java.lang.Throwable -> Lba
            r8 = 3
            java.lang.Class[] r9 = new java.lang.Class[r8]     // Catch: java.lang.Throwable -> Lba
            java.lang.Class<java.lang.Object> r10 = java.lang.Object.class
            r9[r2] = r10     // Catch: java.lang.Throwable -> Lba
            java.lang.Class r10 = org.lsposed.hiddenapibypass.library.C0004.m343()     // Catch: java.lang.Throwable -> Lba
            r9[r0] = r10     // Catch: java.lang.Throwable -> Lba
            java.lang.Class<java.lang.Object> r10 = java.lang.Object.class
            r11 = 2
            r9[r11] = r10     // Catch: java.lang.Throwable -> Lba
            java.lang.reflect.Method r6 = org.lsposed.hiddenapibypass.library.C0003.m295(r6, r7, r9)     // Catch: java.lang.Throwable -> Lba
            java.lang.Object[] r7 = new java.lang.Object[r8]     // Catch: java.lang.Throwable -> Lba
            r7[r2] = r3     // Catch: java.lang.Throwable -> Lba
            java.lang.Long r3 = com.yan.hook.C0001.m166(r4)     // Catch: java.lang.Throwable -> Lba
            r7[r0] = r3     // Catch: java.lang.Throwable -> Lba
            r7[r11] = r13     // Catch: java.lang.Throwable -> Lba
            org.lsposed.hiddenapibypass.library.C0003.m293(r6, r1, r7)     // Catch: java.lang.Throwable -> Lba
            return r0
        Lb0:
            if (r1 == 0) goto Lb6
            r3 = 1833(0x729, float:2.569E-42)
            goto L1d
        Lb6:
            r3 = 1802(0x70a, float:2.525E-42)
            goto L1d
        Lba:
            com.yan.hook.C0001.m152(r12, r0)     // Catch: java.lang.Throwable -> Lc2
            r1 = 0
            com.yan.hook.C0002.m193(r12, r1, r13)     // Catch: java.lang.Throwable -> Lc2
            return r0
        Lc2:
            return r2
        Lc3:
            if (r12 != 0) goto Lc9
            r0 = 1709(0x6ad, float:2.395E-42)
            goto L2
        Lc9:
            r0 = 1678(0x68e, float:2.351E-42)
            goto L2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.uSetStatic(java.lang.reflect.Field, java.lang.Object):boolean");
    }

    private static Object unsafe() {
        try {
            Field m326 = C0004.m326(C0004.m342(m16()), C0001.m169(m19(), 470, C0000.f4 ^ 94, 2265));
            C0001.m152(m326, true);
            return C0003.m283(m326, null);
        } catch (Throwable unused) {
            return null;
        }
    }

    private static void writeParceledListSliceReply(Parcel parcel, Object obj, List<?> list) {
        try {
            try {
                Method m295 = C0003.m295(C0003.m271(obj), m1(new int[]{119, 114, 105, 116, 101, 84, 111, 80, 97, 114, 99, 101, 108}), new Class[]{Parcel.class, C0002.m187()});
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
                    int i = 1616;
                    while (true) {
                        i ^= 1633;
                        if (i == 14) {
                            return;
                        }
                        if (i == 49) {
                            i = 1647;
                        }
                    }
                } catch (Throwable th) {
                    C0003.m290(m299);
                    throw th;
                }
            } catch (Throwable unused) {
                C0004.m325(parcel, 0);
                int i2 = 1740;
                while (true) {
                    i2 ^= 1757;
                    if (i2 == 17) {
                        i2 = 1771;
                    } else if (i2 == 54) {
                        return;
                    }
                }
            }
        } catch (Throwable unused2) {
        }
    }

    /* renamed from: ۟۟۟۠ۧ, reason: not valid java name and contains not printable characters */
    public static Field m0() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return CACHE_SI_DETAILS;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۟۟ۤۤ, reason: not valid java name and contains not printable characters */
    public static String m1(Object obj) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return S((int[]) obj);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۟۠ۨۢ, reason: not valid java name and contains not printable characters */
    public static String m2() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_HAB;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۟ۡۥۤ, reason: not valid java name and contains not printable characters */
    public static void m3(Object obj, Object obj2) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).rewritePackageInfoReply((Parcel) obj2);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m143 = C0001.m143();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m143 <= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Integer.valueOf(C0000.m137("He")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۟۟ۤ۠ۡ, reason: not valid java name and contains not printable characters */
    public static String m4() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_PM_PROXY;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۟ۨۢۧ, reason: not valid java name and contains not printable characters */
    public static String m5() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_PKG_MANAGER;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠۟, reason: not valid java name and contains not printable characters */
    public static File m6(Object obj, Object obj2, Object obj3, Object obj4) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).extractAssetFile((Context) obj2, (String) obj3, (File) obj4);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠۟ۥ۟, reason: not valid java name and contains not printable characters */
    public static HashMap m7() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return OFFSET_CACHE;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠۠ۡۤ, reason: not valid java name and contains not printable characters */
    public static void m8(Object obj, Object obj2, Object obj3) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        writeParceledListSliceReply((Parcel) obj, obj2, (List) obj3);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠ۢ۟۟, reason: not valid java name and contains not printable characters */
    public static void m9(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        log((String) obj, (Throwable) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠ۢ۠ۧ, reason: not valid java name and contains not printable characters */
    public static Object m10(Object obj) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((Signature[]) obj).clone();
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠ۢۤۡ, reason: not valid java name and contains not printable characters */
    public static IBinder m11() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return g_smFake;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠ۢۥۡ, reason: not valid java name and contains not printable characters */
    public static int m12() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_getApplicationInfoVersioned;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠ۧۥۥ, reason: not valid java name and contains not printable characters */
    public static Method m13() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return CACHE_PLS_GETLIST;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۡ۠ۤۨ, reason: not valid java name and contains not printable characters */
    public static boolean m14(Object obj, Object obj2) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return uSetStatic((Field) obj, obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return false;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۡۤۨ, reason: not valid java name and contains not printable characters */
    public static boolean m15(Object obj, Object obj2, Object obj3) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return uSet(obj, (Field) obj2, obj3);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return false;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۡۥۤ, reason: not valid java name and contains not printable characters */
    public static String m16() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_UNSAFE;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۡۦۢ, reason: not valid java name and contains not printable characters */
    public static void m17() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        hideHookDex();
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۡۥ۠ۦ, reason: not valid java name and contains not printable characters */
    public static boolean m18() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return nativeLoaded;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return false;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۡۥۡۧ, reason: not valid java name and contains not printable characters */
    public static short[] m19() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return f0short;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۡۥۧۦ, reason: not valid java name and contains not printable characters */
    public static String m20() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_SM_PROXY;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۡۧۢ۟, reason: not valid java name and contains not printable characters */
    public static void m21(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).rewriteApplicationInfoListReply((Parcel) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۡۨۡ, reason: not valid java name and contains not printable characters */
    public static int m22(Object obj, Object obj2, int i) {
        int m316 = C0004.m316();
        int i2 = 1616;
        while (true) {
            i2 ^= 1633;
            if (i2 != 14) {
                if (i2 != 49) {
                    if (i2 == 204) {
                        return replaceBinderFields(obj, (IBinder) obj2, i);
                    }
                    if (i2 == 239) {
                        int i3 = 1740;
                        while (true) {
                            i3 ^= 1757;
                            if (i3 == 17) {
                                i3 = 1771;
                            } else if (i3 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i2 = 1709;
                }
            }
            i2 = 1678;
        }
    }

    /* renamed from: ۣ۟ۢۤ۠, reason: not valid java name and contains not printable characters */
    public static String m23() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_DEX_FILE;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۢۦۧ۟, reason: not valid java name and contains not printable characters */
    public static void m24(Object obj, Object obj2) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        nativeInstall((String) obj, (String) obj2);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m316 = C0004.m316();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m316 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Double.parseDouble(C0002.m231("ZwaWdkfbYrafY0QG9hDwBNxX")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۟ۢۨۤۦ, reason: not valid java name and contains not printable characters */
    public static Signature[] m25(Object obj) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).sigs();
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۢۨۦۥ, reason: not valid java name and contains not printable characters */
    public static void m26(Object obj) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).applyServiceManagerCacheHook();
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m188 = C0002.m188();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m188 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Long.valueOf(C0001.m158("QF66twszXLbrbc3WGgjlGzer")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۣ۟۟۟, reason: not valid java name and contains not printable characters */
    public static int m27() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_getPackageArchiveInfo;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۢۢۨ, reason: not valid java name and contains not printable characters */
    public static Method m28(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return findMethod((Class) obj, (String) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۧ۠ۨ, reason: not valid java name and contains not printable characters */
    public static void m29(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).installNative((Context) obj2);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m138 = C0000.m138();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m138 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Integer.decode(C0000.m137("46ZqB08C")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۣ۟ۧۤۧ, reason: not valid java name and contains not printable characters */
    public static String m30() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_ACTIVITY_THREAD;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۨۤۦ, reason: not valid java name and contains not printable characters */
    public static void m31(Object obj) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        nativeInstallCtxHook((Class) obj);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m1382 = C0000.m138();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m1382 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Long.valueOf(C0004.m346("1KK")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۣ۟ۨۤۧ, reason: not valid java name and contains not printable characters */
    public static void m32(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).patchInfo((PackageInfo) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۨۦۨ, reason: not valid java name and contains not printable characters */
    public static Signature[] m33(Object obj) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).origSigs;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۨۧۦ, reason: not valid java name and contains not printable characters */
    public static String m34(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).extractOrigApk((Context) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۤ۠۠ۦ, reason: not valid java name and contains not printable characters */
    public static String m35() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_SELF;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۤۢۡۧ, reason: not valid java name and contains not printable characters */
    public static String m36() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_BOOT_CL;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۤۦۢ, reason: not valid java name and contains not printable characters */
    public static Object m37(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return fieldValue(obj, (String) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۤۥۡۦ, reason: not valid java name and contains not printable characters */
    public static Object m38() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return CACHE_PLS_CREATOR;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۤۨۢ۟, reason: not valid java name and contains not printable characters */
    public static String m39() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_DEX_PATH_LIST;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۥ۠ۡۨ, reason: not valid java name and contains not printable characters */
    public static int m40(Object obj, Object obj2, int i) {
        int m274 = C0003.m274();
        int i2 = 1616;
        while (true) {
            i2 ^= 1633;
            if (i2 != 14) {
                if (i2 != 49) {
                    if (i2 == 204) {
                        return staticInt((Class) obj, (String) obj2, i);
                    }
                    if (i2 == 239) {
                        int i3 = 1740;
                        while (true) {
                            i3 ^= 1757;
                            if (i3 == 17) {
                                i3 = 1771;
                            } else if (i3 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i2 = 1709;
                }
            }
            i2 = 1678;
        }
    }

    /* renamed from: ۟ۥۣۤ۠, reason: not valid java name and contains not printable characters */
    public static Field m41() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return CACHE_SD_SIGNATURES;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۥۧۧۢ, reason: not valid java name and contains not printable characters */
    public static String m42() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return DIR_NAME;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۥۨۧۢ, reason: not valid java name and contains not printable characters */
    public static void m43() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        freeReflection();
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m316 = C0004.m316();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m316 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Integer.valueOf(C0001.m158("Dmb8AUoHOTo1Pyn7IvXh")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۟ۦۡۢۥ, reason: not valid java name and contains not printable characters */
    public static String m44() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_SERVICE;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۦۡۦۨ, reason: not valid java name and contains not printable characters */
    public static IBinder m45() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return g_realSM;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۦۤۨۢ, reason: not valid java name and contains not printable characters */
    public static int m46() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_getPackageInfoVersioned;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۦۧۡۡ, reason: not valid java name and contains not printable characters */
    public static Object m47(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return uGet(obj, (Field) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۦۨ۠ۡ, reason: not valid java name and contains not printable characters */
    public static String m48() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_DEX_ELEMENT;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۦۨ۠ۤ, reason: not valid java name and contains not printable characters */
    public static int m49() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_hasSigningCertificate;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۧ۠ۡۨ, reason: not valid java name and contains not printable characters */
    public static String m50() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_ANDROID_OS;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۧ۠ۨۥ, reason: not valid java name and contains not printable characters */
    public static Field m51(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return findField((Class) obj, (String) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۧۢ۠ۡ, reason: not valid java name and contains not printable characters */
    public static void m52(Object obj) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        log((String) obj);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۧۤ۠, reason: not valid java name and contains not printable characters */
    public static String m53() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_APP_PKG_MANAGER;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۧۤۡ, reason: not valid java name and contains not printable characters */
    public static void m54(Object obj) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).applyCtxHookNative();
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m188 = C0002.m188();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m188 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Double.decode(C0000.m137("WxoI2N9rj4JD3j8p5s9Ckc")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۟ۧۥ۟ۢ, reason: not valid java name and contains not printable characters */
    public static int m55() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_getInstalledPackages;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۧۥۡ۠, reason: not valid java name and contains not printable characters */
    public static String m56() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_CREATOR;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۠۠ۤ, reason: not valid java name and contains not printable characters */
    public static String m57() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_ISM;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۠ۢ۟, reason: not valid java name and contains not printable characters */
    public static Object m58(Object obj) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return readParceledListSlice((Parcel) obj);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۠ۦۡۤ, reason: not valid java name and contains not printable characters */
    public static String m59(Object obj) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return readServiceName((Parcel) obj);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۡ۟ۢۢ, reason: not valid java name and contains not printable characters */
    public static void m60(Object obj, Object obj2) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).installCtxHook((IBinder) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۡ۟ۤۤ, reason: not valid java name and contains not printable characters */
    public static Object m61() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return unsafe();
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۡۡۢۡ, reason: not valid java name and contains not printable characters */
    public static void m62(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        bindFake(obj, (IBinder) obj2);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m188 = C0002.m188();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m188 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Long.parseLong(C0000.m137("2w")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۡۢۡۦ, reason: not valid java name and contains not printable characters */
    public static long m63(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return fieldOffset(obj, (Field) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0L;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۡۢۧۤ, reason: not valid java name and contains not printable characters */
    public static String m64() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_BDCL;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۡۥۤ, reason: not valid java name and contains not printable characters */
    public static Field m65() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return CACHE_AI_SIGNATURES;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۡۥۣۢ, reason: not valid java name and contains not printable characters */
    public static boolean m66(Object obj, Object obj2, Object obj3) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return setObjField(obj, (String) obj2, obj3);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return false;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۡۥۧ, reason: not valid java name and contains not printable characters */
    public static String m67() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return pickAbi();
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۢ۠, reason: not valid java name and contains not printable characters */
    public static String m68() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_SIGNING_INFO;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۢۤۡۡ, reason: not valid java name and contains not printable characters */
    public static void m69(Object obj) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        patchGetService((Parcel) obj);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۢۦۢۤ, reason: not valid java name and contains not printable characters */
    public static void m70() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        resolveTransactions();
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۡ۟, reason: not valid java name and contains not printable characters */
    public static List m71(Object obj) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return plsGetList(obj);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۣۤ۠, reason: not valid java name and contains not printable characters */
    public static Signature[] m72(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).loadSignatures((Context) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۦ۟۟, reason: not valid java name and contains not printable characters */
    public static String m73() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return SELF_CLASS;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۤ۟ۥۦ, reason: not valid java name and contains not printable characters */
    public static String m74(Object obj) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).origApkPath;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۤۡۥۣ, reason: not valid java name and contains not printable characters */
    public static void m75(Object obj, Object obj2) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).rewriteApplicationInfoReply((Parcel) obj2);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m138 = C0000.m138();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m138 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Long.decode(C0003.m275("633")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۣۣۤۢ, reason: not valid java name and contains not printable characters */
    public static File m76(Object obj, Object obj2) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).extractNative((Context) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۤۢۧ۠, reason: not valid java name and contains not printable characters */
    public static int m77() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_getPackageInfo;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۤۦۧۨ, reason: not valid java name and contains not printable characters */
    public static Object m78(Object obj) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return uGetStatic((Field) obj);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۤۧۥۣ, reason: not valid java name and contains not printable characters */
    public static void m79(Object obj) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        installFakeCache((IBinder) obj);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۤۧۧۢ, reason: not valid java name and contains not printable characters */
    public static String m80(Object obj) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((PmsHookProvider) obj).appPkg;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۤۧۨۡ, reason: not valid java name and contains not printable characters */
    public static void m81(Object obj) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        patchCheckService2((Parcel) obj);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m274 = C0003.m274();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m274 <= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Float.decode(C0003.m275("3BbbQi4odk9TjAHo55op7h")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۥ۠ۤۧ, reason: contains not printable characters */
    public static boolean m82(Object obj) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return containsSelf(obj);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return false;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۥۡۡ۠, reason: contains not printable characters */
    public static int m83() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_getApplicationInfo;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۥۢۤ۟, reason: contains not printable characters */
    public static void m84(Object obj, Object obj2) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).replaceCreator((Class) obj2);
                        break;
                    } else if (i == 239) {
                        int i2 = 1864;
                        while (true) {
                            i2 ^= 1881;
                            if (i2 == 17) {
                                i2 = 48674;
                            } else if (i2 == 47483) {
                                break;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
        int m188 = C0002.m188();
        int i3 = 1740;
        while (true) {
            i3 ^= 1757;
            if (i3 == 17) {
                i3 = m188 >= 0 ? 1833 : 1802;
            } else if (i3 != 54) {
                if (i3 == 471) {
                    return;
                }
                if (i3 == 500) {
                    System.out.println(Float.decode(C0004.m346("GV4eip3giwMxF3Gsh3rK5ihPkgi")));
                    return;
                }
            }
        }
    }

    /* renamed from: ۥۢۦۧ, reason: contains not printable characters */
    public static void m85() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        prefetchFields();
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۥۣۢۧ, reason: contains not printable characters */
    public static IBinder m86() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return getRealServiceManagerBinder();
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۥۣ۟ۡ, reason: contains not printable characters */
    public static void m87(Object obj, Object obj2) {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).patchAppInfoSignatures((ApplicationInfo) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۦۣۢ, reason: contains not printable characters */
    public static void m88(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).rewritePackageInfoListReply((Parcel) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۧ۟ۥ۠, reason: not valid java name and contains not printable characters */
    public static byte[] m89(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return readAssetBytes((Context) obj, (String) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۧ۠۠, reason: not valid java name and contains not printable characters */
    public static int m90() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return TX_getInstalledApplications;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m316 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۧ۠ۤۢ, reason: not valid java name and contains not printable characters */
    public static void m91() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        restoreHiddenApi();
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۧۤ۠, reason: not valid java name and contains not printable characters */
    public static void m92(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).hook((Context) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۧۥۡ, reason: not valid java name and contains not printable characters */
    public static void m93(Object obj, Object obj2) {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).replaceSigningInfo((PackageInfo) obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۧۦۥۨ, reason: not valid java name and contains not printable characters */
    public static String m94() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_SIGNING_DETAILS;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۧۧۧۨ, reason: not valid java name and contains not printable characters */
    public static String m95() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_PM_STUB;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m138 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨ۠ۥۥ, reason: not valid java name and contains not printable characters */
    public static void m96(Object obj) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).installCreatorHook();
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨۡ۟, reason: not valid java name and contains not printable characters */
    public static String m97() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_PARCELED_LIST;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨۢۥۢ, reason: not valid java name and contains not printable characters */
    public static void m98(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        ((PmsHookProvider) obj).patchByType(obj2);
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨۢۨ۟, reason: not valid java name and contains not printable characters */
    public static String m99() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return C_SM;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨۧۧۧ, reason: not valid java name and contains not printable characters */
    public static void m100() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        clearPackageInfoCache();
                        return;
                    } else if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return;
                            }
                        }
                    }
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨۨ۟ۨ, reason: not valid java name and contains not printable characters */
    public static int m101() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return DUMP_SM;
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return 0;
                            }
                        }
                    }
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣۨۨۡ, reason: not valid java name and contains not printable characters */
    public static byte[] m102(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return soCrypt((byte[]) obj, (String) obj2);
                    }
                    if (i == 239) {
                        int i2 = 1740;
                        while (true) {
                            i2 ^= 1757;
                            if (i2 == 17) {
                                i2 = 1771;
                            } else if (i2 == 54) {
                                return null;
                            }
                        }
                    }
                } else if (m316 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    @Override // android.content.ContentProvider
    public int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public String getType(Uri uri) {
        return null;
    }

    @Override // android.content.ContentProvider
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    /* JADX WARN: Code restructure failed: missing block: B:25:0x001a, code lost:
    
        com.yan.hook.C0002.m226(r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x001d, code lost:
    
        r1 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x001f, code lost:
    
        r1 = r1 ^ 1757;
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
        To view partially-correct add '--show-bad-code' argument
    */
    public boolean onCreate() {
        /*
            r4 = this;
            r0 = 1
            android.content.Context r1 = org.lsposed.hiddenapibypass.library.C0004.m315(r4)     // Catch: java.lang.Throwable -> L36
            r2 = 1616(0x650, float:2.264E-42)
        L7:
            r2 = r2 ^ 1633(0x661, float:2.288E-42)
            r3 = 14
            if (r2 == r3) goto L33
            r3 = 49
            if (r2 == r3) goto L2e
            r3 = 204(0xcc, float:2.86E-43)
            if (r2 == r3) goto L2d
            r3 = 239(0xef, float:3.35E-43)
            if (r2 == r3) goto L1a
            goto L7
        L1a:
            com.yan.hook.C0002.m226(r1)     // Catch: java.lang.Throwable -> L36
            r1 = 1740(0x6cc, float:2.438E-42)
        L1f:
            r1 = r1 ^ 1757(0x6dd, float:2.462E-42)
            r2 = 17
            if (r1 == r2) goto L2a
            r2 = 54
            if (r1 == r2) goto L45
            goto L1f
        L2a:
            r1 = 1771(0x6eb, float:2.482E-42)
            goto L1f
        L2d:
            return r0
        L2e:
            if (r1 != 0) goto L33
            r2 = 1709(0x6ad, float:2.395E-42)
            goto L7
        L33:
            r2 = 1678(0x68e, float:2.351E-42)
            goto L7
        L36:
            r1 = move-exception
            r2 = 15
            int[] r2 = new int[r2]
            r2 = {x0046: FILL_ARRAY_DATA , data: [111, 110, 67, 114, 101, 97, 116, 101, 32, 102, 97, 105, 108, 101, 100} // fill-array
            java.lang.String r2 = m1(r2)
            m9(r2, r1)
        L45:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.yan.hook.PmsHookProvider.onCreate():boolean");
    }

    @Override // android.content.ContentProvider
    public Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
