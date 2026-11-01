package com.yan.tool.shell;

import android.content.pm.ApplicationInfo;
import android.system.ErrnoException;
import android.system.Os;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Field;
import java.util.Enumeration;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/* loaded from: classes.dex */
public abstract class a {
    public static volatile boolean a = false;
    public static volatile boolean b = true;

    public static String a() {
        try {
            Class<?> cls = Class.forName("dalvik.system.VMRuntime");
            return (String) cls.getDeclaredMethod("vmInstructionSet", null).invoke(cls.getDeclaredMethod("getRuntime", null).invoke(null, null), null);
        } catch (Exception e) {
            e.printStackTrace();
            return "arm64";
        }
    }

    public static ApplicationInfo b() {
        try {
            Class<?> cls = Class.forName("android.app.ActivityThread");
            Field declaredField = cls.getDeclaredField("sCurrentActivityThread");
            declaredField.setAccessible(true);
            Object obj = declaredField.get(null);
            Field declaredField2 = cls.getDeclaredField("mBoundApplication");
            declaredField2.setAccessible(true);
            Object obj2 = declaredField2.get(obj);
            Field declaredField3 = obj2.getClass().getDeclaredField("appInfo");
            declaredField3.setAccessible(true);
            return (ApplicationInfo) declaredField3.get(obj2);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x00d7, code lost:
    
        r12 = new byte[4096];
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x00dd, code lost:
    
        if (r2.exists() == false) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x00df, code lost:
    
        r2.setWritable(true);
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x00e5, code lost:
    
        r0 = new java.io.FileOutputStream(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00ea, code lost:
    
        r3 = new java.io.BufferedOutputStream(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x00ef, code lost:
    
        r4 = new java.io.BufferedInputStream(r7.getInputStream(r8));
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x00f8, code lost:
    
        r5 = r4.read(r12);
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00fc, code lost:
    
        if (r5 == (-1)) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:30:0x00fe, code lost:
    
        r3.write(r12, 0, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x0104, code lost:
    
        r4.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x0107, code lost:
    
        r3.close();
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x010a, code lost:
    
        r0.close();
        r8.getName();
        r8.getCrc();
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x0102, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0120, code lost:
    
        throw r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0116, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0129, code lost:
    
        throw r12;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0114, code lost:
    
        r12 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0132, code lost:
    
        throw r12;
     */
    /* JADX WARN: Finally extract failed */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static void c(String str, String str2) {
        String a2 = a();
        StringBuilder sb = new StringBuilder();
        sb.append(str2);
        String str3 = File.separator;
        sb.append(str3);
        sb.append("dpt-libs");
        sb.append(str3);
        sb.append(a2);
        String str4 = "assets/vwwwwwvwww/" + a2 + "/lib830f622c2304076e.so";
        String absolutePath = new File(sb.toString()).getAbsolutePath();
        System.currentTimeMillis();
        File file = new File(absolutePath);
        if (!file.exists()) {
            file.mkdirs();
        }
        File file2 = new File(absolutePath + str3 + "lib830f622c2304076e.so");
        long j = 0;
        if (file2.exists()) {
            try {
                FileInputStream fileInputStream = new FileInputStream(file2);
                try {
                    CheckedInputStream checkedInputStream = new CheckedInputStream(fileInputStream, new CRC32());
                    try {
                        while (checkedInputStream.read(new byte[4096]) != -1) {
                        }
                        j = checkedInputStream.getChecksum().getValue();
                        checkedInputStream.close();
                        fileInputStream.close();
                    } finally {
                    }
                } finally {
                }
            } catch (Throwable unused) {
            }
        }
        try {
            ZipFile zipFile = new ZipFile(str);
            try {
                Enumeration<? extends ZipEntry> entries = zipFile.entries();
                while (true) {
                    if (!entries.hasMoreElements()) {
                        break;
                    }
                    ZipEntry nextElement = entries.nextElement();
                    if (nextElement.getName().equals(str4) && j != nextElement.getCrc()) {
                        break;
                    }
                }
                zipFile.close();
            } catch (Throwable th) {
                try {
                    zipFile.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (file2.exists()) {
            try {
                Os.chmod(file2.getAbsolutePath(), 292);
            } catch (ErrnoException unused2) {
                file2.getAbsolutePath();
                file2.setReadable(true, false);
                file2.setWritable(false, false);
            }
        }
        System.currentTimeMillis();
    }
}
