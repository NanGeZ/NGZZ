// ==UserScript==
// @name         PokéBonk 辅助（黑洞救星）
// @namespace    http://tampermonkey.net/
// @version      3.0.0
// @description  PokéBonk 一键辅助：无敌模式、一击清怪、伤害倍率（1x-100x）、一键清屏、敌人减速/定身、经验磁铁、自动选技能、自动捕捉、实时统计面板，抗游戏更新（方法名混淆自适应），面板内含各功能效果说明
// @match        https://pokebonk.com/*
// @grant        none
// @run-at       document-idle
// @noframes
// ==/UserScript==
/*
 * PokéBonk 辅助 v3.0（游戏黑洞救星）—— 抗更新健壮版
 * ============================================================
 * 背景：游戏每次更新会重新混淆「方法名」（takeHp→$suqtxh、kill→$1fi5xi4…
 *   …），但「属性名」始终稳定（hp / speed / pickups / capture / stats /
 *   player / enemies / combat / run）。本版策略：
 *   - 无敌/清屏/定身/磁铁/统计：纯属性实现，零方法名依赖；
 *   - 伤害倍率：运行时按「源码特征」自动定位核心扣血方法（两参 + 以
 *     let x=this.run.enemies 开头 + 内含 hp 数组扣减），找不到则静默降级，
 *     绝不因改名报错。
 *
 * 七大功能（面板内均带效果说明）：
 *   ① 无敌模式：给 player.hp 装读写拦截器，致命伤自动回满，从写入点根除死亡。
 *   ② 一击清怪：每帧把存活敌人 hp 压到 1，借游戏自带击杀逻辑全场秒杀。
 *   ③ 伤害倍率（1x/5x/20x/100x）：包装核心扣血方法，一切对敌伤害 ×N。
 *   ④ 一键清屏：立刻把当前存活敌人 hp 压到 1（配合自动攻击瞬间清场）。
 *   ⑤ 敌人减速/定身（关/50%/10%/定身）：每帧按基准缓存覆写 enemies.speed[]，
 *      切回"关"自动恢复原始速度；快慢怪各自基准互不影响。
 *   ⑥ 经验磁铁：把 kind=xp 的拾取物每帧传送到玩家脚下（age>0.35 即被收集），
 *      升级速度起飞；只吸经验，金币照常自己捡。
 *   ⑦ 自动选技能 + 自动捕捉 + 实时统计：
 *      - 升级三选一弹窗（DOM .cards）自动点卡，优先高稀有度，挂机全自动；
 *      - run.capture.throwBall() 每帧尝试（内部自校验虚弱状态与距离），自动投球；
 *      - 面板实时显示时长/击杀/总伤/DPS/金币/捕捉数（读 run.stats 现成字段）。
 *
 * 用法（任选其一）：
 *   - 控制台：打开游戏按 F12，粘贴压缩版回车（首次需先输 allow pasting 解锁粘贴）；
 *   - 油猴：见 pokebonk-assist.user.js，装一次自动生效；
 *   - 书签：见 pokebonk-bookmarklet.txt，点书签启用。
 */
