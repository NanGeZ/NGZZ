---
design_type: initiative
created_at: 2026-10-23
---

# 南天门（NGZZ）—— Android APK 综合保护工具箱 · 战略设计

## Problem

Android 开发者希望给自己的 APK 增加防篡改、防调试、防抓包、到期控制等保护能力，但现有方案存在明显断层：商业加固平台依赖云端上传、收费且流程黑盒；开源工具链（apktool、apksigner、StringFog 等）分散在 PC 端，需要手工串流程、写代码接入，门槛高且不可复现。开发者缺少一款「纯本地、全自动化、兼容性好、高强度」的一站式 APK 综合保护工具。

## Vision

「南天门」是一款运行在 Android 手机上的纯本地工具箱（包名 `com.tianmen.ngz`）。开发者在手机上选择一个 APK、勾选保护项、点击处理，即可自动完成「解析 → 插桩 → 字符串加密 → 重打包 → 签名 → 输出」全流程，无需 PC、无需联网、无需在目标工程里写一行代码。处理后的 APK 在运行时自动加载保护壳，检测到篡改/调试/抓包/Root/断网/到期等风险即按配置退出。

## Non-Goals

- 不做云端处理、账号体系、遥测上报；全程离线。
- 不做 Xposed/Frida 类运行时 Hook 注入（不做 Root/特殊环境依赖）。
- 本 initiative 不覆盖 Native 层（SO）深度加固、资源混淆、反二次打包等能力（列为远期迭代）。
- 不追求对抗专业逆向团队；目标是「实用级」防护：对普通篡改、调试、抓包、盗版分发有效。
- 不支持 V3/V4 签名方案的全部高级特性（如 APK Signature Scheme v4 增量签名），以 v2/v3 覆盖为目标。

## Stakeholders

| 角色 | 诉求 |
|---|---|
| Android 开发者（最终用户） | 一键给 APK 加保护，无需安全/逆向背景 |
| 工具箱使用者的 App 最终用户 | 被保护 App 正常可用，误报率低 |
| 本项目维护者 | 架构分层清晰，各阶段可独立交付与回归 |

## Architecture

分三层，核心引擎为纯 Java 模块（不依赖 Android framework），保证可在本地 JVM 上编译与测试：

1. **处理引擎（`:engine`，纯 Java）**
   - APK 解析（基于 `net.dongliu:apk-parser`，读取包名/入口 Activity/签名信息）
   - 字节码插桩（基于 `org.smali:dexlib2`：向目标 APK 注入保护壳 smali，改写 Application/入口类继承链与初始化调用）
   - 字符串加密（StringFog 思路：编译期对常量字符串加密、运行期解密；由插桩阶段完成对目标 dex 的字符串改写与解密存根注入）
   - 重打包（对解包后的 dex/资源重新封装为 APK）
   - 签名（基于 `com.android.tools.build:apksig`：v2/v3 签名，支持自定义 keystore）
2. **保护运行时（`:guard`，Android 库）**：被注入目标 APK 的壳代码。能力项：签名校验、反调试（frida/lsposed/xposed/gg 检测）、包名检测、抓包检测（VPN/WiFi 代理）、Root 检测、网络检测、到期退出。命中策略：退出程序（默认）或静默日志（可配）。到期退出支持自定义到期时间。
3. **工具箱 App（`:app`，Kotlin + Jetpack Compose + Hilt + Room + DataStore）**：任务管理 UI。功能：选择 APK、勾选保护项（含各检测项开关与到期时间设置）、处理进度/日志、任务历史（Room）、设置页（自动签名开关、自定义签名导入、输出目录配置，默认 Download 目录）。

数据流：`选择 APK → 引擎解析 → 插桩保护壳 + 字符串加密 → 重打包 →（可选）自动签名 → 输出至配置目录`。全部在设备本地完成。

## Phase Breakdown

