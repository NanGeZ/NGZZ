import pptxgen from "/root/.nvm/versions/node/v24.1.0/lib/node_modules/pptxgenjs/dist/pptxgen.es.js";
import { addSingleLineToken } from "/data/user/plugins/trae-remote-official/jingmei-ppt/1.0.0/skills/jingmei-ppt/scripts/pptx-text-guards.mjs";

const pptx = new pptxgen();
pptx.defineLayout({ name: "W16x9", width: 13.333, height: 7.5 });
pptx.layout = "W16x9";

const W = 13.333, H = 7.5, M = 0.62;

const C = {
  paper: "F7F4EC",
  ink: "26231E",
  secondary: "4A443B",
  muted: "8C8375",
  accent: "2F5D50",
  accentDark: "24473D",
  accentLight: "DDE7E1",
  tea: "B4713A",
  teaLight: "F0E4D4",
  hairline: "D8D0C0",
  onDark: "F3EFE6",
  onDarkMuted: "A9B8B0",
};

const F = {
  serif: "Noto Serif CJK SC",
  sans: "Noto Sans CJK SC",
  sansLight: "Noto Sans CJK SC Light",
  sansMed: "Noto Sans CJK SC Medium",
  sansBlack: "Noto Sans CJK SC Black",
  latin: "Arial",
};

function chrome(slide, { section, claim, pageNo, dark = false }) {
  if (section) {
    slide.addText(section, {
      x: M, y: 0.42, w: 6, h: 0.28,
      fontFace: F.sansMed, fontSize: 10, color: dark ? C.onDarkMuted : C.muted,
      charSpacing: 3, margin: 0, wrap: false,
    });
  }
  if (claim) {
    slide.addText(claim, {
      x: M, y: 0.78, w: W - 2 * M, h: 0.62,
      fontFace: F.serif, fontSize: 27, bold: true, color: dark ? C.onDark : C.ink,
      margin: 0, wrap: true, valign: "top",
    });
  }
  slide.addShape(pptx.ShapeType.line, {
    x: M, y: 7.02, w: W - 2 * M, h: 0,
    line: { color: dark ? "3E5C52" : C.hairline, width: 0.75 },
  });
  slide.addText("周浩南 · 个人简历", {
    x: M, y: 7.08, w: 3, h: 0.24,
    fontFace: F.sans, fontSize: 8, color: dark ? C.onDarkMuted : C.muted, margin: 0, wrap: false,
  });
  addSingleLineToken(slide, String(pageNo).padStart(2, "0"), {
    x: W - M - 0.5, y: 7.06, w: 0.5, h: 0.26,
    fontFace: F.latin, fontSize: 9, color: dark ? C.onDarkMuted : C.muted, align: "right",
  });
}

function leafMotif(slide, x, y, scale, color, transparency) {
  slide.addShape(pptx.ShapeType.arc, {
    x, y, w: 0.62 * scale, h: 1.15 * scale,
    rotate: 32, fill: { color, transparency },
    line: { color, transparency: 100 },
  });
  slide.addShape(pptx.ShapeType.arc, {
    x: x + 0.34 * scale, y: y + 0.12 * scale, w: 0.52 * scale, h: 0.95 * scale,
    rotate: -28, fill: { color, transparency },
    line: { color, transparency: 100 },
  });
  slide.addShape(pptx.ShapeType.line, {
    x: x + 0.3 * scale, y: y + 0.5 * scale, w: 0.02, h: 1.15 * scale,
    rotate: 8, line: { color, width: 1.2, transparency },
  });
}

function pageBg(slide) {
  slide.background = { color: C.paper };
  slide.addShape(pptx.ShapeType.rect, {
    x: 0, y: 0, w: 0.16, h: H,
    fill: { color: C.accent }, line: { color: C.accent },
  });
  slide.addShape(pptx.ShapeType.rect, {
    x: 0, y: 6.42, w: 0.16, h: 1.08,
    fill: { color: C.tea }, line: { color: C.tea },
  });
}

