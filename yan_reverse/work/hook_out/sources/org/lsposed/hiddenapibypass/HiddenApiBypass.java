package org.lsposed.hiddenapibypass;

import com.yan.hook.C0000;
import com.yan.hook.C0001;
import com.yan.hook.C0002;
import dalvik.system.VMRuntime;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import org.lsposed.hiddenapibypass.Helper;
import org.lsposed.hiddenapibypass.library.C0003;
import org.lsposed.hiddenapibypass.library.C0004;
import sun.misc.Unsafe;

/* loaded from: /workspace/yan_reverse/work/assets/hook.dex */
public final class HiddenApiBypass {
    static final boolean $assertionsDisabled = false;
    private static final long artFieldBias;
    private static final long artFieldSize;
    private static final long artMethodBias;
    private static final long artMethodSize;
    private static final long artOffset;
    private static final long classOffset;
    private static final long iFieldOffset;
    private static final long methodOffset;
    private static final long methodsOffset;
    private static final long sFieldOffset;
    private static final Unsafe unsafe;

    /* renamed from: short, reason: not valid java name */
    private static final short[] f9short = {674, 643, 654, 654, 655, 644, 683, 666, 643, 680, 659, 666, 651, 665, 665, 299, 297, 312, 281, 290, 319, 301, 298, 297, 2933, 2918, 2912, 2905, 2929, 2912, 2940, 2939, 2928, 2272, 2273, 2279, 2280, 2277, 2294, 2285, 2282, 2275, 2247, 2280, 2277, 2295, 2295, 794, 777, 783, 829, 786, 798, 791, 799, 820, 777, 822, 798, 783, 787, 788, 799, 3045, 3050, 3046, 3055, 3047, 3056, 868, 843, 868, 872, 865, 873, 894, 790, 803, 780, 768, 777, 769, 790, 2907, 2899, 2882, 2910, 2905, 2898, 2885, 942, 313, 2814, 2688, 1017, 984, 981, 981, 980, 991, 1008, 961, 984, 1011, 968, 961, 976, 962, 962, 2897, 2934, 2929, 2924, 2929, 2937, 2932, 2929, 2914, 2941, 2872, 2941, 2922, 2922, 2935, 2922, 698, 664, 663, 663, 662, 653, 729, 671, 656, 663, 669, 729, 660, 664, 653, 666, 657, 656, 663, 670, 729, 666, 662, 663, 650, 653, 651, 652, 666, 653, 662, 651, 1440, 1410, 1421, 1421, 1420, 1431, 1475, 1413, 1418, 1421, 1415, 1475, 1422, 1410, 1431, 1408, 1419, 1418, 1421, 1412, 1475, 1422, 1414, 1431, 1419, 1420, 1415, 2504, 2783, 788, 2992, 2988, 2989, 2999, 3044, 2987, 2982, 2990, 2977, 2983, 2992, 3044, 2989, 2999, 3044, 2986, 2987, 2992, 3044, 2981, 2986, 3044, 2989, 2986, 2999, 2992, 2981, 2986, 2983, 2977, 3044, 2987, 2978, 3044, 2992, 2988, 2977, 3044, 2979, 2989, 2994, 2977, 2986, 3044, 2983, 2984, 2981, 2999, 2999, 1318, 1313, 1337, 1312, 1316, 1322, 1710, 1676, 1667, 1667, 1666, 1689, 1741, 1675, 1668, 1667, 1673, 1741, 1664, 1676, 1689, 1678, 1669, 1668, 1667, 1674, 1741, 1664, 1672, 1689, 1669, 1666, 1673, 1404, 1403, 1379, 1402, 1406, 1392, 1144, 1114, 1109, 1109, 1108, 1103, 1051, 1117, 1106, 1109, 1119, 1051, 1110, 1114, 1103, 1112, 1107, 1106, 1109, 1116, 1051, 1112, 1108, 1109, 1096, 1103, 1097, 1102, 1112, 1103, 1108, 1097, 1926, 2003, 2004, 2003, 1998, 1924, 1021, 1003, 1018, 966, 999, 1002, 1002, 1003, 992, 975, 1022, 999, 971, 1014, 1003, 995, 1022, 1018, 999, 993, 992, 1021, 2517, 2519, 2502, 2528, 2503, 2524, 2502, 2523, 2527, 2519, 2286, 2255, 2242, 2242, 2243, 2248, 2279, 2262, 2255, 2276, 2271, 2262, 2247, 2261, 2261};
    private static final String TAG = C0001.m169(m249(), 0, C0003.f12 ^ 674, 746);

