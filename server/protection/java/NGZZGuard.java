package __NGZZ_PKG__;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Debug;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.security.MessageDigest;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Locale;

public final class NGZZGuard {

    public static boolean ENABLE_SIGNATURE = __ENABLE_SIGNATURE__;
    public static boolean ENABLE_ANTI_DEBUG = __ENABLE_ANTI_DEBUG__;
    public static boolean ENABLE_PACKAGE_CHECK = __ENABLE_PACKAGE_CHECK__;
    public static boolean ENABLE_ANTI_CAPTURE = __ENABLE_ANTI_CAPTURE__;
    public static boolean ENABLE_ROOT_CHECK = __ENABLE_ROOT_CHECK__;
    public static boolean ENABLE_NETWORK_CHECK = __ENABLE_NETWORK_CHECK__;
    public static int NETWORK_TIMEOUT_SECONDS = __NETWORK_TIMEOUT__;
    public static int NETWORK_RETRIES = __NETWORK_RETRIES__;

    public static String EXPECTED_PACKAGE = "__PKG__";
    public static String EXPECTED_SIGNATURE_SHA256 = "__SIG_SHA256__";

    private static final String TAG = "NGZZGuard";
    private static final long NETWORK_POLL_INTERVAL_MS = 4000L;
    private static final long DEBUG_POLL_INTERVAL_MS = 2500L;

    private static Application sApp;
    private static boolean sInstalled;
    private static boolean sAppResolved;
    private static volatile boolean sNetworkOk = true;

    private NGZZGuard() {
    }

    public static void install() {
        install(null);
    }

    public static synchronized void install(Application app) {
        if (app != null) {
            sApp = app;
            sAppResolved = true;
        } else if (!sAppResolved && sApp == null) {
            try {
                Object current = Class.forName("android.app.ActivityThread")
                        .getMethod("currentApplication").invoke(null);
                if (current instanceof Application) {
                    sApp = (Application) current;
                    sAppResolved = true;
                }
            } catch (Throwable ignored) {
            }
        }
        if (sApp == null || sInstalled) {
            return;
        }
        sInstalled = true;
        try {
            runSignatureCheck();
        } catch (Throwable t) {
            bail("签名校验异常");
        }
        try {
            runPackageCheck();
        } catch (Throwable t) {
            bail("包名校验异常");
        }
        try {
            if (ENABLE_ROOT_CHECK) {
                runRootCheck();
            }
        } catch (Throwable t) {
            bail("Root 检测异常");
        }
        try {
            if (ENABLE_ANTI_DEBUG) {
                runAntiDebugChecks();
                startDebuggerPolling();
            }
        } catch (Throwable t) {
            bail("反调试检测异常");
        }
        try {
            if (ENABLE_ANTI_CAPTURE) {
                startCaptureWatch();
            }
        } catch (Throwable t) {
            bail("抓包检测异常");
        }
        try {
            if (ENABLE_NETWORK_CHECK) {
                startNetworkWatch();
            }
        } catch (Throwable t) {
            bail("网络检测异常");
        }
    }

    private static void bail(String reason) {
        Log.w(TAG, "protect: " + reason);
        killProcess();
    }

