package org.lsposed.hiddenapibypass.library;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.os.Parcel;
import android.util.Property;
import com.yan.hook.C0000;
import com.yan.hook.C0002;
import com.yan.hook.PmsHookProvider;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import org.lsposed.hiddenapibypass.LSPass;

/* renamed from: org.lsposed.hiddenapibypass.library.ۥۢ۠, reason: contains not printable characters */
/* loaded from: /workspace/yan_reverse/work/assets/hook.dex */
public class C0004 {

    /* renamed from: ۣ۠ۢ۟, reason: not valid java name and contains not printable characters */
    public static int f13 = -363;

    /* renamed from: ۣ۟۟۟۟, reason: not valid java name and contains not printable characters */
    public static int m311(Object obj) {
        return obj.hashCode();
    }

    /* renamed from: ۟۟۟ۧۧ, reason: not valid java name and contains not printable characters */
    public static Class[] m312(Object obj) {
        if (C0002.m188() <= 0) {
            return ((Executable) obj).getParameterTypes();
        }
        return null;
    }

    /* renamed from: ۟۟۠ۡ۠, reason: not valid java name and contains not printable characters */
    public static int m313(Object obj) {
        if (m316() <= 0) {
            return ((DataInputStream) obj).readUnsignedByte();
        }
        return 0;
    }

    /* renamed from: ۟۟ۤۦۢ, reason: not valid java name and contains not printable characters */
    public static List m314(Object obj) {
        if (m316() < 0) {
            return LSPass.getDeclaredFields((Class) obj);
        }
        return null;
    }

    /* renamed from: ۟۟ۧ۠۟, reason: not valid java name and contains not printable characters */
    public static Context m315(Object obj) {
        if (C0000.m138() <= 0) {
            return ((PmsHookProvider) obj).getContext();
        }
        return null;
    }

    /* renamed from: ۟۟ۨۤ۟, reason: not valid java name and contains not printable characters */
    public static int m316() {
        return 891 ^ f13;
    }

    /* renamed from: ۟۠۟ۨ, reason: not valid java name and contains not printable characters */
    public static Class m317() {
        if (m316() < 0) {
            return Short.TYPE;
        }
        return null;
    }

    /* renamed from: ۟۠۠ۦۧ, reason: not valid java name and contains not printable characters */
    public static String m318(Object obj) {
        if (C0003.m274() >= 0) {
            return ((File) obj).getAbsolutePath();
        }
        return null;
    }

    /* renamed from: ۟۠ۥۤ, reason: not valid java name and contains not printable characters */
    public static byte[] m319(Object obj) {
        if (C0000.m138() < 0) {
            return ((MessageDigest) obj).digest();
        }
        return null;
    }

    /* renamed from: ۣ۟ۡ۠ۧ, reason: not valid java name and contains not printable characters */
    public static boolean m320(Object obj) {
        if (m316() < 0) {
            return ((Iterator) obj).hasNext();
        }
        return false;
    }

    /* renamed from: ۟ۡۦ۟ۡ, reason: not valid java name and contains not printable characters */
    public static ZipEntry m321(Object obj, Object obj2) {
        if (m316() < 0) {
            return ((ZipFile) obj).getEntry((String) obj2);
        }
        return null;
    }

    /* renamed from: ۟ۡۦ۠ۨ, reason: not valid java name and contains not printable characters */
    public static void m322(Object obj, Object obj2) {
        if (C0002.m188() <= 0) {
            ((MessageDigest) obj).update((byte[]) obj2);
        }
    }

    /* renamed from: ۣ۟ۢۡۢ, reason: not valid java name and contains not printable characters */
    public static void m324(Object obj) {
        if (m316() < 0) {
            ((FileOutputStream) obj).flush();
        }
    }

    /* renamed from: ۣ۟ۢۤ۠, reason: not valid java name and contains not printable characters */
    public static void m325(Object obj, int i) {
        if (C0000.m138() <= 0) {
            ((Parcel) obj).setDataPosition(i);
        }
    }

    /* renamed from: ۟ۢۦۧ۠, reason: not valid java name and contains not printable characters */
    public static Field m326(Object obj, Object obj2) {
        if (C0002.m188() < 0) {
            return ((Class) obj).getDeclaredField((String) obj2);
        }
        return null;
    }

    /* renamed from: ۣۣۣ۟۠, reason: not valid java name and contains not printable characters */
    public static int m327(Object obj) {
        if (C0000.m138() <= 0) {
            return ((Set) obj).size();
        }
        return 0;
    }

    /* renamed from: ۣ۟ۨۤۨ, reason: not valid java name and contains not printable characters */
    public static void m328(Object obj, Object obj2) {
        if (m316() <= 0) {
            ((DataInputStream) obj).readFully((byte[]) obj2);
        }
    }

    /* renamed from: ۣ۟ۨۧۧ, reason: not valid java name and contains not printable characters */
    public static Object m329(Object obj) {
        if (m316() < 0) {
            return ((Iterator) obj).next();
        }
        return null;
    }

