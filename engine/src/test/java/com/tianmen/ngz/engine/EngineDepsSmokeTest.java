package com.tianmen.ngz.engine;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class EngineDepsSmokeTest {

    @Test
    public void apkParserAvailable() throws Exception {
        assertNotNull(Class.forName("net.dongliu.apk.parser.ApkFile"));
    }

    @Test
    public void apksigAvailable() throws Exception {
        assertNotNull(Class.forName("com.android.apksig.ApkSigner"));
    }

    @Test
    public void dexlib2Available() throws Exception {
        assertNotNull(Class.forName("org.jf.dexlib2.Opcodes"));
    }
}
