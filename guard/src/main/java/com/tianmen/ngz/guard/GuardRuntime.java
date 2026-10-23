package com.tianmen.ngz.guard;

import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Debug;
import android.os.Process;
import android.util.Log;

import com.tianmen.ngz.engine.guard.GuardConfig;
import com.tianmen.ngz.engine.guard.GuardHit;
import com.tianmen.ngz.engine.guard.GuardPolicy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public final class GuardRuntime {

    private static final String[] SU_PATHS = {
            "/system/app/Superuser.apk",
            "/system/xbin/su",
            "/system/bin/su",
            "/sbin/su",
    };

    private final Context context;
    private final GuardPolicy policy;

    public GuardRuntime(Context context, GuardConfig config) {
        this.context = context;
        this.policy = new GuardPolicy(config);
    }

    public void runChecks() {
        apply(GuardHit.SIGNATURE, checkSignature());
        apply(GuardHit.DEBUG, checkAntiDebug());
        apply(GuardHit.PACKAGE_NAME, checkPackageName());
        apply(GuardHit.CAPTURE, checkCapture());
        apply(GuardHit.ROOT, checkRoot());
        apply(GuardHit.NETWORK, checkNetwork());
        apply(GuardHit.EXPIRY, checkExpiry());
    }

    private void apply(GuardHit hit, GuardPolicy.Decision decision) {
        if (decision == GuardPolicy.Decision.EXIT) {
            Process.killProcess(Process.myPid());
        } else if (decision == GuardPolicy.Decision.LOG) {
            Log.w("NgzGuard", "hit: " + hit.name());
        }
    }

    private GuardPolicy.Decision checkSignature() {
        try {
            PackageManager pm = context.getPackageManager();
            android.content.pm.PackageInfo info =
                    pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            boolean missing = info.signatures == null || info.signatures.length == 0;
            return missing ? policy.decide(GuardHit.SIGNATURE) : GuardPolicy.Decision.IGNORE;
        } catch (Exception e) {
            return policy.decide(GuardHit.SIGNATURE);
        }
    }

    private GuardPolicy.Decision checkAntiDebug() {
        if (Debug.isDebuggerConnected()) {
            return policy.decide(GuardHit.DEBUG);
        }
        if (readTracerPid() > 0) {
            return policy.decide(GuardHit.DEBUG);
        }
        return GuardPolicy.Decision.IGNORE;
    }

    private int readTracerPid() {
        File status = new File("/proc/self/status");
        if (!status.exists()) {
            return 0;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(status))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.startsWith("TracerPid:")) {
                    return Integer.parseInt(line.substring("TracerPid:".length()).trim());
                }
            }
        } catch (IOException | NumberFormatException ignored) {
        }
        return 0;
    }

    private GuardPolicy.Decision checkPackageName() {
        if (context.getPackageName() == null || context.getPackageName().isEmpty()) {
            return GuardPolicy.Decision.IGNORE;
        }
        return policy.decide(GuardHit.PACKAGE_NAME);
    }

    private GuardPolicy.Decision checkCapture() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return GuardPolicy.Decision.IGNORE;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return GuardPolicy.Decision.IGNORE;
        }
        if (info.getType() == ConnectivityManager.TYPE_VPN) {
            return policy.decide(GuardHit.CAPTURE);
        }
        String extra = info.getExtraInfo();
        if (extra != null && (extra.contains("proxy") || extra.contains("http_proxy"))) {
            return policy.decide(GuardHit.CAPTURE);
        }
        return GuardPolicy.Decision.IGNORE;
    }

    private GuardPolicy.Decision checkRoot() {
        for (String path : SU_PATHS) {
            if (new File(path).exists()) {
                return policy.decide(GuardHit.ROOT);
            }
        }
        if (Build.TAGS != null && Build.TAGS.contains("test-keys")) {
            return policy.decide(GuardHit.ROOT);
        }
        return GuardPolicy.Decision.IGNORE;
    }

    private GuardPolicy.Decision checkNetwork() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm == null) {
            return GuardPolicy.Decision.IGNORE;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null || !info.isConnected()) {
            return policy.decide(GuardHit.NETWORK);
        }
        return GuardPolicy.Decision.IGNORE;
    }

    private GuardPolicy.Decision checkExpiry() {
        return policy.decide(GuardHit.EXPIRY);
    }
}
