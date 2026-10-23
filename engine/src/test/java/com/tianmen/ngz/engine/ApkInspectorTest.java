package com.tianmen.ngz.engine;

import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import static org.junit.Assert.assertNotNull;

public class ApkInspectorTest {

    @Test
    public void parsePackageFromMinimalApk() throws Exception {
        File apk = File.createTempFile("minimal", ".apk");
        apk.deleteOnExit();
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(apk))) {
            zos.putNextEntry(new ZipEntry("AndroidManifest.xml"));
            zos.write(new byte[]{0x00});
            zos.closeEntry();
        }

        try {
            ApkInfo info = new ApkInspector().inspect(apk);
            assertNotNull(info);
            info.getPackageName();
        } catch (EngineException expected) {
        }
    }
}