function sectionRow(slide, { x, y, w, h, label, value, valueColor = C.secondary, valueSize = 12 }) {
  slide.addText(label, {
    x, y: y + 0.02, w: 1.35, h: 0.34,
    fontFace: F.sansMed, fontSize: 11, bold: true, color: C.accent, margin: 0, wrap: false,
  });
  slide.addText(value, {
    x: x + 1.45, y, w: w - 1.45, h,
    fontFace: F.sans, fontSize: valueSize, color: valueColor, margin: 0, wrap: true,
    valign: "top", lineSpacingMultiple: 1.18,
  });
  slide.addShape(pptx.ShapeType.line, {
    x, y: y + h + 0.14, w, h: 0,
    line: { color: C.hairline, width: 0.75 },
  });
}

function skillRow(slide, { x, y, w, idx, name, desc, tags }) {
  addSingleLineToken(slide, idx, {
    x, y: y + 0.02, w: 0.5, h: 0.34,
    fontFace: F.latin, fontSize: 13, bold: true, color: C.tea,
  });
  slide.addText(name, {
    x: x + 0.55, y: y - 0.02, w: 1.45, h: 0.4,
    fontFace: F.sansMed, fontSize: 15, bold: true, color: C.ink, margin: 0, wrap: false,
  });
  slide.addText(desc, {
    x: x + 2.05, y: y - 0.01, w: w - 2.05, h: 0.72,
    fontFace: F.sans, fontSize: 11.5, color: C.secondary, margin: 0, wrap: true,
    lineSpacingMultiple: 1.15, valign: "top",
  });
  if (tags && tags.length) {
    let tx = x + 2.05;
    for (const t of tags) {
      const tw = 0.36 + t.length * 0.125;
      slide.addShape(pptx.ShapeType.roundRect, {
        x: tx, y: y + 0.62, w: tw, h: 0.3,
        rectRadius: 0.06,
        fill: { color: C.accentLight }, line: { color: C.accentLight },
      });
      slide.addText(t, {
        x: tx, y: y + 0.62, w: tw, h: 0.3,
        fontFace: F.sans, fontSize: 8.5, color: C.accentDark,
        align: "center", valign: "middle", margin: 0, wrap: false,
      });
      tx += tw + 0.1;
    }
  }
}

function projectRow(slide, { x, y, w, name, role, date, metric, metricLabel, lines }) {
  slide.addShape(pptx.ShapeType.rect, {
    x, y: y + 0.06, w: 0.09, h: 0.34,
    fill: { color: C.tea }, line: { color: C.tea },
  });
  slide.addText(name, {
    x: x + 0.22, y, w: 2.6, h: 0.42,
    fontFace: F.sansMed, fontSize: 15, bold: true, color: C.ink, margin: 0, wrap: false,
  });
  slide.addText(role, {
    x: x + 2.9, y: y + 0.04, w: 1.7, h: 0.32,
    fontFace: F.sans, fontSize: 10, color: C.accent, margin: 0, wrap: false,
  });
  addSingleLineToken(slide, date, {
    x: x + w - 2.1, y: y + 0.03, w: 2.1, h: 0.32,
    fontFace: F.latin, fontSize: 10.5, color: C.muted, align: "right",
  });
  let ty = y + 0.52;
  for (const ln of lines) {
    slide.addShape(pptx.ShapeType.ellipse, {
      x: x + 0.28, y: ty + 0.115, w: 0.055, h: 0.055,
      fill: { color: C.muted }, line: { color: C.muted },
    });
    slide.addText(ln, {
      x: x + 0.48, y: ty, w: w - 0.48 - 1.9, h: 0.4,
      fontFace: F.sans, fontSize: 11, color: C.secondary, margin: 0, wrap: true,
      lineSpacingMultiple: 1.12, valign: "top",
    });
    ty += 0.36;
  }
  if (metric) {
    addSingleLineToken(slide, metric, {
      x: x + w - 1.85, y: y + 0.56, w: 1.35, h: 0.52,
      fontFace: F.sansBlack, fontSize: 24, bold: true, color: C.tea, align: "right",
    });
    slide.addText(metricLabel, {
      x: x + w - 1.85, y: y + 1.06, w: 1.85, h: 0.26,
      fontFace: F.sans, fontSize: 8.5, color: C.muted, align: "right", margin: 0, wrap: false,
    });
  }
}

