package org.lsposed.hiddenapibypass.library;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.SigningInfo;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Property;
import com.yan.hook.C0000;
import com.yan.hook.C0002;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.crypto.Cipher;
import org.lsposed.hiddenapibypass.HiddenApiBypass;
import org.lsposed.hiddenapibypass.LSPass;
import sun.misc.Unsafe;

/* renamed from: org.lsposed.hiddenapibypass.library.۟ۢۦۣ, reason: contains not printable characters */
/* loaded from: assets/hook.dex */
public class C0003 {

    /* renamed from: ۣ۟ۢۧ, reason: not valid java name and contains not printable characters */
    public static int f12 = 685;

    /* renamed from: ۟۟ۦۢ۠, reason: not valid java name and contains not printable characters */
    public static List m259(Object obj) {
        if (C0000.m138() <= 0) {
            return HiddenApiBypass.getDeclaredMethods((Class) obj);
        }
        return null;
    }

    /* renamed from: ۟۠ۡۥۥ, reason: not valid java name and contains not printable characters */
    public static Set m260(Object obj) {
        if (C0000.m138() < 0) {
            return ((Map) obj).entrySet();
        }
        return null;
    }

    /* renamed from: ۣۣ۟۠۠, reason: not valid java name and contains not printable characters */
    public static void m261(Object obj, Object obj2, int i) {
        if (m274() > 0) {
            ((Parcel) obj).writeTypedObject((Parcelable) obj2, i);
        }
    }

    /* renamed from: ۟۠ۦۧۢ, reason: not valid java name and contains not printable characters */
    public static void m262(Object obj) {
        if (C0004.m316() <= 0) {
            ((ZipFile) obj).close();
        }
    }

    /* renamed from: ۟۠ۨۧۤ, reason: not valid java name and contains not printable characters */
    public static boolean m263(Object obj) {
        if (C0004.m316() <= 0) {
            return ((Enumeration) obj).hasMoreElements();
        }
        return false;
    }

    /* renamed from: ۟ۡ, reason: not valid java name and contains not printable characters */
    public static void m264(Object obj, int i, Object obj2, Object obj3) {
        if (C0000.m138() < 0) {
            ((Cipher) obj).init(i, (Key) obj2, (AlgorithmParameterSpec) obj3);
        }
    }

    /* renamed from: ۟ۡ۟ۧۢ, reason: not valid java name and contains not printable characters */
    public static Object m265(Object obj, Object obj2, Object obj3, Object obj4) {
        if (m274() > 0) {
            return HiddenApiBypass.invoke((Class) obj, obj2, (String) obj3, (Object[]) obj4);
        }
        return null;
    }

    /* renamed from: ۟ۡ۠ۦۨ, reason: not valid java name and contains not printable characters */
    public static Iterator m266(Object obj) {
        if (C0000.m138() <= 0) {
            return ((Set) obj).iterator();
        }
        return null;
    }

    /* renamed from: ۟ۡۡ۟ۢ, reason: not valid java name and contains not printable characters */
    public static Object m267(Object obj, Object obj2) {
        if (C0002.m188() < 0) {
            return ((HashMap) obj).get(obj2);
        }
        return null;
    }

    /* renamed from: ۟ۡۡۢ۠, reason: not valid java name and contains not printable characters */
    public static boolean m268(Object obj) {
        if (C0004.m316() < 0) {
            return HiddenApiBypass.setHiddenApiExemptions((String[]) obj);
        }
        return false;
    }

    /* renamed from: ۟ۢۡ۠ۥ, reason: not valid java name and contains not printable characters */
    public static boolean m269(Object obj, Object obj2) {
        if (C0002.m188() <= 0) {
            return ((ArrayList) obj).add(obj2);
        }
        return false;
    }

    /* renamed from: ۟ۢۢ۠ۢ, reason: not valid java name and contains not printable characters */
    public static boolean m270(Object obj, Object obj2) {
        if (C0002.m188() < 0) {
            return ((String) obj).startsWith((String) obj2);
        }
        return false;
    }

    /* renamed from: ۟ۢۢۧۡ, reason: not valid java name and contains not printable characters */
    public static Class m271(Object obj) {
        if (C0002.m188() <= 0) {
            return obj.getClass();
        }
        return null;
    }

    /* renamed from: ۟ۢۥۦۥ, reason: not valid java name and contains not printable characters */
    public static boolean m272(Object obj, int i, Object obj2, Object obj3, int i2) {
        if (C0004.m316() < 0) {
            return ((IBinder) obj).transact(i, (Parcel) obj2, (Parcel) obj3, i2);
        }
        return false;
    }

    /* renamed from: ۟ۢۨۦۦ, reason: not valid java name and contains not printable characters */
    public static Constructor m273(Object obj, Object obj2) {
        if (C0002.m188() < 0) {
            return ((Class) obj).getDeclaredConstructor((Class[]) obj2);
        }
        return null;
    }

