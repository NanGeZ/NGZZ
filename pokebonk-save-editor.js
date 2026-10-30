/*
 * PokéBonk 存档编辑器（控制台版）
 * ------------------------------------------------------------
 * 原理（已从游戏代码逐行核实，构建 main-BMSEb-pX）：
 *   - 本地对局续玩存档 key：pb.runsave.<账号id>
 *   - 值格式：{ ticket, save: "<payload的JSON字符串>", hash: "<SHA-256十六进制>" }
 *   - hash = SHA-256(save字符串)，与游戏内 Ck() 算法完全一致
 *   - 游戏载入时只校验 save.v===1 且 JSON 合法，不比对 hash 与内容一致性
 *   - payload 字段：{v,build,summary,player,moves,enemies,director,...}
 * 注意：星尘/解锁图鉴等账号进度在服务器端，本地改了会被覆盖；此脚本改的是
 *       本地续玩存档（改完在游戏里选"继续上局"生效）。
 *
 * 用法（F12 控制台，需先输入 allow pasting 解锁粘贴）：
 *   PBStore.list()                     列出所有本地存档（摘要）
 *   PBStore.peek(id)                   查看某存档的 player 全部字段
 *   PBStore.get(id, 'player.level')    读取字段值
 *   PBStore.set(id, 'player.level', 99)修改字段（自动备份+重算hash）
 *   PBStore.setMany(id, {'player.level':99,'player.hp':9999})  批量修改
 *   PBStore.restore(id)                从自动备份恢复
 *   PBStore.wipe(id)                   删除该续玩存档（回主菜单重新开始）
 * 所有修改前会自动备份到 pb.savebackup.<id>.<时间戳>，随时可还原。
 */