function courseGroup(slide, { x, y, w, name, items, idx }) {
  slide.addShape(pptx.ShapeType.rect, {
    x, y: y + 0.05, w: 0.28, h: 0.28,
    fill: { color: idx % 2 === 0 ? C.accent : C.tea }, line: { color: "FFFFFF", width: 0 },
  });
  addSingleLineToken(slide, String(idx + 1).padStart(2, "0"), {
    x, y: y + 0.05, w: 0.28, h: 0.28,
    fontFace: F.latin, fontSize: 9, bold: true, color: C.paper, align: "center",
  });
  slide.addText(name, {
    x: x + 0.4, y, w: w - 0.4, h: 0.38,
    fontFace: F.sansMed, fontSize: 14, bold: true, color: C.ink, margin: 0, wrap: false,
  });
  const chipY = y + 0.5;
  let cx = x, cy = chipY;
  for (const it of items) {
    const cw = 0.32 + it.length * 0.132;
    if (cx + cw > x + w) { cx = x; cy += 0.42; }
    slide.addShape(pptx.ShapeType.roundRect, {
      x: cx, y: cy, w: cw, h: 0.32,
      rectRadius: 0.05,
      fill: { color: "FFFFFF" }, line: { color: C.hairline, width: 0.75 },
    });
    slide.addText(it, {
      x: cx, y: cy, w: cw, h: 0.32,
      fontFace: F.sans, fontSize: 9.5, color: C.secondary,
      align: "center", valign: "middle", margin: 0, wrap: false,
    });
    cx += cw + 0.11;
  }
}

function statBlock(slide, { x, y, w, value, unit, label, note }) {
  addSingleLineToken(slide, value, {
    x, y, w: 1.75, h: 0.72,
    fontFace: F.sansBlack, fontSize: 40, bold: true, color: C.tea, align: "left",
  });
  slide.addText(unit, {
    x: x + 1.78, y: y + 0.28, w: 1.0, h: 0.32,
    fontFace: F.sansMed, fontSize: 13, bold: true, color: C.tea, margin: 0, wrap: false,
  });
  slide.addText(label, {
    x, y: y + 0.78, w, h: 0.32,
    fontFace: F.sansMed, fontSize: 12.5, bold: true, color: C.ink, margin: 0, wrap: false,
  });
  slide.addText(note, {
    x, y: y + 1.12, w, h: 0.3,
    fontFace: F.sans, fontSize: 9.5, color: C.muted, margin: 0, wrap: false,
  });
}

