---
design_type: initiative
created_at: 2026-10-23
---

# 南天门（NGZZ）— APK 综合保护工具箱

## 问题

Android 开发者需要一款**高强度、纯本地、全自动化、兼容性好**的 APK 综合保护工具：在手机端对自有的 APK 施加签名校验、反调试、包名检测、抓包检测、Root 检测、网络检测、到期退出等保护能力，并输出可直接安装发布的加固 APK。市面上同类能力多依赖云端或 PC，缺乏便携、离线、自动化的一体化方案。

## 愿景

南天门（`com.ngzz.protect`）是一款 Android 手机 APP，内置开关面板（截图同款 UI）配置保护策略，一键对目标 APK 完成「解析 → 注入保护 → 重签名 → 输出」的全自动管线，全部离线完成。

## 非目标

- 不提供绕过他人应用防护/破解/二次分发的能力；仅处理用户自有或已授权的 APK。
- 不做云端处理/云签名/云端特征库。
- 不承诺"不可破解"；目标是显著提高逆向与篡改成本。
- 首版不做 UI 主题定制、多语言、账号体系。

## 利益相关方

- 使用者：Android 开发者（自有应用加固、防篡改、防抓包、防调试）。
- 维护者：本仓库开发/维护方。

## 范围

| 范围内 | 范围外 |
|---|---|
| 手机端开关面板（7 项保护能力，逐项开关） | 云端同步、在线特征更新 |
| 目标 APK 选择（SAF）、处理进度与结果输出 | PC 端 CLI/Gradle 插件 |
| Native 检测引擎（libngzz.so）注入目标 APK | 绕过第三方加固/破解他人 APK |
| Native 打包管线：zip 解析/重排、dex 表项注入、arsc 包名覆写、自动重签名 | 完整 APKTool 级 smali 反编译/回编 |
| 自动重签名（内置 keystore 生成/复用） | Play App Signing 兼容承诺 |
| 到期退出（本地时间 + 防回拨校验） | 远程时间源校验 |
| 纯本地、离线运行 | 网络特征库、云端下发策略 |

## 决策

| # | 决策点 | 选择 | 拒绝的备选 |
|---|---|---|---|
| D1 | 产品形态 | Android 手机 APP（截图同款 UI） | PC CLI/Gradle 插件；两者都要 |
| D2 | 保护施加方式 | 手机端全自动重打包（解包→注入→重签名→输出） | 仅配置生成器；两种模式都要 |
| D3 | 检测逻辑运行位置 | 注入到目标 APK（目标 APP 自检并退出） | 仅工具箱自身检测；注入+自身自检 |
| D4 | 技术路线 | 方案 B：全 Native 管线 + Native 检测引擎 | 纯 Java/smali 注入；A 先行再演进 B |
| D5 | 合规边界 | 仅合法防护自有/授权应用；代码只含防护逻辑 | — |

## 架构（Strategic）

- **UI 层**：Kotlin + Jetpack Compose，开关面板（7 项能力）、APK 选择、处理进度、结果/日志。
- **编排层**：处理流水线状态机（读取 → 校验 → 解包 → 注入 → 重签名 → 输出），任务队列与错误传播。
- **Native 检测引擎** `libngzz.so`：签名（V2/V3 证书指纹）、反调试（ptrace/TracerPid/Frida/lsposed/xposed/gg 特征）、包名、抓包（VPN/代理/证书链）、Root（su/Magisk/mount）、网络、到期（防回拨令牌）。多点冗余校验 + 阈值退出。
- **Native 打包管线** `libngzzpack.so`：zip 中央目录解析与重排、dex 表项注入（Application 代理）、arsc 包名覆写、apksig 移植（V2/V3 签名）、zipalign。
- **构建/依赖**：AGP 8.7.x + Kotlin 2.0.x + NDK（CMake）；Maven 依赖走阿里云镜像 + Google 兜底，Gradle/SDK 下载走腾讯镜像。

## 阶段拆分（Phase Breakdown）

| Phase | 目标 | 关键产出 |
|---|---|---|
| P0 | 骨架可跑 | 工程初始化、镜像/SDK 就绪、开关面板 UI、处理流水线空跑 |
| P1 | 打包管线 | zip/dex/arsc 解析与改写、自动重签名、输出可安装 APK |
| P2 | 检测引擎 | libngzz.so 7 项检测、注入目标 APK、触发退出 |
| P3 | 强度与兼容 | 多层冗余、反回拨、混淆/剥离符号、主流机型兼容测试 |
| P4 | 交付打磨 | 结果导出、日志、错误提示、发布打包 |

每个 Phase 单独走 brainstorming → writing-plans → 执行的细化流程。

## 风险与开放问题

| 风险 | 影响 | 缓解 |
|---|---|---|
| 纯本地逻辑可被逆向 patch | 无法做到"绝对不可破解" | Native 层 + 多点冗余 + 自毁；明示能力边界 |
| 重打包破坏目标 APK（签名/对齐/资源） | 目标 APP 无法安装或崩溃 | 严格对齐、V2/V3 签名、失败回滚不输出 |
| dex 注入与新 DEX 版本/加固冲突 | 处理失败 | 兼容层与明确报错；不碰原 dex 字节码，仅表项注入 |
| 反调试/Root 对抗持续升级 | 检测被绕过 | 特征可更新、多层检测、阈值退出 |
| 到期退出被改机时间绕过 | 过期仍可用 | 防回拨校验 + 签名绑定；接受本地上限 |
| NDK/SDK 体积与下载耗时 | 构建慢 | 腾讯镜像 + 阿里云 Maven；NDK 仅编译必要 ABI |

## 契约（Contracts）

### Intent Contract
```
intent: 交付南天门 Android APP，可在手机端对自有 APK 一键注入 7 项保护并自动重签名输出
constraints: 纯本地离线；不改动目标 APK 原有 dex 字节码逻辑；不提供绕过他人防护的能力；不破坏目标 APK 可安装性
success_criteria: 用户选择 APK 并开启开关后，自动输出可安装的加固 APK；目标 APP 在被篡改/调试/root/抓包/过期时按策略退出
risk_level: high
```

### Verification Contract
```
verify_steps:
  - run tests: ./gradlew test && ./gradlew connectedAndroidTest（或模拟器目标）
  - check: 处理后的 APK 可被系统安装并启动；zipalign -c 与 apksigner verify 通过
  - check: 每项保护开关单独开启时，触发条件能导致目标 APP 退出
  - check: 离线环境（无网络）下完整流程可跑通
  - confirm: 输出 APK 的签名证书为工具生成，包名与校验值符合预期
```

### Governance Contract
```
approval_gates: 每个 Phase 结束需人工确认（风险级 high，安全/对抗相关变更均需复核）
rollback: 各 Phase 独立分支/提交，失败回退到上一可运行 Phase；处理失败不输出半成品 APK
ownership: 仓库维护者负责对抗策略与合规边界把关
```

## 附注（Lint 说明）

本 initiative 文档中"AGP 8.7.x + Kotlin 2.0.x"等为技术选型示例，非逐字节实现规格；具体字节级实现归属各 Phase 的 writing-plans 任务文件。