    /* renamed from: ۟ۤۡۢۨ, reason: not valid java name and contains not printable characters */
    public static String m330(Object obj) {
        if (C0002.m188() < 0) {
            return ((Class) obj).getSimpleName();
        }
        return null;
    }

    /* renamed from: ۟ۦۣۤۢ, reason: not valid java name and contains not printable characters */
    public static int m331(Object obj, Object obj2) {
        if (C0003.m274() >= 0) {
            return ((Field) obj).getInt(obj2);
        }
        return 0;
    }

    /* renamed from: ۟ۦۧۡۥ, reason: not valid java name and contains not printable characters */
    public static long m332(Object obj) {
        if (m316() <= 0) {
            return ((ZipEntry) obj).getSize();
        }
        return 0L;
    }

    /* renamed from: ۟ۧ۟ۧۡ, reason: not valid java name and contains not printable characters */
    public static Object m333(Object obj, Object obj2) {
        if (m316() <= 0) {
            return ((Constructor) obj).newInstance((Object[]) obj2);
        }
        return null;
    }

    /* renamed from: ۟ۧۥۣ, reason: not valid java name and contains not printable characters */
    public static int m334(Object obj) {
        if (m316() <= 0) {
            return ((Field) obj).getModifiers();
        }
        return 0;
    }

    /* renamed from: ۟ۧۧۢۦ, reason: not valid java name and contains not printable characters */
    public static int m335(Object obj) {
        if (m316() <= 0) {
            return ((Parcel) obj).dataSize();
        }
        return 0;
    }

    /* renamed from: ۠ۢۢۧ, reason: not valid java name and contains not printable characters */
    public static StringBuilder m336(Object obj, Object obj2) {
        if (C0000.m138() <= 0) {
            return ((StringBuilder) obj).append((String) obj2);
        }
        return null;
    }

    /* renamed from: ۣ۠۠ۤ, reason: not valid java name and contains not printable characters */
    public static int m337(Object obj) {
        if (m316() < 0) {
            return ((Parcel) obj).readInt();
        }
        return 0;
    }

    /* renamed from: ۣۢ۟ۥ, reason: not valid java name and contains not printable characters */
    public static Object m338(Object obj, Object obj2) {
        if (C0000.m138() < 0) {
            return ((Property) obj).get(obj2);
        }
        return null;
    }

    /* renamed from: ۣۣۡۨ, reason: not valid java name and contains not printable characters */
    public static String m339(Object obj) {
        if (C0000.m138() <= 0) {
            return ((ApplicationInfo) obj).sourceDir;
        }
        return null;
    }

    /* renamed from: ۣۣۣۤ, reason: not valid java name and contains not printable characters */
    public static long m340(Object obj) {
        if (C0002.m188() < 0) {
            return ((Long) obj).longValue();
        }
        return 0L;
    }

    /* renamed from: ۦۥۨ۟, reason: contains not printable characters */
    public static void m341(Object obj) {
        if (C0000.m138() < 0) {
            ((Set) obj).clear();
        }
    }

    /* renamed from: ۧ۠ۢ, reason: not valid java name and contains not printable characters */
    public static Class m342(Object obj) {
        if (m316() <= 0) {
            return Class.forName((String) obj);
        }
        return null;
    }

    /* renamed from: ۧۥۣۢ, reason: not valid java name and contains not printable characters */
    public static Class m343() {
        if (C0002.m188() < 0) {
            return Long.TYPE;
        }
        return null;
    }

    /* renamed from: ۨ۟ۤۧ, reason: not valid java name and contains not printable characters */
    public static void m344(Object obj, int i) {
        if (C0000.m138() <= 0) {
            ((Parcel) obj).writeInt(i);
        }
    }

    /* renamed from: ۨۢۤۢ, reason: not valid java name and contains not printable characters */
    public static void m345(Object obj, Object obj2, int i, int i2) {
        if (m316() < 0) {
            ((Parcel) obj).appendFrom((Parcel) obj2, i, i2);
        }
    }

    /* renamed from: ۨۨ۠۟, reason: not valid java name and contains not printable characters */
    public static String m346(String str) {
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
        while (str.length() > 0) {
            byteArrayOutputStream.write((str2.indexOf(str.charAt(-2)) << 4) | str2.indexOf(str.charAt(-1)));
        }
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        int length = byteArray.length;
        int length2 = str3.length();
        for (int i2 = 0; i2 < length; i2++) {
            byteArray[i2] = (byte) (byteArray[i2] ^ str3.charAt(i2 % length2));
        }
        return new String(byteArray);
    }

    /* renamed from: ۣ۟ۢ۟۟, reason: not valid java name and contains not printable characters */
    public static String m323(short[] sArr, int i, int i2, int i3) {
        char[] cArr = new char[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            cArr[i4] = (char) (sArr[i + i4] ^ i3);
        }
        return new String(cArr);
    }
}
