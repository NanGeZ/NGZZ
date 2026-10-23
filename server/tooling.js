'use strict';

const fs = require('fs');
const path = require('path');
const os = require('os');

const ROOT = path.resolve(__dirname, '..');
const TOOLS_DIR = path.join(ROOT, 'tools');
const BT_DIR = path.join(TOOLS_DIR, 'android-14');
const APKTOOL_JAR = path.join(TOOLS_DIR, 'apktool.jar');

const JAVA_CANDIDATES = [
  '/root/.local/share/mise/installs/java/17.0.20+101/bin',
  '/root/.local/share/mise/installs/java/17/bin',
  '/root/.local/share/mise/installs/java/11.0.32+101/bin',
  '/root/.local/share/mise/installs/java/11/bin',
  '/root/.local/share/mise/installs/java/25.0.4+101.0.LTS/bin',
  '/root/.local/share/mise/installs/java/25/bin',
];

function resolveJavaHome() {
  if (process.env.NGZZ_JAVA_HOME && fs.existsSync(path.join(process.env.NGZZ_JAVA_HOME, 'bin', 'java'))) {
    return process.env.NGZZ_JAVA_HOME;
  }
  for (const dir of JAVA_CANDIDATES) {
    if (fs.existsSync(path.join(dir, 'java'))) return path.dirname(dir);
  }
  const which = require('child_process').spawnSync('which', ['java'], { encoding: 'utf8' });
  if (which.status === 0 && which.stdout.trim()) {
    const real = fs.realpathSync(which.stdout.trim());
    return path.dirname(path.dirname(real));
  }
  return null;
}

const JAVA_HOME = resolveJavaHome();
const JAVA = JAVA_HOME ? path.join(JAVA_HOME, 'bin', 'java') : 'java';
const JAVAC = JAVA_HOME ? path.join(JAVA_HOME, 'bin', 'javac') : 'javac';
const KEYTOOL = JAVA_HOME ? path.join(JAVA_HOME, 'bin', 'keytool') : 'keytool';

const bin = (name) => path.join(BT_DIR, name);

const BAKSMALI_CP = [
  path.join(TOOLS_DIR, 'baksmali.jar'),
  path.join(TOOLS_DIR, 'dexlib2.jar'),
  path.join(TOOLS_DIR, 'util.jar'),
  path.join(TOOLS_DIR, 'guava.jar'),
  path.join(TOOLS_DIR, 'jcommander.jar'),
].join(path.delimiter);

function baksmaliArgs(...args) {
  return ['-cp', BAKSMALI_CP, 'org.jf.baksmali.Main', ...args];
}

function check() {
  const items = [
    { name: 'Java 运行时 (17+)', ok: !!JAVA_HOME, hint: '未找到 java，请安装 JDK17 或设置 NGZZ_JAVA_HOME' },
    { name: 'javac 编译器', ok: fs.existsSync(JAVAC) || !!require('child_process').spawnSync('which', ['javac']).status === 0, hint: '注入编译需要 javac' },
    { name: 'apktool', ok: fs.existsSync(APKTOOL_JAR), hint: '缺少 tools/apktool.jar' },
    { name: 'aapt2', ok: fs.existsSync(bin('aapt2')), hint: '缺少 tools/android-14/aapt2' },
    { name: 'd8', ok: fs.existsSync(bin('d8')), hint: '缺少 tools/android-14/d8' },
    { name: 'apksigner', ok: fs.existsSync(bin('apksigner')), hint: '缺少 tools/android-14/apksigner' },
    { name: 'zipalign', ok: fs.existsSync(bin('zipalign')), hint: '缺少 tools/android-14/zipalign' },
  ];
  return { javaHome: JAVA_HOME, toolsDir: TOOLS_DIR, items, allOk: items.every((i) => i.ok) };
}

function tmpDir(prefix) {
  return fs.mkdtempSync(path.join(os.tmpdir(), prefix));
}

module.exports = {
  ROOT,
  TOOLS_DIR,
  BT_DIR,
  APKTOOL_JAR,
  JAVA,
  JAVAC,
  KEYTOOL,
  JAVA_HOME,
  bin,
  baksmaliArgs,
  check,
  tmpDir,
};
