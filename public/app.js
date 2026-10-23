const state = {
  sessionId: null,
  meta: null,
  options: [],
  selected: new Set(),
  pollTimer: null,
};

const $ = (id) => document.getElementById(id);

function fmtSize(bytes) {
  if (bytes == null) return '—';
  if (bytes < 1024) return bytes + ' B';
  if (bytes < 1048576) return (bytes / 1024).toFixed(1) + ' KB';
  return (bytes / 1048576).toFixed(2) + ' MB';
}

function fmtTime(ts) {
  const d = new Date(ts);
  return d.toTimeString().slice(0, 8);
}

async function fetchJSON(url, opts) {
  let res;
  try {
    res = await fetch(url, opts);
  } catch (e) {
    throw new Error('无法连接本地服务，请先运行 npm start');
  }
  const data = await res.json().catch(() => ({}));
  if (!res.ok) throw new Error(data.error || `请求失败 (${res.status})`);
  return data;
}

/* ---------- 工具链状态 ---------- */
async function initHealth() {
  try {
    const data = await fetchJSON('/api/health');
    const el = $('toolStatus');
    if (data.tooling && data.tooling.allOk) {
      el.innerHTML = '<span class="dot"></span> 工具链就绪 · 本地运行';
    } else {
      el.classList.add('bad');
      el.innerHTML = '<span class="dot"></span> 工具链缺少组件';
    }
  } catch (e) {
    $('toolStatus').classList.add('bad');
    $('toolStatus').innerHTML = '<span class="dot"></span> 服务连接失败';
  }
}

/* ---------- 选项 ---------- */
const ICONS = {
  signature: 'M12 2 4 6v6c0 5 3.4 9.4 8 10 4.6-.6 8-5 8-10V6l-8-4Z',
  antiDebug: 'M12 3a4 4 0 0 0-4 4v2H6v10h12V9h-2V7a4 4 0 0 0-4-4Zm-2 4a2 2 0 1 1 4 0v2h-4V7Z',
  packageCheck: 'M12 3 4 7v10l8 4 8-4V7l-8-4Zm0 2.2 5.5 2.7L12 10.6 6.5 7.9 12 5.2Z',
  antiCapture: 'M12 2a10 10 0 1 0 0 20 10 10 0 0 0 0-20Zm0 2c1.6 0 3.6 2.6 4 7H8c.4-4.4 2.4-7 4-7ZM6.2 11h3.3a20 20 0 0 0 0 2H6.2a8 8 0 0 1 0-2Zm11.6 0a8 8 0 0 1 0 2h-3.3a20 20 0 0 0 0-2h3.3ZM12 20c-1.6 0-3.6-2.6-4-7h8c-.4 4.4-2.4 7-4 7Z',
  rootCheck: 'M12 2 3 7v10l9 5 9-5V7l-9-5Zm0 3 5 2.8-5 2.8-5-2.8L12 5Zm-6 5.6 4 2.2v4.6l-4-2.2v-4.6Zm8 6.8v-4.6l4-2.2v4.6l-4 2.2Z',
  networkCheck: 'M12 18a1.5 1.5 0 1 0 0 3 1.5 1.5 0 0 0 0-3Zm0-4.5c-1.6 0-3.1.6-4.2 1.7l1.4 1.4A4 4 0 0 1 12 15.5a4 4 0 0 1 2.8 1.1l1.4-1.4a6 6 0 0 0-4.2-1.7Zm0-4c-2.8 0-5.3 1.1-7.2 2.9l1.4 1.4a8 8 0 0 1 11.6 0l1.4-1.4a10 10 0 0 0-7.2-2.9Zm0-4c-4 0-7.6 1.6-10.3 4.2l1.4 1.4a12 12 0 0 1 17.8 0l1.4-1.4A14 14 0 0 0 12 5.5Z',
};

function renderOptions(list) {
  state.options = list;
  const wrap = $('optionsList');
  wrap.innerHTML = '';
  list.forEach((opt) => {
    const div = document.createElement('div');
    div.className = 'option';
    div.dataset.id = opt.id;
    div.innerHTML = `
      <div class="check">
        <svg viewBox="0 0 24 24" width="13" height="13" fill="none" stroke="#f2efdd" stroke-width="3">
          <path d="m5 13 4 4L19 7" stroke-linecap="round" stroke-linejoin="round"/>
        </svg>
      </div>
      <div class="option-body">
        <h4>${opt.name}</h4>
        <p>${opt.desc}</p>
        <span class="option-tag">${opt.detail}</span>
      </div>`;
    div.addEventListener('click', () => toggleOption(opt.id));
    wrap.appendChild(div);
  });
  renderExtraPanel();
}

function toggleOption(id) {
  if (state.selected.has(id)) state.selected.delete(id);
  else state.selected.add(id);
  syncOptionUI();
}