    private static void killProcess() {
        try {
            if (sApp != null) {
                ActivityManager am = (ActivityManager) sApp.getSystemService(Context.ACTIVITY_SERVICE);
                if (am != null) {
                    am.killBackgroundProcesses(sApp.getPackageName());
                }
            }
        } catch (Throwable ignored) {
        }
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    private static void runSignatureCheck() {
        if (!ENABLE_SIGNATURE || EXPECTED_SIGNATURE_SHA256.length() == 0) {
            return;
        }
        String current = signatureSha256();
        if (current == null || !current.equalsIgnoreCase(EXPECTED_SIGNATURE_SHA256)) {
            bail("签名校验失败");
        }
    }

    private static String signatureSha256() {
        try {
            PackageManager pm = sApp.getPackageManager();
            String pkg = sApp.getPackageName();
            Signature[] sigs;
            if (Build.VERSION.SDK_INT >= 28) {
                PackageInfo pi = pm.getPackageInfo(pkg, PackageManager.GET_SIGNING_CERTIFICATES);
                if (pi.signingInfo == null) {
                    return null;
                }
                sigs = pi.signingInfo.getApkContentsSigners();
            } else {
                PackageInfo pi = pm.getPackageInfo(pkg, PackageManager.GET_SIGNATURES);
                sigs = pi.signatures;
            }
            if (sigs == null || sigs.length == 0) {
                return null;
            }
            byte[] cert = sigs[0].toByteArray();
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            X509Certificate x509 = (X509Certificate) cf.generateCertificate(new ByteArrayInputStream(cert));
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(x509.getEncoded());
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format(Locale.US, "%02x", b));
            }
            return sb.toString();
        } catch (Throwable t) {
            return null;
        }
    }

    private static void runPackageCheck() {
        if (!ENABLE_PACKAGE_CHECK || EXPECTED_PACKAGE.length() == 0) {
            return;
        }
        String current = sApp.getPackageName();
        if (!EXPECTED_PACKAGE.equals(current)) {
            bail("包名校验失败");
        }
    }

    private static void runRootCheck() {
        if (!hasRootEvidence()) {
            return;
        }
        bail("检测到 Root 环境");
    }

    private static boolean hasRootEvidence() {
        String[] paths = {
                "/system/bin/su", "/system/xbin/su", "/sbin/su", "/system/sd/xbin/su",
                "/system/bin/failsafe/su", "/data/local/su", "/data/local/bin/su",
                "/data/local/xbin/su", "/su/bin/su", "/system/app/Superuser.apk",
                "/system/app/SuperSU.apk", "/system/xbin/daemonsu", "/sbin/.magisk",
                "/data/adb/magisk", "/data/adb/modules"
        };
        for (String p : paths) {
            if (new File(p).exists()) {
                return true;
            }
        }
        String[] rootPackages = {
                "com.topjohnwu.magisk", "eu.chainfire.supersu", "com.noshufou.android.su",
                "com.koushikdutta.superuser", "com.thirdparty.superuser", "com.kingroot.kinguser",
                "com.kingo.root", "com.smedialink.oneclickroot", "com.zhiqupk.root.global"
        };
        try {
            PackageManager pm = sApp.getPackageManager();
            for (String p : rootPackages) {
                try {
                    pm.getPackageInfo(p, 0);
                    return true;
                } catch (PackageManager.NameNotFoundException ignored) {
                }
            }
        } catch (Throwable ignored) {
        }
        String[] tags = {"test-keys", "dev-keys"};
        try {
            String tagsValue = Build.TAGS;
            if (tagsValue != null) {
                for (String t : tags) {
                    if (tagsValue.contains(t)) {
                        return true;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        String[] commands = {"/system/xbin/which su", "which su", "/system/bin/which su"};
        for (String cmd : commands) {
            if (execExists(cmd)) {
                return true;
            }
        }
        try {
            if (Settings.Secure.getInt(sApp.getContentResolver(), Settings.Secure.ADB_ENABLED, 0) == 1) {
                return false;
            }
        } catch (Throwable ignored) {
        }
        return false;
    }

    private static boolean execExists(String cmd) {
        Process process = null;
        try {
            process = Runtime.getRuntime().exec(cmd);
            BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = br.readLine();
            return line != null && line.trim().length() > 0;
        } catch (Throwable ignored) {
            return false;
        } finally {
            if (process != null) {
                process.destroy();
            }
        }
    }

    private static void runAntiDebugChecks() {
        if (Debug.isDebuggerConnected()) {
            bail("检测到调试器");
        }
        if (isTracerPidPresent()) {
            bail("检测到进程注入");
        }
        if (hasHookArtifacts()) {
            bail("检测到 Hook 框架");
        }
    }

    private static boolean isTracerPidPresent() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    new FileInputStream("/proc/self/status"), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("TracerPid:")) {
                    String value = line.substring(line.indexOf(':') + 1).trim();
                    return Integer.parseInt(value) > 0;
                }
            }
        } catch (Throwable ignored) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return false;
    }

    private static boolean hasHookArtifacts() {
        String[] keywords = {
                "frida", "xposed", "lsposed", "edxposed", "magisk", "substrate",
                "libgadget", "libfrida", "re.frida.server", "de.robv.android.xposed"
        };
        try {
            Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces();
            if (interfaces != null) {
                for (NetworkInterface nif : Collections.list(interfaces)) {
                    String name = nif.getName() == null ? "" : nif.getName().toLowerCase(Locale.US);
                    if (name.contains("frida") || name.contains("gadget")) {
                        return true;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            String maps = readFile("/proc/self/maps");
            String lower = maps.toLowerCase(Locale.US);
            for (String k : keywords) {
                if (lower.contains(k)) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
        }
        try {
            String cmdline = readFile("/proc/self/cmdline").toLowerCase(Locale.US);
            for (String k : keywords) {
                if (cmdline.contains(k)) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
        }
        String[] ports = {"27042", "27043", "23946"};
        for (String p : ports) {
            if (localPortOpen(Integer.parseInt(p))) {
                return true;
            }
        }
        return false;
    }

    private static boolean localPortOpen(int port) {
        Socket socket = null;
        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("127.0.0.1", port), 300);
            return true;
        } catch (Throwable ignored) {
            return false;
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException ignored) {
                }
            }
        }
    }

    private static String readFile(String path) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (Throwable ignored) {
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ignored) {
                }
            }
        }
        return sb.toString();
    }

    private static void startDebuggerPolling() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                if (Debug.isDebuggerConnected() || isTracerPidPresent() || hasHookArtifacts()) {
                    bail("运行期检测到调试/注入");
                    return;
                }
                handler.postDelayed(this, DEBUG_POLL_INTERVAL_MS);
            }
        };
        handler.postDelayed(task, DEBUG_POLL_INTERVAL_MS);
    }

    private static void startCaptureWatch() {
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                if (isVpnActive() || isProxySet() || isWifiProxied()) {
                    bail("检测到抓包环境");
                    return;
                }
                handler.postDelayed(this, 3000L);
            }
        };
        handler.post(task);
    }

    private static boolean isVpnActive() {
        try {
            ConnectivityManager cm = (ConnectivityManager) sApp.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return false;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                Network[] networks = cm.getAllNetworks();
                if (networks == null) {
                    return false;
                }
                for (Network network : networks) {
                    NetworkCapabilities caps = cm.getNetworkCapabilities(network);
                    if (caps != null && caps.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                        return true;
                    }
                }
                return false;
            }
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos == null) {
                return false;
            }
            for (NetworkInfo info : infos) {
                if (info != null && "vpn".equalsIgnoreCase(info.getTypeName())) {
                    return true;
                }
            }
        } catch (Throwable ignored) {
        }
        return false;
    }

    private static boolean isProxySet() {
        try {
            String host = System.getProperty("http.proxyHost");
            String port = System.getProperty("http.proxyPort");
            if (host != null && host.trim().length() > 0) {
                return true;
            }
            if (port != null && port.trim().length() > 0 && !"0".equals(port.trim())) {
                return true;
            }
        } catch (Throwable ignored) {
        }
        try {
            String host = Settings.Global.getString(sApp.getContentResolver(), "http_proxy");
            return host != null && host.trim().length() > 0 && !"0.0.0.0:0".equals(host.trim());
        } catch (Throwable ignored) {
        }
        return false;
    }

    private static boolean isWifiProxied() {
        try {
            WifiManager wm = (WifiManager) sApp.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            if (wm == null) {
                return false;
            }
            WifiInfo info = wm.getConnectionInfo();
            if (info == null) {
                return false;
            }
            List<WifiConfiguration> configs = wm.getConfiguredNetworks();
            if (configs == null) {
                return false;
            }
            int netId = info.getNetworkId();
            for (WifiConfiguration cfg : configs) {
                if (cfg != null && cfg.networkId == netId) {
                    try {
                        Object link = cfg.getClass().getField("linkProperties").get(cfg);
                        if (link != null) {
                            Object props = link.getClass().getMethod("getHttpProxy").invoke(link);
                            if (props != null) {
                                return true;
                            }
                        }
                    } catch (Throwable ignored) {
                    }
                    String s = String.valueOf(cfg);
                    if (s.contains("Proxy") && !s.contains("PROXY_NONE")) {
                        return true;
                    }
                }
            }
        } catch (Throwable ignored) {
        }
        return false;
    }

    private static void startNetworkWatch() {
        sNetworkOk = true;
        final Handler handler = new Handler(Looper.getMainLooper());
        final Runnable task = new Runnable() {
            @Override
            public void run() {
                if (!isNetworkAvailable()) {
                    bail("网络不可用，程序退出");
                    return;
                }
                if (!sNetworkOk) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            sNetworkOk = probeNetwork();
                        }
                    }).start();
                }
                handler.postDelayed(this, NETWORK_POLL_INTERVAL_MS);
            }
        };
        handler.post(task);
    }

    private static boolean isNetworkAvailable() {
        try {
            ConnectivityManager cm = (ConnectivityManager) sApp.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (cm == null) {
                return true;
            }
            if (Build.VERSION.SDK_INT >= 23) {
                Network network = cm.getActiveNetwork();
                if (network == null) {
                    return false;
                }
                NetworkCapabilities caps = cm.getNetworkCapabilities(network);
                return caps != null && caps.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
            }
            NetworkInfo info = cm.getActiveNetworkInfo();
            return info != null && info.isConnected();
        } catch (Throwable ignored) {
            return true;
        }
    }

    private static boolean probeNetwork() {
        String[] hosts = {"www.baidu.com", "www.qq.com", "connectivitycheck.gstatic.com"};
        for (int attempt = 0; attempt < NETWORK_RETRIES; attempt++) {
            for (String host : hosts) {
                Socket socket = null;
                try {
                    socket = new Socket();
                    socket.connect(new InetSocketAddress(host, 80), NETWORK_TIMEOUT_SECONDS * 1000);
                    return true;
                } catch (Throwable ignored) {
                } finally {
                    if (socket != null) {
                        try {
                            socket.close();
                        } catch (IOException ignored) {
                        }
                    }
                }
            }
        }
        return false;
    }
}
