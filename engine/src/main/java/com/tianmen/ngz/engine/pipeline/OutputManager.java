package com.tianmen.ngz.engine.pipeline;

import com.tianmen.ngz.engine.EngineException;

import java.io.File;

public final class OutputManager {
    private final File baseDir;

    public OutputManager(File baseDir) {
        this.baseDir = baseDir;
    }

    public File resolveOutput(String fileName) {
        if (!baseDir.exists() && !baseDir.mkdirs() && !baseDir.exists()) {
            throw new EngineException("无法创建输出目录: " + baseDir.getAbsolutePath());
        }
        return new File(baseDir, fileName);
    }
}
