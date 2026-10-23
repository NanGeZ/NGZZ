package com.tianmen.ngz.engine.pipeline;

import com.android.apksig.ApkSigner;
import com.tianmen.ngz.engine.EngineException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class KeystoreSigner {

    public void sign(File unsignedApk, KeystoreSpec spec, File outputApk) {
        if (unsignedApk == null || !unsignedApk.exists()) {
            throw new EngineException("待签名 APK 不存在");
        }
        if (spec == null || spec.getKeystore() == null || !spec.getKeystore().exists()) {
            throw new EngineException("keystore 文件不存在");
        }
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            try (FileInputStream in = new FileInputStream(spec.getKeystore())) {
                keyStore.load(in, spec.getStorePassword().toCharArray());
            }
            PrivateKey privateKey = (PrivateKey) keyStore.getKey(spec.getKeyAlias(), spec.getKeyPassword().toCharArray());
            if (privateKey == null) {
                throw new EngineException("keystore 中不存在别名: " + spec.getKeyAlias());
            }
            Certificate[] chain = keyStore.getCertificateChain(spec.getKeyAlias());
            List<X509Certificate> certs = new ArrayList<>();
            if (chain != null) {
                for (Certificate cert : chain) {
                    certs.add((X509Certificate) cert);
                }
            }
            ApkSigner.SignerConfig signerConfig = new ApkSigner.SignerConfig.Builder(
                    spec.getKeyAlias(), privateKey, certs).build();
            ApkSigner signer = new ApkSigner.Builder(Collections.singletonList(signerConfig))
                    .setInputApk(unsignedApk)
                    .setOutputApk(outputApk)
                    .setV1SigningEnabled(true)
                    .setV2SigningEnabled(true)
                    .setV3SigningEnabled(true)
                    .setMinSdkVersion(21)
                    .build();
            signer.sign();
        } catch (IOException e) {
            throw new EngineException("签名失败: " + e.getMessage(), e);
        } catch (GeneralSecurityException e) {
            throw new EngineException("keystore 解析失败: " + e.getMessage(), e);
        } catch (com.android.apksig.apk.ApkFormatException e) {
            throw new EngineException("APK 格式非法: " + e.getMessage(), e);
        }
    }
}
