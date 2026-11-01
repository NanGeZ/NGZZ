package com.yan.hook;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import org.lsposed.hiddenapibypass.LSPass;
import org.lsposed.hiddenapibypass.library.C0003;
import org.lsposed.hiddenapibypass.library.C0004;

/* renamed from: com.yan.hook.۟ۧ۟ۥۨ, reason: contains not printable characters */
/* loaded from: /workspace/yan_reverse/jadx_out/resources/assets/hook.dex */
public class C0002 {

    /* renamed from: ۣۤۨۢ, reason: not valid java name and contains not printable characters */
    public static int f6 = -482;

    /* renamed from: ۟۟ۤ, reason: not valid java name and contains not printable characters */
    public static void m186(Object obj) {
        if (m188() < 0) {
            System.load((String) obj);
        }
    }

    /* renamed from: ۟۠۠۠ۥ, reason: not valid java name and contains not printable characters */
    public static Class m187() {
        if (C0004.m316() < 0) {
            return Integer.TYPE;
        }
        return null;
    }

    /* renamed from: ۣ۟۠۟ۡ, reason: not valid java name and contains not printable characters */
    public static int m188() {
        return 488 ^ C0004.f13;
    }

    /* renamed from: ۟۠ۦۣۧ, reason: not valid java name and contains not printable characters */
    public static void m189(Object obj) {
        if (C0003.m274() > 0) {
            ((Map) obj).clear();
        }
    }

    /* renamed from: ۟ۢ۠ۥۡ, reason: not valid java name and contains not printable characters */
    public static void m190(Object obj) {
        if (C0000.m138() <= 0) {
            ((FileOutputStream) obj).close();
        }
    }

    /* renamed from: ۣ۟ۦۦ, reason: not valid java name and contains not printable characters */
    public static Method m192(Object obj, Object obj2, Object obj3) {
        if (C0004.m316() < 0) {
            return ((Class) obj).getDeclaredMethod((String) obj2, (Class[]) obj3);
        }
        return null;
    }

    /* renamed from: ۣ۟ۨۥ۠, reason: not valid java name and contains not printable characters */
    public static void m193(Object obj, Object obj2, Object obj3) {
        if (C0000.m138() <= 0) {
            ((Field) obj).set(obj2, obj3);
        }
    }

    /* renamed from: ۟ۤۥۣۦ, reason: not valid java name and contains not printable characters */
    public static void m194(Object obj, Object obj2, boolean z) {
        if (m188() < 0) {
            ((Field) obj).setBoolean(obj2, z);
        }
    }

    /* renamed from: ۟ۤۨۢۧ, reason: not valid java name and contains not printable characters */
    public static Object m195(Object obj) {
        if (C0000.m138() < 0) {
            return ((Map.Entry) obj).getKey();
        }
        return null;
    }

    /* renamed from: ۟ۥۢۤۥ, reason: not valid java name and contains not printable characters */
    public static String[] m196(Object obj, Object obj2, int i) {
        if (m188() < 0) {
            return ((String) obj).split((String) obj2, i);
        }
        return null;
    }

    /* renamed from: ۟ۥۤۢۥ, reason: not valid java name and contains not printable characters */
    public static String m197(Object obj, Object obj2) {
        if (C0004.m316() < 0) {
            return System.getProperty((String) obj, (String) obj2);
        }
        return null;
    }

    /* renamed from: ۟ۥۣۤۧ, reason: not valid java name and contains not printable characters */
    public static String m198(Object obj) {
        if (C0000.m138() < 0) {
            return ((Executable) obj).getName();
        }
        return null;
    }

    /* renamed from: ۟ۥۣۧۢ, reason: not valid java name and contains not printable characters */
    public static boolean m199(Object obj) {
        if (m188() <= 0) {
            return ((Class) obj).isArray();
        }
        return false;
    }

    /* renamed from: ۟ۥۨۤۧ, reason: not valid java name and contains not printable characters */
    public static boolean m200(Object obj, Object obj2) {
        if (C0004.m316() <= 0) {
            return ((String) obj).equals(obj2);
        }
        return false;
    }

    /* renamed from: ۟ۦۣۣ۟, reason: not valid java name and contains not printable characters */
    public static IBinder m201() {
        if (m188() <= 0) {
            return PmsHookProvider.makeFakeSM();
        }
        return null;
    }

