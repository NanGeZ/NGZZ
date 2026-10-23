'use strict';

const fs = require('fs');
const path = require('path');
const { run, withJavaEnv, tmpDir } = require('../apk');
const { JAVA, JAVAC, bin, baksmaliArgs } = require('../tooling');
const {
  readManifest,
  writeManifest,
  findPackage,
  findMinSdk,
  addPermission,
  addComponent,
  setAppAttribute,
  removeAppAttribute,
  hardenApplication,
} = require('./manifest');

const JAVA_SRC_DIR = path.join(__dirname, 'java');
const GUARD_CLASSES = ['NGZZGuard', 'NGZZProxy', 'NGZZInit'];
const PROVIDER_AUTH_SUFFIX = '.ngzzinit';

function findAndroidJar(workDir) {
  const candidates = [];
  if (process.env.ANDROID_HOME) {
    const root = process.env.ANDROID_HOME;
    for (const d of fs.existsSync(path.join(root, 'platforms')) ? fs.readdirSync(path.join(root, 'platforms')) : []) {
      candidates.push(path.join(root, 'platforms', d, 'android.jar'));
    }
  }
  const sdkGuess = '/root/Android/Sdk';
  if (fs.existsSync(path.join(sdkGuess, 'platforms'))) {
    for (const d of fs.readdirSync(path.join(sdkGuess, 'platforms'))) {
      candidates.push(path.join(sdkGuess, 'platforms', d, 'android.jar'));
    }
  }
  for (const c of candidates) {
    if (fs.existsSync(c)) return c;
  }
  return null;
}

function fetchAndroidJar(cacheDir, log) {
  const cached = path.join(cacheDir, 'android.jar');
  if (fs.existsSync(cached) && fs.statSync(cached).size > 1000000) return cached;
  fs.mkdirSync(cacheDir, { recursive: true });
  const urls = [
    'https://raw.githubusercontent.com/Sable/android-platforms/master/android-30/android.jar',
    'https://github.com/Sable/android-platforms/raw/master/android-30/android.jar',
    'https://mirrors.cloud.tencent.com/AndroidSDK/android-30/android.jar',
  ];
  for (const url of urls) {
    log(`未找到本地 android.jar，正在下载 android-30 平台库（约 20MB，仅首次）: ${url.split('/')[2]}`);
    const res = run('curl', ['-sL', '--fail', '--max-time', '300', '-o', cached, url]);
    if (res.status === 0 && fs.existsSync(cached) && fs.statSync(cached).size > 1000000) {
      log('android.jar 就绪');
      return cached;
    }
    fs.rmSync(cached, { force: true });
  }
  throw new Error('下载 android.jar 失败，请手动放置 tools/cache/android.jar 或配置 ANDROID_HOME');
}

function resolveAndroidJar(workDir, log) {
  return findAndroidJar(workDir) || fetchAndroidJar(path.resolve(__dirname, '..', '..', 'tools', 'cache'), log);
}

function countDex(workDir) {
  const smaliDir = fs.readdirSync(workDir).filter((d) => /^smali(_classes\d+)?$/.test(d));
  return smaliDir.length ? Math.max(...smaliDir.map((d) => (d === 'smali' ? 1 : parseInt(d.split('_classes')[1], 10)))) : 1;
}

function nextDexDir(workDir) {
  const n = countDex(workDir);
  return n === 1 ? 'smali_classes2' : `smali_classes${n + 1}`;
}

function mergeSmali(workDir, smaliOut, log) {
  const target = path.join(workDir, nextDexDir(workDir));
  fs.mkdirSync(target, { recursive: true });
  const walk = (dir, base) => {
    for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
      const src = path.join(dir, entry.name);
      if (entry.isDirectory()) {
        walk(src, path.join(base, entry.name));
      } else if (entry.name.endsWith('.smali')) {
        const dstDir = path.join(target, base);
        fs.mkdirSync(dstDir, { recursive: true });
        fs.copyFileSync(src, path.join(dstDir, entry.name));
      }
    }
  };
  walk(smaliOut, '');
  log(`注入 smali 完成 → ${path.basename(target)}`);
}

function applyManifestPatches(manifest, ctx, log) {
  let xml = manifest.xml;
  const pkgDir = ctx.packageName.replace(/\./g, '/');
  const providerTag =
    `<provider android:name="${ctx.packageName}.NGZZInit" ` +
    `android:authorities="${ctx.packageName}${PROVIDER_AUTH_SUFFIX}" ` +
    `android:exported="false" android:initOrder="100" />`;

  if (ctx.selected.length > 0) {
    xml = addPermission(xml, 'android.permission.INTERNET');
    xml = addPermission(xml, 'android.permission.ACCESS_NETWORK_STATE');
    xml = addPermission(xml, 'android.permission.ACCESS_WIFI_STATE');
    xml = addComponent(xml, providerTag);
  }

  if (ctx.options.antiDebug) {
    xml = hardenApplication(xml);
    xml = removeAppAttribute(xml, 'android:testOnly');
    log('Manifest 加固：debuggable=false · allowBackup=false · 移除 testOnly');
  }

  if (ctx.options.antiCapture && ctx.options.antiCapture.exitOnDetect) {
    xml = setAppAttribute(xml, 'android:usesCleartextTraffic', 'true');
    xml = setAppAttribute(xml, 'android:networkSecurityConfig', '@xml/ngzz_network_security');
  }
  return xml;
}

function writeNetworkSecurityConfig(workDir, log) {
  const dir = path.join(workDir, 'res', 'xml');
  fs.mkdirSync(dir, { recursive: true });
  const xml = `<?xml version="1.0" encoding="utf-8"?>
<network-security-config>
    <base-config cleartextTrafficPermitted="true" />
</network-security-config>
`;
  fs.writeFileSync(path.join(dir, 'ngzz_network_security.xml'), xml);
  log('已写入网络安全配置 res/xml/ngzz_network_security.xml');
}

