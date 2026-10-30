/*
 * PokéBonk 辅助（游戏黑洞救星）
 * ------------------------------------------------------------
 * 原理：浏览器对同一 URL 的 ES 模块只实例化一次。本脚本动态发现游戏
 * 真正的入口模块并 import() 它，拿到导出的 App 类；再通过给 App.prototype
 * 打一个空补丁捕获游戏实例，最后在运行时给玩家受伤逻辑、对敌伤害逻辑
 * 打补丁。全程不修改、不下载篡改游戏文件，只在你自己的浏览器里生效。
 *
 * 用法（任选其一）：
 *   1) 打开 https://pokebonk.com/ 后，按 F12 粘贴本文件全部内容到控制台回车；
 *   2) 或存成油猴(Tampermonkey)脚本，头部加 @match https://pokebonk.com/*；
 *   3) 或做成书签，把本文件内容包进 javascript:(function(){ ... })()。
 *
 * 功能：
 *   - 无敌模式：玩家不再掉血
 *   - 伤害倍率：玩家输出 1x ~ 100x（100x 基本秒杀）
 *   - 一键清屏：立刻消灭场上所有普通敌人
 */
(() => {
  'use strict';
  if (window.__PB_ASSIST__) { window.__PB_ASSIST__.togglePanel?.(); return; }

  const S = { god: true, mult: 1, panel: true };
  const log = (...a) => console.log('%c[PokéBonk辅助]', 'color:#e94756;font-weight:bold', ...a);

  // ---- 1. 发现游戏入口模块 ------------------------------------------------
  async function findGameEntry() {
    const home = await fetch('/', { cache: 'no-store' }).then(r => r.text());
    const outer = home.match(/assets\/(main-[\w-]+\.js)/)?.[1];
    if (!outer) throw new Error('未找到外层入口');
    const outerJs = await fetch('/assets/' + outer).then(r => r.text());
    // 外层 bundle 内引用了真正的游戏 bundle
    const inner = [...new Set(outerJs.match(/main-[\w-]+\.js/g) || [])].find(n => n !== outer);
    if (!inner) throw new Error('未找到游戏模块');
    return new URL('./assets/' + inner, location.origin).href;
  }

  // ---- 2. 给类原型打补丁 --------------------------------------------------
  function patchOnce(app) {
    if (S._patched) return;
    const run = app.run;
    if (!run || !run.player || !run.combat) return; // 还没开局
    S._patched = true;

    // 无敌：受伤结算后立刻把血量拉满
    const pProto = Object.getPrototypeOf(run.player);
    if (!pProto.__pbGod) {
      pProto.__pbGod = true;
      const orig = pProto.hurt;
      pProto.hurt = function (...args) {
        const r = orig.apply(this, args);
        if (S.god) { this.hp = this.maxHp; this.iframes = Math.max(this.iframes || 0, 0.45); }
        return r;
      };
    }

    // 伤害倍率：放大玩家打向敌人的每一次伤害
    const cProto = Object.getPrototypeOf(run.combat);
    if (!cProto.__pbDmg) {
      cProto.__pbDmg = true;
      for (const name of ['takeHp', 'remoteHit']) {
        const orig = cProto[name];
        if (typeof orig !== 'function') continue;
        cProto[name] = function (idx, dmg, ...rest) {
          if (S.mult !== 1 && typeof dmg === 'number') dmg *= S.mult;
          return orig.call(this, idx, dmg, ...rest);
        };
      }
    }
    log('已接入游戏实例并完成打补丁 ✔');
  }

  // ---- 3. 一键清屏 --------------------------------------------------------
  function wipeScreen() {
    const app = window.__pbApp__; const run = app?.run;
    const combat = run?.combat, en = run?.enemies;
    if (!combat || !en) return;
    for (let i = 0; i < en.n; i++) if (en.alive[i]) combat.takeHp(i, 1e12);
    toast('已清屏');
  }

  // ---- 4. 悬浮面板 --------------------------------------------------------
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
        .panel{position:fixed;right:16px;bottom:80px;z-index:2147483647;width:232px;background:#1a1630;color:#fff;
          border:1px solid #3a3160;border-radius:14px;padding:14px;box-shadow:0 10px 40px rgba(0,0,0,.55);font-size:13px}
        .panel h4{margin:0 0 10px;font-size:14px;letter-spacing:.5px;color:#ffd166}
        .row{display:flex;align-items:center;justify-content:space-between;margin:9px 0}
        .lbl{opacity:.9}
        .sw{position:relative;width:40px;height:22px;border-radius:11px;background:#413a6b;cursor:pointer;transition:.2s}
        .sw.on{background:#3ec46d}
        .sw::after{content:"";position:absolute;top:2px;left:2px;width:18px;height:18px;border-radius:50%;background:#fff;transition:.2s}
        .sw.on::after{left:20px}
        .mults{display:flex;gap:6px;margin-top:4px}
        .mults button{flex:1;padding:6px 0;border:0;border-radius:8px;background:#2b2450;color:#fff;cursor:pointer;font-size:12px}
        .mults button.sel{background:#e94756;font-weight:700}
        .wipe{width:100%;margin-top:10px;padding:8px 0;border:0;border-radius:8px;background:#ffb703;color:#22203a;font-weight:700;cursor:pointer}
        .st{margin-top:9px;font-size:11px;color:#8f86c7;line-height:1.5}
      </style>
      <button class="fab" title="PokéBonk 辅助">🎮</button>
      <div class="panel">
        <h4>PokéBonk 辅助</h4>
        <div class="row"><span class="lbl">无敌模式</span><div class="sw on" id="god"></div></div>
        <div class="row" style="margin-bottom:2px"><span class="lbl">伤害倍率</span></div>
        <div class="mults" id="mults">
          <button data-m="1" class="sel">1x</button>
          <button data-m="5">5x</button>
          <button data-m="20">20x</button>
          <button data-m="100">100x</button>
        </div>
        <button class="wipe" id="wipe">一键清屏</button>
        <div class="st" id="st">状态：连接中…</div>
      </div>`;
    document.documentElement.appendChild(host);

    const $ = id => root.getElementById(id);
    const fab = root.querySelector('.fab'), panel = root.querySelector('.panel');
    panel.style.display = 'block';
    fab.onclick = () => panel.style.display = panel.style.display === 'none' ? 'block' : 'none';

    $('god').onclick = e => { S.god = !S.god; e.target.classList.toggle('on', S.god); };
    root.querySelectorAll('#mults button').forEach(b => b.onclick = () => {
      S.mult = +b.dataset.m;
      root.querySelectorAll('#mults button').forEach(x => x.classList.toggle('sel', x === b));
    });
    $('wipe').onclick = wipeScreen;

    // 状态刷新
    setInterval(() => {
      const app = window.__pbApp__;
      const inRun = !!app?.run;
      $('st').textContent = inRun
        ? `状态：游戏中 ✔  血量 ${Math.round(app.run.player?.hp ?? 0)}/${Math.round(app.run.player?.maxHp ?? 0)}`
        : '状态：等待进入对局（在主菜单开始一局）';
    }, 500);

    window.__PB_ASSIST__.togglePanel = () => {
      panel.style.display = panel.style.display === 'none' ? 'block' : 'none';
    };
  }

  // ---- 5. 提示气泡 --------------------------------------------------------
  function toast(msg) {
    const d = document.createElement('div');
    d.textContent = msg;
    d.style.cssText = 'position:fixed;left:50%;top:14%;transform:translateX(-50%);z-index:2147483647;' +
      'background:#1a1630;color:#ffd166;padding:10px 20px;border-radius:10px;font:700 15px system-ui;' +
      'box-shadow:0 8px 24px rgba(0,0,0,.5);pointer-events:none';
    document.body.appendChild(d);
    setTimeout(() => d.remove(), 1400);
  }

  // ---- 6. 主流程 ----------------------------------------------------------
  async function main() {
    const url = await findGameEntry();
    log('游戏模块：', url);
    const mod = await import(url);            // 复用页面已加载的同一模块实例
    const App = mod.App;
    if (!App?.prototype) throw new Error('未导出 App');

    // 捕获游戏实例（开机）：boot 由 App 实例直接调用
    const origBoot = App.prototype.boot;
    if (typeof origBoot === 'function') {
      App.prototype.boot = function (...a) {
        window.__pbApp__ = this;
        return origBoot.apply(this, a);
      };
    }

    // 捕获游戏实例（每帧）：frameGap 每帧都会被调用，是最稳的注入点。
    // 这里同时做「每帧把血量拉满」，覆盖所有伤害来源。
    const origGap = App.prototype.frameGap;
    App.prototype.frameGap = function (...a) {
      window.__pbApp__ = this;
      patchOnce(this);
      const p = this.run?.player;
      if (S.god && p && typeof p.maxHp === 'number') {
        if (typeof p.hp !== 'number' || p.hp < p.maxHp) p.hp = p.maxHp;
      }
      return origGap.apply(this, a);
    };

    window.__PB_ASSIST__ = { togglePanel: null, wipe: wipeScreen, state: S };
    buildPanel();
    toast('辅助已启动');
  }

  main().catch(e => { console.error('[PokéBonk辅助] 启动失败：', e); alert('PokéBonk 辅助启动失败：\n' + e.message); });
})();
