package com.tianmen.ngz.engine.pipeline;

import com.android.apksig.ApkVerifier;
import com.tianmen.ngz.engine.EngineException;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class KeystoreImporterTest {

    private static final String UPLOADED_JKS = "/workspace/.uploads/cc28c33c-83be-4417-b309-9a7cb4718e2e_nantianmen.jks";

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void importsUploadedNantianmenKeystore() throws Exception {
        File source = new File(UPLOADED_JKS);
        assertTrue("uploaded keystore fixture missing", source.exists());
        File dest = new File(temp.getRoot(), "imported.jks");
        KeystoreImported imported = new KeystoreImporter().importKeystore(source, dest,
                "nantianmen", "nantianmen", "nantianmen");
        assertEquals("nantianmen", imported.getAlias());
        assertTrue(dest.exists());
    }

    @Test
    public void importRejectsWrongPassword() throws Exception {
        File source = new File(UPLOADED_JKS);
        try {
            new KeystoreImporter().importKeystore(source,
                    new File(temp.getRoot(), "imported.jks"),
                    "nantianmen", "wrongpass", "wrongpass");
            fail("expected EngineException");
        } catch (EngineException expected) {
        }
    }

    @Test
    public void signsWithImportedNantianmenKeystore() throws Exception {
        File source = new File(UPLOADED_JKS);
        File keystore = new File(temp.getRoot(), "imported.jks");
        new KeystoreImporter().importKeystore(source, keystore,
                "nantianmen", "nantianmen", "nantianmen");
        File input = writeMinimalApk();
        ProtectionConfig config = new ProtectionConfig.Builder()
                .inputApk(input)
                .outputApk(new File(temp.getRoot(), "signed.apk"))
                .autoSign(true)
                .keystore(keystore)
                .keyAlias("nantianmen")
                .keyPassword("nantianmen")
                .storePassword("nantianmen")
                .injectGuard(false)
                .build();
        ProtectResult result = new ProtectionPipeline().run(config);
        ApkVerifier.Result verifyResult = new ApkVerifier.Builder(result.getOutputApk())
                .setMinCheckedPlatformVersion(21)
                .build().verify();
        assertTrue(verifyResult.isVerified());
    }

    private File writeMinimalApk() throws IOException {
        File input = new File(temp.getRoot(), "in.apk");
        byte[] axml = new byte[8];
        axml[0] = 0x03;
        axml[2] = 0x08;
        axml[4] = 0x10;
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(input))) {
            zos.putNextEntry(new ZipEntry("AndroidManifest.xml"));
            zos.write(axml);
            zos.closeEntry();
        }
        return input;
    }
}
