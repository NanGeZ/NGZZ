package com.nantianmen.protect.core.protect

object GuardSource {

    fun guardApplication(originalClass: String, pkg: String): String {
        val hasDelegate = originalClass != "android.app.Application" && originalClass.isNotEmpty()

        val delegateDecl = if (hasDelegate) {
            "private final $originalClass delegate = new $originalClass();"
        } else {
            "private final android.app.Application delegate = null;"
        }

        val attach = if (hasDelegate) {
            """
            super.attachBaseContext(base);
            try {
                java.lang.reflect.Method m = delegate.getClass().getDeclaredMethod("attachBaseContext", Context.class);
                m.setAccessible(true);
                m.invoke(delegate, base);
            } catch (Throwable t) {}
            """.trimIndent()
        } else {
            "super.attachBaseContext(base);"
        }

        val onCreate = if (hasDelegate) {
            """
            super.onCreate();
            try {
                delegate.onCreate();
            } catch (Throwable t) {}
            """.trimIndent()
        } else {
            "super.onCreate();"
        }

        return """
            package com.nantianmen.guard;

            import android.app.Application;
            import android.content.Context;

            public class GuardApplication extends Application {
                $delegateDecl

                @Override
                protected void attachBaseContext(Context base) {
                    $attach
                    com.nantianmen.guard.GuardInit.init(base, "$pkg");
                }

                @Override
                public void onCreate() {
                    $onCreate
                    com.nantianmen.guard.GuardInit.init(this, "$pkg");
                }
            }
        """.trimIndent()
    }

    fun guardInit(): String {
        return """
            package com.nantianmen.guard;

            import android.content.Context;

            public class GuardInit {
                private static boolean initialized = false;

                public static synchronized void init(Context ctx, String expectedPkg) {
                    if (initialized) return;
                    initialized = true;
                    int features = com.nantianmen.guard.GuardConfig.features;
                    if (features == 0) return;
                    try {
                        System.loadLibrary("nantianmen_guard");
                        com.nantianmen.guard.Guard.nativeInit(features, expectedPkg != null ? expectedPkg : ctx.getPackageName());
                    } catch (Throwable t) {
                    }
                }
            }
        """.trimIndent()
    }

    fun guardConfig(features: Int, pkg: String): String {
        return """
            package com.nantianmen.guard;

            public class GuardConfig {
                public static final int features = $features;
                public static final String targetPackage = "$pkg";
            }
        """.trimIndent()
    }

    fun guard(): String {
        return """
            package com.nantianmen.guard;

            public class Guard {
                static {
                    try {
                        System.loadLibrary("nantianmen_guard");
                    } catch (Throwable t) {
                    }
                }

                public static native void nativeInit(int features, String expectedPkg);
                public static native int nativeCheck();

                public static void onViolation(String type, String detail) {
                    android.util.Log.w("NantianmenGuard", "violation: " + type + " " + detail);
                    try {
                        android.os.Process.killProcess(android.os.Process.myPid());
                    } catch (Throwable t) {
                    }
                    System.exit(1);
                }
            }
        """.trimIndent()
    }

    fun bootstrap(): String {
        return """
            package com.nantianmen.guard;

            import android.content.Context;
            import android.os.Handler;
            import android.os.Looper;

            public class Bootstrap {
                private static boolean started = false;

                public static synchronized void start(final Context ctx, final String expectedPkg) {
                    if (started) return;
                    started = true;
                    int features = GuardConfig.features;
                    if (features == 0) return;
                    try {
                        System.loadLibrary("nantianmen_guard");
                        Guard.nativeInit(features, expectedPkg != null ? expectedPkg : ctx.getPackageName());
                    } catch (Throwable t) {
                        startFallback(ctx, expectedPkg, features);
                    }
                }

                private static void startFallback(final Context ctx, final String expectedPkg, final int features) {
                    final Handler h = new Handler(Looper.getMainLooper());
                    h.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            int violations = FallbackChecks.checkAll(ctx, expectedPkg, features);
                            if (violations > 0) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                            h.postDelayed(this, 3000);
                        }
                    }, 3000);
                }
            }
        """.trimIndent()
    }

    fun fallbackChecks(): String {
        return """
            package com.nantianmen.guard;

            import android.content.Context;
            import android.net.ConnectivityManager;
            import android.net.NetworkInfo;
            import android.net.ProxyInfo;
            import java.io.BufferedReader;
            import java.io.File;
            import java.io.FileReader;

            public class FallbackChecks {
                public static int checkAll(Context ctx, String expectedPkg, int features) {
                    int v = 0;
                    if ((features & 1) != 0) v += checkDebug();
                    if ((features & 2) != 0) v += checkPackage(expectedPkg);
                    if ((features & 4) != 0) v += checkProxy(ctx);
                    if ((features & 8) != 0) v += checkRoot();
                    if ((features & 16) != 0) v += checkNetwork(ctx);
                    return v;
                }

                static int checkDebug() {
                    try {
                        BufferedReader r = new BufferedReader(new FileReader("/proc/self/status"));
                        String line;
                        while ((line = r.readLine()) != null) {
                            if (line.startsWith("TracerPid:")) {
                                int pid = Integer.parseInt(line.substring(10).trim());
                                r.close();
                                return pid > 0 ? 1 : 0;
                            }
                        }
                        r.close();
                    } catch (Throwable t) {}
                    return 0;
                }

                static int checkPackage(String expectedPkg) {
                    if (expectedPkg == null || expectedPkg.length() == 0) return 0;
                    try {
                        BufferedReader r = new BufferedReader(new FileReader("/proc/self/cmdline"));
                        String line = r.readLine();
                        r.close();
                        if (line != null && !line.contains(expectedPkg)) return 1;
                    } catch (Throwable t) {}
                    return 0;
                }

                static int checkProxy(Context ctx) {
                    try {
                        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                        ProxyInfo p = cm.getDefaultProxy();
                        if (p != null && p.getHost() != null && p.getHost().length() > 0) return 1;
                    } catch (Throwable t) {}
                    return 0;
                }

                static int checkRoot() {
                    String[] paths = {
                        "/system/bin/su", "/system/xbin/su", "/sbin/su", "/su/bin/su",
                        "/system/app/Superuser.apk", "/system/xbin/daemonsu"
                    };
                    for (String p : paths) {
                        if (new File(p).exists()) return 1;
                    }
                    try {
                        BufferedReader r = new BufferedReader(new FileReader("/system/build.tags"));
                        String line = r.readLine();
                        r.close();
                        if (line != null && line.contains("test-keys")) return 1;
                    } catch (Throwable t) {}
                    return 0;
                }

                static int checkNetwork(Context ctx) {
                    try {
                        ConnectivityManager cm = (ConnectivityManager) ctx.getSystemService(Context.CONNECTIVITY_SERVICE);
                        NetworkInfo info = cm.getActiveNetworkInfo();
                        return (info == null || !info.isConnected()) ? 1 : 0;
                    } catch (Throwable t) {
                        return 0;
                    }
                }
            }
        """.trimIndent()
    }
}
