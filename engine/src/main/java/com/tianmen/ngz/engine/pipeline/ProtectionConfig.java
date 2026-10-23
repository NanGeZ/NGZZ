package com.tianmen.ngz.engine.pipeline;

import java.io.File;

public final class ProtectionConfig {
    private final File inputApk;
    private final File outputApk;
    private final boolean autoSign;
    private final File keystore;
    private final String keyAlias;
    private final String keyPassword;
    private final String storePassword;
    private final boolean injectGuard;

    private ProtectionConfig(Builder b) {
        this.inputApk = b.inputApk;
        this.outputApk = b.outputApk;
        this.autoSign = b.autoSign;
        this.keystore = b.keystore;
        this.keyAlias = b.keyAlias;
        this.keyPassword = b.keyPassword;
        this.storePassword = b.storePassword;
        this.injectGuard = b.injectGuard;
    }

    public File getInputApk() {
        return inputApk;
    }

    public File getOutputApk() {
        return outputApk;
    }

    public boolean isAutoSign() {
        return autoSign;
    }

    public File getKeystore() {
        return keystore;
    }

    public String getKeyAlias() {
        return keyAlias;
    }

    public String getKeyPassword() {
        return keyPassword;
    }

    public String getStorePassword() {
        return storePassword;
    }

    public boolean isInjectGuard() {
        return injectGuard;
    }

    public static final class Builder {
        private File inputApk;
        private File outputApk;
        private boolean autoSign;
        private File keystore;
        private String keyAlias;
        private String keyPassword;
        private String storePassword;
        private boolean injectGuard;

        public Builder inputApk(File inputApk) {
            this.inputApk = inputApk;
            return this;
        }

        public Builder outputApk(File outputApk) {
            this.outputApk = outputApk;
            return this;
        }

        public Builder autoSign(boolean autoSign) {
            this.autoSign = autoSign;
            return this;
        }

        public Builder keystore(File keystore) {
            this.keystore = keystore;
            return this;
        }

        public Builder keyAlias(String keyAlias) {
            this.keyAlias = keyAlias;
            return this;
        }

        public Builder keyPassword(String keyPassword) {
            this.keyPassword = keyPassword;
            return this;
        }

        public Builder storePassword(String storePassword) {
            this.storePassword = storePassword;
            return this;
        }

        public Builder injectGuard(boolean injectGuard) {
            this.injectGuard = injectGuard;
            return this;
        }

        public ProtectionConfig build() {
            return new ProtectionConfig(this);
        }
    }
}