    /* renamed from: ۟ۦۦ۟ۧ, reason: not valid java name and contains not printable characters */
    public static void m202(Object obj, int i) {
        if (C0003.m274() >= 0) {
            ((Parcel) obj).setDataSize(i);
        }
    }

    /* renamed from: ۟ۦۨۡۢ, reason: not valid java name and contains not printable characters */
    public static String m203(Object obj) {
        if (m188() < 0) {
            return ((Parcel) obj).readString();
        }
        return null;
    }

    /* renamed from: ۣ۟ۧ۠۠, reason: not valid java name and contains not printable characters */
    public static String m204(Object obj) {
        if (C0004.m316() < 0) {
            return String.valueOf(obj);
        }
        return null;
    }

    /* renamed from: ۟ۧ۠ۥۣ, reason: not valid java name and contains not printable characters */
    public static Object m205(Object obj, Object obj2) {
        if (m188() <= 0) {
            return ((Map) obj).remove(obj2);
        }
        return null;
    }

    /* renamed from: ۟ۧۧۢۨ, reason: not valid java name and contains not printable characters */
    public static boolean m206(Object obj) {
        if (C0000.m138() <= 0) {
            return ((Class) obj).isPrimitive();
        }
        return false;
    }

    /* renamed from: ۣ۠۠ۧ, reason: not valid java name and contains not printable characters */
    public static String m207(Object obj) {
        if (C0003.m274() > 0) {
            return ((Context) obj).getPackageName();
        }
        return null;
    }

    /* renamed from: ۠ۤۥۦ, reason: not valid java name and contains not printable characters */
    public static Method[] m208(Object obj) {
        if (m188() < 0) {
            return ((Class) obj).getDeclaredMethods();
        }
        return null;
    }

    /* renamed from: ۠ۧۥۦ, reason: not valid java name and contains not printable characters */
    public static void m209(Object obj, Object obj2, int i) {
        if (C0000.m138() < 0) {
            ((PackageInfo) obj).writeToParcel((Parcel) obj2, i);
        }
    }

    /* renamed from: ۡۦۨ, reason: not valid java name and contains not printable characters */
    public static String m210(Object obj) {
        if (m188() < 0) {
            return ((Method) obj).getName();
        }
        return null;
    }

    /* renamed from: ۡۧۧۢ, reason: not valid java name and contains not printable characters */
    public static MessageDigest m211(Object obj) {
        if (C0004.m316() < 0) {
            return MessageDigest.getInstance((String) obj);
        }
        return null;
    }

    /* renamed from: ۡۨۨۥ, reason: not valid java name and contains not printable characters */
    public static File m212(Object obj) {
        if (C0004.m316() <= 0) {
            return ((File) obj).getParentFile();
        }
        return null;
    }

    /* renamed from: ۢ۟ۤۡ, reason: not valid java name and contains not printable characters */
    public static int m213(Object obj) {
        return obj.hashCode();
    }

    /* renamed from: ۢۦۨۥ, reason: not valid java name and contains not printable characters */
    public static Parcelable.Creator m214() {
        if (C0000.m138() < 0) {
            return PackageInfo.CREATOR;
        }
        return null;
    }

    /* renamed from: ۣۣ۠ۢ, reason: not valid java name and contains not printable characters */
    public static MethodHandle m215(Object obj, Object obj2) {
        if (m188() <= 0) {
            return ((MethodHandles.Lookup) obj).unreflectGetter((Field) obj2);
        }
        return null;
    }

    /* renamed from: ۣۤۡ۠, reason: not valid java name and contains not printable characters */
    public static byte[] m216(Object obj, Object obj2) {
        if (C0000.m138() < 0) {
            return ((Cipher) obj).doFinal((byte[]) obj2);
        }
        return null;
    }

    /* renamed from: ۣۦۣۥ, reason: not valid java name and contains not printable characters */
    public static void m217(Object obj, Object obj2) {
        if (m188() <= 0) {
            ((FileOutputStream) obj).write((byte[]) obj2);
        }
    }

    /* renamed from: ۣۧۨ, reason: not valid java name and contains not printable characters */
    public static Parcelable.Creator m218() {
        if (m188() <= 0) {
            return ApplicationInfo.CREATOR;
        }
        return null;
    }