(() => {
  'use strict';
  const LS = window.localStorage;
  const PREFIX = 'pb.runsave.';
  const BAK = 'pb.savebackup.';

  const sha256hex = async (str) => {
    const buf = await crypto.subtle.digest('SHA-256', new TextEncoder().encode(str));
    return [...new Uint8Array(buf)].map(b => b.toString(16).padStart(2, '0')).join('');
  };

  const ids = () => Object.keys(LS).filter(k => k.startsWith(PREFIX)).map(k => k.slice(PREFIX.length));

  const raw = (id) => {
    const t = LS.getItem(PREFIX + id);
    if (!t) return null;
    try { return JSON.parse(t); } catch { return null; }
  };

  // dot-path 读取
  const getPath = (obj, path) => path.split('.').reduce((o, k) => (o == null ? o : o[k]), obj);

  // dot-path 写入（仅对已存在的数值/布尔字段安全；新建键会提示）
  const setPath = (obj, path, val) => {
    const keys = path.split('.');
    const last = keys.pop();
    const parent = keys.reduce((o, k) => {
      if (o[k] == null || typeof o[k] !== 'object') o[k] = {};
      return o[k];
    }, obj);
    const existed = Object.prototype.hasOwnProperty.call(parent, last);
    parent[last] = val;
    return existed;
  };

  const summaryLine = (id, r) => {
    if (!r || typeof r.save !== 'string') return `${id}  （格式异常）`;
    try {
      const p = JSON.parse(r.save);
      const s = p.summary || {};
      return `${id}  |  等级 ${s.level ?? '?'}  形态#${s.finalSpecies ?? '?'}  ${s.mode ?? '?'}  ` +
             `击杀 ${s.kills ?? 0}  ${(s.time ?? 0) | 0}s  v${p.v}  build:${p.build || '?'}`;
    } catch { return `${id}  （save无法解析）`; }
  };

  // 写回：payload → JSON字符串 → sha256 → 存储（与游戏 bk() 完全同构）
  const writeBack = async (id, payload) => {
    const r = raw(id);
    const saveStr = JSON.stringify(payload);
    const hash = await sha256hex(saveStr);
    LS.setItem(PREFIX + id, JSON.stringify({ ticket: r?.ticket ?? '', save: saveStr, hash }));
    return hash;
  };

  const backup = (id) => {
    const r = LS.getItem(PREFIX + id);
    if (!r) return null;
    const key = BAK + id + '.' + Date.now();
    LS.setItem(key, r);
    return key;
  };

  const err = (msg) => { console.error('%c[PokéBonk存档]', 'color:#e94756;font-weight:bold', msg); };
  const ok = (msg) => console.log('%c[PokéBonk存档]', 'color:#3ec46d;font-weight:bold', msg);

  const needPayload = (id) => {
    const r = raw(id);
    if (!r || typeof r.save !== 'string') { throw new Error(`存档 ${id} 不存在或格式异常`); }
    return JSON.parse(r.save);
  };

  window.PBStore = {
    list() {
      const list = ids();
      if (!list.length) return ok('本地没有续玩存档（pb.runsave.*）。'), [];
      console.table(list.map(id => ({ id, ...(()=>{ try { const s = JSON.parse(raw(id).save).summary || {}; return { 等级: s.level, 形态: s.finalSpecies, 模式: s.mode, 击杀: s.kills, 时长秒: s.time | 0 }; } catch { return { 备注: '解析失败' }; } })() })));
      list.forEach(id => console.log(summaryLine(id, raw(id))));
      return list;
    },

    async peek(id) {
      const p = needPayload(id);
      console.log('summary:', p.summary);
      console.log('player :', p.player);
      console.log('moves  :', p.moves);
      console.log('顶层字段:', Object.keys(p));
      return p;
    },

    get(id, path) {
      const v = getPath(needPayload(id), path);
      console.log(`${path} =`, v);
      return v;
    },

    async set(id, path, value) {
      const p = needPayload(id);
      const existed = setPath(p, path, value);
      if (!existed) console.warn(`字段 ${path} 原本不存在，已新增（游戏可能不认识它）`);
      const bak = backup(id);
      const hash = await writeBack(id, p);
      ok(`已修改 ${path} = ${JSON.stringify(value)}  hash:${hash.slice(0, 12)}…  备份:${bak ?? '无'}`);
      return true;
    },

    async setMany(id, map) {
      const p = needPayload(id);
      for (const [path, value] of Object.entries(map)) setPath(p, path, value);
      const bak = backup(id);
      const hash = await writeBack(id, p);
      ok(`已批量修改 ${Object.keys(map).length} 个字段  hash:${hash.slice(0, 12)}…  备份:${bak ?? '无'}`);
      return true;
    },

    restore(id) {
      const baks = Object.keys(LS).filter(k => k.startsWith(BAK + id + '.')).sort();
      if (!baks.length) return err(`没有 ${id} 的备份`);
      const newest = baks[baks.length - 1];
      // 先备份当前，避免恢复错方向
      backup(id);
      LS.setItem(PREFIX + id, LS.getItem(newest));
      ok(`已从备份恢复 ${id}（来源 ${newest}）`);
    },

    wipe(id) {
      backup(id);
      LS.removeItem(PREFIX + id);
      ok(`已删除续玩存档 ${id}（原内容已备份）。游戏内将无法继续该局，需重新开局。`);
    },

    backups() {
      const list = Object.keys(LS).filter(k => k.startsWith(BAK));
      console.table(list.map(k => ({ key: k, bytes: (LS.getItem(k) || '').length })));
      return list;
    },

    help() {
      console.log(`%cPokéBonk 存档编辑器`, 'color:#ffd166;font-weight:700;font-size:14px');
      console.log(`PBStore.list()                       列出本地续玩存档`);
      console.log(`PBStore.peek('存档id')               查看 player/moves 字段`);
      console.log(`PBStore.get('id','player.level')     读字段`);
      console.log(`PBStore.set('id','player.level',99)  改字段（自动备份+重算hash）`);
      console.log(`PBStore.setMany('id',{...})          批量改`);
      console.log(`PBStore.restore('id')                恢复备份`);
      console.log(`PBStore.wipe('id')                   删存档（重新开局）`);
      console.log(`提示：修改后回游戏主菜单选“继续上局/Resume”生效。`);
      console.log(`注意：星尘/解锁等账号进度在服务器端，本地不可改。`);
    }
  };

  ok('存档编辑器已加载。先运行 PBStore.list() 查看存档，PBStore.help() 看全部命令。');
})();
