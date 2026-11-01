package org.lsposed.hiddenapibypass;

import android.util.Property;
import com.yan.hook.C0000;
import com.yan.hook.C0001;
import com.yan.hook.C0002;
import dalvik.system.VMRuntime;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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
    */
    public static boolean addHiddenApiExemptions(String... strArr) {
        Set set = null;
        List list = null;
        String str = "ۢۡۧ";
        boolean z = false;
        int i = 0;
        while (true) {
            switch (C0004.m311(str)) {
                case 56389:
                    z = C0001.m177(strArr);
                    str = "ۣۣۥ";
                    break;
                case 56482:
                    list = C0001.m165(strArr);
                    str = "ۣۧۢ";
                    break;
                case 1747901:
                    i = C0004.m327(set);
                    str = "ۥۤۦ";
                    break;
                case 1748708:
                    set = m255();
                    str = "ۣۧۥ";
                    break;
                case 1749669:
                    set = m255();
                    str = "۠ۧۤ";
                    break;
                case 1750661:
                    break;
                case 1750782:
                    z = C0001.m172(set, list);
                    str = "ۢۢۥ";
                    break;
                case 1750785:
                    C0003.m286(set, strArr);
                    str = "ۢۧ";
                    break;
                case 1752615:
                    strArr = new String[i];
                    str = "ۡۢۥ";
                    break;
                default:
                    set = m255();
                    str = "ۥۧ";
                    break;
            }
        }
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
    */
    public static Constructor<?> getDeclaredConstructor(Class<?> cls, Class<?>... clsArr) throws NoSuchMethodException {
        Iterator m287 = C0003.m287(C0002.m219(cls));
        while (true) {
            boolean m320 = C0004.m320(m287);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            break;
                        }
                        if (i == 239) {
                            throw new NoSuchMethodException(C0002.m191(m257(), 55, C0003.f12 ^ 653, 3152));
                        }
                    } else if (m320) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0015, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<Constructor<?>> getDeclaredConstructors(Class<?> cls) {
        Property property = null;
        Constructor[] constructorArr = null;
        List<Constructor<?>> list = null;
        String str = "۠ۡۧ";
        Object obj = null;
        while (true) {
            switch (C0004.m311(str)) {
                case 1747718:
                    property = m252();
                    str = "ۣۢۧ";
                    break;
                case 1750720:
                    constructorArr = (Constructor[]) obj;
                    str = "ۣۤ۟";
                    break;
                case 1751496:
                    list = C0001.m165(constructorArr);
                    str = "ۥ۟ۢ";
                    break;
                case 1752456:
                    break;
                default:
                    obj = C0004.m338(property, cls);
                    str = "ۣۥۢ";
                    break;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0029, code lost:
    
        return r1;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<Field> getDeclaredFields(Class<?> cls) {
        Property property = null;
        Object obj = null;
        Field[] fieldArr = null;
        String str = "ۣۡ۟";
        List<Field> list = null;
        while (true) {
            switch (C0004.m311(str)) {
                case 1747904:
                    break;
                case 1748611:
                    fieldArr = (Field[]) obj;
                    str = "ۨۦۥ";
                    break;
                case 1748613:
                    property = m253();
                    str = "۟ۥۥ";
                    break;
                case 1755559:
                    list = C0001.m165(fieldArr);
                    str = "۠ۧۧ";
                    break;
                default:
                    obj = C0004.m338(property, cls);
                    str = "ۡ۟ۡ";
                    break;
            }
        }
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
    */
    public static Method getDeclaredMethod(Class<?> cls, String str, Class<?>... clsArr) throws NoSuchMethodException {
        Iterator m287 = C0003.m287(C0001.m180(cls));
        while (true) {
            boolean m320 = C0004.m320(m287);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            break;
                        }
                        if (i == 239) {
                            throw new NoSuchMethodException(C0002.m191(m257(), 87, C0000.f4 ^ 76, 809));
                        }
                    } else if (m320) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:5:0x000e, code lost:
    
        return r3;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static List<Method> getDeclaredMethods(Class<?> cls) {
        Method[] methodArr = null;
        Object obj = null;
        List<Method> list = null;
        String str = "ۣۨۡ";
        Property property = null;
        while (true) {
            switch (C0004.m311(str)) {
                case 56479:
                    list = C0001.m165(methodArr);
                    str = "ۤۨ۟";
                    break;
                case 56507:
                    obj = C0004.m338(property, cls);
                    str = "ۣۡ۟";
                    break;
                case 1750593:
                    methodArr = (Method[]) obj;
                    str = "ۥۤ";
                    break;
                case 1755462:
                    property = m254();
                    str = "ۦۡ";
                    break;
            }
        }
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
    */
    public static List<Field> getInstanceFields(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Iterator m287 = C0003.m287(C0004.m314(cls));
        while (true) {
            boolean m320 = C0004.m320(m287);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            break;
                        }
                        if (i == 239) {
                            return arrayList;
                        }
                    } else if (m320) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
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
    */
    public static List<Field> getStaticFields(Class<?> cls) {
        ArrayList arrayList = new ArrayList();
        Iterator m287 = C0003.m287(C0004.m314(cls));
        while (true) {
            boolean m320 = C0004.m320(m287);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            break;
                        }
                        if (i == 239) {
                            return arrayList;
                        }
                    } else if (m320) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
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
    */
    public static Object invoke(Class<?> cls, Object obj, String str, Object... objArr) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Iterator m287 = C0003.m287(C0001.m180(cls));
        while (true) {
            boolean m320 = C0004.m320(m287);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            break;
                        }
                        if (i == 239) {
                            throw new NoSuchMethodException(C0003.m285(m257(), 114, C0001.f5 ^ (-602), 2899));
                        }
                    } else if (m320) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
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
    */
    public static Object newInstance(Class<?> cls, Object... objArr) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Iterator m287 = C0003.m287(C0002.m219(cls));
        while (true) {
            boolean m320 = C0004.m320(m287);
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            break;
                        }
                        if (i == 239) {
                            throw new NoSuchMethodException(C0001.m169(m257(), 141, C0001.f5 ^ (-611), 3138));
                        }
                    } else if (m320) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
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
