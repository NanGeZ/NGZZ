'use strict';

const fs = require('fs');

function listEntries(apkPath) {
  const fd = fs.openSync(apkPath, 'r');
  try {
    const size = fs.fstatSync(fd).size;
    const buf = Buffer.alloc(Math.min(size, 65557));
    const tail = Math.max(0, size - 65557);
    fs.readSync(fd, buf, 0, buf.length, tail);
    const eocd = buf.lastIndexOf(Buffer.from([0x50, 0x4b, 0x05, 0x06]));
    if (eocd < 0) return [];
    const total = buf.readUInt16LE(eocd + 10);
    const cdOffset = buf.readUInt32LE(eocd + 16);
    const cd = Buffer.alloc(total * 512);
    const readLen = Math.min(cd.length, size - cdOffset);
    fs.readSync(fd, cd, 0, readLen, cdOffset);
    const entries = [];
    let p = 0;
    for (let i = 0; i < total; i++) {
      if (cd.readUInt32LE(p) !== 0x02014b50) break;
      const nameLen = cd.readUInt16LE(p + 28);
      const extraLen = cd.readUInt16LE(p + 30);
      const commentLen = cd.readUInt16LE(p + 32);
      const name = cd.slice(p + 46, p + 46 + nameLen).toString('utf8');
      entries.push(name);
      p += 46 + nameLen + extraLen + commentLen;
    }
    return entries;
  } finally {
    fs.closeSync(fd);
  }
}

module.exports = { listEntries };
