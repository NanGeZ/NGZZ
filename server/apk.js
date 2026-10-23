'use strict';

const fs = require('fs');
const path = require('path');
const { spawnSync } = require('child_process');
const crypto = require('crypto');
const { JAVA, JAVA_HOME, APKTOOL_JAR, bin, tmpDir } = require('./tooling');

function run(cmd, args, opts = {}) {
  const res = spawnSync(cmd, args, {
    encoding: 'utf8',
    maxBuffer: 64 * 1024 * 1024,
    ...opts,
  });
  if (res.error && res.status == null) {
    res.stderr = `${res.stderr || ''}\n${res.error.message}`;
  }
  return res;
}

function withJavaEnv(env = {}) {
  return { ...process.env, ...(JAVA_HOME ? { JAVA_HOME } : {}), ...env };
}

function sha256(file) {
  return crypto.createHash('sha256').update(fs.readFileSync(file)).digest('hex');
}

function parseBadging(apkPath) {
  const res = run(bin('aapt'), ['dump', 'badging', apkPath], { env: withJavaEnv() });
  const out = `${res.stdout || ''}`;
  const meta = {
    package: null,
    versionName: null,
    versionCode: null,
    sdkVersion: null,
    targetSdkVersion: null,
    permissions: [],
    label: null,
    icon: null,
  };
  const pkg = out.match(/package: name='([^']+)'(?:\s+versionCode='([^']*)')?(?:\s+versionName='([^']*)')?/);
  if (pkg) {
    meta.package = pkg[1];
    meta.versionCode = pkg[2] || null;
    meta.versionName = pkg[3] || null;
  }
  const sdk = out.match(/sdkVersion:'([^']*)'/);
  if (sdk) meta.sdkVersion = sdk[1];
  const tsdk = out.match(/targetSdkVersion:'([^']*)'/);
  if (tsdk) meta.targetSdkVersion = tsdk[1];
  const label = out.match(/application-label(?:-[\w-]+)?:'([^']*)'/);
  if (label) meta.label = label[1];
  const icon = out.match(/application: label='[^']*' icon='([^']*)'/);
  if (icon) meta.icon = icon[1];
  for (const m of out.matchAll(/uses-permission: name='([^']+)'/g)) {
    meta.permissions.push(m[1]);
  }
  const launchable = out.match(/launchable-activity: name='([^']+)'/);
  if (launchable) meta.launchableActivity = launchable[1];
  return meta;
}

function collectSignatures(apkPath) {
  const sigs = [];
  try {
    const { listEntries } = require('./ziputil');
    for (const n of listEntries(apkPath)) {
      if (/^META-INF\/.*\.(RSA|DSA|EC|SIG)$/i.test(n)) sigs.push(n);
      if (n === 'META-INF/MANIFEST.MF') sigs.push(n);
    }
  } catch (e) {
    /* ignore */
  }
  return sigs;
}

function countDexEntries(apkPath) {
  try {
    const { listEntries } = require('./ziputil');
    return listEntries(apkPath).filter((n) => /^classes\d*\.dex$/.test(n)).length;
  } catch (e) {
    return 0;
  }
}

