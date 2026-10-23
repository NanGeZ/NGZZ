package com.tianmen.ngz.engine.pipeline;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OutputManagerTest {

    @Rule
    public TemporaryFolder temp = new TemporaryFolder();

    @Test
    public void resolveOutputUnderBaseDir() throws Exception {
        File baseDir = temp.newFolder("base");
        OutputManager manager = new OutputManager(baseDir);
        File resolved = manager.resolveOutput("sample.apk");
        assertEquals(new File(baseDir, "sample.apk").getCanonicalPath(), resolved.getCanonicalPath());
    }

    @Test
    public void createsBaseDirIfMissing() throws Exception {
        File baseDir = new File(temp.getRoot(), "notyet");
        OutputManager manager = new OutputManager(baseDir);
        manager.resolveOutput("a.apk");
        assertTrue(baseDir.exists());
    }
}