/* ---------------- Slide 1 · Cover ---------------- */
{
  const s = pptx.addSlide();
  s.background = { color: C.paper };
  s.addShape(pptx.ShapeType.rect, {
    x: 8.15, y: 0, w: W - 8.15, h: H,
    fill: { color: C.accentDark }, line: { color: C.accentDark },
  });
  s.addShape(pptx.ShapeType.rect, {
    x: 8.15, y: 0, w: 0.09, h: H,
    fill: { color: C.tea }, line: { color: C.tea },
  });

  leafMotif(s, 6.15, 4.75, 0.85, C.accent, 84);
  leafMotif(s, 0.78, 0.28, 0.62, C.tea, 88);

  s.addText("个人简历 · RESUME", {
    x: M + 0.15, y: 1.02, w: 5, h: 0.32,
    fontFace: F.sansMed, fontSize: 11, color: C.muted, charSpacing: 3, margin: 0, wrap: false,
  });
  s.addShape(pptx.ShapeType.line, {
    x: M + 0.15, y: 1.52, w: 1.15, h: 0,
    line: { color: C.tea, width: 2.5 },
  });
  s.addText("周浩南", {
    x: M + 0.1, y: 1.78, w: 5.2, h: 1.35,
    fontFace: F.serif, fontSize: 66, bold: true, color: C.ink, margin: 0, wrap: false,
  });
  s.addText("茶学 × 技术  复合背景", {
    x: M + 0.15, y: 3.18, w: 5.6, h: 0.5,
    fontFace: F.sansLight, fontSize: 21, color: C.accent, margin: 0, wrap: false,
  });
  s.addText("既能下机制茶、审评茶叶，也能写代码、做产品", {
    x: M + 0.15, y: 3.78, w: 6.4, h: 0.4,
    fontFace: F.sans, fontSize: 13, color: C.secondary, margin: 0, wrap: false,
  });

  const chips = ["制茶工艺", "网站开发", "安卓开发", "安卓逆向"];
  let cx = M + 0.15;
  for (const c of chips) {
    const cw = 0.36 + c.length * 0.165;
    s.addShape(pptx.ShapeType.roundRect, {
      x: cx, y: 4.32, w: cw, h: 0.38, rectRadius: 0.07,
      fill: { color: C.paper }, line: { color: C.accent, width: 1 },
    });
    s.addText(c, {
      x: cx, y: 4.32, w: cw, h: 0.38,
      fontFace: F.sans, fontSize: 10.5, color: C.accent,
      align: "center", valign: "middle", margin: 0, wrap: false,
    });
    cx += cw + 0.14;
  }

  const metas = [
    ["联系电话", "13775389830"],
    ["电子邮箱", "zhn6660@gmail.com"],
    ["现居城市", "江苏"],
  ];
  let my = 5.35;
  for (const [k, v] of metas) {
    s.addText(k, {
      x: M + 0.15, y: my, w: 1.1, h: 0.3,
      fontFace: F.sans, fontSize: 10, color: C.muted, margin: 0, wrap: false,
    });
    s.addText(v, {
      x: M + 1.35, y: my, w: 3.6, h: 0.3,
      fontFace: F.sansMed, fontSize: 11.5, color: C.ink, margin: 0, wrap: false,
    });
    my += 0.42;
  }

  s.addText("TEA", {
    x: 8.75, y: 0.85, w: 3.9, h: 1.5,
    fontFace: F.latin, fontSize: 82, bold: true, color: C.onDark,
    margin: 0, wrap: false, transparency: 88,
  });
  s.addText("&  CRAFT", {
    x: 8.75, y: 2.25, w: 3.9, h: 1.1,
    fontFace: F.latin, fontSize: 52, bold: true, color: C.onDark,
    margin: 0, wrap: false, transparency: 88,
  });
  leafMotif(s, 11.15, 3.6, 1.15, C.onDarkMuted, 82);

  s.addShape(pptx.ShapeType.line, {
    x: 8.75, y: 5.45, w: 1.15, h: 0,
    line: { color: C.tea, width: 2.5 },
  });
  s.addText("把一件事\n做深做长", {
    x: 8.75, y: 5.62, w: 3.6, h: 1.1,
    fontFace: F.serif, fontSize: 22, bold: true, color: C.onDark,
    margin: 0, wrap: true, lineSpacingMultiple: 1.25,
  });
  s.addText("2026.10", {
    x: 8.75, y: 6.85, w: 2, h: 0.3,
    fontFace: F.latin, fontSize: 10, color: C.onDarkMuted, margin: 0, wrap: false,
  });
}

