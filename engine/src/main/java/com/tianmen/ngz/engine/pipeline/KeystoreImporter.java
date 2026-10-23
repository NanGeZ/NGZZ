package com.tianmen.ngz.engine.pipeline;

import com.tianmen.ngz.engine.EngineException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;

public final class KeystoreImporter {

    public KeystoreImported importKeystore(File source, File dest, String alias,
                                           String keyPassword, String storePassword) {
        if (source == null || !source.exists()) {
            throw new EngineException("签名文件不存在");
        }
        if (dest == null) {
            throw new EngineException("缺少导入目标路径");
        }
        if (alias == null || alias.isEmpty()) {
            throw new EngineException("缺少别名");
        }
        if (keyPassword == null || keyPassword.isEmpty() || storePassword == null || storePassword.isEmpty()) {
            throw new EngineException("缺少密码");
        }
        try {
            KeyStore keyStore = loadAnyType(source, storePassword);
            if (!keyStore.containsAlias(alias)) {
                throw new EngineException("签名文件中不存在别名: " + alias);
            }
            if (!keyStore.isKeyEntry(alias)) {
                throw new EngineException("别名不是私钥条目: " + alias);
            }
            keyStore.getKey(alias, keyPassword.toCharArray());
            File parent = dest.getParentFile();
            if (parent != null && !parent.exists() && !parent.mkdirs()) {
                throw new EngineException("无法创建导入目录: " + parent.getAbsolutePath());
            }
            copy(source, dest);
            return new KeystoreImported(dest, alias);
        } catch (IOException e) {
            throw new EngineException("签名文件读取失败: " + e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            throw new EngineException("签名文件解析失败: 密码或别名不匹配", e);
        }
    }

    private KeyStore loadAnyType(File source, String storePassword) throws GeneralSecurityException, IOException {
        GeneralSecurityException last = null;
        for (String type : new String[]{"JKS", "PKCS12"}) {
            try {
                KeyStore keyStore = KeyStore.getInstance(type);
                try (InputStream in = new FileInputStream(source)) {
                    keyStore.load(in, storePassword.toCharArray());
                }
                return keyStore;
            } catch (GeneralSecurityException e) {
                last = e;
            }
        }
        throw last != null ? last : new GeneralSecurityException("无法识别的签名文件格式");
    }

    private void copy(File source, File dest) throws IOException {
        try (InputStream in = new FileInputStream(source);
             OutputStream out = new FileOutputStream(dest)) {
            byte[] buffer = new byte[8192];
            int read;
            while ((read = in.read(buffer)) != -1) {
                out.write(buffer, 0, read);
            }
        }
    }
}
