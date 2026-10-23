package com.tianmen.ngz.engine;

public class ApkInfo {

    private final String packageName;
    private final String versionName;
    private final String entryActivity;

    public ApkInfo(String packageName, String versionName, String entryActivity) {
        this.packageName = packageName;
        this.versionName = versionName;
        this.entryActivity = entryActivity;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getVersionName() {
        return versionName;
    }

    public String getEntryActivity() {
        return entryActivity;
    }
}
