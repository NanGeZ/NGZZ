package org.lsposed.hiddenapibypass;

import android.util.Property;
import com.yan.hook.C0000;
import com.yan.hook.C0001;
import com.yan.hook.C0002;
import dalvik.system.VMRuntime;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Set;
import org.lsposed.hiddenapibypass.library.C0003;
import org.lsposed.hiddenapibypass.library.C0004;

/* loaded from: /workspace/yan_reverse/work/assets/hook.dex */
public final class LSPass {

    /* renamed from: short, reason: not valid java name */
    private static final short[] f10short = {2666, 2677, 2678, 2631, 2645, 2645, 2293, 2260, 2258, 2269, 2256, 2243, 2260, 2261, 2300, 2260, 2245, 2265, 2270, 2261, 2242, 2396, 2429, 2427, 2420, 2425, 2410, 2429, 2428, 2395, 2423, 2422, 2411, 2412, 2410, 2413, 2427, 2412, 2423, 2410, 2411, 3289, 3320, 3326, 3313, 3324, 3311, 3320, 3321, 3291, 3316, 3320, 3313, 3321, 3310, 3091, 3121, 3134, 3134, 3135, 3108, 3184, 3126, 3129, 3134, 3124, 3184, 3133, 3121, 3108, 3123, 3128, 3129, 3134, 3127, 3184, 3123, 3135, 3134, 3107, 3108, 3106, 3109, 3123, 3108, 3135, 3106, 874, 840, 839, 839, 838, 861, 777, 847, 832, 839, 845, 777, 836, 840, 861, 842, 833, 832, 839, 846, 777, 836, 844, 861, 833, 838, 845, 2832, 2866, 2877, 2877, 2876, 2855, 2931, 2869, 2874, 2877, 2871, 2931, 2878, 2866, 2855, 2864, 2875, 2874, 2877, 2868, 2931, 2878, 2870, 2855, 2875, 2876, 2871, 3073, 3107, 3116, 3116, 3117, 3126, 3170, 3108, 3115, 3116, 3110, 3170, 3119, 3107, 3126, 3105, 3114, 3115, 3116, 3109, 3170, 3105, 3117, 3116, 3121, 3126, 3120, 3127, 3105, 3126, 3117, 3120, 1443, 1461, 1444, 1432, 1465, 1460, 1460, 1461, 1470, 1425, 1440, 1465, 1429, 1448, 1461, 1469, 1440, 1444, 1465, 1471, 1470, 1443, 868, 870, 887, 849, 886, 877, 887, 874, 878, 870, 2578, 2573, 2574, 2623, 2605, 2605};
    private static final String TAG = C0002.m191(m257(), 0, C0000.f4 ^ 81, 2598);
    private static final Property<Class, Method[]> methods = C0003.m310(Class.class, Method[].class, C0002.m191(m257(), 6, C0001.f5 ^ (-590), 2225));
    private static final Property<Class, Constructor[]> constructors = C0003.m310(Class.class, Constructor[].class, C0004.m323(m257(), 21, C0003.f12 ^ 697, 2328));
    private static final Property<Class, Field[]> fields = C0003.m310(Class.class, Field[].class, C0004.m323(m257(), 41, C0003.f12 ^ 675, 3229));

