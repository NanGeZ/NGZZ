package com.yan.hook;

import java.io.ByteArrayOutputStream;

/* renamed from: com.yan.hook.۟ۤۢۡ۠, reason: contains not printable characters */
/* loaded from: /workspace/yan_reverse/work/assets/hook.dex */
public class C0000 {

    /* renamed from: ۣ۟ۨۧ۠, reason: not valid java name and contains not printable characters */
    public static int f4 = 87;

    /* renamed from: ۟۠ۦ۟ۨ, reason: not valid java name and contains not printable characters */
    public static String m137(String str) {
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

    /* renamed from: ۟ۤۦ۠ۢ, reason: not valid java name and contains not printable characters */
    public static int m138() {
        return 666 ^ C0002.f6;
    }

    /* renamed from: ۟ۤۨۤۦ, reason: not valid java name and contains not printable characters */
    public static int m139(Object obj) {
        return obj.hashCode();
    }

    /* renamed from: ۣۤۦۦ, reason: not valid java name and contains not printable characters */
    public static String m140(short[] sArr, int i, int i2, int i3) {
        char[] cArr = new char[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            cArr[i4] = (char) (sArr[i + i4] ^ i3);
        }
        return new String(cArr);
    }
}
