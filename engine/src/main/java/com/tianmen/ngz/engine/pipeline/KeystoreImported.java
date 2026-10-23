package com.tianmen.ngz.engine.pipeline;

import java.io.File;

public final class KeystoreImported {
    private final File keystore;
    private final String alias;

    public KeystoreImported(File keystore, String alias) {
        this.keystore = keystore;
        this.alias = alias;
    }

    public File getKeystore() {
        return keystore;
    }

    public String getAlias() {
        return alias;
    }
}