/* ---------------- Slide 2 · 教育背景 ---------------- */
{
  const s = pptx.addSlide();
  pageBg(s);
  chrome(s, { section: "01 · 教育背景", claim: "茶学专业系统训练，计算机应用能力同步养成", pageNo: 2 });

  s.addShape(pptx.ShapeType.roundRect, {
    x: M, y: 1.72, w: W - 2 * M, h: 1.62, rectRadius: 0.08,
    fill: { color: "FFFFFF" }, line: { color: C.hairline, width: 0.75 },
  });
  s.addShape(pptx.ShapeType.rect, {
    x: M, y: 1.72, w: 0.1, h: 1.62,
    fill: { color: C.accent }, line: { color: C.accent },
  });
  s.addText("江苏农林职业技术学院", {
    x: M + 0.35, y: 1.98, w: 5.6, h: 0.5,
    fontFace: F.serif, fontSize: 24, bold: true, color: C.ink, margin: 0, wrap: false,
  });
  s.addText("学历 / 专业", {
    x: M + 0.35, y: 2.62, w: 1.3, h: 0.3,
    fontFace: F.sans, fontSize: 10, color: C.muted, margin: 0, wrap: false,
  });
  s.addText("专科 · 茶叶生产与加工技术", {
    x: M + 1.75, y: 2.58, w: 4.2, h: 0.36,
    fontFace: F.sansMed, fontSize: 13.5, bold: true, color: C.secondary, margin: 0, wrap: false,
  });
  s.addText("在校荣誉 / 证书", {
    x: M + 6.35, y: 2.62, w: 1.5, h: 0.3,
    fontFace: F.sans, fontSize: 10, color: C.muted, margin: 0, wrap: false,
  });
  s.addText("计算机一级证书", {
    x: M + 7.95, y: 2.58, w: 2.6, h: 0.36,
    fontFace: F.sansMed, fontSize: 13.5, bold: true, color: C.secondary, margin: 0, wrap: false,
  });
  addSingleLineToken(s, "2024.09 — 2027.05", {
    x: W - M - 3.0, y: 2.02, w: 2.7, h: 0.4,
    fontFace: F.latin, fontSize: 15, bold: true, color: C.tea, align: "right",
  });
  s.addText("在校时间", {
    x: W - M - 3.0, y: 2.46, w: 2.7, h: 0.26,
    fontFace: F.sans, fontSize: 9, color: C.muted, align: "right", margin: 0, wrap: false,
  });

  const tlY = 4.28;
  s.addShape(pptx.ShapeType.line, {
    x: M + 0.35, y: tlY, w: W - 2 * M - 0.7, h: 0,
    line: { color: C.hairline, width: 1.5 },
  });
  const nodes = [
    ["2024.09", "入学", "专科 · 茶叶生产与加工技术", true],
    ["2025 — 2026", "成长", "公众号运营 · 网站开发 · 安卓开发", false],
    ["2027.05", "毕业", "茶产业 × 技术  长期深耕", false],
  ];
  const span = (W - 2 * M - 0.7) / 3;
  nodes.forEach(([date, tag, desc, done], i) => {
    const nx = M + 0.35 + span * i + 0.12;
    s.addShape(pptx.ShapeType.ellipse, {
      x: nx - 0.09, y: tlY - 0.09, w: 0.18, h: 0.18,
      fill: { color: done ? C.tea : C.paper },
      line: { color: C.tea, width: 1.75 },
    });
    addSingleLineToken(s, date, {
      x: nx - 0.09, y: tlY - 0.62, w: 2.2, h: 0.34,
      fontFace: F.latin, fontSize: 13, bold: true, color: C.ink,
    });
    s.addText(tag, {
      x: nx - 0.09, y: tlY + 0.22, w: 1.6, h: 0.3,
      fontFace: F.sansMed, fontSize: 12, bold: true, color: C.accent, margin: 0, wrap: false,
    });
    s.addText(desc, {
      x: nx - 0.09, y: tlY + 0.54, w: span - 0.35, h: 0.62,
      fontFace: F.sans, fontSize: 10.5, color: C.secondary, margin: 0, wrap: true,
      lineSpacingMultiple: 1.15, valign: "top",
    });
  });

  s.addShape(pptx.ShapeType.roundRect, {
    x: M, y: 5.85, w: W - 2 * M, h: 0.92, rectRadius: 0.08,
    fill: { color: C.teaLight }, line: { color: C.teaLight },
  });
  s.addText("专业主线", {
    x: M + 0.35, y: 6.12, w: 1.1, h: 0.36,
    fontFace: F.sansMed, fontSize: 12, bold: true, color: C.tea, margin: 0, wrap: false,
  });
  s.addText("茶树栽培 → 茶叶加工与品质 → 审评与检测 → 茶文化与市场，完整覆盖茶产业生产与经营链条", {
    x: M + 1.55, y: 6.12, w: W - 2 * M - 1.9, h: 0.36,
    fontFace: F.sans, fontSize: 12, color: C.secondary, margin: 0, wrap: false,
  });
}