function syncOptionUI() {
  document.querySelectorAll('.option').forEach((el) => {
    el.classList.toggle('on', state.selected.has(el.dataset.id));
  });
  renderExtraPanel();
  const canProtect = !!state.sessionId && state.selected.size > 0;
  $('protectBtn').disabled = !canProtect;
  $('protectHint').textContent = !state.sessionId
    ? '请先上传 APK'
    : state.selected.size === 0
      ? '请至少选择一项保护'
      : `已选择 ${state.selected.size} 项保护，点击开始处理`;
}

function renderExtraPanel() {
  const panel = $('extraPanel');
  const needCapture = state.selected.has('antiCapture');
  const needNetwork = state.selected.has('networkCheck');
  if (!needCapture && !needNetwork) {
    panel.classList.add('hidden');
    panel.innerHTML = '';
    return;
  }
  panel.classList.remove('hidden');
  let html = '';
  if (needCapture) {
    html += `
      <div><b>抓包检测</b> · 检测到 VPN / WiFi 代理环境时的处理方式</div>
      <div class="extra-row">
        <label><input type="checkbox" id="captureExit" checked> 检测到抓包环境立即退出程序</label>
      </div>`;
  }
  if (needNetwork) {
    html += `
      <div style="margin-top:10px"><b>网络检测</b> · 断网探测参数</div>
      <div class="extra-row">
        <label>探测超时 <input type="number" id="netTimeout" value="5" min="1" max="30"> 秒</label>
        <label>重试次数 <input type="number" id="netRetries" value="2" min="1" max="5"> 次</label>
        <label><input type="checkbox" id="netExit" checked> 断网时退出程序</label>
      </div>`;
  }
  panel.innerHTML = html;
}

function collectOptions() {
  const opts = {};
  if (state.selected.has('signature')) opts.signature = true;
  if (state.selected.has('antiDebug')) opts.antiDebug = true;
  if (state.selected.has('packageCheck')) opts.packageCheck = true;
  if (state.selected.has('rootCheck')) opts.rootCheck = true;
  if (state.selected.has('antiCapture')) {
    opts.antiCapture = { exitOnDetect: $('captureExit') ? $('captureExit').checked : true };
  }
  if (state.selected.has('networkCheck')) {
    opts.networkCheck = {
      timeout: parseInt($('netTimeout') ? $('netTimeout').value : 5, 10) || 5,
      retries: parseInt($('netRetries') ? $('netRetries').value : 2, 10) || 2,
      exitOnDisconnect: $('netExit') ? $('netExit').checked : true,
    };
  }
  return opts;
}

/* ---------- 上传 ---------- */
function setupUpload() {
  const zone = $('dropZone');
  const input = $('fileInput');

  $('pickBtn').addEventListener('click', () => input.click());
  zone.addEventListener('click', (e) => {
    if (e.target.closest('button')) return;
    if (!state.sessionId) input.click();
  });
  zone.addEventListener('dragover', (e) => {
    e.preventDefault();
    zone.classList.add('drag');
  });
  zone.addEventListener('dragleave', () => zone.classList.remove('drag'));
  zone.addEventListener('drop', (e) => {
    e.preventDefault();
    zone.classList.remove('drag');
    const file = e.dataTransfer.files[0];
    if (file) uploadFile(file);
  });
  input.addEventListener('change', () => {
    if (input.files[0]) uploadFile(input.files[0]);
    input.value = '';
  });
}

function setUploadProgress(pct, text) {
  const wrap = $('uploadProgress');
  wrap.classList.remove('hidden');
  $('uploadBar').style.width = pct + '%';
  $('uploadText').textContent = text;
}

function uploadFile(file) {
  if (!file.name.toLowerCase().endsWith('.apk')) {
    alert('请选择 .apk 文件');
    return;
  }
  $('errorCard').classList.add('hidden');
  $('resultCard').classList.add('hidden');
  setUploadProgress(0, '上传中…');

  const xhr = new XMLHttpRequest();
  xhr.open('POST', '/api/upload');
  xhr.upload.onprogress = (e) => {
    if (e.lengthComputable) {
      const pct = Math.round((e.loaded / e.total) * 100);
      setUploadProgress(pct, `上传中 ${pct}%`);
    }
  };
  xhr.onload = () => {
    let data = {};
    try { data = JSON.parse(xhr.responseText); } catch (e) { /* ignore */ }
    if (xhr.status !== 200) {
      setUploadProgress(0, '上传失败');
      showError(data.error || '上传失败');
      return;
    }
    setUploadProgress(100, '解析完成');
    setTimeout(() => $('uploadProgress').classList.add('hidden'), 800);
    state.sessionId = data.sessionId;
    state.meta = data.meta;
    renderMeta(data.meta);
    syncOptionUI();
  };
  xhr.onerror = () => {
    setUploadProgress(0, '上传失败');
    showError('网络错误，上传失败');
  };
  const form = new FormData();
  form.append('apk', file);
  xhr.send(form);
}