    /* renamed from: ۟ۢۨۦۨ, reason: not valid java name and contains not printable characters */
    public static int m274() {
        return (-480) ^ C0004.f13;
    }

    /* renamed from: ۣ۟۟ۡۥ, reason: not valid java name and contains not printable characters */
    public static String m275(String str) {
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
        for (int i3 = 0; i3 < length; i3++) {
            byteArray[i3] = (byte) (byteArray[i3] ^ str3.charAt(i3 % length2));
        }
        for (int i4 = 0; i4 < byteArray.length; i4 = "".length() + 1) {
        }
        return new String(byteArray);
    }

    /* renamed from: ۣ۟۠ۨۥ, reason: not valid java name and contains not printable characters */
    public static int m276(Object obj) {
        return obj.hashCode();
    }

    /* renamed from: ۣ۟ۤۧۥ, reason: not valid java name and contains not printable characters */
    public static InputStream m277(Object obj, Object obj2) {
        if (C0002.m188() <= 0) {
            return ((ZipFile) obj).getInputStream((ZipEntry) obj2);
        }
        return null;
    }

    /* renamed from: ۣ۟ۦۦ۟, reason: not valid java name and contains not printable characters */
    public static Field[] m278(Object obj) {
        if (m274() >= 0) {
            return ((Class) obj).getDeclaredFields();
        }
        return null;
    }

    /* renamed from: ۣ۟ۤ۟ۢ, reason: not valid java name and contains not printable characters */
    public static void m279(Object obj, Object obj2, int i) {
        if (C0002.m188() < 0) {
            ((ApplicationInfo) obj).writeToParcel((Parcel) obj2, i);
        }
    }

    /* renamed from: ۟ۤ۠, reason: not valid java name and contains not printable characters */
    public static SigningInfo m280(Object obj) {
        if (C0002.m188() < 0) {
            return ((PackageInfo) obj).signingInfo;
        }
        return null;
    }

    /* renamed from: ۣ۟ۤۢ۟, reason: not valid java name and contains not printable characters */
    public static String m281(Object obj) {
        if (C0004.m316() <= 0) {
            return ((StringBuilder) obj).toString();
        }
        return null;
    }

    /* renamed from: ۟ۤۢۨۦ, reason: not valid java name and contains not printable characters */
    public static Member m282(Object obj, Object obj2) {
        if (C0002.m188() < 0) {
            return MethodHandles.reflectAs((Class) obj, (MethodHandle) obj2);
        }
        return null;
    }

    /* renamed from: ۣ۟ۤ۠۟, reason: not valid java name and contains not printable characters */
    public static Object m283(Object obj, Object obj2) {
        if (C0000.m138() < 0) {
            return ((Field) obj).get(obj2);
        }
        return null;
    }

    /* renamed from: ۟ۤۥۢۡ, reason: not valid java name and contains not printable characters */
    public static boolean m284(Object obj, Object obj2) {
        if (C0002.m188() <= 0) {
            return ((Class) obj).isInstance(obj2);
        }
        return false;
    }

    /* renamed from: ۟ۤۧۡۢ, reason: not valid java name and contains not printable characters */
    public static Object[] m286(Object obj, Object obj2) {
        if (C0002.m188() < 0) {
            return ((Set) obj).toArray((Object[]) obj2);
        }
        return null;
    }

    /* renamed from: ۟ۤۧۨ, reason: not valid java name and contains not printable characters */
    public static Iterator m287(Object obj) {
        if (C0000.m138() < 0) {
            return ((List) obj).iterator();
        }
        return null;
    }

    /* renamed from: ۟ۤۨ۟ۨ, reason: not valid java name and contains not printable characters */
    public static Object m288(Object obj, Object obj2, Object obj3, Object obj4) {
        if (m274() >= 0) {
            return LSPass.invoke((Class) obj, obj2, (String) obj3, (Object[]) obj4);
        }
        return null;
    }

    /* renamed from: ۟ۥ۠۟ۥ, reason: not valid java name and contains not printable characters */
    public static boolean m289(Object obj) {
        if (m274() > 0) {
            return ((File) obj).delete();
        }
        return false;
    }

    /* renamed from: ۟ۦۤۤۤ, reason: not valid java name and contains not printable characters */
    public static void m290(Object obj) {
        if (C0002.m188() < 0) {
            ((Parcel) obj).recycle();
        }
    }

    /* renamed from: ۟ۧ۠ۨ۟, reason: not valid java name and contains not printable characters */
    public static int m291(Object obj, Object obj2) {
        if (C0000.m138() < 0) {
            return ((InputStream) obj).read((byte[]) obj2);
        }
        return 0;
    }

    /* renamed from: ۟ۨ۟ۢ, reason: not valid java name and contains not printable characters */
    public static Integer m292(int i) {
        if (m274() > 0) {
            return Integer.valueOf(i);
        }
        return null;
    }

