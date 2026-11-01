package com.yan.hook;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.os.ServiceManager;
import com.yan.hook.PmsHookProvider;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import javax.crypto.Cipher;
import org.lsposed.hiddenapibypass.LSPass;
import org.lsposed.hiddenapibypass.library.C0003;
import org.lsposed.hiddenapibypass.library.C0004;
import sun.misc.Unsafe;

/* renamed from: com.yan.hook.۟ۥۨۢۥ, reason: contains not printable characters */
/* loaded from: assets/hook.dex */
public class C0001 {

    /* renamed from: ۤۤۢۦ, reason: not valid java name and contains not printable characters */
    public static int f5 = -579;

    /* renamed from: ۟۠ۧۢ, reason: not valid java name and contains not printable characters */
    public static boolean m141(Object obj, Object obj2) {
        if (C0004.m316() <= 0) {
            return ((List) obj).add(obj2);
        }
        return false;
    }

    /* renamed from: ۟ۡۡۨۢ, reason: not valid java name and contains not printable characters */
    public static String[] m142() {
        if (C0004.m316() < 0) {
            return Build.SUPPORTED_ABIS;
        }
        return null;
    }

    /* renamed from: ۟ۡۥۣۢ, reason: not valid java name and contains not printable characters */
    public static int m143() {
        return (-719) ^ C0002.f6;
    }

    /* renamed from: ۟ۢۦ۟ۨ, reason: not valid java name and contains not printable characters */
    public static void m144(Object obj) {
        if (C0004.m316() < 0) {
            ((DataInputStream) obj).close();
        }
    }

    /* renamed from: ۣ۟۟ۢۦ, reason: not valid java name and contains not printable characters */
    public static Object m145(Object obj) {
        if (C0002.m188() <= 0) {
            return ((Enumeration) obj).nextElement();
        }
        return null;
    }

    /* renamed from: ۣ۟ۡۥۨ, reason: not valid java name and contains not printable characters */
    public static void m146(Object obj, Object obj2, int i) {
        if (C0004.m316() <= 0) {
            ((Field) obj).setInt(obj2, i);
        }
    }

    /* renamed from: ۟ۤۡۤۢ, reason: not valid java name and contains not printable characters */
    public static ClassLoader m147(Object obj) {
        if (C0003.m274() >= 0) {
            return ((Class) obj).getClassLoader();
        }
        return null;
    }

    /* renamed from: ۟ۤۤۨۤ, reason: not valid java name and contains not printable characters */
    public static void m148(Object obj, Object obj2, int i, int i2) {
        if (C0004.m316() < 0) {
            ((FileOutputStream) obj).write((byte[]) obj2, i, i2);
        }
    }

    /* renamed from: ۟ۤۥۤۡ, reason: not valid java name and contains not printable characters */
    public static int m149(Object obj) {
        return obj.hashCode();
    }

    /* renamed from: ۟ۤۧۤۢ, reason: not valid java name and contains not printable characters */
    public static Class m150(Object obj) {
        if (C0002.m188() < 0) {
            return ((Class) obj).getSuperclass();
        }
        return null;
    }

    /* renamed from: ۟ۥۥ۟ۢ, reason: not valid java name and contains not printable characters */
    public static Cipher m151(Object obj) {
        if (C0002.m188() <= 0) {
            return Cipher.getInstance((String) obj);
        }
        return null;
    }

    /* renamed from: ۟ۥۥۨۦ, reason: not valid java name and contains not printable characters */
    public static void m152(Object obj, boolean z) {
        if (C0000.m138() <= 0) {
            ((Field) obj).setAccessible(z);
        }
    }

    /* renamed from: ۟ۦ۟ۦۢ, reason: not valid java name and contains not printable characters */
    public static int m153(Object obj, long j) {
        if (C0000.m138() < 0) {
            return ((Unsafe) obj).getInt(j);
        }
        return 0;
    }

    /* renamed from: ۟ۦۧۡۡ, reason: not valid java name and contains not printable characters */
    public static Boolean m154(boolean z) {
        if (C0000.m138() <= 0) {
            return Boolean.valueOf(z);
        }
        return null;
    }

    /* renamed from: ۟ۧۢ۟ۡ, reason: not valid java name and contains not printable characters */
    public static int m155(Object obj) {
        if (C0004.m316() < 0) {
            return ((Number) obj).intValue();
        }
        return 0;
    }