function renderTemplates(ctx) {
  const guardSrc = fs.readFileSync(path.join(JAVA_SRC_DIR, 'NGZZGuard.java'), 'utf8');
  const proxySrc = fs.readFileSync(path.join(JAVA_SRC_DIR, 'NGZZProxy.java'), 'utf8');
  const initSrc = fs.readFileSync(path.join(JAVA_SRC_DIR, 'NGZZInit.java'), 'utf8');

  const net = (ctx.options.networkCheck && ctx.options.networkCheck) || {};
  const repl = (src) =>
    src
      .replace(/__NGZZ_PKG__/g, ctx.packageName)
      .replace('__PKG__', ctx.packageName)
      .replace('__SIG_SHA256__', ctx.signatureSha256 || '')
      .replace('__ENABLE_SIGNATURE__', String(!!ctx.options.signature))
      .replace('__ENABLE_ANTI_DEBUG__', String(!!ctx.options.antiDebug))
      .replace('__ENABLE_PACKAGE_CHECK__', String(!!ctx.options.packageCheck))
      .replace('__ENABLE_ANTI_CAPTURE__', String(!!ctx.options.antiCapture))
      .replace('__ENABLE_ROOT_CHECK__', String(!!ctx.options.rootCheck))
      .replace('__ENABLE_NETWORK_CHECK__', String(!!ctx.options.networkCheck))
      .replace('__NETWORK_TIMEOUT__', String(net.timeout || 5))
      .replace('__NETWORK_RETRIES__', String(net.retries || 2));

  return {
    'NGZZGuard.java': repl(guardSrc),
    'NGZZProxy.java': repl(proxySrc),
    'NGZZInit.java': repl(initSrc),
  };
}

function compileGuard(ctx, log) {
  const workTmp = tmpDir('ngzz-src-');
  const classesDir = path.join(workTmp, 'classes');
  const pkgDir = path.join(workTmp, 'src', ...ctx.packageName.split('.'));
  fs.mkdirSync(pkgDir, { recursive: true });
  fs.mkdirSync(classesDir, { recursive: true });

  const sources = renderTemplates(ctx);
  const files = [];
  for (const [name, content] of Object.entries(sources)) {
    const p = path.join(pkgDir, name);
    fs.writeFileSync(p, content);
    files.push(p);
  }

  const androidJar = resolveAndroidJar(ctx.workDir, log);
  log('正在编译 NGZZ 保护代码（javac → d8 → dex → baksmali）...');
  const javacRes = run(JAVAC, ['-source', '8', '-target', '8', '-cp', androidJar, '-d', classesDir, ...files], {
    env: withJavaEnv(),
    cwd: workTmp,
  });
  if (javacRes.status !== 0) {
    throw new Error(`javac 编译失败：${(javacRes.stderr || '').split('\n').slice(0, 8).join(' ')}`);
  }

  const classFiles = [];
  for (const cls of GUARD_CLASSES) {
    classFiles.push(path.join(classesDir, ...ctx.packageName.split('.'), `${cls}.class`));
  }
  const dexDir = path.join(workTmp, 'dex');
  fs.mkdirSync(dexDir, { recursive: true });
  const d8Res = run(
    bin('d8'),
    ['--release', '--min-api', String(ctx.minSdk || 21), '--output', dexDir, '--lib', androidJar, ...classFiles],
    { env: withJavaEnv(), cwd: workTmp }
  );
  if (d8Res.status !== 0) {
    throw new Error(`d8 转换失败：${(d8Res.stderr || d8Res.stdout || '').split('\n').slice(0, 8).join(' ')}`);
  }

  const smaliOut = path.join(workTmp, 'smali-out');
  const bak = run(JAVA, baksmaliArgs('d', path.join(dexDir, 'classes.dex'), '-o', smaliOut), {
    env: withJavaEnv(),
  });
  if (bak.status !== 0) {
    throw new Error(
      `baksmali 反编译失败（exit=${bak.status}）：${(bak.stderr || bak.stdout || '').split('\n').slice(0, 8).join(' ')}`
    );
  }
  return { smaliOut, workTmp };
}

function inject(workDir, options, meta, rawLog) {
  const log = typeof rawLog === 'function' ? rawLog : () => {};
  const manifest = readManifest(workDir);
  const packageName = meta.package || findPackage(manifest.xml);
  const ctx = {
    workDir,
    packageName,
    minSdk: parseInt(meta.sdkVersion || findMinSdk(manifest.xml), 10) || 21,
    signatureSha256: (meta.certSha256 || '').replace(/:/g, '').toLowerCase(),
    options,
    selected: Object.keys(options).filter((k) => options[k]),
  };

  if (ctx.selected.length === 0) {
    log('未选择任何保护项，跳过注入');
    return;
  }

  const { smaliOut, workTmp } = compileGuard(ctx, log);
  mergeSmali(workDir, smaliOut, log);

  if (options.antiCapture && options.antiCapture.exitOnDetect) {
    writeNetworkSecurityConfig(workDir, log);
  }

  const xml = applyManifestPatches(manifest, ctx, log);
  writeManifest(manifest, xml);
  log(`Manifest 注入完成（ContentProvider 自启动 · ${ctx.packageName}.NGZZInit）`);

  try {
    fs.rmSync(workTmp, { recursive: true, force: true });
  } catch (e) {
    /* ignore */
  }
}

module.exports = { inject, PROVIDER_AUTH_SUFFIX };