    public LSPass() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        System.out.println(Integer.decode(C0003.m275("Vyt6ep8qfEoNIe09BQT")));
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

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0028, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static boolean addHiddenApiExemptions(java.lang.String... r5) {
        /*
            r0 = 0
            r1 = 0
            java.lang.String r2 = "ۢۡۧ"
            r1 = r0
            r4 = r2
            r2 = 0
            r3 = 0
        L8:
            int r4 = org.lsposed.hiddenapibypass.library.C0004.m311(r4)
            switch(r4) {
                case 56389: goto L45;
                case 56482: goto L3e;
                case 1747901: goto L37;
                case 1748708: goto L30;
                case 1749669: goto L29;
                case 1750661: goto L28;
                case 1750782: goto L21;
                case 1750785: goto L1b;
                case 1752615: goto L16;
                default: goto Lf;
            }
        Lf:
            java.util.Set r0 = m255()
            java.lang.String r4 = "ۥۧ"
            goto L8
        L16:
            java.lang.String[] r5 = new java.lang.String[r3]
            java.lang.String r4 = "ۡۢۥ"
            goto L8
        L1b:
            org.lsposed.hiddenapibypass.library.C0003.m286(r0, r5)
            java.lang.String r4 = "ۢۧ"
            goto L8
        L21:
            boolean r2 = com.yan.hook.C0001.m172(r0, r1)
            java.lang.String r4 = "ۢۢۥ"
            goto L8
        L28:
            return r2
        L29:
            java.util.Set r0 = m255()
            java.lang.String r4 = "۠ۧۤ"
            goto L8
        L30:
            java.util.Set r0 = m255()
            java.lang.String r4 = "ۣۧۥ"
            goto L8
        L37:
            int r3 = org.lsposed.hiddenapibypass.library.C0004.m327(r0)
            java.lang.String r4 = "ۥۤۦ"
            goto L8
        L3e:
            java.util.List r1 = com.yan.hook.C0001.m165(r5)
            java.lang.String r4 = "ۣۧۢ"
            goto L8
        L45:
            boolean r2 = com.yan.hook.C0001.m177(r5)
            java.lang.String r4 = "ۣۣۥ"
            goto L8
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.addHiddenApiExemptions(java.lang.String[]):boolean");
    }

    public static boolean clearHiddenApiExemptions() {
        C0004.m341(m255());
        return C0001.m177(new String[0]);
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x009e, code lost:
    
        if (r8 == 32495) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x00a1, code lost:
    
        r0 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x00a4, code lost:
    
        r0 = r0 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:105:0x00a8, code lost:
    
        if (r0 == 17) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:107:0x00af, code lost:
    
        r0 = 49697;
     */
    /* JADX WARN: Code restructure failed: missing block: B:112:0x00ac, code lost:
    
        if (r0 == 50) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x00b9, code lost:
    
        r0 = r0 + (org.lsposed.hiddenapibypass.library.C0003.f12 ^ 684);
        r5 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00c1, code lost:
    
        r5 = r5 ^ 49807;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00c7, code lost:
    
        if (r5 == 18) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x00cb, code lost:
    
        if (r5 == 241) goto L136;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x00ce, code lost:
    
        r5 = 49821;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x00d6, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0037, code lost:
    
        r1 = (java.lang.reflect.Constructor) org.lsposed.hiddenapibypass.library.C0004.m329(r10);
        r3 = com.yan.hook.C0002.m228(r1);
        r5 = r3.length;
        r6 = r11.length;
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0046, code lost:
    
        r0 = r0 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004a, code lost:
    
        if (r0 == 17) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00db, code lost:
    
        if (r5 == r6) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00dd, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00e1, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x004e, code lost:
    
        if (r0 == 54) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0052, code lost:
    
        if (r0 == 471) goto L78;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0056, code lost:
    
        if (r0 == 500) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0059, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005b, code lost:
    
        r0 = r0 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005d, code lost:
    
        if (r0 == 17) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0065, code lost:
    
        r0 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0062, code lost:
    
        if (r0 == 47483) goto L83;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0069, code lost:
    
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x006a, code lost:
    
        r8 = r11.length;
        r5 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x006e, code lost:
    
        r5 = r5 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0072, code lost:
    
        if (r5 == 14) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0076, code lost:
    
        if (r5 == 45) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x007a, code lost:
    
        if (r5 == 76) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x007c, code lost:
    
        if (r5 == 239) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x007f, code lost:
    
        if (r0 >= r8) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x00d7, code lost:
    
        r5 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x0081, code lost:
    
        r5 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0085, code lost:
    
        r5 = r11[r0];
        r6 = r3[r0];
        r8 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x008c, code lost:
    
        r8 = r8 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0092, code lost:
    
        if (r8 == 22) goto L121;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0096, code lost:
    
        if (r8 == 53) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x009a, code lost:
    
        if (r8 == 503) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x00b3, code lost:
    
        if (r5 == r6) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x00d2, code lost:
    
        r8 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00b5, code lost:
    
        r8 = 49635;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.reflect.Constructor<?> getDeclaredConstructor(java.lang.Class<?> r10, java.lang.Class<?>... r11) throws java.lang.NoSuchMethodException {
        /*
            Method dump skipped, instructions count: 239
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.getDeclaredConstructor(java.lang.Class, java.lang.Class[]):java.lang.reflect.Constructor");
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0015, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Constructor<?>> getDeclaredConstructors(java.lang.Class<?> r5) {
        /*
            r0 = 0
            java.lang.String r1 = "۠ۡۧ"
            r2 = r0
            r3 = r2
            r4 = r1
            r1 = r3
        L7:
            int r4 = org.lsposed.hiddenapibypass.library.C0004.m311(r4)
            switch(r4) {
                case 1747718: goto L23;
                case 1750720: goto L1d;
                case 1751496: goto L16;
                case 1752456: goto L15;
                default: goto Le;
            }
        Le:
            java.lang.Object r1 = org.lsposed.hiddenapibypass.library.C0004.m338(r0, r5)
            java.lang.String r4 = "ۣۥۢ"
            goto L7
        L15:
            return r3
        L16:
            java.util.List r3 = com.yan.hook.C0001.m165(r2)
            java.lang.String r4 = "ۥ۟ۢ"
            goto L7
        L1d:
            r2 = r1
            java.lang.reflect.Constructor[] r2 = (java.lang.reflect.Constructor[]) r2
            java.lang.String r4 = "ۣۤ۟"
            goto L7
        L23:
            android.util.Property r0 = m252()
            java.lang.String r4 = "ۣۢۧ"
            goto L7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.getDeclaredConstructors(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0029, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Field> getDeclaredFields(java.lang.Class<?> r5) {
        /*
            r0 = 0
            java.lang.String r1 = "ۣۡ۟"
            r2 = r0
            r3 = r2
            r4 = r1
            r1 = r3
        L7:
            int r4 = org.lsposed.hiddenapibypass.library.C0004.m311(r4)
            switch(r4) {
                case 1747904: goto L29;
                case 1748611: goto L23;
                case 1748613: goto L1c;
                case 1755559: goto L15;
                default: goto Le;
            }
        Le:
            java.lang.Object r2 = org.lsposed.hiddenapibypass.library.C0004.m338(r0, r5)
            java.lang.String r4 = "ۡ۟ۡ"
            goto L7
        L15:
            java.util.List r1 = com.yan.hook.C0001.m165(r3)
            java.lang.String r4 = "۠ۧۧ"
            goto L7
        L1c:
            android.util.Property r0 = m253()
            java.lang.String r4 = "۟ۥۥ"
            goto L7
        L23:
            r3 = r2
            java.lang.reflect.Field[] r3 = (java.lang.reflect.Field[]) r3
            java.lang.String r4 = "ۨۦۥ"
            goto L7
        L29:
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.getDeclaredFields(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:100:0x010c, code lost:
    
        r2 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0110, code lost:
    
        r2 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x00ac, code lost:
    
        if (r2 == 50) goto L145;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x00ae, code lost:
    
        if (r2 == 76) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x00b2, code lost:
    
        if (r2 == 83) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x00b5, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00b6, code lost:
    
        r2 = r12[r3];
        r7 = r0[r3];
        r8 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x00bd, code lost:
    
        r8 = r8 ^ 49807;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x00c3, code lost:
    
        if (r8 == 18) goto L155;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x00c7, code lost:
    
        if (r8 == 51) goto L156;
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x00cb, code lost:
    
        if (r8 == 84) goto L143;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x00cf, code lost:
    
        if (r8 == 241) goto L157;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x00d2, code lost:
    
        if (r2 == r7) goto L158;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x0106, code lost:
    
        r8 = 49852;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x00d4, code lost:
    
        r8 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x00d8, code lost:
    
        r0 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x00db, code lost:
    
        r0 = r0 ^ 49931;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x00e1, code lost:
    
        if (r0 == 497) goto L164;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x00e8, code lost:
    
        r0 = 50596;
     */
    /* JADX WARN: Code restructure failed: missing block: B:147:0x00e5, code lost:
    
        if (r0 == 1711) goto L105;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x00ec, code lost:
    
        r8 = r3 + (org.lsposed.hiddenapibypass.library.C0004.f13 ^ (-364));
        r2 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x00f5, code lost:
    
        r2 = r2 ^ 50706;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x00fb, code lost:
    
        if (r2 == 19) goto L169;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x0102, code lost:
    
        r2 = 50720;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x00fd, code lost:
    
        if (r2 == 50) goto L170;
     */
    /* JADX WARN: Code restructure failed: missing block: B:161:0x0100, code lost:
    
        r3 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0038, code lost:
    
        r1 = (java.lang.reflect.Method) org.lsposed.hiddenapibypass.library.C0004.m329(r10);
        r3 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m210(r1), r11);
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0049, code lost:
    
        r0 = r0 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004d, code lost:
    
        if (r0 == 17) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x0119, code lost:
    
        if (r3 != false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x011b, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x011f, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0051, code lost:
    
        if (r0 == 54) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0055, code lost:
    
        if (r0 == 471) goto L96;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0059, code lost:
    
        if (r0 == 500) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x005c, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005e, code lost:
    
        r0 = r0 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0060, code lost:
    
        if (r0 == 17) goto L126;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0068, code lost:
    
        r0 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0065, code lost:
    
        if (r0 == 47483) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x006c, code lost:
    
        r0 = org.lsposed.hiddenapibypass.library.C0003.m300(r1);
        r7 = r0.length;
        r8 = r12.length;
        r3 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0075, code lost:
    
        r3 = r3 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0079, code lost:
    
        if (r3 == 14) goto L132;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x007d, code lost:
    
        if (r3 == 45) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x007f, code lost:
    
        if (r3 == 76) goto L101;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0081, code lost:
    
        if (r3 == 239) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x0084, code lost:
    
        if (r7 == r8) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x0114, code lost:
    
        r3 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0086, code lost:
    
        r3 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x008a, code lost:
    
        r0 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x008d, code lost:
    
        r0 = r0 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0093, code lost:
    
        if (r0 == 22) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0097, code lost:
    
        if (r0 == 503) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x009a, code lost:
    
        r0 = 48922;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x009e, code lost:
    
        r3 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00a0, code lost:
    
        r9 = r12.length;
        r2 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00a4, code lost:
    
        r2 = r2 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x00a8, code lost:
    
        if (r2 == 17) goto L144;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x010a, code lost:
    
        if (r3 >= r9) goto L150;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.reflect.Method getDeclaredMethod(java.lang.Class<?> r10, java.lang.String r11, java.lang.Class<?>... r12) throws java.lang.NoSuchMethodException {
        /*
            Method dump skipped, instructions count: 301
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.getDeclaredMethod(java.lang.Class, java.lang.String, java.lang.Class[]):java.lang.reflect.Method");
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000e, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Method> getDeclaredMethods(java.lang.Class<?> r5) {
        /*
            r0 = 0
            java.lang.String r1 = "ۣۨۡ"
            r2 = r0
            r3 = r2
            r4 = r1
            r1 = r3
        L7:
            int r4 = org.lsposed.hiddenapibypass.library.C0004.m311(r4)
            switch(r4) {
                case 56479: goto L23;
                case 56507: goto L1c;
                case 1750593: goto L16;
                case 1755462: goto Lf;
                default: goto Le;
            }
        Le:
            return r3
        Lf:
            android.util.Property r1 = m254()
            java.lang.String r4 = "ۦۡ"
            goto L7
        L16:
            r0 = r2
            java.lang.reflect.Method[] r0 = (java.lang.reflect.Method[]) r0
            java.lang.String r4 = "ۥۤ"
            goto L7
        L1c:
            java.lang.Object r2 = org.lsposed.hiddenapibypass.library.C0004.m338(r1, r5)
            java.lang.String r4 = "ۣۡ۟"
            goto L7
        L23:
            java.util.List r3 = com.yan.hook.C0001.m165(r0)
            java.lang.String r4 = "ۤۨ۟"
            goto L7
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.getDeclaredMethods(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0027, code lost:
    
        r2 = (java.lang.reflect.Field) org.lsposed.hiddenapibypass.library.C0004.m329(r6);
        r3 = org.lsposed.hiddenapibypass.library.C0003.m308(org.lsposed.hiddenapibypass.library.C0004.m334(r2));
        r1 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0038, code lost:
    
        r1 = r1 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x003c, code lost:
    
        if (r1 == 17) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005e, code lost:
    
        if (r3 != false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0060, code lost:
    
        r1 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0063, code lost:
    
        r1 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0040, code lost:
    
        if (r1 == 54) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0044, code lost:
    
        if (r1 == 471) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0048, code lost:
    
        if (r1 == 500) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x004b, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m269(r0, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x004e, code lost:
    
        r1 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0050, code lost:
    
        r1 = r1 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0052, code lost:
    
        if (r1 == 17) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x005a, code lost:
    
        r1 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0057, code lost:
    
        if (r1 == 47483) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Field> getInstanceFields(java.lang.Class<?> r6) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r6 = org.lsposed.hiddenapibypass.library.C0004.m314(r6)
            java.util.Iterator r6 = org.lsposed.hiddenapibypass.library.C0003.m287(r6)
        Ld:
            boolean r1 = org.lsposed.hiddenapibypass.library.C0004.m320(r6)
            r2 = 1616(0x650, float:2.264E-42)
        L13:
            r2 = r2 ^ 1633(0x661, float:2.288E-42)
            r3 = 14
            if (r2 == r3) goto L6b
            r3 = 49
            if (r2 == r3) goto L66
            r3 = 204(0xcc, float:2.86E-43)
            if (r2 == r3) goto L27
            r3 = 239(0xef, float:3.35E-43)
            if (r2 == r3) goto L26
            goto L13
        L26:
            return r0
        L27:
            java.lang.Object r1 = org.lsposed.hiddenapibypass.library.C0004.m329(r6)
            r2 = r1
            java.lang.reflect.Field r2 = (java.lang.reflect.Field) r2
            int r1 = org.lsposed.hiddenapibypass.library.C0004.m334(r2)
            boolean r3 = org.lsposed.hiddenapibypass.library.C0003.m308(r1)
            r1 = 1740(0x6cc, float:2.438E-42)
        L38:
            r1 = r1 ^ 1757(0x6dd, float:2.462E-42)
            r4 = 17
            if (r1 == r4) goto L5e
            r5 = 54
            if (r1 == r5) goto L63
            r5 = 471(0x1d7, float:6.6E-43)
            if (r1 == r5) goto L4e
            r5 = 500(0x1f4, float:7.0E-43)
            if (r1 == r5) goto L4b
            goto L38
        L4b:
            org.lsposed.hiddenapibypass.library.C0003.m269(r0, r2)
        L4e:
            r1 = 1864(0x748, float:2.612E-42)
        L50:
            r1 = r1 ^ 1881(0x759, float:2.636E-42)
            if (r1 == r4) goto L5a
            r2 = 47483(0xb97b, float:6.6538E-41)
            if (r1 == r2) goto Ld
            goto L50
        L5a:
            r1 = 48674(0xbe22, float:6.8207E-41)
            goto L50
        L5e:
            if (r3 != 0) goto L63
            r1 = 1833(0x729, float:2.569E-42)
            goto L38
        L63:
            r1 = 1802(0x70a, float:2.525E-42)
            goto L38
        L66:
            if (r1 == 0) goto L6b
            r2 = 1709(0x6ad, float:2.395E-42)
            goto L13
        L6b:
            r2 = 1678(0x68e, float:2.351E-42)
            goto L13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.getInstanceFields(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0027, code lost:
    
        r2 = (java.lang.reflect.Field) org.lsposed.hiddenapibypass.library.C0004.m329(r6);
        r3 = org.lsposed.hiddenapibypass.library.C0003.m308(org.lsposed.hiddenapibypass.library.C0004.m334(r2));
        r1 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0038, code lost:
    
        r1 = r1 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x003c, code lost:
    
        if (r1 == 17) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x005e, code lost:
    
        if (r3 == false) goto L57;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0060, code lost:
    
        r1 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x0063, code lost:
    
        r1 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0040, code lost:
    
        if (r1 == 54) goto L50;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0044, code lost:
    
        if (r1 == 471) goto L51;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0048, code lost:
    
        if (r1 == 500) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x004b, code lost:
    
        org.lsposed.hiddenapibypass.library.C0003.m269(r0, r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x004e, code lost:
    
        r1 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0050, code lost:
    
        r1 = r1 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0052, code lost:
    
        if (r1 == 17) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x005a, code lost:
    
        r1 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0057, code lost:
    
        if (r1 == 47483) goto L39;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.util.List<java.lang.reflect.Field> getStaticFields(java.lang.Class<?> r6) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r6 = org.lsposed.hiddenapibypass.library.C0004.m314(r6)
            java.util.Iterator r6 = org.lsposed.hiddenapibypass.library.C0003.m287(r6)
        Ld:
            boolean r1 = org.lsposed.hiddenapibypass.library.C0004.m320(r6)
            r2 = 1616(0x650, float:2.264E-42)
        L13:
            r2 = r2 ^ 1633(0x661, float:2.288E-42)
            r3 = 14
            if (r2 == r3) goto L6b
            r3 = 49
            if (r2 == r3) goto L66
            r3 = 204(0xcc, float:2.86E-43)
            if (r2 == r3) goto L27
            r3 = 239(0xef, float:3.35E-43)
            if (r2 == r3) goto L26
            goto L13
        L26:
            return r0
        L27:
            java.lang.Object r1 = org.lsposed.hiddenapibypass.library.C0004.m329(r6)
            r2 = r1
            java.lang.reflect.Field r2 = (java.lang.reflect.Field) r2
            int r1 = org.lsposed.hiddenapibypass.library.C0004.m334(r2)
            boolean r3 = org.lsposed.hiddenapibypass.library.C0003.m308(r1)
            r1 = 1740(0x6cc, float:2.438E-42)
        L38:
            r1 = r1 ^ 1757(0x6dd, float:2.462E-42)
            r4 = 17
            if (r1 == r4) goto L5e
            r5 = 54
            if (r1 == r5) goto L63
            r5 = 471(0x1d7, float:6.6E-43)
            if (r1 == r5) goto L4e
            r5 = 500(0x1f4, float:7.0E-43)
            if (r1 == r5) goto L4b
            goto L38
        L4b:
            org.lsposed.hiddenapibypass.library.C0003.m269(r0, r2)
        L4e:
            r1 = 1864(0x748, float:2.612E-42)
        L50:
            r1 = r1 ^ 1881(0x759, float:2.636E-42)
            if (r1 == r4) goto L5a
            r2 = 47483(0xb97b, float:6.6538E-41)
            if (r1 == r2) goto Ld
            goto L50
        L5a:
            r1 = 48674(0xbe22, float:6.8207E-41)
            goto L50
        L5e:
            if (r3 == 0) goto L63
            r1 = 1833(0x729, float:2.569E-42)
            goto L38
        L63:
            r1 = 1802(0x70a, float:2.525E-42)
            goto L38
        L66:
            if (r1 == 0) goto L6b
            r2 = 1709(0x6ad, float:2.395E-42)
            goto L13
        L6b:
            r2 = 1678(0x68e, float:2.351E-42)
            goto L13
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.getStaticFields(java.lang.Class):java.util.List");
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0037, code lost:
    
        r1 = (java.lang.reflect.Method) org.lsposed.hiddenapibypass.library.C0004.m329(r7);
        r3 = com.yan.hook.C0002.m200(com.yan.hook.C0002.m210(r1), r9);
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:27:0x0048, code lost:
    
        r0 = r0 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x004c, code lost:
    
        if (r0 == 17) goto L76;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00ae, code lost:
    
        if (r3 != false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x00b0, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x00b3, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0050, code lost:
    
        if (r0 == 54) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x0054, code lost:
    
        if (r0 == 471) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0058, code lost:
    
        if (r0 == 500) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x005b, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005d, code lost:
    
        r0 = r0 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005f, code lost:
    
        if (r0 == 17) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0067, code lost:
    
        r0 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0064, code lost:
    
        if (r0 == 47483) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x006b, code lost:
    
        r0 = m256(org.lsposed.hiddenapibypass.library.C0003.m300(r1), r10);
        r3 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0076, code lost:
    
        r3 = r3 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x007a, code lost:
    
        if (r3 == 14) goto L92;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x007e, code lost:
    
        if (r3 == 45) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0082, code lost:
    
        if (r3 == 76) goto L90;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0084, code lost:
    
        if (r3 == 239) goto L91;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0087, code lost:
    
        if (r0 != false) goto L93;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x00aa, code lost:
    
        r3 = 48829;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0089, code lost:
    
        r3 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x008d, code lost:
    
        r0 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0090, code lost:
    
        r0 = r0 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0096, code lost:
    
        if (r0 == 22) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x009a, code lost:
    
        if (r0 == 503) goto L99;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x009d, code lost:
    
        r0 = 48922;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x00a1, code lost:
    
        com.yan.hook.C0001.m171(r1, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x00a9, code lost:
    
        return org.lsposed.hiddenapibypass.library.C0003.m293(r1, r8, r10);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object invoke(java.lang.Class<?> r7, java.lang.Object r8, java.lang.String r9, java.lang.Object... r10) throws java.lang.NoSuchMethodException, java.lang.reflect.InvocationTargetException, java.lang.IllegalAccessException {
        /*
            java.util.List r7 = com.yan.hook.C0001.m180(r7)
            java.util.Iterator r7 = org.lsposed.hiddenapibypass.library.C0003.m287(r7)
        L8:
            boolean r0 = org.lsposed.hiddenapibypass.library.C0004.m320(r7)
            r1 = 1616(0x650, float:2.264E-42)
        Le:
            r1 = r1 ^ 1633(0x661, float:2.288E-42)
            r2 = 14
            if (r1 == r2) goto Lbc
            r3 = 49
            if (r1 == r3) goto Lb6
            r3 = 204(0xcc, float:2.86E-43)
            r4 = 239(0xef, float:3.35E-43)
            if (r1 == r3) goto L37
            if (r1 == r4) goto L21
            goto Le
        L21:
            java.lang.NoSuchMethodException r7 = new java.lang.NoSuchMethodException
            short[] r8 = m257()
            int r9 = com.yan.hook.C0001.f5
            r9 = r9 ^ (-602(0xfffffffffffffda6, float:NaN))
            r10 = 2899(0xb53, float:4.062E-42)
            r0 = 114(0x72, float:1.6E-43)
            java.lang.String r8 = org.lsposed.hiddenapibypass.library.C0003.m285(r8, r0, r9, r10)
            r7.<init>(r8)
            throw r7
        L37:
            java.lang.Object r0 = org.lsposed.hiddenapibypass.library.C0004.m329(r7)
            r1 = r0
            java.lang.reflect.Method r1 = (java.lang.reflect.Method) r1
            java.lang.String r0 = com.yan.hook.C0002.m210(r1)
            boolean r3 = com.yan.hook.C0002.m200(r0, r9)
            r0 = 1740(0x6cc, float:2.438E-42)
        L48:
            r0 = r0 ^ 1757(0x6dd, float:2.462E-42)
            r5 = 17
            if (r0 == r5) goto Lae
            r6 = 54
            if (r0 == r6) goto Lb3
            r6 = 471(0x1d7, float:6.6E-43)
            if (r0 == r6) goto L6b
            r6 = 500(0x1f4, float:7.0E-43)
            if (r0 == r6) goto L5b
            goto L48
        L5b:
            r0 = 1864(0x748, float:2.612E-42)
        L5d:
            r0 = r0 ^ 1881(0x759, float:2.636E-42)
            if (r0 == r5) goto L67
            r1 = 47483(0xb97b, float:6.6538E-41)
            if (r0 == r1) goto L8
            goto L5d
        L67:
            r0 = 48674(0xbe22, float:6.8207E-41)
            goto L5d
        L6b:
            java.lang.Class[] r0 = org.lsposed.hiddenapibypass.library.C0003.m300(r1)
            boolean r0 = m256(r0, r10)
            r3 = 48767(0xbe7f, float:6.8337E-41)
        L76:
            r5 = 48784(0xbe90, float:6.8361E-41)
            r3 = r3 ^ r5
            if (r3 == r2) goto Laa
            r5 = 45
            if (r3 == r5) goto La1
            r5 = 76
            if (r3 == r5) goto L8d
            if (r3 == r4) goto L87
            goto L76
        L87:
            if (r0 != 0) goto Laa
            r3 = 48860(0xbedc, float:6.8467E-41)
            goto L76
        L8d:
            r0 = 48891(0xbefb, float:6.8511E-41)
        L90:
            r1 = 48908(0xbf0c, float:6.8535E-41)
            r0 = r0 ^ r1
            r1 = 22
            if (r0 == r1) goto L8
            r1 = 503(0x1f7, float:7.05E-43)
            if (r0 == r1) goto L9d
            goto L90
        L9d:
            r0 = 48922(0xbf1a, float:6.8554E-41)
            goto L90
        La1:
            r7 = 1
            com.yan.hook.C0001.m171(r1, r7)
            java.lang.Object r7 = org.lsposed.hiddenapibypass.library.C0003.m293(r1, r8, r10)
            return r7
        Laa:
            r3 = 48829(0xbebd, float:6.8424E-41)
            goto L76
        Lae:
            if (r3 != 0) goto Lb3
            r0 = 1833(0x729, float:2.569E-42)
            goto L48
        Lb3:
            r0 = 1802(0x70a, float:2.525E-42)
            goto L48
        Lb6:
            if (r0 == 0) goto Lbc
            r1 = 1709(0x6ad, float:2.395E-42)
            goto Le
        Lbc:
            r1 = 1678(0x68e, float:2.351E-42)
            goto Le
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.invoke(java.lang.Class, java.lang.Object, java.lang.String, java.lang.Object[]):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x0037, code lost:
    
        r1 = (java.lang.reflect.Constructor) org.lsposed.hiddenapibypass.library.C0004.m329(r5);
        r2 = m256(com.yan.hook.C0002.m228(r1), r6);
        r0 = 1740;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0048, code lost:
    
        r0 = r0 ^ 1757;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x004c, code lost:
    
        if (r0 == 17) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x0074, code lost:
    
        if (r2 != false) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0076, code lost:
    
        r0 = 1833;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0079, code lost:
    
        r0 = 1802;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0050, code lost:
    
        if (r0 == 54) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0054, code lost:
    
        if (r0 == 471) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0058, code lost:
    
        if (r0 == 500) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x005b, code lost:
    
        r0 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x005d, code lost:
    
        r0 = r0 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x005f, code lost:
    
        if (r0 == 17) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0067, code lost:
    
        r0 = 48674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0064, code lost:
    
        if (r0 == 47483) goto L42;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x006b, code lost:
    
        com.yan.hook.C0002.m220(r1, true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0073, code lost:
    
        return org.lsposed.hiddenapibypass.library.C0004.m333(r1, r6);
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct add '--show-bad-code' argument
    */
    public static java.lang.Object newInstance(java.lang.Class<?> r5, java.lang.Object... r6) throws java.lang.NoSuchMethodException, java.lang.IllegalAccessException, java.lang.reflect.InvocationTargetException, java.lang.InstantiationException {
        /*
            java.util.List r5 = com.yan.hook.C0002.m219(r5)
            java.util.Iterator r5 = org.lsposed.hiddenapibypass.library.C0003.m287(r5)
        L8:
            boolean r0 = org.lsposed.hiddenapibypass.library.C0004.m320(r5)
            r1 = 1616(0x650, float:2.264E-42)
        Le:
            r1 = r1 ^ 1633(0x661, float:2.288E-42)
            r2 = 14
            if (r1 == r2) goto L81
            r2 = 49
            if (r1 == r2) goto L7c
            r2 = 204(0xcc, float:2.86E-43)
            if (r1 == r2) goto L37
            r2 = 239(0xef, float:3.35E-43)
            if (r1 == r2) goto L21
            goto Le
        L21:
            java.lang.NoSuchMethodException r5 = new java.lang.NoSuchMethodException
            short[] r6 = m257()
            int r0 = com.yan.hook.C0001.f5
            r0 = r0 ^ (-611(0xfffffffffffffd9d, float:NaN))
            r1 = 3138(0xc42, float:4.397E-42)
            r2 = 141(0x8d, float:1.98E-43)
            java.lang.String r6 = com.yan.hook.C0001.m169(r6, r2, r0, r1)
            r5.<init>(r6)
            throw r5
        L37:
            java.lang.Object r0 = org.lsposed.hiddenapibypass.library.C0004.m329(r5)
            r1 = r0
            java.lang.reflect.Constructor r1 = (java.lang.reflect.Constructor) r1
            java.lang.Class[] r0 = com.yan.hook.C0002.m228(r1)
            boolean r2 = m256(r0, r6)
            r0 = 1740(0x6cc, float:2.438E-42)
        L48:
            r0 = r0 ^ 1757(0x6dd, float:2.462E-42)
            r3 = 17
            if (r0 == r3) goto L74
            r4 = 54
            if (r0 == r4) goto L79
            r4 = 471(0x1d7, float:6.6E-43)
            if (r0 == r4) goto L6b
            r4 = 500(0x1f4, float:7.0E-43)
            if (r0 == r4) goto L5b
            goto L48
        L5b:
            r0 = 1864(0x748, float:2.612E-42)
        L5d:
            r0 = r0 ^ 1881(0x759, float:2.636E-42)
            if (r0 == r3) goto L67
            r1 = 47483(0xb97b, float:6.6538E-41)
            if (r0 == r1) goto L8
            goto L5d
        L67:
            r0 = 48674(0xbe22, float:6.8207E-41)
            goto L5d
        L6b:
            r5 = 1
            com.yan.hook.C0002.m220(r1, r5)
            java.lang.Object r5 = org.lsposed.hiddenapibypass.library.C0004.m333(r1, r6)
            return r5
        L74:
            if (r2 != 0) goto L79
            r0 = 1833(0x729, float:2.569E-42)
            goto L48
        L79:
            r0 = 1802(0x70a, float:2.525E-42)
            goto L48
        L7c:
            if (r0 == 0) goto L81
            r1 = 1709(0x6ad, float:2.395E-42)
            goto Le
        L81:
            r1 = 1678(0x68e, float:2.351E-42)
            goto Le
        */
        throw new UnsupportedOperationException("Method not decompiled: org.lsposed.hiddenapibypass.LSPass.newInstance(java.lang.Class, java.lang.Object[]):java.lang.Object");
    }

    public static boolean setHiddenApiExemptions(String... strArr) {
        try {
            C0003.m288(VMRuntime.class, C0003.m288(VMRuntime.class, null, C0001.m169(m257(), 195, C0000.f4 ^ 93, 771), new Object[0]), C0003.m285(m257(), 173, C0004.f13 ^ (-381), 1488), new Object[]{strArr});
            return true;
        } catch (ReflectiveOperationException unused) {
            C0001.m169(m257(), 205, C0000.f4 ^ 81, 2654);
            return false;
        }
    }

    /* renamed from: ۣ۟ۡ۟۟, reason: not valid java name and contains not printable characters */
    public static Property m252() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return constructors;
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

    /* renamed from: ۟ۢ۟ۧۥ, reason: not valid java name and contains not printable characters */
    public static Property m253() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return fields;
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

    /* renamed from: ۣ۟ۧۢۦ, reason: not valid java name and contains not printable characters */
    public static Property m254() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return methods;
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

    /* renamed from: ۟ۨۥۢ, reason: not valid java name and contains not printable characters */
    public static Set m255() {
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
                } else if (m188 < 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۥۦ۟ۡ, reason: contains not printable characters */
    public static boolean m256(Object obj, Object obj2) {
        int m274 = C0003.m274();
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
                } else if (m274 > 0) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }

    /* renamed from: ۨ۠ۡ, reason: not valid java name and contains not printable characters */
    public static short[] m257() {
        int m274 = C0003.m274();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return f10short;
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
