'use strict';

const http = require('http');
const fs = require('fs');
const path = require('path');
const { pipeline } = require('stream/promises');

const tooling = require('./tooling');
const sessions = require('./sessions');
sessions.ensureDirs();
const { protect } = require('./pipeline');
const options = require('./protection/options');
const apk = require('./apk');

const PORT = process.env.PORT ? parseInt(process.env.PORT, 10) : 8787;
const PUBLIC_DIR = path.join(__dirname, '..', 'public');
const MAX_UPLOAD = 300 * 1024 * 1024;

function sendJSON(res, code, obj) {
  const body = JSON.stringify(obj);
  res.writeHead(code, {
    'Content-Type': 'application/json; charset=utf-8',
    'Access-Control-Allow-Origin': '*',
  });
  res.end(body);
}

function mimeOf(file) {
  const ext = path.extname(file).toLowerCase();
  return {
    '.html': 'text/html; charset=utf-8',
    '.css': 'text/css; charset=utf-8',
    '.js': 'application/javascript; charset=utf-8',
    '.json': 'application/json; charset=utf-8',
    '.svg': 'image/svg+xml',
    '.png': 'image/png',
    '.jpg': 'image/jpeg',
    '.ico': 'image/x-icon',
  }[ext] || 'application/octet-stream';
}

function sendFile(res, file, downloadName) {
  if (!fs.existsSync(file)) return sendJSON(res, 404, { error: '文件不存在' });
  const stat = fs.statSync(file);
  res.writeHead(200, {
    'Content-Type': downloadName ? 'application/vnd.android.package-archive' : mimeOf(file),
    'Content-Length': stat.size,
    ...(downloadName
      ? { 'Content-Disposition': `attachment; filename="${encodeURIComponent(downloadName)}"` }
      : {}),
    'Access-Control-Allow-Origin': '*',
  });
  fs.createReadStream(file).pipe(res);
}

function parseMultipart(req) {
  const contentType = req.headers['content-type'] || '';
  const m = contentType.match(/boundary=(?:"([^"]+)"|([^;]+))/);
  if (!m) return null;
  return { boundary: m[1] || m[2] };
}

async function saveUpload(req, destPath) {
  const out = fs.createWriteStream(destPath);
  await pipeline(req, out);
}

async function handleUpload(req, res) {
  const ct = req.headers['content-type'] || '';
  if (ct.includes('multipart/form-data')) {
    const len = parseInt(req.headers['content-length'] || '0', 10);
    if (len > MAX_UPLOAD) {
      req.resume();
      return sendJSON(res, 413, { error: 'APK 超过 300MB 限制' });
    }
    const chunks = [];
    let size = 0;
    for await (const chunk of req) {
      size += chunk.length;
      if (size > MAX_UPLOAD) {
        return sendJSON(res, 413, { error: 'APK 超过 300MB 限制' });
      }
      chunks.push(chunk);
    }
    const buf = Buffer.concat(chunks);
    const mp = parseMultipart(req);
    if (!mp) return sendJSON(res, 400, { error: '缺少 multipart boundary' });
    const boundary = Buffer.from('--' + mp.boundary);
    const parts = [];
    let start = buf.indexOf(boundary);
    while (start >= 0) {
      const next = buf.indexOf(boundary, start + boundary.length);
      if (next < 0) break;
      const partBuf = buf.slice(start + boundary.length + 2, next - 2);
      const headerEnd = partBuf.indexOf('\r\n\r\n');
      if (headerEnd > 0) {
        const header = partBuf.slice(0, headerEnd).toString('utf8');
        const body = partBuf.slice(headerEnd + 4);
        const nameM = header.match(/name="([^"]*)"/);
        const fileM = header.match(/filename="([^"]*)"/);
        parts.push({ name: nameM ? nameM[1] : '', filename: fileM ? fileM[1] : null, body });
      }
      start = next;
    }
    const filePart = parts.find((p) => p.filename);
    if (!filePart) return sendJSON(res, 400, { error: '未找到上传文件字段' });
    if (filePart.body.length === 0) return sendJSON(res, 422, { error: '上传内容为空，请重新选择 APK 文件' });
    const session = sessions.create({ name: filePart.filename, size: filePart.body.length });
    fs.writeFileSync(session.upload.path || (session.upload.path = path.join(sessions.UPLOAD_DIR, `${session.id}.apk`)), filePart.body);
    sessions.log(session, `APK 已上传: ${filePart.filename}（${(filePart.body.length / 1048576).toFixed(2)} MB）`, 'info', 3);
    try {
      const meta = apk.analyze(session.upload.path);
      session.meta = meta;
      return sendJSON(res, 200, { sessionId: session.id, meta });
    } catch (e) {
      return sendJSON(res, 422, { error: `APK 解析失败: ${e.message}`, sessionId: session.id });
    }
  }

  let size = 0;
  sessions.ensureDirs();
  const outPath = path.join(sessions.UPLOAD_DIR, `up_${Date.now()}.apk`);
  const out = fs.createWriteStream(outPath);
  try {
    for await (const chunk of req) {
      size += chunk.length;
      if (size > MAX_UPLOAD) {
        out.destroy();
        fs.rmSync(outPath, { force: true });
        return sendJSON(res, 413, { error: 'APK 超过 300MB 限制' });
      }
      out.write(chunk);
    }
    await new Promise((r) => out.end(r));
    const session = sessions.create({ name: path.basename(outPath), size });
    session.upload.path = outPath;
    sessions.log(session, `APK 已上传（${(size / 1048576).toFixed(2)} MB）`, 'info', 3);
    const meta = apk.analyze(outPath);
    session.meta = meta;
    return sendJSON(res, 200, { sessionId: session.id, meta });
  } catch (e) {
    return sendJSON(res, 422, { error: `APK 解析失败: ${e.message}` });
  }
}

