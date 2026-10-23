'use strict';

const fs = require('fs');
const path = require('path');
const apk = require('./apk');
const { inject } = require('./protection/inject');
const { ensureKeystore } = require('./apk');
const sessionStore = require('./sessions');

function sleep(ms) {
  return new Promise((r) => setTimeout(r, ms));
}

async function protect(session, options) {
  const log = (msg, level, progress) => sessionStore.log(session, msg, level, progress);
  session.options = options;
  session.status = 'processing';
  try {
    const uploadPath = session.upload.path;
    log('读取 APK 元信息...', 'info', 5);
    const meta = apk.analyze(uploadPath);
    session.meta = meta;
    log(
      `应用: ${meta.label || '-'}（${meta.package}）· 版本 ${meta.versionName || '-'} · dex ×${meta.dexCount}`,
      'info',
      8
    );
    log(`签名证书 SHA-256: ${meta.certSha256 || '未检测到'}`, 'info', 9);

    const workDir = path.join(session.workDir, 'decoded');
    fs.rmSync(workDir, { recursive: true, force: true });
    apk.decode(uploadPath, workDir, (m) => log(m, 'info', 15));

    log('注入 NGZZ 保护代码...', 'info', 40);
    inject(workDir, options, meta, (m) => log(m, 'info', 55));

    const unsigned = path.join(session.workDir, 'protected-unsigned.apk');
    apk.build(workDir, unsigned, (m) => log(m, 'info', 70));

    const aligned = path.join(session.workDir, 'protected-aligned.apk');
    apk.zipalign(unsigned, aligned, (m) => log(m, 'info', 80));

    const { ks } = ensureKeystore(session.workDir, (m) => log(m, 'info', 85));

    const outName = `${(meta.package || 'app').replace(/[^\w.-]/g, '_')}-protected.apk`;
    const outPath = path.join(session.workDir, outName);
    apk.sign(aligned, outPath, ks, 'ngzz', 'ngzz123456', (m) => log(m, 'info', 92));

    apk.verify(outPath, (m) => log(m, 'info', 96));

    const outMeta = apk.analyze(outPath);
    session.result = {
      file: outPath,
      name: outName,
      size: fs.statSync(outPath).size,
      sha256: outMeta.sha256,
      signed: outMeta.signed,
      certDn: outMeta.certDn,
      options,
    };
    session.status = 'done';
    log(`保护完成 ✔ ${outName}`, 'success', 100);
    return session.result;
  } catch (err) {
    session.status = 'error';
    session.error = err.message;
    log(`处理失败: ${err.message}`, 'error', 100);
    throw err;
  } finally {
    try {
      fs.rmSync(path.join(session.workDir, 'decoded'), { recursive: true, force: true });
    } catch (e) {
      /* ignore */
    }
  }
}

module.exports = { protect };
