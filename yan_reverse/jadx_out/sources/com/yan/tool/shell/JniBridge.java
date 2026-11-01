package com.yan.tool.shell;

import java.io.File;

/* loaded from: classes.dex */
public class JniBridge {
    public static void a(String str) {
        String[] strArr = {"lib830f622c2304076e.so"};
        try {
            String a = a.a();
            StringBuilder sb = new StringBuilder();
            sb.append(str);
            String str2 = File.separator;
            sb.append(str2);
            sb.append("dpt-libs");
            sb.append(str2);
            sb.append(a);
            File[] listFiles = new File(sb.toString()).listFiles();
            if (listFiles != null) {
                for (File file : listFiles) {
                    String absolutePath = file.getAbsolutePath();
                    if (absolutePath.endsWith(File.separator + strArr[0])) {
                        System.load(absolutePath);
                    }
                }
            }
        } catch (Throwable unused) {
        }
    }

    public static native void cbde(ClassLoader classLoader);

    public static native void clinit();

    public static native void craoc(String str);

    public static native String gap();

    public static native String gdp();

    public static native void ia();

    public static native Object ra(String str);

    public static native String rapn();

    public static native String rcf();

    public static native void rde(ClassLoader classLoader, String str);
}
