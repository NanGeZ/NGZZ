package com.tianmen.ngz.engine;

import net.dongliu.apk.parser.ApkFile;
import net.dongliu.apk.parser.bean.ApkMeta;

import java.io.File;
import java.io.IOException;

public class ApkInspector {

    public ApkInfo inspect(File apk) {
        try (ApkFile apkFile = new ApkFile(apk)) {
            ApkMeta meta = apkFile.getApkMeta();
            String entry = findLauncherActivity(apkFile);
            return new ApkInfo(
                    meta.getPackageName(),
                    meta.getVersionName(),
                    entry
            );
        } catch (IOException | RuntimeException e) {
            throw new EngineException("无法解析 APK: " + apk.getName() + " (" + e.getMessage() + ")");
        }
    }

    private String findLauncherActivity(ApkFile apkFile) {
        try {
            String manifest = apkFile.getManifestXml();
            if (manifest == null) {
                return null;
            }
            java.util.regex.Matcher activity =
                    java.util.regex.Pattern
                            .compile("<activity[\\s\\S]*?</activity>")
                            .matcher(manifest);
            while (activity.find()) {
                String block = activity.group();
                if (block.contains("android.intent.action.MAIN")
                        && block.contains("android.intent.category.LAUNCHER")) {
                    java.util.regex.Matcher name =
                            java.util.regex.Pattern
                                    .compile("android:name=\"([^\"]+)\"")
                                    .matcher(block);
                    if (name.find()) {
                        String raw = name.group(1);
                        if (raw.startsWith(".")) {
                            return apkFile.getApkMeta().getPackageName() + raw;
                        }
                        return raw;
                    }
                }
            }
        } catch (IOException | RuntimeException ignored) {
        }
        return null;
    }
}
