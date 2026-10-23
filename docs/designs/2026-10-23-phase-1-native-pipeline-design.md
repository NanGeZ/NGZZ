---
design_type: phase
created_at: 2026-10-23
---

# Phase 1 — Native 打包管线与检测引擎

## Intent Contract
```
intent: 让南天门在手机端把保护配置注入目标 APK 并自动重签名输出可安装 APK，同时提供 libngzz.so 七项检测引擎
constraints: 纯本地离线；不改动目标 APK 原 dex 字节码逻辑；处理失败不输出半成品；只含防护逻辑
success_criteria: 处理后的 APK 通过 zipalign 校验与签名校验并可安装；libngzz.so 暴露七项检测开关并按配置触发退出
risk_level: high
```

## Verification Contract
```
verify_steps:
  - run tests: ./gradlew :app:assembleDebug
  - check: libngzzpack.so 与 libngzz.so 随 APK 打包产出（arm64-v8a/armeabi-v7a）
  - check: protect() 对输入 APK 产出输出 APK，且失败时不留下半成品
  - confirm: Native 接口签名与 Kotlin 声明一致，构建无 NDK/JNI 链接错误
```

## Governance Contract
```
approval_gates: P1 完成后人工确认再进入 P2/P3 强度对抗部分
rollback: 回退到 P0 UI 骨架；native 模块可独立禁用
ownership: 仓库维护者
```

## Scope

| 范围内 | 范围外 |
|---|---|
| zip 中央目录解析/重排、dex 表项注入、arsc 包名覆写 | 完整 smali 反编译/回编 |
| V2/V3 重签名（内置 keystore） | Play App Signing |
| libngzz.so 七项检测（签名/反调试/包名/抓包/Root/网络/到期） | 云端特征更新 |

## Decisions

| # | 决策点 | 选择 | 拒绝的备选 |
|---|---|---|---|
| 1 | 打包管线语言 | C/C++（NDK） | Kotlin 实现 zip 改写 |
| 2 | 签名 | apksig V2/V3 移植 | 仅 V1 JAR 签名 |

## Surface

- JNI 接口：`NativePack.protect(inputApk, config, outputApk)` 与 `NativePack.Companion` 检测入口。
- 配置格式：4 字节小端 flag 位图（Protection 枚举顺序）。
- 文件：`app/src/main/cpp/ngzz_pack.cpp`、`ngzz_engine.cpp`、`CMakeLists.txt`。

## Risks & Open Questions

| 风险 | 影响 | 缓解 |
|---|---|---|
| dex 表项注入遇新 DEX 版本 | 处理失败 | 明确报错，不破坏输入 |
| 重签名遇原始签名方案差异 | 无法安装 | V2/V3 全量重签 + 对齐校验 |