/* ---------------- Slide 3 · 专业技能 ---------------- */
{
  const s = pptx.addSlide();
  pageBg(s);
  chrome(s, { section: "02 · 专业技能", claim: "四条能力线：茶叶技艺、网站开发、安卓开发、逆向分析", pageNo: 3 });

  const rows = [
    {
      idx: "01", name: "制茶工艺",
      desc: "手工与机器制作扁形茶、卷形茶、针型茶等，掌握主要茶类造型工艺。",
      tags: ["扁形茶", "卷形茶", "针型茶"],
    },
    {
      idx: "02", name: "网站开发",
      desc: "熟悉从网站设计与开发到服务器部署上线的完整流程，具备网站长期运营与性能优化经验。",
      tags: ["设计开发", "部署上线", "运营优化"],
    },
    {
      idx: "03", name: "安卓开发",
      desc: "熟练使用开发工具进行开发并构建安卓 APK，可独立完成从需求到发布的完整交付。",
      tags: ["APK 构建", "独立发布"],
    },
    {
      idx: "04", name: "安卓逆向",
      desc: "运用 JADX、IDA、MT 管理器等逆向工具对 APP 进行解包、抓包等逆向分析。",
      tags: ["JADX", "IDA", "MT 管理器", "抓包分析"],
    },
  ];

  const colW = (W - 2 * M - 0.5) / 2;
  rows.forEach((r, i) => {
    const col = i % 2, row = Math.floor(i / 2);
    const x = M + col * (colW + 0.5);
    const y = 1.85 + row * 2.42;
    s.addShape(pptx.ShapeType.roundRect, {
      x, y: y - 0.22, w: colW, h: 2.16, rectRadius: 0.08,
      fill: { color: "FFFFFF" }, line: { color: C.hairline, width: 0.75 },
    });
    skillRow(s, { x: x + 0.3, y: y + 0.06, w: colW - 0.6, ...r });
  });

  s.addShape(pptx.ShapeType.roundRect, {
    x: M, y: 6.28, w: W - 2 * M, h: 0.56, rectRadius: 0.08,
    fill: { color: C.accentLight }, line: { color: C.accentLight },
  });
  s.addText("茶叶技艺 × 工程能力：从一片叶子到一款产品，两端都能独立交付", {
    x: M + 0.35, y: 6.4, w: W - 2 * M - 0.7, h: 0.32,
    fontFace: F.sansMed, fontSize: 12, bold: true, color: C.accentDark, margin: 0, wrap: false,
  });
}

/* ---------------- Slide 4 · 项目与实践经历 ① ---------------- */
{
  const s = pptx.addSlide();
  pageBg(s);
  chrome(s, { section: "03 · 项目与实践经历", claim: "内容与产品：公众号 3 万+ 粉丝，网站 35 万+ 访问", pageNo: 4 });

  projectRow(s, {
    x: M, y: 1.9, w: W - 2 * M,
    name: "个人微信公众号", role: "独立运营", date: "2024.09 — 2026.10",
    metric: "3万+", metricLabel: "粉丝规模",
    lines: [
      "从 0 到 1 独立完成公众号的定位、选题、内容创作与粉丝运营，粉丝规模达 3 万+。",
      "坚持长期稳定输出，单篇阅读量均破千，多篇破万，具备持续的内容产出与传播能力。",
    ],
  });
  s.addShape(pptx.ShapeType.line, {
    x: M, y: 3.62, w: W - 2 * M, h: 0,
    line: { color: C.hairline, width: 0.75 },
  });
  projectRow(s, {
    x: M, y: 3.92, w: W - 2 * M,
    name: "个人网站", role: "开发与运营", date: "2025.12 — 2026.10",
    metric: "35万+", metricLabel: "全网累计访问量",
    lines: [
      "独立完成个人网站的策划、开发、部署与长期运营，全网累计访问量达 35 万+。",
      "覆盖设计、开发、服务器部署上线到长期维护的完整链路，持续进行性能优化。",
    ],
  });

  s.addShape(pptx.ShapeType.roundRect, {
    x: M, y: 5.98, w: W - 2 * M, h: 0.82, rectRadius: 0.08,
    fill: { color: C.teaLight }, line: { color: C.teaLight },
  });
  s.addText("能力印证", {
    x: M + 0.35, y: 6.2, w: 1.1, h: 0.36,
    fontFace: F.sansMed, fontSize: 12, bold: true, color: C.tea, margin: 0, wrap: false,
  });
  s.addText("两个从 0 到 1 的自驱项目，验证了选题传播、内容产出、全栈开发与长期运营的完整能力", {
    x: M + 1.55, y: 6.2, w: W - 2 * M - 1.9, h: 0.36,
    fontFace: F.sans, fontSize: 12, color: C.secondary, margin: 0, wrap: false,
  });
}