function analyze(apkPath) {
  const size = fs.statSync(apkPath).size;
  const meta = parseBadging(apkPath);
  meta.fileSize = size;
  meta.sha256 = sha256(apkPath);
  meta.signatureFiles = collectSignatures(apkPath);
  meta.dexCount = countDexEntries(apkPath);

  const v2 = run(bin('apksigner'), ['verify', '--print-certs', apkPath], { env: withJavaEnv() });
  meta.signed = v2.status === 0;
  if (v2.stdout) {
    const dn = v2.stdout.match(/Signer #1 certificate DN: ([^\n]+)/);
    if (dn) meta.certDn = dn[1].trim();
    const sha = v2.stdout.match(/Signer #1 certificate SHA-256 digest: ([^\n]+)/);
    if (sha) meta.certSha256 = sha[1].trim();
  }

  meta.abis = [];
  meta.hasNativeLibs = false;
  try {
    const { listEntries } = require('./ziputil');
    const abiSet = new Set();
    for (const n of listEntries(apkPath)) {
      const m = n.match(/^lib\/([^/]+)\/.*\.so$/);
      if (m) {
        abiSet.add(m[1]);
        meta.hasNativeLibs = true;
      }
    }
    meta.abis = [...abiSet];
  } catch (e) {
    /* ignore */
  }
  return meta;
}

function decode(apkPath, workDir, log) {
  log('正在解包 APK（apktool decode）...');
  const res = run(JAVA, ['-jar', APKTOOL_JAR, 'd', '-f', '-o', workDir, apkPath], {
    env: withJavaEnv(),
  });
  if (res.status !== 0) {
    throw new Error(`apktool 解包失败：${(res.stderr || res.stdout || '').split('\n').slice(0, 6).join(' ')}`);
  }
  log('解包完成');
}

function build(srcDir, outApk, log) {
  log('正在回编译打包（apktool build）...');
  const res = run(JAVA, ['-jar', APKTOOL_JAR, 'b', '-o', outApk, srcDir], { env: withJavaEnv() });
  if (res.status !== 0) {
    throw new Error(`apktool 打包失败：${(res.stderr || res.stdout || '').split('\n').slice(0, 8).join(' ')}`);
  }
  log('回编译完成');
  return outApk;
}

function zipalign(src, dst, log) {
  log('正在对齐（zipalign 4 字节）...');
  const res = run(bin('zipalign'), ['-f', '-p', '4', src, dst], { env: withJavaEnv() });
  if (res.status !== 0) throw new Error(`zipalign 失败：${res.stderr || res.stdout}`);
  log('对齐完成');
}

function sign(src, dst, keystore, alias, storepass, log) {
  log('正在签名（apksigner v1+v2+v3）...');
  const res = run(
    bin('apksigner'),
    [
      'sign',
      '--ks',
      keystore,
      '--ks-key-alias',
      alias,
      '--ks-pass',
      `pass:${storepass}`,
      '--key-pass',
      `pass:${storepass}`,
      '--out',
      dst,
      src,
    ],
    { env: withJavaEnv() }
  );
  if (res.status !== 0) throw new Error(`apksigner 签名失败：${res.stderr || res.stdout}`);
  log('签名完成');
}

function verify(apkPath, log) {
  log('正在校验签名...');
  const res = run(bin('apksigner'), ['verify', '--print-certs', apkPath], { env: withJavaEnv() });
  if (res.status !== 0) throw new Error(`签名校验失败：${res.stderr || res.stdout}`);
  log('签名校验通过');
  return res.stdout || '';
}

function ensureKeystore(dir, log) {
  const ks = path.join(dir, 'ngzz-release.jks');
  if (fs.existsSync(ks)) return { ks, existed: true };
  log('未发现调试密钥库，正在生成 keystore（RSA 2048 / 有效期 10000 天）...');
  const { KEYTOOL } = require('./tooling');
  const res = run(
    KEYTOOL,
    [
      '-genkeypair',
      '-v',
      '-keystore',
      ks,
      '-alias',
      'ngzz',
      '-keyalg',
      'RSA',
      '-keysize',
      '2048',
      '-validity',
      '10000',
      '-storepass',
      'ngzz123456',
      '-keypass',
      'ngzz123456',
      '-dname',
      'CN=NGZZ Protected, OU=NGZZ, O=NGZZ, L=Beijing, ST=Beijing, C=CN',
    ],
    { env: withJavaEnv() }
  );
  if (res.status !== 0) throw new Error(`生成 keystore 失败：${res.stderr || res.stdout}`);
  log('keystore 已生成');
  return { ks, existed: false };
}

module.exports = {
  run,
  withJavaEnv,
  analyze,
  decode,
  build,
  zipalign,
  sign,
  verify,
  ensureKeystore,
  tmpDir,
};