| 阶段 | 内容 | 关键交付 |
|---|---|---|
| Phase 1 · 基础设施 | Android 工程脚手架（`:app`/`:engine`/`:guard` 三模块、Gradle 配置、Hilt/Compose 骨架）；`:engine` 纯 Java 模块可独立构建与单测；`:guard` 保护壳空实现可编译打包 | 多模块工程可构建；`:engine` 单测通过 |
| Phase 2 · 核心处理流水线 | APK 解析、保护壳注入（自动改写启动入口）、重打包、v2/v3 签名（默认自动签名 + 自定义 keystore）、输出目录管理 | 对样例 APK 一键产出可安装 APK |
| Phase 3 · 保护能力与字符串加密 | `:guard` 全部检测能力实现与命中退出策略；到期退出自定义时间；StringFog 式字符串加密 | 处理后 APK 在各风险场景下按配置触发行为 |
| Phase 4 · 设置与任务管理 | Compose 主界面、保护项配置 UI、任务历史（Room）、设置页（自动签名开关/签名导入/输出目录）、端到端联调 | 一键处理全流程验收通过（见验收标准） |

各 Phase 依次进入独立的 brainstorming → writing-plans → 执行循环。

## Risks & Open Questions

| 风险 | 影响 | 缓解 |
|---|---|---|
| Android 设备端集成 Java 工具链较重 | 安装包体积、内存占用 | `:engine` 纯 Java 精简依赖；仅内嵌必要库；大文件处理用流式 IO |
| 插桩对宿主 App 兼容性（Application 链、加固壳冲突） | 处理后 APK 崩溃 | 覆盖主流启动链模式（直接继承 Application / manifest 注册 / 多进程）；构建兼容性样例集回归；失败时明确报错不产出 |
| 误报（如正常用户开着 VPN 导致抓包误判） | 被保护 App 误退出 | 检测项独立开关 + 命中策略可配（退出/日志）；默认策略保守 |
| 到期退出依赖本地时间可被篡改 | 到期机制被绕过 | 时间校验结合安装时间戳与签名身份；接受实用级边界（不做硬件级时间锚定） |
| 引擎在沙箱中验证受限（无 Android SDK/真机） | 阶段验证不完整 | `:engine` 纯 Java 保证 JVM 单测覆盖；App 端验证步骤标注为人工 gate；请用户在真机回归 |

**开放问题**：目标 APK 的 minSdk 兼容范围待 Phase 2 样例验证后固化（预期 minSdk 21+）。

## Intent Contract

```
intent: 交付一款纯本地、全自动的 Android APK 综合保护工具箱「南天门」，支持安全检测插桩与字符串加密，一键处理并输出已签名 APK
constraints: 全程本地离线；不改宿主设备、不做运行时 Hook；目标 APK 零代码接入；设置项（自动签名开关/自定义签名/输出目录）与到期退出自定义时间必须支持；包名 com.tianmen.ngz
success_criteria: 一键处理全流程（选 APK→勾选→处理→签名→输出）在手机端无需 PC/联网完成；处理后 APK 可安装运行且各检测项在对应场景按配置生效；设置页三项配置（自动签名开关、自定义签名导入、输出目录默认 Download）完整可用
risk_level: high
```

## Verification Contract

```
verify_steps:
  - run tests: `./gradlew :engine:test`（引擎解析/插桩/签名单测）
  - run tests: `./gradlew :guard:test`（检测逻辑单元测试，JVM 可测部分）
  - run tests: `./gradlew :app:assembleDebug`（App 构建通过）
  - check: 用样例 APK 走完一键流水线，产出 APK 文件位于配置的输出目录
  - check: 处理后 APK 可安装启动，篡改/调试/抓包/Root/到期等场景按配置触发退出或日志
  - confirm: 端到端验收（一键全流程、保护生效、设置项完整）由用户在真机签字确认
```

## Governance Contract

```
approval_gates:
  - 每个 Phase 的设计文档与工作流须经用户批准后才进入实现
  - risk_level: high 的安全相关实现（保护壳检测逻辑、签名流程）步骤须人工复核
  - 端到端验收（真机回归）须用户确认后才算完成
rollback: 各 Phase 在独立 git 分支/工作树中实现，任一阶段失败可整体回退该分支，不影响主干
ownership: 用户负责验收与真机回归；实现 agent 负责实现、自测与提交
```
