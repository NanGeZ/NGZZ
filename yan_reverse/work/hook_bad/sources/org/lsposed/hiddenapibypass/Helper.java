package org.lsposed.hiddenapibypass;

import com.yan.hook.C0000;
import com.yan.hook.C0001;
import com.yan.hook.C0002;
import java.lang.invoke.MethodType;
import java.util.HashSet;
import java.util.Set;
import org.lsposed.hiddenapibypass.library.C0003;
import org.lsposed.hiddenapibypass.library.C0004;

/* loaded from: /workspace/yan_reverse/work/assets/hook.dex */
public class Helper {
    static final Set<String> signaturePrefixes = new HashSet();

    public static class AccessibleObject {
        private boolean override;

        public AccessibleObject() {
            int m143 = C0001.m143();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Float.decode(C0002.m231("s7QpMjV8lXfILd")));
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
    }

    public static final class Class {
        private transient int accessFlags;
        private transient int classFlags;
        private transient ClassLoader classLoader;
        private transient int classSize;
        private transient int clinitThreadId;
        private transient java.lang.Class<?> componentType;
        private transient short copiedMethodsOffset;
        private transient Object dexCache;
        private transient int dexClassDefIndex;
        private volatile transient int dexTypeIndex;
        private transient Object extData;
        private transient long iFields;
        private transient Object[] ifTable;
        private transient long methods;
        private transient String name;
        private transient int numReferenceInstanceFields;
        private transient int numReferenceStaticFields;
        private transient int objectSize;
        private transient int objectSizeAllocFastPath;
        private transient int primitiveType;
        private transient int referenceInstanceOffsets;
        private transient long sFields;
        private transient int status;
        private transient java.lang.Class<?> superClass;
        private transient short virtualMethodsOffset;
        private transient Object vtable;

