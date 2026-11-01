package org.lsposed.hiddenapibypass;

import com.yan.hook.C0000;
import com.yan.hook.C0001;
import com.yan.hook.C0002;
import dalvik.system.PathClassLoader;
import java.lang.invoke.MethodHandle;
import java.lang.reflect.Executable;
import org.lsposed.hiddenapibypass.Helper;
import org.lsposed.hiddenapibypass.library.C0003;
import org.lsposed.hiddenapibypass.library.C0004;

/* loaded from: assets/hook.dex */
final class CoreOjClassLoader extends PathClassLoader {
    static final boolean $assertionsDisabled = false;

    /* renamed from: short, reason: not valid java name */
    private static final short[] f7short = {2621, 2614, 2593, 2614, 2681, 2613, 2616, 2616, 2595, 2681, 2612, 2619, 2614, 2596, 2596, 2681, 2599, 2614, 2595, 2623, 2489};

    CoreOjClassLoader() {
        super(m234(), null);
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        System.out.println(Long.valueOf(C0003.m275("RtGQNt3h")));
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

    /* JADX WARN: Code restructure failed: missing block: B:5:0x0012, code lost:
    
        return r2;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private static String getCoreOjPath() {
        String str = null;
        int i = 0;
        int i2 = 0;
        short[] sArr = null;
        String[] strArr = null;
        String str2 = null;
        String str3 = "ۧۢۥ";
        String str4 = null;
        while (true) {
            switch (C0004.m311(str3)) {
                case 56537:
                    str2 = C0002.m197(str, str4);
                    str3 = "۠۠ۤ";
                    break;
                case 1746909:
                    str = C0003.m285(sArr, 0, i, 2647);
                    str3 = "ۥۣ۠";
                    break;
                case 1747684:
                    str3 = "ۢۥۥ";
                    break;
                case 1748833:
                    i2 = C0002.f6;
                    str3 = "ۨۤۡ";
                    break;
                case 1749762:
                    sArr = m235();
                    str3 = "ۢۧۦ";
                    break;
                case 1749825:
                    str = C0002.m191(sArr, 20, 1, 2435);
                    str3 = "ۥۡ۠";
                    break;
                case 1750534:
                    str4 = strArr[0];
                    str3 = "ۥۨۢ";
                    break;
                case 1750787:
                    i = i2 ^ 687;
                    str3 = "ۧ۠۟";
                    break;
                case 1752516:
                    i2 = C0003.f12;
                    str3 = "ۣۧۧ";
                    break;
                case 1752578:
                    str4 = C0001.m184();
                    str3 = "ۧ۠";
                    break;
                case 1754406:
                    strArr = C0002.m196(str2, str, i);
                    str3 = "ۣ۟ۢ";
                    break;
                case 1754474:
                    sArr = m235();
                    str3 = "ۡۦۦ";
                    break;
                case 1755493:
                    i = i2 ^ (-502);
                    str3 = "۟ۦۤ";
                    break;
            }
        }
    }

    /* renamed from: ۣ۟ۨۦۧ, reason: not valid java name and contains not printable characters */
    public static String m234() {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return getCoreOjPath();
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

    /* renamed from: ۢ۠ۥۡ, reason: not valid java name and contains not printable characters */
    public static short[] m235() {
        int m138 = C0000.m138();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return f7short;
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

    /* renamed from: ۦۦۣ۟, reason: contains not printable characters */
    public static Class m236(Object obj, Object obj2) {
        int m316 = C0004.m316();
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return ((CoreOjClassLoader) obj).findClass((String) obj2);
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

    @Override // java.lang.ClassLoader
    public Class<?> loadClass(String str) throws ClassNotFoundException {
        boolean m200 = C0002.m200(C0002.m223(Object.class), str);
        int i = 1616;
        while (true) {
            i ^= 1633;
            if (i != 14) {
                if (i != 49) {
                    if (i == 204) {
                        return Object.class;
                    }
                    if (i == 239) {
                        try {
                            return m236(this, str);
                        } catch (ClassNotFoundException unused) {
                            boolean m2002 = C0002.m200(C0002.m223(Executable.class), str);
                            int i2 = 1740;
                            while (true) {
                                i2 ^= 1757;
                                if (i2 == 17) {
                                    i2 = m2002 ? 1833 : 1802;
                                } else if (i2 != 54) {
                                    if (i2 == 471) {
                                        boolean m2003 = C0002.m200(C0002.m223(MethodHandle.class), str);
                                        int i3 = 1864;
                                        while (true) {
                                            i3 ^= 1881;
                                            if (i3 == 17) {
                                                i3 = m2003 ? 48736 : 48705;
                                            } else if (i3 == 47384) {
                                                boolean m2004 = C0002.m200(C0002.m223(Class.class), str);
                                                int i4 = 48767;
                                                while (true) {
                                                    i4 ^= 48784;
                                                    if (i4 != 14) {
                                                        if (i4 == 45) {
                                                            return super.loadClass(str);
                                                        }
                                                        if (i4 == 76) {
                                                            return Helper.Class.class;
                                                        }
                                                        if (i4 == 239) {
                                                            if (m2004) {
                                                                i4 = 48860;
                                                            }
                                                        }
                                                    }
                                                    i4 = 48829;
                                                }
                                            } else {
                                                if (i3 == 47417) {
                                                    return Helper.MethodHandle.class;
                                                }
                                                if (i3 != 47483) {
                                                }
                                            }
                                        }
                                    } else if (i2 == 500) {
                                        return Helper.Executable.class;
                                    }
                                }
                            }
                        }
                    }
                } else if (m200) {
                    i = 1709;
                }
            }
            i = 1678;
        }
    }
}
