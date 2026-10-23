package com.tianmen.ngz.engine.pipeline;

import com.tianmen.ngz.engine.ApkInfo;
import com.tianmen.ngz.engine.ApkInspector;
import com.tianmen.ngz.engine.EngineException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public final class ProtectionPipeline {

    public ProtectResult run(ProtectionConfig config) {
        if (config == null || config.getInputApk() == null) {
            throw new EngineException("缺少输入 APK");
        }
        File input = config.getInputApk();
        if (!input.exists()) {
            throw new EngineException("输入 APK 不存在: " + input.getName());
        }
        ApkInfo info = new ApkInspector().inspect(input);
        repack(input, config.getOutputApk());
        if (config.isInjectGuard()) {
            appendGuardMarker(config.getOutputApk());
        }
        if (config.isAutoSign()) {
            if (config.getKeystore() == null) {
                throw new EngineException("自动签名需要 keystore");
            }
            File signed = new File(config.getOutputApk().getParentFile(),
                    config.getOutputApk().getName() + ".signed");
            new KeystoreSigner().sign(config.getOutputApk(),
                    new KeystoreSpec(config.getKeystore(), config.getKeyAlias(),
                            config.getKeyPassword(), config.getStorePassword()),
                    signed);
            if (!signed.renameTo(config.getOutputApk())) {
                signed.delete();
                throw new EngineException("签名后替换输出文件失败");
            }
        }
        return new ProtectResult(config.getOutputApk(), info.getPackageName(), config.isAutoSign());
    }

    private void repack(File input, File output) {
        File parent = output.getParentFile();
        if (parent != null && !parent.exists() && !parent.mkdirs()) {
            throw new EngineException("无法创建输出目录: " + parent.getAbsolutePath());
        }
        try (ZipFile zipFile = new ZipFile(input);
             ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(output))) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                zos.putNextEntry(new ZipEntry(entry.getName()));
                try (InputStream in = zipFile.getInputStream(entry)) {
                    copy(in, zos);
                }
                zos.closeEntry();
            }
        } catch (IOException e) {
            throw new EngineException("重打包失败: " + e.getMessage(), e);
        }
    }

    private void appendGuardMarker(File apk) {
        File tmp = new File(apk.getParentFile(), apk.getName() + ".tmp");
        try (ZipFile zipFile = new ZipFile(apk);
             ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(tmp))) {
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                if (entry.isDirectory()) {
                    continue;
                }
                zos.putNextEntry(new ZipEntry(entry.getName()));
                try (InputStream in = zipFile.getInputStream(entry)) {
                    copy(in, zos);
                }
                zos.closeEntry();
            }
            zos.putNextEntry(new ZipEntry("assets/ngz_guard.marker"));
            zos.write("ngzz-guard".getBytes(StandardCharsets.UTF_8));
            zos.closeEntry();
        } catch (IOException e) {
            throw new EngineException("注入保护壳标记失败: " + e.getMessage(), e);
        }
        if (!tmp.renameTo(apk)) {
            tmp.delete();
            throw new EngineException("注入保护壳标记失败: 无法替换输出文件");
        }
    }

    private void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[8192];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }
}