/* ---------------- Slide 5 · 项目与实践经历 ② ---------------- */
{
  const s = pptx.addSlide();
  pageBg(s);
  chrome(s, { section: "03 · 项目与实践经历", claim: "开发与安全：三款安卓应用独立发布，完成校园平台渗透测试", pageNo: 5 });

  projectRow(s, {
    x: M, y: 1.9, w: W - 2 * M,
    name: "Android 应用开发", role: "独立开发并发布三款应用", date: "2026.09",
    metric: "3款", metricLabel: "独立发布 · 用户量均破千",
    lines: [
      "独立完成三款 Android 应用的需求设计、开发、发布，三款应用用户量均破千。",
      "单款应用全链路交付：需求设计 → 开发构建 APK → 发布上线 → 用户积累。",
    ],
  });
  s.addShape(pptx.ShapeType.line, {
    x: M, y: 3.62, w: W - 2 * M, h: 0,
    line: { color: C.hairline, width: 0.75 },
  });
  projectRow(s, {
    x: M, y: 3.92, w: W - 2 * M,
    name: "校园智能体平台", role: "安全渗透测试", date: "2026.09 — 2026.10",
    metric: "多项", metricLabel: "漏洞挖掘与规范报告",
    lines: [
      "使用 AI 辅助对学校开发的智能体平台开展渗透测试，并挖掘多项漏洞。",
      "将发现的安全风险整理为规范的漏洞报告，正式递交给相关老师。",
    ],
  });

  s.addShape(pptx.ShapeType.roundRect, {
    x: M, y: 5.98, w: W - 2 * M, h: 0.82, rectRadius: 0.08,
    fill: { color: C.accentLight }, line: { color: C.accentLight },
  });
  s.addText("能力印证", {
    x: M + 0.35, y: 6.2, w: 1.1, h: 0.36,
    fontFace: F.sansMed, fontSize: 12, bold: true, color: C.accentDark, margin: 0, wrap: false,
  });
  s.addText("从开发到逆向再到安全，形成「会做产品、也懂攻防」的工程闭环", {
    x: M + 1.55, y: 6.2, w: W - 2 * M - 1.9, h: 0.36,
    fontFace: F.sans, fontSize: 12, color: C.secondary, margin: 0, wrap: false,
  });
}

/* ---------------- Slide 6 · 专业课程 ---------------- */
{
  const s = pptx.addSlide();
  pageBg(s);
  chrome(s, { section: "04 · 专业课程", claim: "四组课程覆盖茶产业从种植、加工到市场的完整链条", pageNo: 6 });

  const groups = [
    { name: "植物与茶树基础", items: ["植物生产环境", "植物与植物生理", "茶树栽培技术", "茶树品种及繁育技术", "茶树病虫害绿色防控技术"] },
    { name: "茶叶加工与品质", items: ["茶叶品质化学", "茶叶生产与加工技术", "茶叶机械设备", "茶叶审评技术", "茶叶质量与检测技术", "茶叶标准与法规"] },
    { name: "文化与市场", items: ["茶文化", "茶艺表演与编创", "新式调饮", "茶叶市场营销"] },
    { name: "综合与拓展", items: ["茶的综合利用", "茶叶包装与储运", "专业英语"] },
  ];

  const colW = (W - 2 * M - 0.5) / 2;
  groups.forEach((g, i) => {
    const col = i % 2, row = Math.floor(i / 2);
    const x = M + col * (colW + 0.5);
    const y = 1.85 + row * 2.42;
    s.addShape(pptx.ShapeType.roundRect, {
      x, y: y - 0.22, w: colW, h: 2.18, rectRadius: 0.08,
      fill: { color: "FFFFFF" }, line: { color: C.hairline, width: 0.75 },
    });
    courseGroup(s, { x: x + 0.3, y: y + 0.05, w: colW - 0.6, name: g.name, items: g.items, idx: i });
  });

  s.addShape(pptx.ShapeType.roundRect, {
    x: M, y: 6.28, w: W - 2 * M, h: 0.56, rectRadius: 0.08,
    fill: { color: C.teaLight }, line: { color: C.teaLight },
  });
  s.addText("18 门专业课程 · 从茶园到茶杯，从品质审评到市场经营", {
    x: M + 0.35, y: 6.4, w: W - 2 * M - 0.7, h: 0.32,
    fontFace: F.sansMed, fontSize: 12, bold: true, color: C.tea, margin: 0, wrap: false,
  });
}