    /* renamed from: ۤۤ۠۟, reason: not valid java name and contains not printable characters */
    public static List m219(Object obj) {
        if (C0000.m138() < 0) {
            return LSPass.getDeclaredConstructors((Class) obj);
        }
        return null;
    }

    /* renamed from: ۥۡۨ, reason: contains not printable characters */
    public static void m220(Object obj, boolean z) {
        if (C0003.m274() >= 0) {
            ((Constructor) obj).setAccessible(z);
        }
    }

    /* renamed from: ۥۥۦۤ, reason: contains not printable characters */
    public static String m221(Object obj) {
        if (C0004.m316() < 0) {
            return ((ApplicationInfo) obj).packageName;
        }
        return null;
    }

    /* renamed from: ۥۦۨۢ, reason: contains not printable characters */
    public static void m222(Object obj) {
        if (C0000.m138() <= 0) {
            ((Parcel) obj).writeNoException();
        }
    }

    /* renamed from: ۥۧ۠۠, reason: contains not printable characters */
    public static String m223(Object obj) {
        if (C0000.m138() < 0) {
            return ((Class) obj).getName();
        }
        return null;
    }

    /* renamed from: ۥۨۢۡ, reason: contains not printable characters */
    public static long m224(Object obj) {
        if (C0004.m316() <= 0) {
            return ((Number) obj).longValue();
        }
        return 0L;
    }

    /* renamed from: ۦ۟ۦۥ, reason: contains not printable characters */
    public static Object m225(Object obj) {
        if (C0000.m138() <= 0) {
            return ((Map.Entry) obj).getValue();
        }
        return null;
    }

    /* renamed from: ۦ۠۠ۨ, reason: contains not printable characters */
    public static void m226(Object obj) {
        if (m188() <= 0) {
            PmsHookProvider.entry((Context) obj);
        }
    }

    /* renamed from: ۦۣۢۢ, reason: contains not printable characters */
    public static void m227(Object obj) {
        if (C0003.m274() >= 0) {
            ((InputStream) obj).close();
        }
    }

    /* renamed from: ۦۢۤۢ, reason: contains not printable characters */
    public static Class[] m228(Object obj) {
        if (C0000.m138() < 0) {
            return ((Constructor) obj).getParameterTypes();
        }
        return null;
    }

    /* renamed from: ۧۤۤ۠, reason: not valid java name and contains not printable characters */
    public static Object m229(Object obj, Object obj2) {
        if (C0000.m138() <= 0) {
            return ((Parcel) obj).readTypedObject((Parcelable.Creator) obj2);
        }
        return null;
    }

    /* renamed from: ۧۦۧۦ, reason: not valid java name and contains not printable characters */
    public static MethodHandle m230(Object obj, Object obj2) {
        if (C0003.m274() > 0) {
            return ((MethodHandles.Lookup) obj).unreflect((Method) obj2);
        }
        return null;
    }

    /* renamed from: ۨ۟ۢۥ, reason: not valid java name and contains not printable characters */
    public static String m231(String str) {
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
        int length = byteArray.length;
        int length2 = str3.length();
        while (length > 0) {
            byteArray[-1] = (byte) (byteArray[-1] ^ str3.charAt((-1) % length2));
        }
        for (int i3 = 0; i3 < byteArray.length; i3 = "".length() + 1) {
        }
        return new String(byteArray);
    }

    /* renamed from: ۨ۠ۥۨ, reason: not valid java name and contains not printable characters */
    public static Object m232(Object obj, Object obj2) {
        if (C0000.m138() < 0) {
            return ((Parcelable.Creator) obj).createFromParcel((Parcel) obj2);
        }
        return null;
    }

    /* renamed from: ۨۥۣۧ, reason: not valid java name and contains not printable characters */
    public static Class m233() {
        if (C0004.m316() <= 0) {
            return Float.TYPE;
        }
        return null;
    }

    /* renamed from: ۟ۢۧۢ۟, reason: not valid java name and contains not printable characters */
    public static String m191(short[] sArr, int i, int i2, int i3) {
        char[] cArr = new char[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            cArr[i4] = (char) (sArr[i + i4] ^ i3);
        }
        return new String(cArr);
    }
}
