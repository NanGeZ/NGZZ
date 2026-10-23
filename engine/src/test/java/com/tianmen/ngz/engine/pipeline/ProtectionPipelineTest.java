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
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class ProtectionPipelineTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void pipelineRejectsMissingInput() {
        ProtectionConfig config = new ProtectionConfig.Builder()
                .inputApk(new File(temp.getRoot(), "missing.apk"))
                .outputApk(new File(temp.getRoot(), "out.apk"))
                .autoSign(false)
                .injectGuard(false)
                .build();
        try {
            new ProtectionPipeline().run(config);
            fail("expected EngineException");
        } catch (EngineException expected) {
        }
    }

    @Test
    public void pipelineProducesUnsignedOutput() throws Exception {
        File input = writeMinimalApk();
        ProtectionConfig config = new ProtectionConfig.Builder()
                .inputApk(input)
                .outputApk(new File(temp.getRoot(), "out.apk"))
                .autoSign(false)
                .injectGuard(false)
                .build();
        ProtectResult result = new ProtectionPipeline().run(config);
        assertTrue(result.getOutputApk().exists());
        assertEquals(false, result.getSigned());
    }

    @Test
    public void pipelineSignsWithGeneratedKeystore() throws Exception {
        File input = writeMinimalApk();
        File keystore = generateKeystore();
        ProtectionConfig config = new ProtectionConfig.Builder()
                .inputApk(input)
                .outputApk(new File(temp.getRoot(), "signed.apk"))
                .autoSign(true)
                .keystore(keystore)
                .keyAlias("testkey")
                .keyPassword("changeit")
                .storePassword("changeit")
                .injectGuard(false)
                .build();
        ProtectResult result = new ProtectionPipeline().run(config);
        assertTrue(result.getOutputApk().exists());
        assertEquals(true, result.getSigned());
        ApkVerifier.Result verifyResult = new ApkVerifier.Builder(result.getOutputApk())
                .setMinCheckedPlatformVersion(21)
                .build().verify();
        assertTrue(verifyResult.isVerified());
    }

    private File writeMinimalApk() throws IOException {
        File input = new File(temp.getRoot(), "in.apk");
        byte[] axml = buildMinimalAxml();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(input))) {
            zos.putNextEntry(new ZipEntry("AndroidManifest.xml"));
            zos.write(axml);
            zos.closeEntry();
        }
        return input;
    }

    private static byte[] buildMinimalAxml() {
        byte[] body = new byte[8];
        body[0] = 0x03;
        body[1] = 0x00;
        body[2] = 0x08;
        body[3] = 0x00;
        body[4] = 0x10;
        body[5] = 0x00;
        body[6] = 0x00;
        body[7] = 0x00;
        return body;
    }

    private File generateKeystore() throws Exception {
        File keystore = new File(temp.getRoot(), "test.jks");
        String keytool = new File(new File(System.getProperty("java.home"), "bin"),
                isWindows() ? "keytool.exe" : "keytool").getAbsolutePath();
        Process process = new ProcessBuilder(keytool, "-genkeypair",
                "-alias", "testkey",
                "-keyalg", "RSA",
                "-keysize", "2048",
                "-validity", "3650",
                "-keystore", keystore.getAbsolutePath(),
                "-storepass", "changeit",
                "-keypass", "changeit",
                "-dname", "CN=Test")
                .redirectErrorStream(true)
                .start();
        readFully(process.getInputStream());
        if (!process.waitFor(60, TimeUnit.SECONDS) || process.exitValue() != 0) {
            throw new IllegalStateException("keytool failed");
        }
        return keystore;
    }

    private static void readFully(InputStream in) throws IOException {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        byte[] chunk = new byte[4096];
        int read;
        while ((read = in.read(chunk)) != -1) {
            buffer.write(chunk, 0, read);
        }
    }

    private static boolean isWindows() {
        return System.getProperty("os.name", "").toLowerCase().contains("win");
    }
}