(() => {
  'use strict';
  if (window.__PB_ASSIST__) { window.__PB_ASSIST__.togglePanel?.(); return; }

  const S = {
    god: true, oneShot: false, mult: 1, dmg: null,
    slow: 1, magnet: false, autoPick: false, autoCatch: false
  }; // dmg: null 未检测 / true 已挂接 / false 未适配；slow: 1关 0.5/0.1 减速 0 定身
  const tried = new WeakSet();
  let slowBase = [], slowApplied = [], slowRestore = false, lastThrow = 0;
  const log = (...a) => console.log('%c[PokéBonk辅助]', 'color:#e94756;font-weight:bold', ...a);

  // ---- 1. 发现游戏入口模块（自动适配构建号变化） --------------------------
  async function findGameEntry() {
    const home = await fetch('/', { cache: 'no-store' }).then(r => r.text());
    const outer = home.match(/assets\/(main-[\w-]+\.js)/)?.[1];
    if (!outer) throw new Error('未找到外层入口');
    const outerJs = await fetch('/assets/' + outer).then(r => r.text());
    const inner = [...new Set(outerJs.match(/main-[\w-]+\.js/g) || [])].find(n => n !== outer);
    if (!inner) throw new Error('未找到游戏模块');
    return new URL('./assets/' + inner, location.origin).href;
  }

  // ---- 2. 无敌：给 player.hp 装读写拦截器 --------------------------------
  function installGod(player) {
    if (player.__pbGod) return;
    let cur = player.hp;
    try {
      Object.defineProperty(player, 'hp', {
        configurable: true, enumerable: true,
        get() { return cur; },
        set(v) {
          if (!S.god) { cur = v; return; }               // 关闭无敌时正常写入
          // 无敌开启：任何会导致死亡(≤0/非法)的写入都回满；正常掉血保留观感
          if (typeof v !== 'number' || !isFinite(v) || v <= 0) cur = this.maxHp;
          else cur = Math.min(v, this.maxHp);
        }
      });
      player.__pbGod = true;
    } catch (e) { /* 若属性不可配置则跳过，不影响游戏 */ }
  }

  // ---- 3. 伤害倍率：运行时特征匹配核心扣血方法（抗改名） ------------------
  function patchDamage(combat) {
    const proto = Object.getPrototypeOf(combat);
    if (!proto) return false;
    if (proto.__pbDmg) return true;                       // 本次页面已挂接过
    for (const k of Object.getOwnPropertyNames(proto)) {
      let fn;
      try { fn = Object.getOwnPropertyDescriptor(proto, k)?.value; } catch (e) { continue; }
      if (typeof fn !== 'function') continue;
      let src;
      try { src = Function.prototype.toString.call(fn); } catch (e) { continue; }
      // 特征：恰两参小方法、开头取出 run.enemies、体内有 hp 数组扣减
      if (src.length > 900) continue;
      if (!/^[\w$]{1,30}\(\w{1,3},\w{1,3}\)\{let \w+=this\.run\.enemies/.test(src)) continue;
      if (!/\.hp\[\w+\]/.test(src) || !/-=/.test(src)) continue;
      const orig = fn;
      try {
        proto[k] = function (e, t) {
          if (S.mult > 1 && typeof t === 'number' && isFinite(t)) t *= S.mult;
          return orig.call(this, e, t);
        };
        proto.__pbDmg = k;
      } catch (err) { return false; }
      log('伤害倍率已挂接核心扣血方法：', k);
      return true;
    }
    log('伤害倍率：未匹配到核心扣血方法（游戏可能已更新），该功能暂不可用，其余功能不受影响');
    return false;
  }

  // ---- 4. 每帧处理 --------------------------------------------------------
  function frameAssist(app) {
    const run = app.run;
    if (!run) return;
    if (run.player) installGod(run.player);

    // 倍率：每个战斗实例只尝试挂接一次
    const cb = run.combat;
    if (cb && !tried.has(cb)) {
      tried.add(cb);
      try { S.dmg = patchDamage(cb); } catch (e) { S.dmg = false; }
    }

    const en = run.enemies;
    if (en && typeof en.n === 'number') {
      // 一击清怪
      if (S.oneShot) {
        for (let i = 0; i < en.n; i++) {
          if (en.alive ? en.alive[i] : true) en.hp[i] = 1;
        }
      }
      // 敌人减速/定身：基准缓存覆写（游戏只在生成时写 speed，其余 buff 均不回写）
      if (en.speed) {
        if (slowApplied.length !== en.speed.length) {
          slowBase = new Array(en.speed.length).fill(NaN);
          slowApplied = new Array(en.speed.length).fill(NaN);
        }
        if (S.slow < 1) {
          for (let i = 0; i < en.n; i++) {
            if (en.alive && !en.alive[i]) continue;
            if (en.speed[i] !== slowApplied[i]) slowBase[i] = en.speed[i]; // 游戏写入新值(新怪)→重记基准
            en.speed[i] = slowApplied[i] = slowBase[i] * S.slow;
          }
        } else if (slowRestore) { // 切回"关"→恢复原始速度
          for (let i = 0; i < slowBase.length; i++) {
            if (!Number.isNaN(slowBase[i])) en.speed[i] = slowBase[i];
            slowBase[i] = NaN; slowApplied[i] = NaN;
          }
          slowRestore = false;
        }
      }
    }

    // 经验磁铁：xp 宝石(kind=0)传送到玩家脚下，age>0.35 即触发收集
    if (S.magnet) {
      const pk = run.pickups, pl = run.player;
      if (pk && pl?.pos && typeof pk.count === 'number') {
        for (let i = 0; i < pk.count; i++) {
          if (pk.alive && !pk.alive[i]) continue;
          if (pk.kind && pk.kind[i] !== 0) continue;
          pk.x[i] = pl.pos.x; pk.z[i] = pl.pos.z;
        }
      }
    }

    // 自动捕捉：throwBall 内部自校验虚弱状态+距离，带冷却防连扔
    if (S.autoCatch && run.capture && typeof run.capture.throwBall === 'function') {
      const now = performance.now();
      if (now - lastThrow > 800) {
        lastThrow = now;
        try { run.capture.throwBall(); } catch (e) {}
      }
    }
  }

  // ---- 5. 一键清屏 --------------------------------------------------------
  function wipeScreen() {
    const run = window.__pbApp__?.run;
    const en = run?.enemies;
    if (!en || typeof en.n !== 'number') return;
    let c = 0;
    for (let i = 0; i < en.n; i++) {
      if (en.alive ? en.alive[i] : true) { en.hp[i] = 1; c++; }
    }
    toast(`已清场（${c}）`);
  }

  // ---- 6. 悬浮面板（每个功能带效果说明 + 实时统计） -----------------------
  const fmtN = n => n >= 1e6 ? (n / 1e6).toFixed(1) + 'M' : n >= 1e3 ? (n / 1e3).toFixed(1) + 'k' : Math.round(n || 0);

  function buildPanel() {
    const host = document.createElement('div');
    host.id = 'pb-assist-host';
    const root = host.attachShadow({ mode: 'open' });
    root.innerHTML = `
      <style>
        :host{all:initial}
        *{box-sizing:border-box;font-family:system-ui,-apple-system,"PingFang SC","Microsoft YaHei",sans-serif}
        .fab{position:fixed;right:16px;bottom:16px;z-index:2147483647;width:52px;height:52px;border-radius:50%;
          background:#e94756;color:#fff;border:0;cursor:pointer;font-size:22px;box-shadow:0 6px 20px rgba(0,0,0,.45)}
        .panel{position:fixed;right:16px;bottom:80px;z-index:2147483647;width:264px;max-height:calc(100vh - 100px);overflow-y:auto;
          background:#1a1630;color:#fff;border:1px solid #3a3160;border-radius:14px;padding:14px;
          box-shadow:0 10px 40px rgba(0,0,0,.55);font-size:13px}
        .panel h4{margin:0 0 10px;font-size:14px;letter-spacing:.5px;color:#ffd166}
        .row{display:flex;align-items:center;justify-content:space-between;margin:10px 0 0}
        .lbl{opacity:.92}
        .desc{margin:4px 0 10px;padding-left:2px;font-size:10px;color:#7d75ae;line-height:1.5}
        .sw{position:relative;width:40px;height:22px;border-radius:11px;background:#413a6b;cursor:pointer;transition:.2s;flex:none}
        .sw.on{background:#3ec46d}
        .sw::after{content:"";position:absolute;top:2px;left:2px;width:18px;height:18px;border-radius:50%;background:#fff;transition:.2s}
        .sw.on::after{left:20px}
        .chips{display:flex;gap:4px;flex:none}
        .chips b{padding:3px 7px;border-radius:7px;background:#413a6b;color:#cfc9f0;font-size:11px;font-weight:700;cursor:pointer;transition:.15s}
        .chips b.on{background:#ffd166;color:#22203a}
        .wipe{width:100%;margin-top:2px;padding:8px 0;border:0;border-radius:8px;background:#ffb703;color:#22203a;font-weight:700;cursor:pointer}
        .st{margin-top:10px;font-size:11px;color:#8f86c7;line-height:1.5}
        .stats{margin-top:9px;padding-top:8px;border-top:1px solid #2d2750;font-size:10px;color:#8f86c7;line-height:1.7}
      </style>
      <button class="fab" title="PokéBonk 辅助">🎮</button>
      <div class="panel">
        <h4>PokéBonk 辅助</h4>
        <div class="row"><span class="lbl">无敌模式</span><div class="sw on" id="god"></div></div>
        <div class="desc">血量不会被打到 0，受到致命伤自动回满</div>
        <div class="row"><span class="lbl">一击清怪</span><div class="sw" id="os"></div></div>
        <div class="desc">全场敌人血量压到 1，随便碰到一下就倒</div>
        <div class="row"><span class="lbl">伤害倍率</span><div class="chips" id="chips">
          <b class="on" data-m="1">1x</b><b data-m="5">5x</b><b data-m="20">20x</b><b data-m="100">100x</b>
        </div></div>
        <div class="desc">你对敌人的每次伤害 ×倍数，普攻/技能/灼烧中毒全生效</div>
        <div class="row"><span class="lbl">敌人速度</span><div class="chips" id="schips">
          <b class="on" data-s="1">关</b><b data-s="0.5">50%</b><b data-s="0.1">10%</b><b data-s="0">定身</b>
        </div></div>
        <div class="desc">全场敌人移动变慢，选"定身"站着输出都打不到你</div>
        <div class="row"><span class="lbl">经验磁铁</span><div class="sw" id="mg"></div></div>
        <div class="desc">经验宝石自动吸到脚下，升级快人一步（金币不吸）</div>
        <div class="row"><span class="lbl">自动选技能</span><div class="sw" id="ap"></div></div>
        <div class="desc">升级弹窗自动选卡，优先高稀有度，挂机全自动</div>
        <div class="row"><span class="lbl">自动捕捉</span><div class="sw" id="ac"></div></div>
        <div class="desc">可捕捉的传说敌人倒地虚弱后自动投球收入队</div>
        <button class="wipe" id="wipe">一键清屏</button>
        <div class="desc">立即清空当前场上所有敌人（配合自动攻击瞬间清场）</div>
        <div class="st" id="st">状态：连接中…</div>
        <div class="stats" id="stats">统计：进入对局后显示</div>
      </div>`;
    document.documentElement.appendChild(host);

    const $ = id => root.getElementById(id);
    const fab = root.querySelector('.fab'), panel = root.querySelector('.panel');
    panel.style.display = 'block';
    fab.onclick = () => panel.style.display = panel.style.display === 'none' ? 'block' : 'none';

    const bindSw = (id, key) => $(id).onclick = e => { S[key] = !S[key]; e.target.classList.toggle('on', S[key]); };
    bindSw('god', 'god'); bindSw('os', 'oneShot'); bindSw('mg', 'magnet');
    bindSw('ap', 'autoPick'); bindSw('ac', 'autoCatch');
    $('wipe').onclick = wipeScreen;

    // 伤害倍率 chips
    const chips = [...root.querySelectorAll('#chips b')];
    chips.forEach(b => b.onclick = () => {
      S.mult = +b.dataset.m;
      chips.forEach(x => x.classList.toggle('on', x === b));
      toast(S.mult > 1
        ? (S.dmg ? `伤害倍率 ×${S.mult} 已生效` : '伤害倍率 ×' + S.mult + '：当前游戏版本未适配，暂无效')
        : '伤害倍率：原始伤害');
    });
    // 敌人速度 chips
    const schips = [...root.querySelectorAll('#schips b')];
    schips.forEach(b => b.onclick = () => {
      S.slow = +b.dataset.s;
      if (S.slow === 1) slowRestore = true;               // 切回"关"时恢复原始速度
      schips.forEach(x => x.classList.toggle('on', x === b));
      toast(S.slow === 0 ? '敌人已定身' : S.slow === 1 ? '敌人速度：正常' : `敌人速度 ×${S.slow}`);
    });

    // 自动选技能：升级弹窗是 DOM（.cards 容器内为技能卡片），点第一张/最高稀有度
    setInterval(() => {
      if (!S.autoPick) return;
      const cards = document.querySelector('.cards');
      if (!cards || !cards.children.length) return;
      const kids = Array.from(cards.children);
      const pick = kids.find(c => /rare|epic|legend|mythic/i.test(c.className)) || kids[0];
      const btn = pick.tagName === 'BUTTON' ? pick : pick.querySelector('button') || pick;
      try { btn.click(); } catch (e) {}
    }, 400);

    // 状态 + 实时统计（500ms 刷新；DPS 按 run.time 秒数滑动采样）
    let lastT = 0, lastD = 0;
    setInterval(() => {
      const run = window.__pbApp__?.run, p = run?.player;
      const mg = S.mult > 1 ? ` · 倍率×${S.mult}${S.dmg ? '' : '(未适配)'}` : '';
      $('st').textContent = p
        ? `状态：游戏中 ✔  血量 ${Math.round(p.hp ?? 0)}/${Math.round(p.maxHp ?? 0)}${mg}`
        : '状态：等待进入对局（在主菜单开始一局）';
      const st = run?.stats;
      if (!p || !st) { $('stats').textContent = '统计：进入对局后显示'; lastT = 0; return; }
      const now = run.time || 0, dmg = st.damage || 0;
      let dps = 0;
      if (lastT && now > lastT) dps = Math.max(0, (dmg - lastD) / (now - lastT));
      lastT = now; lastD = dmg;
      const mm = Math.floor(now / 60), ss = String(Math.floor(now % 60)).padStart(2, '0');
      $('stats').textContent =
        `时长 ${mm}:${ss} · 击杀 ${st.kills ?? 0} · 捕捉 ${st.captured ?? 0}\n` +
        `总伤 ${fmtN(dmg)} · DPS ${fmtN(dps)} · 金币 ${fmtN(st.gold)}`;
      $('stats').style.whiteSpace = 'pre-line';
    }, 500);

    window.__PB_ASSIST__.togglePanel = () => {
      panel.style.display = panel.style.display === 'none' ? 'block' : 'none';
    };
  }

  // ---- 7. 提示气泡 --------------------------------------------------------
  function toast(msg) {
    const d = document.createElement('div');
    d.textContent = msg;
    d.style.cssText = 'position:fixed;left:50%;top:14%;transform:translateX(-50%);z-index:2147483647;' +
      'background:#1a1630;color:#ffd166;padding:10px 20px;border-radius:10px;font:700 15px system-ui;' +
      'box-shadow:0 8px 24px rgba(0,0,0,.5);pointer-events:none';
    (document.body || document.documentElement).appendChild(d);
    setTimeout(() => d.remove(), 1400);
  }

  // ---- 8. 主流程 ----------------------------------------------------------
  async function main() {
    const url = await findGameEntry();
    log('游戏模块：', url);
    const mod = await import(url);            // 复用页面已加载的同一模块实例
    const App = mod.App;
    if (!App?.prototype) throw new Error('未导出 App');

    // 捕获游戏实例：frameGap 每帧都被调用（方法名稳定），是最稳的注入点
    const origGap = App.prototype.frameGap;
    App.prototype.frameGap = function (...a) {
      window.__pbApp__ = this;
      try { frameAssist(this); } catch (e) {}
      return origGap.apply(this, a);
    };

    window.__PB_ASSIST__ = { togglePanel: null, wipe: wipeScreen, state: S };
    buildPanel();
    toast('辅助已启动');
  }

  main().catch(e => { console.error('[PokéBonk辅助] 启动失败：', e); alert('PokéBonk 辅助启动失败：\n' + e.message); });
})();