        public Class() {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Integer.parseInt(C0004.m346("7HnDw6A2mgrc15O0FCfnrCTo")));
                            return;
                        } else if (i == 239) {
                            return;
                        }
                    } else if (m316 >= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }
    }

    public static final class Executable extends AccessibleObject {
        private int accessFlags;
        private long artMethod;
        private Class declaringClass;
        private Class declaringClassOfOverriddenMethod;
        private Object[] parameters;

        public Executable() {
            int m143 = C0001.m143();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Long.decode(C0001.m158("yfGvAOgGi9")));
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
    }

    public static class InvokeStub {

        /* renamed from: short, reason: not valid java name */
        private static final short[] f8short = {2935, 2896, 2904, 2909, 2900, 2901, 2833, 2885, 2910, 2833, 2911, 2900, 2886, 2833, 2896, 2833, 2904, 2911, 2882, 2885, 2896, 2911, 2898, 2900, 1207, 1168, 1176, 1181, 1172, 1173, 1233, 1157, 1182, 1233, 1176, 1183, 1159, 1182, 1178, 1172, 1233, 1157, 1177, 1172, 1233, 1180, 1172, 1157, 1177, 1182, 1173};

        /* JADX WARN: Code restructure failed: missing block: B:5:0x0018, code lost:
        
            throw r2;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private InvokeStub(Object... objArr) {
            short[] sArr = null;
            IllegalStateException illegalStateException = null;
            String str = "ۣۥۦ";
            int i = 0;
            int i2 = 0;
            String str2 = null;
            while (true) {
                switch (C0004.m311(str)) {
                    case 1746785:
                        str2 = C0003.m285(sArr, 0, i, 2865);
                        str = "ۦ۟۟";
                        break;
                    case 1749793:
                        i2 = C0003.f12;
                        str = "ۣۧۢ";
                        break;
                    case 1750724:
                        sArr = m237();
                        str = "ۢۦۥ";
                        break;
                    case 1753414:
                        illegalStateException = new IllegalStateException(str2);
                        str = "ۨۨ۟";
                        break;
                    case 1755615:
                        break;
                    default:
                        i = i2 ^ 693;
                        str = "۟ۢۤ";
                        break;
                }
            }
        }

        /* JADX WARN: Code restructure failed: missing block: B:9:0x001a, code lost:
        
            throw r1;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        private static Object invoke(Object... objArr) {
            short[] sArr = null;
            String str = null;
            String str2 = "ۣۢۢ";
            int i = 0;
            int i2 = 0;
            IllegalStateException illegalStateException = null;
            while (true) {
                switch (C0004.m311(str2)) {
                    case 1746693:
                        str = C0003.m285(sArr, 24, i, 1265);
                        str2 = "ۡۦۥ";
                        break;
                    case 1748832:
                        illegalStateException = new IllegalStateException(str);
                        str2 = "ۤۦۧ";
                        break;
                    case 1750627:
                        sArr = m237();
                        str2 = "ۤ۟ۧ";
                        break;
                    case 1751717:
                        break;
                    case 1754437:
                        i = i2 ^ 76;
                        str2 = "۟۟ۥ";
                        break;
                    default:
                        i2 = C0000.f4;
                        str2 = "ۧۡ۟";
                        break;
                }
            }
        }

        /* renamed from: ۟ۢۢۤ, reason: not valid java name and contains not printable characters */
        public static short[] m237() {
            int m138 = C0000.m138();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            return f8short;
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
    }

    public static class MethodHandle {
        private MethodHandle cachedSpreadInvoker;
        private MethodType nominalType;
        private final MethodType type = null;
        protected final int handleKind = 0;
        protected final long artFieldOrMethod = 0;

        public MethodHandle() {
            int m274 = C0003.m274();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Double.parseDouble(C0004.m346("ARCas8SHHGNYl")));
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
    }

    public static class NeverCall {
        private static int s;
        private static int t;
        private int i;
        private int j;

        public NeverCall() {
            int m274 = C0003.m274();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Long.decode(C0004.m346("QHbs653T8qRV7f5uQv72mEDwoH90")));
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

        private static void a() {
            int m316 = C0004.m316();
            int i = 1616;
            while (true) {
                i ^= 1633;
                if (i != 14) {
                    if (i != 49) {
                        if (i == 204) {
                            System.out.println(Float.decode(C0002.m231("EFqJ5z7rKog")));
                            return;
                        } else if (i == 239) {
                            return;
                        }
                    } else if (m316 >= 0) {
                        i = 1709;
                    }
                }
                i = 1678;
            }
        }

        private static void b() {
        }
    }

    public Helper() {
        int m188 = C0002.m188();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        System.out.println(Integer.parseInt(C0004.m346("0PrJe8G3pkS")));
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

    /* JADX WARN: Code restructure failed: missing block: B:100:0x00b0, code lost:
    
        if (r4 == 50) goto L344;
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x02f3, code lost:
    
        r4 = 49728;
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x02f6, code lost:
    
        r14 = '5';
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x00b2, code lost:
    
        r6 = 241;
        r5 = 18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x00b8, code lost:
    
        if (r4 == 76) goto L345;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x00ba, code lost:
    
        if (r4 == 83) goto L346;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x00e2, code lost:
    
        r3 = r21[r2];
        r10 = org.lsposed.hiddenapibypass.library.C0003.m304();
        r4 = 49914;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x00eb, code lost:
    
        r4 = r4 ^ 49931;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x00f2, code lost:
    
        if (r4 == 497) goto L352;
     */
    /* JADX WARN: Code restructure failed: missing block: B:118:0x00f6, code lost:
    
        if (r4 == 1711) goto L353;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x02c8, code lost:
    
        r4 = 50627;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x02cb, code lost:
    
        r5 = 18;
        r14 = '5';
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x00fc, code lost:
    
        if (r4 == 1736) goto L354;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x00fe, code lost:
    
        if (r4 == 1769) goto L355;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x0105, code lost:
    
        r3 = r22[r2] instanceof java.lang.Character;
        r4 = 50689;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x010c, code lost:
    
        r4 = r4 ^ 50706;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0110, code lost:
    
        if (r4 == 19) goto L360;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x011a, code lost:
    
        if (r3 != false) goto L366;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x011c, code lost:
    
        r4 = 50782;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0120, code lost:
    
        r4 = 50751;
     */
    /* JADX WARN: Code restructure failed: missing block: B:145:0x0112, code lost:
    
        if (r4 == 45) goto L361;
     */
    /* JADX WARN: Code restructure failed: missing block: B:146:0x0114, code lost:
    
        if (r4 == 50) goto L362;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x0116, code lost:
    
        if (r4 == 76) goto L305;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x0119, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x0124, code lost:
    
        r4 = r21[r2];
        r3 = org.lsposed.hiddenapibypass.library.C0003.m296();
        r10 = 50813;
     */
    /* JADX WARN: Code restructure failed: missing block: B:155:0x012d, code lost:
    
        r10 = r10 ^ 50830;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x0132, code lost:
    
        if (r10 == r5) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0136, code lost:
    
        if (r10 == r14) goto L371;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x013a, code lost:
    
        if (r10 == 243) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x013e, code lost:
    
        if (r10 == 4075) goto L372;
     */
    /* JADX WARN: Code restructure failed: missing block: B:164:0x0145, code lost:
    
        r10 = r22[r2] instanceof java.lang.Boolean;
        r3 = 51588;
     */
    /* JADX WARN: Code restructure failed: missing block: B:165:0x014c, code lost:
    
        r3 = r3 ^ 51605;
     */
    /* JADX WARN: Code restructure failed: missing block: B:166:0x0150, code lost:
    
        if (r3 == 17) goto L376;
     */
    /* JADX WARN: Code restructure failed: missing block: B:168:0x015e, code lost:
    
        if (r10 != false) goto L382;
     */
    /* JADX WARN: Code restructure failed: missing block: B:170:0x0160, code lost:
    
        r3 = 51681;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x0164, code lost:
    
        r3 = 51650;
     */
    /* JADX WARN: Code restructure failed: missing block: B:178:0x0154, code lost:
    
        if (r3 == 54) goto L377;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x0158, code lost:
    
        if (r3 == 87) goto L378;
     */
    /* JADX WARN: Code restructure failed: missing block: B:183:0x015a, code lost:
    
        if (r3 == 116) goto L307;
     */
    /* JADX WARN: Code restructure failed: missing block: B:185:0x015d, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x016e, code lost:
    
        r10 = r21[r2];
        r14 = org.lsposed.hiddenapibypass.library.C0003.m305();
        r3 = 51712;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x0177, code lost:
    
        r3 = r3 ^ 51729;
     */
    /* JADX WARN: Code restructure failed: missing block: B:190:0x017d, code lost:
    
        if (r3 == 14) goto L389;
     */
    /* JADX WARN: Code restructure failed: missing block: B:191:0x017f, code lost:
    
        if (r3 == 17) goto L390;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0298, code lost:
    
        if (r10 != r14) goto L391;
     */
    /* JADX WARN: Code restructure failed: missing block: B:196:0x02a5, code lost:
    
        r3 = 51774;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x029a, code lost:
    
        r3 = 51805;
     */
    /* JADX WARN: Code restructure failed: missing block: B:204:0x0183, code lost:
    
        if (r3 == 47) goto L387;
     */
    /* JADX WARN: Code restructure failed: missing block: B:205:0x0185, code lost:
    
        if (r3 == 76) goto L388;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x0188, code lost:
    
        r3 = r22[r2] instanceof java.lang.Double;
        r4 = 51836;
     */
    /* JADX WARN: Code restructure failed: missing block: B:208:0x018f, code lost:
    
        r4 = r4 ^ 51853;
     */
    /* JADX WARN: Code restructure failed: missing block: B:209:0x0195, code lost:
    
        if (r4 == 241) goto L398;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x01a5, code lost:
    
        if (r3 != false) goto L403;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x01a7, code lost:
    
        r4 = 52580;
     */
    /* JADX WARN: Code restructure failed: missing block: B:217:0x01ab, code lost:
    
        r4 = 52549;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x0199, code lost:
    
        if (r4 == 1963) goto L399;
     */
    /* JADX WARN: Code restructure failed: missing block: B:225:0x019d, code lost:
    
        if (r4 == 1992) goto L400;
     */
    /* JADX WARN: Code restructure failed: missing block: B:227:0x01a1, code lost:
    
        if (r4 == 2025) goto L308;
     */
    /* JADX WARN: Code restructure failed: missing block: B:229:0x01a4, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:232:0x01af, code lost:
    
        r3 = r21[r2];
        r4 = com.yan.hook.C0002.m233();
        r10 = 52611;
     */
    /* JADX WARN: Code restructure failed: missing block: B:233:0x01b8, code lost:
    
        r10 = r10 ^ 52628;
     */
    /* JADX WARN: Code restructure failed: missing block: B:234:0x01be, code lost:
    
        if (r10 == 23) goto L412;
     */
    /* JADX WARN: Code restructure failed: missing block: B:237:0x0286, code lost:
    
        if (r3 != r4) goto L415;
     */
    /* JADX WARN: Code restructure failed: missing block: B:239:0x0288, code lost:
    
        r10 = 52704;
     */
    /* JADX WARN: Code restructure failed: missing block: B:243:0x028d, code lost:
    
        r10 = 52673;
     */
    /* JADX WARN: Code restructure failed: missing block: B:247:0x01c2, code lost:
    
        if (r10 == 54) goto L409;
     */
    /* JADX WARN: Code restructure failed: missing block: B:252:0x01c6, code lost:
    
        if (r10 == 85) goto L410;
     */
    /* JADX WARN: Code restructure failed: missing block: B:253:0x01c8, code lost:
    
        if (r10 == 116) goto L411;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x01cb, code lost:
    
        r3 = r22[r2] instanceof java.lang.Float;
        r4 = 52735;
     */
    /* JADX WARN: Code restructure failed: missing block: B:256:0x01d2, code lost:
    
        r4 = r4 ^ 52752;
     */
    /* JADX WARN: Code restructure failed: missing block: B:257:0x01d8, code lost:
    
        if (r4 == 14) goto L421;
     */
    /* JADX WARN: Code restructure failed: missing block: B:258:0x01da, code lost:
    
        if (r4 == 45) goto L422;
     */
    /* JADX WARN: Code restructure failed: missing block: B:260:0x01de, code lost:
    
        if (r4 == 1007) goto L423;
     */
    /* JADX WARN: Code restructure failed: missing block: B:262:0x01e6, code lost:
    
        if (r3 != false) goto L424;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x01ec, code lost:
    
        r4 = 52797;
     */
    /* JADX WARN: Code restructure failed: missing block: B:268:0x01e8, code lost:
    
        r4 = 53479;
     */
    /* JADX WARN: Code restructure failed: missing block: B:272:0x01e2, code lost:
    
        if (r4 == 7927) goto L309;
     */
    /* JADX WARN: Code restructure failed: missing block: B:274:0x01e5, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:277:0x01f0, code lost:
    
        r10 = r21[r2];
        r3 = org.lsposed.hiddenapibypass.library.C0004.m343();
        r4 = 53510;
     */
    /* JADX WARN: Code restructure failed: missing block: B:278:0x01f9, code lost:
    
        r4 = r4 ^ 53527;
     */
    /* JADX WARN: Code restructure failed: missing block: B:279:0x01ff, code lost:
    
        if (r4 == 17) goto L434;
     */
    /* JADX WARN: Code restructure failed: missing block: B:281:0x0275, code lost:
    
        if (r10 != r3) goto L437;
     */
    /* JADX WARN: Code restructure failed: missing block: B:283:0x0277, code lost:
    
        r4 = 53603;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x027c, code lost:
    
        r4 = 53572;
     */
    /* JADX WARN: Code restructure failed: missing block: B:290:0x0201, code lost:
    
        if (r4 == 50) goto L431;
     */
    /* JADX WARN: Code restructure failed: missing block: B:293:0x0203, code lost:
    
        if (r4 == 83) goto L432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:294:0x0205, code lost:
    
        if (r4 == 116) goto L433;
     */
    /* JADX WARN: Code restructure failed: missing block: B:296:0x0208, code lost:
    
        r3 = r22[r2] instanceof java.lang.Long;
        r4 = 53634;
     */
    /* JADX WARN: Code restructure failed: missing block: B:297:0x020f, code lost:
    
        r4 = r4 ^ 53651;
     */
    /* JADX WARN: Code restructure failed: missing block: B:298:0x0213, code lost:
    
        if (r4 == 17) goto L443;
     */
    /* JADX WARN: Code restructure failed: missing block: B:301:0x0224, code lost:
    
        if (r3 != false) goto L449;
     */
    /* JADX WARN: Code restructure failed: missing block: B:303:0x0226, code lost:
    
        r4 = 53727;
     */
    /* JADX WARN: Code restructure failed: missing block: B:307:0x022a, code lost:
    
        r4 = 53696;
     */
    /* JADX WARN: Code restructure failed: missing block: B:310:0x0215, code lost:
    
        if (r4 == 50) goto L444;
     */
    /* JADX WARN: Code restructure failed: missing block: B:314:0x0217, code lost:
    
        if (r4 == 76) goto L300;
     */
    /* JADX WARN: Code restructure failed: missing block: B:316:0x021b, code lost:
    
        if (r4 == 83) goto L445;
     */
    /* JADX WARN: Code restructure failed: missing block: B:318:0x022e, code lost:
    
        r4 = r21[r2];
        r3 = org.lsposed.hiddenapibypass.library.C0004.m317();
        r10 = 53758;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x0237, code lost:
    
        r10 = r10 ^ 53775;
     */
    /* JADX WARN: Code restructure failed: missing block: B:320:0x023d, code lost:
    
        if (r10 == 1009) goto L457;
     */
    /* JADX WARN: Code restructure failed: missing block: B:322:0x026b, code lost:
    
        if (r4 != r3) goto L460;
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x026d, code lost:
    
        r10 = 54502;
     */
    /* JADX WARN: Code restructure failed: missing block: B:328:0x0271, code lost:
    
        r10 = 54471;
     */
    /* JADX WARN: Code restructure failed: missing block: B:332:0x0241, code lost:
    
        if (r10 == 1703) goto L454;
     */
    /* JADX WARN: Code restructure failed: missing block: B:335:0x0243, code lost:
    
        if (r10 == 1736) goto L455;
     */
    /* JADX WARN: Code restructure failed: missing block: B:336:0x0245, code lost:
    
        if (r10 == 1769) goto L456;
     */
    /* JADX WARN: Code restructure failed: missing block: B:338:0x0248, code lost:
    
        r3 = r22[r2] instanceof java.lang.Short;
        r4 = 54533;
     */
    /* JADX WARN: Code restructure failed: missing block: B:339:0x024f, code lost:
    
        r4 = r4 ^ 54550;
     */
    /* JADX WARN: Code restructure failed: missing block: B:340:0x0253, code lost:
    
        if (r4 == 19) goto L466;
     */
    /* JADX WARN: Code restructure failed: missing block: B:342:0x025d, code lost:
    
        if (r3 != false) goto L472;
     */
    /* JADX WARN: Code restructure failed: missing block: B:344:0x025f, code lost:
    
        r4 = 54626;
     */
    /* JADX WARN: Code restructure failed: missing block: B:348:0x0263, code lost:
    
        r4 = 54595;
     */
    /* JADX WARN: Code restructure failed: missing block: B:351:0x0255, code lost:
    
        if (r4 == 50) goto L467;
     */
    /* JADX WARN: Code restructure failed: missing block: B:354:0x0257, code lost:
    
        if (r4 == 85) goto L468;
     */
    /* JADX WARN: Code restructure failed: missing block: B:355:0x0259, code lost:
    
        if (r4 == 116) goto L302;
     */
    /* JADX WARN: Code restructure failed: missing block: B:357:0x025c, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:361:0x034f, code lost:
    
        r3 = (org.lsposed.hiddenapibypass.library.C0003.f12 ^ 684) + r2;
        r2 = 55556;
     */
    /* JADX WARN: Code restructure failed: missing block: B:362:0x0357, code lost:
    
        r2 = r2 ^ 55573;
     */
    /* JADX WARN: Code restructure failed: missing block: B:363:0x035d, code lost:
    
        if (r2 == 17) goto L477;
     */
    /* JADX WARN: Code restructure failed: missing block: B:365:0x0369, code lost:
    
        r2 = 55587;
     */
    /* JADX WARN: Code restructure failed: missing block: B:369:0x035f, code lost:
    
        if (r2 == 54) goto L478;
     */
    /* JADX WARN: Code restructure failed: missing block: B:377:0x021e, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:388:0x0140, code lost:
    
        r5 = 18;
        r14 = '5';
     */
    /* JADX WARN: Code restructure failed: missing block: B:389:0x0168, code lost:
    
        if (r4 != r3) goto L223;
     */
    /* JADX WARN: Code restructure failed: missing block: B:390:0x016a, code lost:
    
        r10 = 51557;
     */
    /* JADX WARN: Code restructure failed: missing block: B:393:0x02aa, code lost:
    
        r10 = 50875;
     */
    /* JADX WARN: Code restructure failed: missing block: B:398:0x02c2, code lost:
    
        if (r3 != r10) goto L228;
     */
    /* JADX WARN: Code restructure failed: missing block: B:399:0x02c4, code lost:
    
        r4 = 50658;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0033, code lost:
    
        r3 = com.yan.hook.C0002.m206(r21[r2]);
        r4 = 1864;
     */
    /* JADX WARN: Code restructure failed: missing block: B:401:0x00c3, code lost:
    
        r4 = r22[r2] instanceof java.lang.Byte;
        r3 = 49790;
     */
    /* JADX WARN: Code restructure failed: missing block: B:402:0x00ca, code lost:
    
        r3 = r3 ^ 49807;
     */
    /* JADX WARN: Code restructure failed: missing block: B:403:0x00ce, code lost:
    
        if (r3 == r5) goto L485;
     */
    /* JADX WARN: Code restructure failed: missing block: B:405:0x00d2, code lost:
    
        if (r3 == 51) goto L483;
     */
    /* JADX WARN: Code restructure failed: missing block: B:407:0x00d6, code lost:
    
        if (r3 == 84) goto L304;
     */
    /* JADX WARN: Code restructure failed: missing block: B:408:0x00d8, code lost:
    
        if (r3 == r6) goto L484;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x003b, code lost:
    
        r4 = r4 ^ 1881;
     */
    /* JADX WARN: Code restructure failed: missing block: B:410:0x00db, code lost:
    
        if (r4 != false) goto L486;
     */
    /* JADX WARN: Code restructure failed: missing block: B:412:0x02d5, code lost:
    
        r3 = 49852;
        r5 = 18;
        r6 = 241;
        r14 = '5';
     */
    /* JADX WARN: Code restructure failed: missing block: B:416:0x00dd, code lost:
    
        r3 = 49883;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x003d, code lost:
    
        if (r4 == 17) goto L320;
     */
    /* JADX WARN: Code restructure failed: missing block: B:420:0x00e1, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:425:0x02ed, code lost:
    
        if (r10 != r3) goto L235;
     */
    /* JADX WARN: Code restructure failed: missing block: B:426:0x02ef, code lost:
    
        r4 = 49759;
     */
    /* JADX WARN: Code restructure failed: missing block: B:431:0x030d, code lost:
    
        r4 = r22[r2];
        r3 = 54657;
     */
    /* JADX WARN: Code restructure failed: missing block: B:432:0x0314, code lost:
    
        r3 = r3 ^ 54674;
     */
    /* JADX WARN: Code restructure failed: missing block: B:433:0x0318, code lost:
    
        if (r3 == 19) goto L493;
     */
    /* JADX WARN: Code restructure failed: missing block: B:436:0x036f, code lost:
    
        if (r4 == null) goto L499;
     */
    /* JADX WARN: Code restructure failed: missing block: B:438:0x0371, code lost:
    
        r3 = 55401;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x0048, code lost:
    
        if (r4 == 47384) goto L318;
     */
    /* JADX WARN: Code restructure failed: missing block: B:442:0x0375, code lost:
    
        r3 = 54719;
     */
    /* JADX WARN: Code restructure failed: missing block: B:445:0x031a, code lost:
    
        if (r3 == 45) goto L494;
     */
    /* JADX WARN: Code restructure failed: missing block: B:446:0x031c, code lost:
    
        if (r3 == 50) goto L495;
     */
    /* JADX WARN: Code restructure failed: missing block: B:451:0x0320, code lost:
    
        if (r3 == 3579) goto L492;
     */
    /* JADX WARN: Code restructure failed: missing block: B:453:0x0323, code lost:
    
        r3 = org.lsposed.hiddenapibypass.library.C0003.m284(r21[r2], r22[r2]);
        r4 = 55432;
     */
    /* JADX WARN: Code restructure failed: missing block: B:454:0x032e, code lost:
    
        r4 = r4 ^ 55449;
     */
    /* JADX WARN: Code restructure failed: missing block: B:455:0x0332, code lost:
    
        if (r4 == 17) goto L504;
     */
    /* JADX WARN: Code restructure failed: missing block: B:457:0x0342, code lost:
    
        if (r3 != false) goto L509;
     */
    /* JADX WARN: Code restructure failed: missing block: B:459:0x0344, code lost:
    
        r4 = 55525;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x004d, code lost:
    
        if (r4 == 47417) goto L319;
     */
    /* JADX WARN: Code restructure failed: missing block: B:463:0x0348, code lost:
    
        r4 = 55494;
     */
    /* JADX WARN: Code restructure failed: missing block: B:467:0x0336, code lost:
    
        if (r4 == 62) goto L505;
     */
    /* JADX WARN: Code restructure failed: missing block: B:471:0x033a, code lost:
    
        if (r4 == 95) goto L506;
     */
    /* JADX WARN: Code restructure failed: missing block: B:473:0x033e, code lost:
    
        if (r4 == 124) goto L306;
     */
    /* JADX WARN: Code restructure failed: missing block: B:475:0x0341, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0052, code lost:
    
        if (r4 == 47483) goto L317;
     */
    /* JADX WARN: Code restructure failed: missing block: B:482:0x037d, code lost:
    
        if (r3 == false) goto L275;
     */
    /* JADX WARN: Code restructure failed: missing block: B:483:0x037f, code lost:
    
        r4 = 48736;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0383, code lost:
    
        r4 = 48705;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0386, code lost:
    
        r5 = 14;
        r6 = 239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x005b, code lost:
    
        r3 = r21[r2];
        r4 = com.yan.hook.C0002.m187();
        r10 = 48767;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0064, code lost:
    
        r10 = r10 ^ 48784;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0068, code lost:
    
        if (r10 == r5) goto L326;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x006a, code lost:
    
        r14 = '5';
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x006e, code lost:
    
        if (r10 == 45) goto L327;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0070, code lost:
    
        if (r10 == 76) goto L324;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x0072, code lost:
    
        if (r10 == r6) goto L325;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0075, code lost:
    
        if (r3 != r4) goto L328;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x0300, code lost:
    
        r10 = 48829;
        r5 = 14;
        r6 = 239;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x0077, code lost:
    
        r10 = 48860;
     */
    /* JADX WARN: Code restructure failed: missing block: B:74:0x007b, code lost:
    
        r3 = r22[r2] instanceof java.lang.Integer;
        r4 = 48891;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x0082, code lost:
    
        r4 = r4 ^ 48908;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0088, code lost:
    
        if (r4 == 22) goto L334;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x008a, code lost:
    
        if (r4 == 53) goto L335;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x008e, code lost:
    
        if (r4 == 503) goto L336;
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x0096, code lost:
    
        if (r3 != false) goto L337;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x009c, code lost:
    
        r4 = 48953;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0098, code lost:
    
        r4 = 49635;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0092, code lost:
    
        if (r4 == 32495) goto L303;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0095, code lost:
    
        return false;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x00a0, code lost:
    
        r10 = r21[r2];
        r3 = com.yan.hook.C0001.m179();
        r4 = 49666;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x00a9, code lost:
    
        r4 = r4 ^ 49683;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x00ae, code lost:
    
        if (r4 == 17) goto L347;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    static boolean checkArgsForInvokeMethod(java.lang.Class<?>[] clsArr, Object[] objArr) {
        int i;
        int length = clsArr.length;
        int length2 = objArr.length;
        int i2 = 1616;
        while (true) {
            i2 ^= 1633;
            char c = 14;
            if (i2 != 14) {
                if (i2 != 49) {
                    if (i2 == 204) {
                        return false;
                    }
                    char c2 = 239;
                    if (i2 == 239) {
                        int i3 = 0;
                        while (true) {
                            int length3 = clsArr.length;
                            int i4 = 1740;
                            while (true) {
                                i4 ^= 1757;
                                if (i4 != 17) {
                                    if (i4 != 54) {
                                        if (i4 == 471) {
                                            return true;
                                        }
                                        if (i4 == 500) {
                                            break;
                                        }
                                    }
                                } else if (i3 < length3) {
                                    i4 = 1833;
                                    c = 14;
                                    c2 = 239;
                                }
                                i4 = 1802;
                                c = 14;
                                c2 = 239;
                            }
                            i3 = i;
                            c = 14;
                            c2 = 239;
                        }
                    }
                } else if (length != length2) {
                    i2 = 1709;
                }
            }
            i2 = 1678;
        }
    }
}
