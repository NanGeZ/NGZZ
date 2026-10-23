package com.tianmen.ngz.engine.pipeline;

import com.tianmen.ngz.engine.EngineException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.fail;

public class KeystoreSignerTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void signRejectsMissingInput() {
        KeystoreSigner signer = new KeystoreSigner();
        KeystoreSpec spec = new KeystoreSpec(new File(temp.getRoot(), "k.jks"), "alias", "kp", "sp");
        try {
            signer.sign(new File(temp.getRoot(), "missing.apk"), spec, new File(temp.getRoot(), "out.apk"));
            fail("expected EngineException");
        } catch (EngineException expected) {
        }
    }

    @Test
    public void signRejectsMissingKeystore() throws Exception {
        File input = new File(temp.getRoot(), "in.zip");
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(input))) {
            zos.putNextEntry(new ZipEntry("a.txt"));
            zos.write("hello".getBytes("UTF-8"));
            zos.closeEntry();
        }
        KeystoreSigner signer = new KeystoreSigner();
        KeystoreSpec spec = new KeystoreSpec(new File(temp.getRoot(), "missing.jks"), "alias", "kp", "sp");
        try {
            signer.sign(input, spec, new File(temp.getRoot(), "out.apk"));
            fail("expected EngineException");
        } catch (EngineException expected) {
        }
    }
}
