package com.tianmen.ngz.engine.pipeline;

import java.io.File;

public final class KeystoreSpec {
    private final File keystore;
    private final String keyAlias;
    private final String keyPassword;
    private final String storePassword;

    public KeystoreSpec(File keystore, String keyAlias, String keyPassword, String storePassword) {
        this.keystore = keystore;
        this.keyAlias = keyAlias;
        this.keyPassword = keyPassword;
        this.storePassword = storePassword;
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
}