async function readBody(req) {
  const chunks = [];
  for await (const c of req) chunks.push(c);
  const raw = Buffer.concat(chunks).toString('utf8');
  return raw ? JSON.parse(raw) : {};
}

const server = http.createServer(async (req, res) => {
  const url = new URL(req.url, `http://${req.headers.host}`);
  const p = url.pathname;
  try {
    if (req.method === 'OPTIONS') {
      res.writeHead(204, {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET,POST,OPTIONS',
        'Access-Control-Allow-Headers': 'Content-Type',
      });
      return res.end();
    }

    if (p === '/api/health') {
      return sendJSON(res, 200, { ok: true, tooling: tooling.check() });
    }
    if (p === '/api/options') {
      return sendJSON(res, 200, { options });
    }
    if (p === '/api/upload' && req.method === 'POST') {
      return await handleUpload(req, res);
    }
    if (p === '/api/sessions' && req.method === 'GET') {
      return sendJSON(res, 200, { sessions: sessions.list() });
    }
    if (p.startsWith('/api/session/')) {
      const rest = p.slice('/api/session/'.length);
      const [id, action] = rest.split('/');
      const session = sessions.get(id);
      if (!session) return sendJSON(res, 404, { error: '会话不存在' });

      if (!action && req.method === 'GET') {
        return sendJSON(res, 200, {
          id: session.id,
          status: session.status,
          upload: { name: session.upload.name, size: session.upload.size },
          meta: session.meta,
          logs: session.logs,
          options: session.options,
          error: session.error,
          result: session.result ? { name: session.result.name, size: session.result.size, sha256: session.result.sha256 } : null,
        });
      }
      if (action === 'protect' && req.method === 'POST') {
        if (session.status === 'processing') return sendJSON(res, 409, { error: '任务正在处理中' });
        const body = await readBody(req);
        protect(session, body.options || {}).catch(() => {});
        return sendJSON(res, 202, { ok: true, status: session.status });
      }
      if (action === 'download') {
        if (!session.result) return sendJSON(res, 404, { error: '结果尚未生成' });
        return sendFile(res, session.result.file, session.result.name);
      }
    }

    if (p === '/' || p === '/index.html') {
      return sendFile(res, path.join(PUBLIC_DIR, 'index.html'));
    }
    const staticFile = path.join(PUBLIC_DIR, path.normalize(p).replace(/^([/\\])+/, ''));
    if (staticFile.startsWith(PUBLIC_DIR) && fs.existsSync(staticFile) && fs.statSync(staticFile).isFile()) {
      return sendFile(res, staticFile);
    }
    sendJSON(res, 404, { error: 'Not Found' });
  } catch (err) {
    sendJSON(res, 500, { error: err.message });
  }
});

server.listen(PORT, () => {
  console.log(`NGZZ APK 综合保护工具已启动: http://localhost:${PORT}`);
  const t = tooling.check();
  if (!t.allOk) {
    console.warn('警告：部分工具链缺失', t.items.filter((i) => !i.ok).map((i) => i.name));
  }
});