    /* renamed from: ۣ۠۠, reason: not valid java name and contains not printable characters */
    public static Object m293(Object obj, Object obj2, Object obj3) {
        if (C0004.m316() < 0) {
            return ((Method) obj).invoke(obj2, (Object[]) obj3);
        }
        return null;
    }

    /* renamed from: ۣ۠ۤ۟, reason: not valid java name and contains not printable characters */
    public static byte[] m294(Object obj, Object obj2) {
        if (C0004.m316() <= 0) {
            return ((String) obj).getBytes((String) obj2);
        }
        return null;
    }

    /* renamed from: ۠ۥۢۧ, reason: not valid java name and contains not printable characters */
    public static Method m295(Object obj, Object obj2, Object obj3) {
        if (C0004.m316() <= 0) {
            return ((Class) obj).getMethod((String) obj2, (Class[]) obj3);
        }
        return null;
    }

    /* renamed from: ۠ۥۣۨ, reason: not valid java name and contains not printable characters */
    public static Class m296() {
        if (m274() > 0) {
            return Boolean.TYPE;
        }
        return null;
    }

    /* renamed from: ۠ۨۦۣ, reason: not valid java name and contains not printable characters */
    public static ApplicationInfo m297(Object obj) {
        if (C0004.m316() <= 0) {
            return ((Context) obj).getApplicationInfo();
        }
        return null;
    }

    /* renamed from: ۡۧۡ, reason: not valid java name and contains not printable characters */
    public static File m298(Object obj) {
        if (C0000.m138() <= 0) {
            return ((Context) obj).getCacheDir();
        }
        return null;
    }

    /* renamed from: ۡۧۡۤ, reason: not valid java name and contains not printable characters */
    public static Parcel m299() {
        if (m274() >= 0) {
            return Parcel.obtain();
        }
        return null;
    }

    /* renamed from: ۢ۠ۦۥ, reason: not valid java name and contains not printable characters */
    public static Class[] m300(Object obj) {
        if (C0004.m316() <= 0) {
            return ((Method) obj).getParameterTypes();
        }
        return null;
    }

    /* renamed from: ۣۢۥۤ, reason: not valid java name and contains not printable characters */
    public static StringBuilder m301(Object obj, boolean z) {
        if (m274() > 0) {
            return ((StringBuilder) obj).append(z);
        }
        return null;
    }

    /* renamed from: ۢۥۨ۠, reason: not valid java name and contains not printable characters */
    public static void m302(Object obj, Object obj2) {
        if (C0002.m188() < 0) {
            ((Parcel) obj).writeStrongBinder((IBinder) obj2);
        }
    }

    /* renamed from: ۣۢۧۤ, reason: not valid java name and contains not printable characters */
    public static void m303(Object obj, Object obj2, long j, long j2) {
        if (C0004.m316() <= 0) {
            ((Unsafe) obj).putLong(obj2, j, j2);
        }
    }

    /* renamed from: ۢۧۦۥ, reason: not valid java name and contains not printable characters */
    public static Class m304() {
        if (m274() >= 0) {
            return Character.TYPE;
        }
        return null;
    }

    /* renamed from: ۤ۠۟ۨ, reason: not valid java name and contains not printable characters */
    public static Class m305() {
        if (C0002.m188() < 0) {
            return Double.TYPE;
        }
        return null;
    }

    /* renamed from: ۤۥۢ۠, reason: not valid java name and contains not printable characters */
    public static String m306(Object obj) {
        if (C0004.m316() < 0) {
            return ((Field) obj).getName();
        }
        return null;
    }

    /* renamed from: ۦۢۨۢ, reason: contains not printable characters */
    public static long m307(Object obj, Object obj2) {
        if (m274() > 0) {
            return ((Unsafe) obj).objectFieldOffset((Field) obj2);
        }
        return 0L;
    }

    /* renamed from: ۦۥۥۣ, reason: contains not printable characters */
    public static boolean m308(int i) {
        if (m274() > 0) {
            return Modifier.isStatic(i);
        }
        return false;
    }

    /* renamed from: ۧۤۧۢ, reason: not valid java name and contains not printable characters */
    public static int m309(Object obj) {
        if (C0002.m188() < 0) {
            return ((Parcel) obj).dataPosition();
        }
        return 0;
    }

    /* renamed from: ۧۨۢۨ, reason: not valid java name and contains not printable characters */
    public static Property m310(Object obj, Object obj2, Object obj3) {
        if (C0002.m188() <= 0) {
            return Property.of((Class) obj, (Class) obj2, (String) obj3);
        }
        return null;
    }

    /* renamed from: ۟ۤۥۣۨ, reason: not valid java name and contains not printable characters */
    public static String m285(short[] sArr, int i, int i2, int i3) {
        char[] cArr = new char[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            cArr[i4] = (char) (sArr[i + i4] ^ i3);
        }
        return new String(cArr);
    }
}
