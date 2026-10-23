'use strict';

const fs = require('fs');
const path = require('path');

const UPLOAD_DIR = path.join(__dirname, '..', 'out', 'uploads');
const SESSION_DIR = path.join(__dirname, '..', 'out', 'sessions');

const sessions = new Map();

function ensureDirs() {
  fs.mkdirSync(UPLOAD_DIR, { recursive: true });
  fs.mkdirSync(SESSION_DIR, { recursive: true });
}

function create(file) {
  ensureDirs();
  const id = 's_' + Date.now().toString(36) + Math.random().toString(36).slice(2, 8);
  const workDir = path.join(SESSION_DIR, id);
  fs.mkdirSync(workDir, { recursive: true });
  const session = {
    id,
    createdAt: Date.now(),
    upload: file,
    workDir,
    status: 'uploaded',
    logs: [],
    meta: null,
    options: null,
    result: null,
    error: null,
    cancelRequested: false,
  };
  sessions.set(id, session);
  return session;
}

function get(id) {
  return sessions.get(id) || null;
}

function list() {
  return [...sessions.values()].map((s) => ({
    id: s.id,
    createdAt: s.createdAt,
    upload: s.upload,
    status: s.status,
    progress: s.logs.length ? s.logs[s.logs.length - 1].progress : 0,
    hasResult: !!s.result,
  }));
}

function log(session, message, level = 'info', progress = null) {
  const entry = {
    time: Date.now(),
    level,
    message,
    progress: progress == null ? (session.logs.length ? session.logs[session.logs.length - 1].progress : 0) : progress,
  };
  session.logs.push(entry);
  if (session.logs.length > 500) session.logs.shift();
  process.stdout.write(`[${session.id}] ${message}\n`);
}

module.exports = { create, get, list, log, ensureDirs, UPLOAD_DIR, SESSION_DIR };