function renderMeta(meta) {
  $('metaCard').classList.remove('hidden');
  $('appName').textContent = meta.label || meta.package || '未知应用';
  $('appPackage').textContent = meta.package || '—';
  $('appIcon').textContent = (meta.label || meta.package || 'A').trim().charAt(0).toUpperCase();
  $('metaVersion').textContent = meta.versionName || '—';
  $('metaSize').textContent = fmtSize(meta.fileSize);
  $('metaDex').textContent = `×${meta.dexCount || 1}`;
  $('metaSdk').textContent = `${meta.sdkVersion || '—'} / ${meta.targetSdkVersion || '—'}`;
  $('metaSigned').textContent = meta.signed ? '已签名' : '未签名/损坏';
  $('metaSha').textContent = meta.sha256 || '—';
  $('metaSha').title = meta.sha256 || '';
}

/* ---------- 日志 ---------- */
function renderLogs(logs) {
  const view = $('logView');
  if (!logs || !logs.length) return;
  view.innerHTML = logs.map((l) => `
    <div class="log-line ${l.level}">
      <span class="log-time">${fmtTime(l.time)}</span>
      <span class="log-msg">${escapeHtml(l.message)}</span>
    </div>`).join('');
  view.scrollTop = view.scrollHeight;
}

function escapeHtml(s) {
  return String(s).replace(/[&<>"']/g, (c) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', '"': '&quot;', "'": '&#39;' }[c]));
}

function setStatusChip(status) {
  const chip = $('statusChip');
  chip.className = 'status-chip';
  if (status === 'processing') { chip.classList.add('processing'); chip.textContent = '处理中'; }
  else if (status === 'done') { chip.classList.add('done'); chip.textContent = '已完成'; }
  else if (status === 'error') { chip.classList.add('error'); chip.textContent = '失败'; }
  else chip.textContent = '待机';
}

function setOverall(pct) {
  $('overallBar').style.width = pct + '%';
  $('overallText').textContent = pct + '%';
}

/* ---------- 保护 ---------- */
async function startProtect() {
  if (!state.sessionId || state.selected.size === 0) return;
  $('protectBtn').disabled = true;
  $('errorCard').classList.add('hidden');
  $('resultCard').classList.add('hidden');
  $('logView').innerHTML = '';
  setOverall(0);
  setStatusChip('processing');
  try {
    await fetchJSON(`/api/session/${state.sessionId}/protect`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ options: collectOptions() }),
    });
    startPolling();
  } catch (e) {
    showError(e.message);
    setStatusChip('error');
    $('protectBtn').disabled = false;
  }
}

function startPolling() {
  stopPolling();
  state.pollTimer = setInterval(async () => {
    try {
      const data = await fetchJSON(`/api/session/${state.sessionId}`);
      renderLogs(data.logs);
      const last = data.logs[data.logs.length - 1];
      if (last && typeof last.progress === 'number') setOverall(last.progress);
      if (data.status === 'done') {
        stopPolling();
        setStatusChip('done');
        setOverall(100);
        showResult(data.result);
        $('protectBtn').disabled = false;
      } else if (data.status === 'error') {
        stopPolling();
        setStatusChip('error');
        showError(data.error || '处理失败');
        $('protectBtn').disabled = false;
      }
    } catch (e) {
      /* ignore transient errors */
    }
  }, 700);
}

function stopPolling() {
  if (state.pollTimer) clearInterval(state.pollTimer);
  state.pollTimer = null;
}

function showResult(result) {
  if (!result) return;
  $('resultCard').classList.remove('hidden');
  $('resultName').textContent = result.name;
  $('resultSize').textContent = fmtSize(result.size);
  $('resultSha').textContent = result.sha256 || '—';
  $('resultSha').title = result.sha256 || '';
  $('resultCert').textContent = result.certDn || '已签名';
  $('downloadBtn').onclick = () => {
    window.location.href = `/api/session/${state.sessionId}/download`;
  };
}

function showError(msg) {
  $('errorCard').classList.remove('hidden');
  $('errorText').textContent = msg || '未知错误';
}

function initActions() {
  $('selectAll').addEventListener('click', () => {
    state.options.forEach((o) => state.selected.add(o.id));
    syncOptionUI();
  });
  $('selectNone').addEventListener('click', () => {
    state.selected.clear();
    syncOptionUI();
  });
  $('protectBtn').addEventListener('click', startProtect);
  $('resetBtn').addEventListener('click', () => {
    stopPolling();
    state.sessionId = null;
    state.meta = null;
    $('metaCard').classList.add('hidden');
    $('resultCard').classList.add('hidden');
    $('errorCard').classList.add('hidden');
    $('logView').innerHTML = '<div class="log-empty">上传 APK 并点击「开始保护处理」后，<br>此处将实时输出解包、注入、打包、签名日志。</div>';
    setOverall(0);
    setStatusChip('idle');
    syncOptionUI();
  });
}

(async function init() {
  initHealth();
  setupUpload();
  initActions();
  try {
    const data = await fetchJSON('/api/options');
    renderOptions(data.options);
    syncOptionUI();
  } catch (e) {
    showError('无法加载保护选项：' + e.message);
  }
})();