/* ---------------- Slide 7 · 自我评价 ---------------- */
{
  const s = pptx.addSlide();
  s.background = { color: C.accentDark };
  s.addShape(pptx.ShapeType.rect, {
    x: 0, y: 0, w: 0.16, h: H,
    fill: { color: C.tea }, line: { color: C.tea },
  });
  chrome(s, { section: "05 · 自我评价", claim: "复合背景、项目驱动、长期主义", pageNo: 7, dark: true });

  s.addText("茶学专业与技术能力的复合背景：既能下机制茶、审评茶叶，也能写代码、做产品；习惯以项目驱动自学，为运营公众号钻研内容创作，为解决需求钻研开发、逆向与安全。相比追逐技术热点，更愿意把一件事做深做长，享受从想法到成品完整交付的过程，愿在茶产业与技术的交叉点上长期深耕。", {
    x: M, y: 1.72, w: W - 2 * M, h: 1.7,
    fontFace: F.serif, fontSize: 15.5, color: C.onDark, margin: 0, wrap: true,
    lineSpacingMultiple: 1.5, valign: "top",
  });

  const cols = [
    ["复合背景", "茶学 × 技术", "既能下机制茶、审评茶叶，也能写代码、做产品"],
    ["项目驱动", "以做代学", "为运营公众号钻研内容创作，为解决需求钻研开发、逆向与安全"],
    ["长期主义", "做深做长", "不追逐技术热点，享受从想法到成品的完整交付过程"],
    ["深耕方向", "茶业深耕", "愿在茶产业与技术的交叉点上长期深耕"],
  ];
  const cw = (W - 2 * M - 0.75) / 4;
  cols.forEach(([tag, title, desc], i) => {
    const x = M + i * (cw + 0.25);
    s.addShape(pptx.ShapeType.line, {
      x, y: 3.85, w: cw - 0.1, h: 0,
      line: { color: C.tea, width: 2 },
    });
    s.addText(tag, {
      x, y: 3.98, w: cw - 0.1, h: 0.28,
      fontFace: F.sans, fontSize: 9.5, color: C.onDarkMuted, charSpacing: 2, margin: 0, wrap: false,
    });
    s.addText(title, {
      x, y: 4.3, w: cw - 0.1, h: 0.44,
      fontFace: F.serif, fontSize: 19, bold: true, color: C.onDark, margin: 0, wrap: false,
    });
    s.addText(desc, {
      x, y: 4.86, w: cw - 0.15, h: 1.1,
      fontFace: F.sans, fontSize: 10.5, color: C.onDarkMuted, margin: 0, wrap: true,
      lineSpacingMultiple: 1.3, valign: "top",
    });
  });

  leafMotif(s, 12.55, 1.55, 0.62, C.onDarkMuted, 88);

  s.addShape(pptx.ShapeType.line, {
    x: M, y: 6.28, w: W - 2 * M, h: 0,
    line: { color: "3E5C52", width: 0.75 },
  });
  const contacts = [
    ["电话", "13775389830"],
    ["邮箱", "zhn6660@gmail.com"],
    ["城市", "江苏"],
  ];
  let cx = M;
  for (const [k, v] of contacts) {
    s.addText(k, {
      x: cx, y: 6.44, w: 0.6, h: 0.3,
      fontFace: F.sans, fontSize: 10, color: C.onDarkMuted, margin: 0, wrap: false,
    });
    s.addText(v, {
      x: cx + 0.65, y: 6.42, w: 2.6, h: 0.32,
      fontFace: F.sansMed, fontSize: 11.5, color: C.onDark, margin: 0, wrap: false,
    });
    cx += 3.55;
  }
  s.addText("感谢阅读", {
    x: W - M - 2.0, y: 6.38, w: 2.0, h: 0.38,
    fontFace: F.serif, fontSize: 16, bold: true, color: C.onDark,
    align: "right", margin: 0, wrap: false,
  });
}

await pptx.writeFile({ fileName: "/workspace/resume_ppt/周浩南-个人简历.pptx" });
console.log("PPTX written");
