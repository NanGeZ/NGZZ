package com.tianmen.ngz.engine.pipeline;

import java.io.File;

public final class ProtectResult {
    private final File outputApk;
    private final String packageName;
    private final boolean signed;

    public ProtectResult(File outputApk, String packageName, boolean signed) {
        this.outputApk = outputApk;
        this.packageName = packageName;
        this.signed = signed;
    }

    public File getOutputApk() {
        return outputApk;
    }

    public String getPackageName() {
        return packageName;
    }

    public boolean getSigned() {
        return signed;
    }
}