    /* renamed from: ۟ۧۥۣۢ, reason: not valid java name and contains not printable characters */
    public static int m156(Object obj) {
        if (C0000.m138() <= 0) {
            return PmsHookProvider.AnonymousClass0.m((Method) obj);
        }
        return 0;
    }

    /* renamed from: ۠۟ۦۧ, reason: not valid java name and contains not printable characters */
    public static int m157(Object obj, Object obj2, int i, int i2) {
        if (C0003.m274() > 0) {
            return ((InputStream) obj).read((byte[]) obj2, i, i2);
        }
        return 0;
    }

    /* renamed from: ۣ۠ۨۨ, reason: not valid java name and contains not printable characters */
    public static String m158(String str) {
        String str2 = "";
        int i = 0;
        String str3 = "";
        while (i < 15) {
            str2 = new StringBuffer().append(str2).append(Integer.toHexString(i)).toString();
            String stringBuffer = new StringBuffer().append(str3).append(((int) (Math.random() * 10)) ^ i).toString();
            i++;
            str3 = stringBuffer;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(str.length() / 2);
        for (int i2 = 0; i2 < str.length(); i2 += 2) {
            byteArrayOutputStream.write((str2.indexOf(str.charAt(i2)) << 4) | str2.indexOf(str.charAt(i2 + 1)));
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        String str4 = "a";
        while (str4.length() > 0) {
            str4 = "";
            if ("".length() == 0) {
                str4 = "a";
            }
        }
        int length = str4.length();
        int length2 = str3.length();
        for (int i3 = 0; i3 < length; i3++) {
            byteArray[i3] = (byte) (byteArray[i3] ^ str3.charAt(i3 % length2));
        }
        for (int i4 = 0; i4 < byteArray.length; i4 = "".length() + 1) {
        }
        return new String(byteArray);
    }

    /* renamed from: ۡۦۧۨ, reason: not valid java name and contains not printable characters */
    public static PackageManager m159(Object obj) {
        if (C0004.m316() < 0) {
            return ((Context) obj).getPackageManager();
        }
        return null;
    }

    /* renamed from: ۢ۟ۨۤ, reason: not valid java name and contains not printable characters */
    public static ApplicationInfo m160(Object obj) {
        if (C0002.m188() < 0) {
            return ((Application) obj).getApplicationInfo();
        }
        return null;
    }

    /* renamed from: ۣۢ۠۟, reason: not valid java name and contains not printable characters */
    public static Class m161(Object obj, Object obj2) {
        if (C0004.m316() < 0) {
            return ((ClassLoader) obj).loadClass((String) obj2);
        }
        return null;
    }

    /* renamed from: ۣۢۢۡ, reason: not valid java name and contains not printable characters */
    public static String m162(Object obj) {
        if (C0002.m188() <= 0) {
            return ((PackageInfo) obj).packageName;
        }
        return null;
    }

    /* renamed from: ۣۢۧ۠, reason: not valid java name and contains not printable characters */
    public static Object m163(Object obj, int i) {
        if (C0003.m274() > 0) {
            return Array.newInstance((Class<?>) obj, i);
        }
        return null;
    }

    /* renamed from: ۢۦۢۤ, reason: not valid java name and contains not printable characters */
    public static List m164() {
        if (C0004.m316() < 0) {
            return Collections.EMPTY_LIST;
        }
        return null;
    }

    /* renamed from: ۣ۟ۦۣ, reason: not valid java name and contains not printable characters */
    public static List m165(Object obj) {
        if (C0004.m316() <= 0) {
            return Arrays.asList((Object[]) obj);
        }
        return null;
    }

    /* renamed from: ۣۣ۠ۡ, reason: not valid java name and contains not printable characters */
    public static Long m166(long j) {
        if (C0000.m138() < 0) {
            return Long.valueOf(j);
        }
        return null;
    }

    /* renamed from: ۣۥۡۡ, reason: not valid java name and contains not printable characters */
    public static StringBuilder m167(Object obj, int i) {
        if (C0000.m138() <= 0) {
            return ((StringBuilder) obj).append(i);
        }
        return null;
    }

    /* renamed from: ۣۣۨۧ, reason: not valid java name and contains not printable characters */
    public static IBinder m168(Object obj) {
        if (C0002.m188() < 0) {
            return ServiceManager.getService((String) obj);
        }
        return null;
    }

    /* renamed from: ۤۡ۟۟, reason: not valid java name and contains not printable characters */
    public static void m170(Object obj) {
        if (C0000.m138() < 0) {
            ((MessageDigest) obj).reset();
        }
    }

    /* renamed from: ۣۤۦ۟, reason: not valid java name and contains not printable characters */
    public static void m171(Object obj, boolean z) {
        if (C0003.m274() >= 0) {
            ((Method) obj).setAccessible(z);
        }
    }

    /* renamed from: ۣۤۨ۠, reason: not valid java name and contains not printable characters */
    public static boolean m172(Object obj, Object obj2) {
        if (C0004.m316() < 0) {
            return ((Set) obj).addAll((Collection) obj2);
        }
        return false;
    }

    /* renamed from: ۥ۠ۡۥ, reason: contains not printable characters */
    public static StringBuilder m173(Object obj, Object obj2) {
        if (C0003.m274() >= 0) {
            return ((StringBuilder) obj).append(obj2);
        }
        return null;
    }

    /* renamed from: ۥۣۡۡ, reason: contains not printable characters */
    public static ApplicationInfo m174(Object obj) {
        if (C0004.m316() < 0) {
            return ((PackageInfo) obj).applicationInfo;
        }
        return null;
    }

    /* renamed from: ۥۤۦ۟, reason: contains not printable characters */
    public static void m175(Object obj, Object obj2, long j, Object obj3) {
        if (C0002.m188() <= 0) {
            ((Unsafe) obj).putObject(obj2, j, obj3);
        }
    }

    /* renamed from: ۥۧۧۦ, reason: contains not printable characters */
    public static int m176(Object obj) {
        if (C0000.m138() <= 0) {
            return ((DataInputStream) obj).readInt();
        }
        return 0;
    }

    /* renamed from: ۦۢۧۤ, reason: contains not printable characters */
    public static boolean m177(Object obj) {
        if (C0000.m138() < 0) {
            return LSPass.setHiddenApiExemptions((String[]) obj);
        }
        return false;
    }

    /* renamed from: ۦۣۣۨ, reason: contains not printable characters */
    public static boolean m178(Object obj) {
        if (C0004.m316() <= 0) {
            return ((File) obj).mkdirs();
        }
        return false;
    }

    /* renamed from: ۧ۟ۥۧ, reason: not valid java name and contains not printable characters */
    public static Class m179() {
        if (C0004.m316() < 0) {
            return Byte.TYPE;
        }
        return null;
    }

    /* renamed from: ۧۡ۟, reason: not valid java name and contains not printable characters */
    public static List m180(Object obj) {
        if (C0004.m316() < 0) {
            return LSPass.getDeclaredMethods((Class) obj);
        }
        return null;
    }

    /* renamed from: ۧۨ, reason: not valid java name and contains not printable characters */
    public static boolean m181(Object obj) {
        if (C0003.m274() >= 0) {
            return ((File) obj).exists();
        }
        return false;
    }

    /* renamed from: ۨۢۡ, reason: not valid java name and contains not printable characters */
    public static Object m182(Object obj, Object obj2, Object obj3) {
        if (C0003.m274() > 0) {
            return ((HashMap) obj).put(obj2, obj3);
        }
        return null;
    }

    /* renamed from: ۨۢۥۣ, reason: not valid java name and contains not printable characters */
    public static long m183(Object obj, Object obj2, long j) {
        if (C0000.m138() < 0) {
            return ((Unsafe) obj).getLong(obj2, j);
        }
        return 0L;
    }

    /* renamed from: ۨۦ۟۟, reason: not valid java name and contains not printable characters */
    public static String m184() {
        if (C0002.m188() <= 0) {
            return "";
        }
        return null;
    }

    /* renamed from: ۨۦۢۦ, reason: not valid java name and contains not printable characters */
    public static Object m185(Object obj, Object obj2, Object obj3) {
        if (C0002.m188() <= 0) {
            return Proxy.newProxyInstance((ClassLoader) obj, (Class[]) obj2, (InvocationHandler) obj3);
        }
        return null;
    }

    /* renamed from: ۤ۟۟۟, reason: not valid java name and contains not printable characters */
    public static String m169(short[] sArr, int i, int i2, int i3) {
        char[] cArr = new char[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            cArr[i4] = (char) (sArr[i + i4] ^ i3);
        }
        return new String(cArr);
    }
}
