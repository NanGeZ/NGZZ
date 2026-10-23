package com.tianmen.ngz.engine.guard;

public final class GuardPolicy {

    public enum Decision {
        EXIT,
        LOG,
        IGNORE
    }

    private final GuardConfig config;

    public GuardPolicy(GuardConfig config) {
        this.config = config;
    }

    public Decision decide(GuardHit hit) {
        if (!isEnabled(hit)) {
            return Decision.IGNORE;
        }
        if (hit == GuardHit.EXPIRY) {
            long expiry = config.getExpiryEpochMillis();
            if (expiry <= 0) {
                return Decision.IGNORE;
            }
            if (System.currentTimeMillis() <= expiry) {
                return Decision.IGNORE;
            }
        }
        return config.isExitOnHit() ? Decision.EXIT : Decision.LOG;
    }

    private boolean isEnabled(GuardHit hit) {
        switch (hit) {
            case SIGNATURE:
                return config.isCheckSignature();
            case DEBUG:
                return config.isAntiDebug();
            case PACKAGE_NAME:
                return config.isCheckPackageName();
            case CAPTURE:
                return config.isCheckCapture();
            case ROOT:
                return config.isCheckRoot();
            case NETWORK:
                return config.isCheckNetwork();
            case EXPIRY:
                return config.isCheckExpiry();
            default:
                return false;
        }
    }
}
