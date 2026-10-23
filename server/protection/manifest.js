'use strict';

const fs = require('fs');
const path = require('path');

function readManifest(workDir) {
  const p = path.join(workDir, 'AndroidManifest.xml');
  return { path: p, xml: fs.readFileSync(p, 'utf8') };
}

function writeManifest(m, xml) {
  fs.writeFileSync(m.path, xml);
}

function findAppTagRange(xml) {
  const idx = xml.search(/<application[\s>]/);
  if (idx < 0) throw new Error('AndroidManifest.xml 中未找到 <application> 标签');
  const openEnd = xml.indexOf('>', idx);
  if (openEnd < 0) throw new Error('<application> 标签不完整');
  return { idx, openEnd, selfClose: xml[openEnd - 1] === '/' };
}

function findPackage(xml) {
  const m = xml.match(/<manifest[^>]*\bpackage="([^"]+)"/);
  return m ? m[1] : 'app.ngzz.protected';
}

function findMinSdk(xml) {
  const m = xml.match(/<uses-sdk[^>]*\bminSdkVersion="([^"]+)"/);
  return m ? m[1] : '21';
}

function addPermission(xml, name) {
  if (xml.includes(`android:name="${name}"`)) return xml;
  const anchor = xml.search(/<uses-permission[\s>]/);
  const tag = `    <uses-permission android:name="${name}" />\n`;
  if (anchor >= 0) {
    return xml.slice(0, anchor) + tag + xml.slice(anchor);
  }
  const idx = xml.indexOf('<application');
  return xml.slice(0, idx) + tag + '\n    ' + xml.slice(idx);
}

function addComponent(xml, tagXml) {
  const { openEnd, selfClose } = findAppTagRange(xml);
  if (selfClose) {
    return xml.slice(0, openEnd - 1) + `>\n        ${tagXml}\n    </application>` + xml.slice(openEnd + 1);
  }
  return xml.slice(0, openEnd + 1) + `\n        ${tagXml}` + xml.slice(openEnd + 1);
}

function setAppAttribute(xml, name, value) {
  const { idx, openEnd } = findAppTagRange(xml);
  const tag = xml.slice(idx, openEnd + 1);
  const attrRe = new RegExp(`\\s*${name}="[^"]*"`, 'g');
  if (new RegExp(`\\b${name}=`).test(tag)) {
    const replaced = tag.replace(attrRe, ` ${name}="${value}"`);
    return xml.slice(0, idx) + replaced + xml.slice(openEnd + 1);
  }
  return xml.slice(0, idx) + tag.replace(/<application/, `<application ${name}="${value}"`) + xml.slice(openEnd + 1);
}

function removeAppAttribute(xml, name) {
  const { idx, openEnd } = findAppTagRange(xml);
  const tag = xml.slice(idx, openEnd + 1);
  if (!new RegExp(`\\b${name}=`).test(tag)) return xml;
  return xml.slice(0, idx) + tag.replace(new RegExp(`\\s*${name}="[^"]*"`, 'g'), '') + xml.slice(openEnd + 1);
}

function hardenApplication(xml) {
  let out = setAppAttribute(xml, 'android:debuggable', 'false');
  out = setAppAttribute(out, 'android:allowBackup', 'false');
  return out;
}

module.exports = {
  readManifest,
  writeManifest,
  findAppTagRange,
  findPackage,
  findMinSdk,
  addPermission,
  addComponent,
  setAppAttribute,
  removeAppAttribute,
  hardenApplication,
};
