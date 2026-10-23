package com.tianmen.ngz.engine.guard;

public final class GuardConfig {
    private final boolean checkSignature;
    private final boolean antiDebug;
    private final boolean checkPackageName;
    private final boolean checkCapture;
    private final boolean checkRoot;
    private final boolean checkNetwork;
    private final boolean checkExpiry;
    private final long expiryEpochMillis;
    private final boolean exitOnHit;

    private GuardConfig(Builder b) {
        this.checkSignature = b.checkSignature;
        this.antiDebug = b.antiDebug;
        this.checkPackageName = b.checkPackageName;
        this.checkCapture = b.checkCapture;
        this.checkRoot = b.checkRoot;
        this.checkNetwork = b.checkNetwork;
        this.checkExpiry = b.checkExpiry;
        this.expiryEpochMillis = b.expiryEpochMillis;
        this.exitOnHit = b.exitOnHit;
    }

    public boolean isCheckSignature() {
        return checkSignature;
    }

    public boolean isAntiDebug() {
        return antiDebug;
    }

    public boolean isCheckPackageName() {
        return checkPackageName;
    }

    public boolean isCheckCapture() {
        return checkCapture;
    }

    public boolean isCheckRoot() {
        return checkRoot;
    }

    public boolean isCheckNetwork() {
        return checkNetwork;
    }

    public boolean isCheckExpiry() {
        return checkExpiry;
    }

    public long getExpiryEpochMillis() {
        return expiryEpochMillis;
    }

    public boolean isExitOnHit() {
        return exitOnHit;
    }

    public static final class Builder {
        private boolean checkSignature;
        private boolean antiDebug;
        private boolean checkPackageName;
        private boolean checkCapture;
        private boolean checkRoot;
        private boolean checkNetwork;
        private boolean checkExpiry;
        private long expiryEpochMillis;
        private boolean exitOnHit;

        public Builder checkSignature(boolean checkSignature) {
            this.checkSignature = checkSignature;
            return this;
        }

        public Builder antiDebug(boolean antiDebug) {
            this.antiDebug = antiDebug;
            return this;
        }

        public Builder checkPackageName(boolean checkPackageName) {
            this.checkPackageName = checkPackageName;
            return this;
        }

        public Builder checkCapture(boolean checkCapture) {
            this.checkCapture = checkCapture;
            return this;
        }

        public Builder checkRoot(boolean checkRoot) {
            this.checkRoot = checkRoot;
            return this;
        }

        public Builder checkNetwork(boolean checkNetwork) {
            this.checkNetwork = checkNetwork;
            return this;
        }

        public Builder checkExpiry(boolean checkExpiry) {
            this.checkExpiry = checkExpiry;
            return this;
        }

        public Builder expiryEpochMillis(long expiryEpochMillis) {
            this.expiryEpochMillis = expiryEpochMillis;
            return this;
        }

        public Builder exitOnHit(boolean exitOnHit) {
            this.exitOnHit = exitOnHit;
            return this;
        }

        public GuardConfig build() {
            return new GuardConfig(this);
        }
    }
}