    static {
        long m307;
        long m3072;
        try {
            unsafe = (Unsafe) C0003.m293(C0002.m192(Unsafe.class, C0001.m169(m249(), 15, C0002.f6 ^ (-489), 332), new Class[0]), null, new Object[0]);
            CoreOjClassLoader coreOjClassLoader = new CoreOjClassLoader();
            Class m161 = C0001.m161(coreOjClassLoader, C0002.m223(Executable.class));
            Class m1612 = C0001.m161(coreOjClassLoader, C0002.m223(MethodHandle.class));
            Class m1613 = C0001.m161(coreOjClassLoader, C0002.m223(Class.class));
            methodOffset = C0003.m307(m245(), C0004.m326(m161, C0001.m169(m249(), 24, C0000.f4 ^ 94, 2836)));
            classOffset = C0003.m307(m245(), C0004.m326(m161, C0004.m323(m249(), 33, C0003.f12 ^ 675, 2180)));
            artOffset = C0003.m307(m245(), C0004.m326(m1612, C0002.m191(m249(), 47, C0001.f5 ^ (-595), 891)));
            try {
                m307 = C0003.m307(m245(), C0004.m326(m1613, C0002.m191(m249(), 63, C0000.f4 ^ 81, 2947)));
                int i = 1616;
                while (true) {
                    i ^= 1633;
                    if (i == 14) {
                        break;
                    } else if (i == 49) {
                        i = 1647;
                    }
                }
                m3072 = m307;
            } catch (NoSuchFieldException unused) {
                m307 = C0003.m307(m245(), C0004.m326(m1613, C0003.m285(m249(), 69, C0003.f12 ^ 682, 781)));
                m3072 = C0003.m307(m245(), C0004.m326(m1613, C0003.m285(m249(), 76, C0002.f6 ^ (-487), 869)));
            }
            iFieldOffset = m307;
            sFieldOffset = m3072;
            methodsOffset = C0003.m307(m245(), C0004.m326(m1613, C0001.m169(m249(), 83, C0002.f6 ^ (-487), 2870)));
            Method m192 = C0002.m192(Helper.NeverCall.class, C0004.m323(m249(), 90, 1, 975), new Class[0]);
            Method m1922 = C0002.m192(Helper.NeverCall.class, C0003.m285(m249(), 91, 1, 347), new Class[0]);
            C0001.m171(m192, true);
            C0001.m171(m1922, true);
            MethodHandle m230 = C0002.m230(MethodHandles.lookup(), m192);
            MethodHandle m2302 = C0002.m230(MethodHandles.lookup(), m1922);
            long m183 = C0001.m183(m245(), m230, m250());
            long m1832 = C0001.m183(m245(), m2302, m250());
            long m1833 = C0001.m183(m245(), Helper.NeverCall.class, m242());
            artMethodSize = m1832 - m183;
            artMethodBias = (m183 - m1833) - m247();
            Field m326 = C0004.m326(Helper.NeverCall.class, C0002.m191(m249(), 92, 1, 2711));
            Field m3262 = C0004.m326(Helper.NeverCall.class, C0001.m169(m249(), 93, 1, 2794));
            C0001.m152(m326, true);
            C0001.m152(m3262, true);
            MethodHandle m215 = C0002.m215(MethodHandles.lookup(), m326);
            MethodHandle m2152 = C0002.m215(MethodHandles.lookup(), m3262);
            long m1834 = C0001.m183(m245(), m215, m250());
            long m1835 = C0001.m183(m245(), m2152, m250());
            long m1836 = C0001.m183(m245(), Helper.NeverCall.class, m238());
            artFieldSize = m1835 - m1834;
            artFieldBias = m1834 - m1836;
        } catch (ReflectiveOperationException e) {
            C0002.m191(m249(), 94, C0001.f5 ^ (-590), 945);
            C0001.m169(m249(), 109, C0002.f6 ^ (-498), 2840);
            throw new ExceptionInInitializerError(e);
        }
    }

    public HiddenApiBypass() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        System.out.println(Long.parseLong(C0000.m137("r5lk95dVskPvGgAu8chbjBg5Zl2")));
                        return;
                    } else if (i == 239) {
                        return;
                    }
                } else if (m138 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x002b, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean addHiddenApiExemptions(java.lang.String... r5) {
        /*
            r0 = 0
            r1 = 0
            java.lang.String r2 = "ۧۨۨ"
            r1 = r0
            r4 = r2
            r2 = 0
            r3 = 0
        L8:
            int r4 = org.lsposed.hiddenapibypass.library.C0004.m311(r4)
            switch(r4) {
                case 1747685: goto L47;
                case 1747686: goto L40;
                case 1749764: goto L39;
                case 1750626: goto L33;
                case 1751655: goto L2c;
                case 1751679: goto L2b;
                case 1753573: goto L24;
                case 1754629: goto L1d;
                case 1754663: goto L16;
                default: goto Lf;
            }
        Lf:
            java.util.List r1 = com.yan.hook.C0001.m165(r5)
            java.lang.String r4 = "ۢۥۧ"
            goto L8
        L16:
            java.util.Set r0 = m246()
            java.lang.String r4 = "ۥ۠ۦ"
            goto L8
        L1d:
            java.util.Set r0 = m246()
            java.lang.String r4 = "ۣۢۡ"
            goto L8
        L24:
            boolean r3 = org.lsposed.hiddenapibypass.library.C0003.m268(r5)
            java.lang.String r4 = "ۤۥ۠"
            goto L8
        L2b:
            return r3
        L2c:
            int r2 = org.lsposed.hiddenapibypass.library.C0004.m327(r0)
            java.lang.String r4 = "۠۠ۥ"
            goto L8
        L33:
            org.lsposed.hiddenapibypass.library.C0003.m286(r0, r5)
            java.lang.String r4 = "ۦۣۤ"
            goto L8
        L39:
            boolean r3 = com.yan.hook.C0001.m172(r0, r1)
            java.lang.String r4 = "۠۠ۦ"
            goto L8
        L40:
            java.util.Set r0 = m246()
            java.lang.String r4 = "ۤۤۧ"
            goto L8
        L47:
            java.lang.String[] r5 = new java.lang.String[r2]
            java.lang.String r4 = "ۧۧۥ"
            goto L8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.addHiddenApiExemptions(java.lang.String[]):boolean");
    }

    public static boolean clearHiddenApiExemptions() {
        C0004.m341(m246());
        return C0003.m268(new String[0]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0109, code lost:
    
        r2 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x010d, code lost:
    
        r2 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x00a7, code lost:
    
        if (r2 == 50) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x00a9, code lost:
    
        if (r2 == 76) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x00ad, code lost:
    
        if (r2 == 83) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x00b2, code lost:
    
        return (java.lang.reflect.Constructor) r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00b3, code lost:
    
        r2 = r11[r3];
        r6 = r0[r3];
        r7 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x00ba, code lost:
    
        r7 = r7 ^ 49807;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x00c0, code lost:
    
        if (r7 == 18) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x00c4, code lost:
    
        if (r7 == 51) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x00c8, code lost:
    
        if (r7 == 84) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x00cc, code lost:
    
        if (r7 == 241) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x00cf, code lost:
    
        if (r2 == r6) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0103, code lost:
    
        r7 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x00d1, code lost:
    
        r7 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x00d5, code lost:
    
        r0 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x00d8, code lost:
    
        r0 = r0 ^ 49931;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x00de, code lost:
    
        if (r0 == 497) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:143:0x00e5, code lost:
    
        r0 = 50596;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x00e2, code lost:
    
        if (r0 == 1711) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x00e9, code lost:
    
        r7 = r3 + (com.yan.hook.C0001.f5 ^ (-580));
        r2 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x00f2, code lost:
    
        r2 = r2 ^ 50706;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x00f8, code lost:
    
        if (r2 == 19) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x00ff, code lost:
    
        r2 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x00fa, code lost:
    
        if (r2 == 50) goto L171;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x00fd, code lost:
    
        r3 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0037, code lost:
    
        r1 = (java.lang.reflect.Executable) org.lsposed.hiddenapibypass.library.C0004.m329(r10);
        r3 = r1 instanceof java.lang.reflect.Constructor;
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0042, code lost:
    
        r0 = r0 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0046, code lost:
    
        if (r0 == 17) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0116, code lost:
    
        if (r3 != false) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0118, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x011c, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x004a, code lost:
    
        if (r0 == 54) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x004e, code lost:
    
        if (r0 == 471) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0052, code lost:
    
        if (r0 == 500) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0055, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0057, code lost:
    
        r0 = r0 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0059, code lost:
    
        if (r0 == 17) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0061, code lost:
    
        r0 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x005e, code lost:
    
        if (r0 == 47483) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0065, code lost:
    
        r0 = org.lsposed.hiddenapibypass.library.C0004.m312(r1);
        r6 = r0.length;
        r7 = r11.length;
        r3 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x006e, code lost:
    
        r3 = r3 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0072, code lost:
    
        if (r3 == 14) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0078, code lost:
    
        if (r3 == 45) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x007a, code lost:
    
        if (r3 == 76) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x007c, code lost:
    
        if (r3 == 239) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x007f, code lost:
    
        if (r6 == r7) goto L134;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0111, code lost:
    
        r3 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0081, code lost:
    
        r3 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0085, code lost:
    
        r0 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0088, code lost:
    
        r0 = r0 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x008e, code lost:
    
        if (r0 == 22) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0092, code lost:
    
        if (r0 == 503) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0095, code lost:
    
        r0 = 48922;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0099, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x009b, code lost:
    
        r8 = r11.length;
        r2 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x009f, code lost:
    
        r2 = r2 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00a3, code lost:
    
        if (r2 == 17) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0107, code lost:
    
        if (r3 >= r8) goto L151;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.reflect.Constructor<?> getDeclaredConstructor(java.lang.Class<?> r10, java.lang.Class<?>... r11) throws java.lang.NoSuchMethodException {
        /*
            Method dump skipped, instructions count: 298
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.getDeclaredConstructor(java.lang.Class, java.lang.Class[]):java.lang.reflect.Constructor");
    }

    /* JADX WARN: Code restructure failed: missing block: B:103:0x0141, code lost:
    
        r0 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x00ac, code lost:
    
        if (r0 == 50) goto L166;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x00b0, code lost:
    
        if (r0 == 76) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x00b4, code lost:
    
        if (r0 == 83) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x00b7, code lost:
    
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x00b8, code lost:
    
        r9 = r15.length;
        r10 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x00bc, code lost:
    
        r10 = r10 ^ 49931;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00c2, code lost:
    
        if (r10 == 497) goto L176;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x011e, code lost:
    
        if (r0 >= r9) goto L182;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0120, code lost:
    
        r10 = 50658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0124, code lost:
    
        r10 = 50627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x00c6, code lost:
    
        if (r10 == 1711) goto L177;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x00ca, code lost:
    
        if (r10 == 1736) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x00ce, code lost:
    
        if (r10 == 1769) goto L178;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x00d1, code lost:
    
        r9 = r15[r0];
        r10 = r3[r0];
        r11 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x00d8, code lost:
    
        r11 = r11 ^ 50706;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x00de, code lost:
    
        if (r11 == 19) goto L189;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0111, code lost:
    
        if (r9 == r10) goto L192;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0113, code lost:
    
        r11 = 50782;
     */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0117, code lost:
    
        r11 = 50751;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x00e0, code lost:
    
        if (r11 == 45) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x00e2, code lost:
    
        if (r11 == 50) goto L188;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x00e4, code lost:
    
        if (r11 == 76) goto L175;
     */
    /* JADX WARN: Code restructure failed: missing block: B:157:0x00e7, code lost:
    
        r0 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x00ea, code lost:
    
        r0 = r0 ^ 50830;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x00ee, code lost:
    
        if (r0 == 18) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x00f2, code lost:
    
        if (r0 == 243) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:163:0x00f5, code lost:
    
        r0 = 50844;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x00f9, code lost:
    
        r11 = r0 + (org.lsposed.hiddenapibypass.library.C0003.f12 ^ 684);
        r0 = 51588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:171:0x0102, code lost:
    
        r0 = r0 ^ 51605;
     */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x0106, code lost:
    
        if (r0 == 17) goto L202;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x010d, code lost:
    
        r0 = 51619;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0108, code lost:
    
        if (r0 == 54) goto L203;
     */
    /* JADX WARN: Code restructure failed: missing block: B:180:0x010b, code lost:
    
        r0 = r11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x011d, code lost:
    
        return (java.lang.reflect.Method) r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x0128, code lost:
    
        r0 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x012b, code lost:
    
        r0 = r0 ^ 49807;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x012f, code lost:
    
        if (r0 == 18) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:192:0x0133, code lost:
    
        if (r0 == 241) goto L208;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0136, code lost:
    
        r0 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0037, code lost:
    
        r1 = (java.lang.reflect.Executable) org.lsposed.hiddenapibypass.library.C0004.m329(r13);
        r3 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m198(r1), r14);
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0048, code lost:
    
        r0 = r0 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004c, code lost:
    
        if (r0 == 17) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x014b, code lost:
    
        if (r3 != false) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x014d, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0151, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0050, code lost:
    
        if (r0 == 54) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0054, code lost:
    
        if (r0 == 471) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0058, code lost:
    
        if (r0 == 500) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x005b, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005d, code lost:
    
        r0 = r0 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005f, code lost:
    
        if (r0 == 17) goto L148;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0067, code lost:
    
        r0 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0064, code lost:
    
        if (r0 == 47483) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x006b, code lost:
    
        r0 = r1 instanceof java.lang.reflect.Method;
        r3 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0070, code lost:
    
        r3 = r3 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0074, code lost:
    
        if (r3 == 14) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x007a, code lost:
    
        if (r3 == 45) goto L118;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x007c, code lost:
    
        if (r3 == 76) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x007e, code lost:
    
        if (r3 == 239) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0081, code lost:
    
        if (r0 != false) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0146, code lost:
    
        r3 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0083, code lost:
    
        r3 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0087, code lost:
    
        r0 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x008a, code lost:
    
        r0 = r0 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0090, code lost:
    
        if (r0 == 22) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0094, code lost:
    
        if (r0 == 503) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0097, code lost:
    
        r0 = 48922;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x009b, code lost:
    
        r3 = org.lsposed.hiddenapibypass.library.C0004.m312(r1);
        r9 = r3.length;
        r10 = r15.length;
        r0 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00a4, code lost:
    
        r0 = r0 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00a8, code lost:
    
        if (r0 == 17) goto L165;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x013a, code lost:
    
        if (r9 == r10) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x013c, code lost:
    
        r0 = 49759;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.reflect.Method getDeclaredMethod(java.lang.Class<?> r13, java.lang.String r14, java.lang.Class<?>... r15) throws java.lang.NoSuchMethodException {
        /*
            Method dump skipped, instructions count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.getDeclaredMethod(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00e0, code lost:
    
        if (r3 == 503) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00e3, code lost:
    
        r3 = 48922;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00a4, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m303(m245(), r0, m250(), m239() + ((r15 * m247()) + r12));
        com.yan.hook.C0001.m141(r14, (java.lang.reflect.Executable) org.lsposed.hiddenapibypass.library.C0003.m282(java.lang.reflect.Executable.class, r0));
        r15 = r15 + (org.lsposed.hiddenapibypass.library.C0003.f12 ^ 684);
        r3 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00d6, code lost:
    
        r3 = r3 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00dc, code lost:
    
        if (r3 == 22) goto L117;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Executable> getDeclaredMethods(java.lang.Class<?> r20) {
        /*
            Method dump skipped, instructions count: 309
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.getDeclaredMethods(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00db, code lost:
    
        if (r7 == 53) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00df, code lost:
    
        if (r7 == 503) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x00ea, code lost:
    
        if (r6 != false) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x010a, code lost:
    
        r7 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x00ec, code lost:
    
        r7 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x00e3, code lost:
    
        if (r7 == 32495) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x00e6, code lost:
    
        com.yan.hook.C0001.m141(r13, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00f0, code lost:
    
        r7 = r14 + (com.yan.hook.C0002.f6 ^ (-481));
        r5 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00f9, code lost:
    
        r5 = r5 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x00fd, code lost:
    
        if (r5 == 17) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0106, code lost:
    
        r5 = 49697;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0101, code lost:
    
        if (r5 == 50) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00a2, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m303(m245(), r0, m250(), m241() + ((r14 * m251()) + r11));
        r5 = (java.lang.reflect.Field) org.lsposed.hiddenapibypass.library.C0003.m282(java.lang.reflect.Field.class, r0);
        r6 = org.lsposed.hiddenapibypass.library.C0003.m308(org.lsposed.hiddenapibypass.library.C0004.m334(r5));
        r7 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00d1, code lost:
    
        r7 = r7 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00d7, code lost:
    
        if (r7 == 22) goto L142;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Field> getInstanceFields(java.lang.Class<?> r18) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.getInstanceFields(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x00db, code lost:
    
        if (r7 == 53) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00df, code lost:
    
        if (r7 == 503) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x00ea, code lost:
    
        if (r6 == false) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x010a, code lost:
    
        r7 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x00ec, code lost:
    
        r7 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x00e3, code lost:
    
        if (r7 == 32495) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x00e6, code lost:
    
        com.yan.hook.C0001.m141(r13, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00f0, code lost:
    
        r7 = r14 + (com.yan.hook.C0000.f4 ^ 86);
        r5 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00f9, code lost:
    
        r5 = r5 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x00fd, code lost:
    
        if (r5 == 17) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0106, code lost:
    
        r5 = 49697;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0101, code lost:
    
        if (r5 == 50) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00a2, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m303(m245(), r0, m250(), m241() + ((r14 * m251()) + r11));
        r5 = (java.lang.reflect.Field) org.lsposed.hiddenapibypass.library.C0003.m282(java.lang.reflect.Field.class, r0);
        r6 = org.lsposed.hiddenapibypass.library.C0003.m308(org.lsposed.hiddenapibypass.library.C0004.m334(r5));
        r7 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00d1, code lost:
    
        r7 = r7 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00d7, code lost:
    
        if (r7 == 22) goto L142;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Field> getStaticFields(java.lang.Class<?> r18) {
        /*
            Method dump skipped, instructions count: 351
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.getStaticFields(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x0147, code lost:
    
        if (r11 == 18) goto L152;
     */
    /* JADX WARN: Code restructure failed: missing block: B:102:0x014b, code lost:
    
        if (r11 == 241) goto L153;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x014e, code lost:
    
        r11 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x0128, code lost:
    
        return org.lsposed.hiddenapibypass.library.C0003.m293(r4, r22, r24);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x00c4, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m303(m245(), r4, m244(), m239() + ((r10 * m247()) + r7));
        r11 = com.yan.hook.C0002.m200(r23, com.yan.hook.C0002.m210(r4));
        r13 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00f3, code lost:
    
        r13 = r13 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00f9, code lost:
    
        if (r13 == 22) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00fd, code lost:
    
        if (r13 == 53) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0101, code lost:
    
        if (r13 == 503) goto L130;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0133, code lost:
    
        if (r11 == false) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0158, code lost:
    
        r13 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0135, code lost:
    
        r13 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0105, code lost:
    
        if (r13 == 32495) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0108, code lost:
    
        r13 = m240(org.lsposed.hiddenapibypass.library.C0003.m300(r4), r24);
        r11 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0113, code lost:
    
        r11 = r11 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0117, code lost:
    
        if (r11 == 17) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0129, code lost:
    
        if (r13 == false) goto L147;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x012b, code lost:
    
        r11 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x012f, code lost:
    
        r11 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x011b, code lost:
    
        if (r11 == 50) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x011d, code lost:
    
        if (r11 == 76) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0121, code lost:
    
        if (r11 == 83) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0139, code lost:
    
        r10 = r10 + (com.yan.hook.C0000.f4 ^ 86);
        r11 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x0141, code lost:
    
        r11 = r11 ^ 49807;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object invoke(java.lang.Class<?> r21, java.lang.Object r22, java.lang.String r23, java.lang.Object... r24) throws java.lang.NoSuchMethodException, java.lang.reflect.InvocationTargetException, java.lang.IllegalAccessException {
        /*
            Method dump skipped, instructions count: 434
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.invoke(java.lang.Class, java.lang.Object, java.lang.String, java.lang.Object[]):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:44:0x0085, code lost:
    
        r16 = m239() + ((r7 * m247()) + r4);
        r18 = r12;
        org.lsposed.hiddenapibypass.library.C0003.m303(m245(), r2, m244(), r16);
        r8 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m191(m249(), 307, org.lsposed.hiddenapibypass.library.C0003.f12 ^ 683, 1978), com.yan.hook.C0002.m210(r2));
        r9 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x00bf, code lost:
    
        r9 = r9 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x00c1, code lost:
    
        if (r9 == 17) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0142, code lost:
    
        if (r8 == false) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0144, code lost:
    
        r9 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0149, code lost:
    
        r9 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x00c6, code lost:
    
        if (r9 == 47384) goto L97;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x00cb, code lost:
    
        if (r9 == 47417) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x00d0, code lost:
    
        if (r9 == 47483) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x00d7, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m303(m245(), r3, m244(), r16);
        com.yan.hook.C0001.m175(m245(), r3, m248(), r19);
        r8 = m240(com.yan.hook.C0002.m228(r3), r20);
        r9 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x00fb, code lost:
    
        r9 = r9 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x00ff, code lost:
    
        if (r9 == 14) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0103, code lost:
    
        if (r9 == 45) goto L106;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x0107, code lost:
    
        if (r9 == 76) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x010b, code lost:
    
        if (r9 == 239) goto L107;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x010e, code lost:
    
        if (r8 == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x011b, code lost:
    
        r9 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0110, code lost:
    
        r9 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0118, code lost:
    
        return org.lsposed.hiddenapibypass.library.C0004.m333(r3, r20);
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x011f, code lost:
    
        r7 = r7 + (com.yan.hook.C0000.f4 ^ 86);
        r8 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0129, code lost:
    
        r8 = r8 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x012f, code lost:
    
        if (r8 == 22) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0133, code lost:
    
        if (r8 == 503) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0136, code lost:
    
        r8 = 48922;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object newInstance(java.lang.Class<?> r19, java.lang.Object... r20) throws java.lang.NoSuchMethodException, java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException, java.lang.InstantiationException {
        /*
            Method dump skipped, instructions count: 378
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.HiddenApiBypass.newInstance(java.lang.Class, java.lang.Object[]):java.lang.Object");
    }

    public static boolean setHiddenApiExemptions(String... strArr) {
        try {
            C0003.m265(VMRuntime.class, C0003.m265(VMRuntime.class, null, C0002.m191(m249(), 335, C0000.f4 ^ 93, 2482), new Object[0]), C0001.m169(m249(), 313, C0003.f12 ^ 699, 910), new Object[]{strArr});
            return true;
        } catch (ReflectiveOperationException unused) {
            C0002.m191(m249(), 345, C0000.f4 ^ 88, 2214);
            return false;
        }
    }

    /* renamed from: ۣۣ۟۟ۡ, reason: not valid java name and contains not printable characters */
    public static long m238() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return iFieldOffset;
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
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟۠ۡۥۦ, reason: not valid java name and contains not printable characters */
    public static long m239() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return artMethodBias;
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
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۣ۟ۡ۟۟, reason: not valid java name and contains not printable characters */
    public static boolean m240(Object obj, Object obj2) {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return Helper.checkArgsForInvokeMethod((Class[]) obj, (Object[]) obj2);
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

    /* renamed from: ۣ۟۟۟ۥ, reason: not valid java name and contains not printable characters */
    public static long m241() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return artFieldBias;
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

    /* renamed from: ۣ۟۠ۥۢ, reason: not valid java name and contains not printable characters */
    public static long m242() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return methodsOffset;
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
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۥۣ۟۟, reason: not valid java name and contains not printable characters */
    public static long m243() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return sFieldOffset;
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
                } else if (m188 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۥۨۨۧ, reason: not valid java name and contains not printable characters */
    public static long m244() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return methodOffset;
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
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۟ۧ۠ۥۨ, reason: not valid java name and contains not printable characters */
    public static Unsafe m245() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return unsafe;
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

    /* renamed from: ۟ۨۦۦ, reason: not valid java name and contains not printable characters */
    public static Set m246() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return Helper.signaturePrefixes;
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

    /* renamed from: ۡۢۡۡ, reason: not valid java name and contains not printable characters */
    public static long m247() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return artMethodSize;
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
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۦۦۡۨ, reason: contains not printable characters */
    public static long m248() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return classOffset;
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
                } else if (m138 <= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۧۨۧ۠, reason: not valid java name and contains not printable characters */
    public static short[] m249() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return f9short;
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

    /* renamed from: ۨۢۨۧ, reason: not valid java name and contains not printable characters */
    public static long m250() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return artOffset;
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
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨۦۦۢ, reason: not valid java name and contains not printable characters */
    public static long m251() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return artFieldSize;
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
                } else if (m274 >= 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }
}
