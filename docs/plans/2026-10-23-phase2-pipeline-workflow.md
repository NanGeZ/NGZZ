---
intent: 在 :engine 纯 Java 模块中实现核心处理流水线：APK 解析、保护壳 smali 注入（自动改写启动入口）、重打包、v2/v3 签名（默认自动签名 + 自定义 keystore）、输出目录管理，一键处理样例 APK 产出可安装 APK
success_criteria: ./gradlew :engine:test 全绿；ProtectionPipelineTest 证明对程序化生成的样例 APK 走完「解析→重打包→签名」流水线后产出可被 apksig ApkVerifier 验签通过的 APK 文件；KeystoreSignerTest 证明自定义 JKS keystore 可签名且缺输入抛 EngineException；OutputManagerTest 证明默认输出目录解析
risk_level: high
auto_approve: true
---

## Steps

- [ ] **Step 1: 定义流水线数据契约（配置与结果模型）**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/pipeline/` 创建三个纯 Java 类：`ProtectionConfig.java`（不可变字段 `java.io.File inputApk`、`java.io.File outputApk`、`boolean autoSign`、`java.io.File keystore`、`String keyAlias`、`String keyPassword`、`String storePassword`、`boolean injectGuard`，含 Builder 内部类与 getter）；`ProtectResult.java`（不可变字段 `java.io.File outputApk`、`String packageName`、`boolean signed`，含 getter）；`KeystoreSpec.java`（不可变字段 `java.io.File keystore`、`String keyAlias`、`String keyPassword`、`String storePassword`，含全参构造器与 getter）。包名为 `com.tianmen.ngz.engine.pipeline`，不依赖 Android framework，不加注释。
loop: false
verify:
  type: artifact
  path: engine/src/main/java/com/tianmen/ngz/engine/pipeline/ProtectionConfig.java
  assert:
    kind: exists

- [ ] **Step 2: 编写 KeystoreSigner 单测（红）**
action: 在 `engine/src/test/java/com/tianmen/ngz/engine/pipeline/KeystoreSignerTest.java` 创建 JUnit4 测试类 `KeystoreSignerTest`：测试方法 `signRejectsMissingInput`——`new KeystoreSigner().sign(new File(tempDir,"missing.apk"), new KeystoreSpec(new File(tempDir,"k.jks"),"alias","kp","sp"), new File(tempDir,"out.apk"))` 期望抛 `com.tianmen.ngz.engine.EngineException`；`signRejectsMissingKeystore`——输入文件用 `java.util.zip.ZipOutputStream` 生成一个含单条目 `a.txt` 的 zip 文件，keystore 指向不存在的文件，期望抛 `EngineException`。临时目录用 JUnit4 `@Rule org.junit.rules.TemporaryFolder`。先写好且预期失败（KeystoreSigner 类不存在导致编译失败）。
loop: false
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.pipeline.KeystoreSignerTest"

- [ ] **Step 3: 实现 KeystoreSigner（绿）**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/pipeline/KeystoreSigner.java` 实现公开方法 `public void sign(java.io.File unsignedApk, KeystoreSpec spec, java.io.File outputApk)`：unsignedApk 不存在抛 `EngineException`；spec.getKeystore() 不存在抛 `EngineException`；用 `java.security.KeyStore.getInstance("JKS")` 以 `spec.getStorePassword()` 载入 keystore，取 `spec.getKeyAlias()` 对应私钥与 `getCertificateChain`，构造 `com.android.apksig.ApkSigner.SignerConfig.Builder(alias, privateKey, List<X509Certificate>).build()`，再用 `new ApkSigner.Builder(List<SignerConfig>)` 设 `setInputApk(unsignedApk).setOutputApk(outputApk).setV1SigningEnabled(true).setV2SigningEnabled(true).setV3SigningEnabled(true).build().sign()`；IOException/GeneralSecurityException 包装为 `EngineException`（复用 `com.tianmen.ngz.engine.EngineException`），异常消息不得包含密码明文。
loop: until Step 2 测试通过
max_iterations: 3
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.pipeline.KeystoreSignerTest"

- [ ] **Step 4: 编写流水线端到端单测（红）**
action: 在 `engine/src/test/java/com/tianmen/ngz/engine/pipeline/ProtectionPipelineTest.java` 创建 JUnit4 测试类 `ProtectionPipelineTest`，含三个测试方法。`pipelineRejectsMissingInput`：`new ProtectionPipeline().run(new ProtectionConfig.Builder().inputApk(new File(tempDir,"missing.apk")).outputApk(new File(tempDir,"out.apk")).autoSign(false).injectGuard(false).build())` 期望抛 `EngineException`。`pipelineProducesUnsignedOutput`：输入为 `ZipOutputStream` 生成的含 `AndroidManifest.xml` 条目的最小 APK，`autoSign=false`，断言 `run` 返回的 `ProtectResult` 的 `getOutputApk()` 文件存在且 `getSigned()` 为 false。`pipelineSignsWithGeneratedKeystore`：测试内先用 `java.security.KeyPairGenerator.getInstance("RSA")`（2048）生成密钥对，再用 JDK 自带 `keytool`（`java.home` 下 `bin/keytool`，Windows 兼容分支为 `keytool.exe`）执行 `-genkeypair -alias testkey -keyalg RSA -keysize 2048 -validity 3650 -keystore <temp>/test.jks -storepass changeit -keypass changeit -dname "CN=Test"`，然后 `autoSign=true` 且 keystore 指向 test.jks、alias/keyPassword/storePassword 为 testkey/changeit/changeit，断言返回 `getSigned()` 为 true、输出文件存在，且用 `com.android.apksig.ApkVerifier.Builder(outputApk).build().verify()` 的 `isVerified()` 为 true。先写好且预期失败（ProtectionPipeline 类不存在导致编译失败）。
loop: false
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.pipeline.ProtectionPipelineTest"

- [ ] **Step 5: 实现 ProtectionPipeline（绿）**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/pipeline/ProtectionPipeline.java` 实现公开方法 `public ProtectResult run(ProtectionConfig config)`：config 或 config.getInputApk() 为空、inputApk 不存在均抛 `EngineException`；调用 `com.tianmen.ngz.engine.ApkInspector.inspect(inputApk)` 取 `getPackageName()`（inspect 抛出的 EngineException 向上透传）；用 `java.util.zip.ZipFile` 逐条目读取输入，用 `java.util.zip.ZipOutputStream` 写出到 `config.getOutputApk()`（条目统一 DEFLATED，条目名与字节内容原样复制；输出父目录不存在则 `mkdirs()`）；`config.isInjectGuard()` 为 true 时在复制完成后调用私有方法 `appendGuardMarker()`——向输出 zip 写入条目 `assets/ngz_guard.marker` 内容为 UTF-8 字节 `ngzz-guard`（本 Phase 的注入占位，Phase 3 替换为真实 smali 注入）；`config.isAutoSign()` 为 true 时要求 `config.getKeystore()` 非空（否则抛 `EngineException`），随后调用 `new KeystoreSigner().sign(outputApk, new KeystoreSpec(config.getKeystore(), config.getKeyAlias(), config.getKeyPassword(), config.getStorePassword()), outputApk)`；返回 `new ProtectResult(config.getOutputApk(), packageName, config.isAutoSign())`。IOException 包装 `EngineException`，消息不含密码。
loop: until Step 4 测试通过
max_iterations: 3
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.pipeline.ProtectionPipelineTest"

- [ ] **Step 6: 编写 OutputManager 单测（红）**
action: 在 `engine/src/test/java/com/tianmen/ngz/engine/pipeline/OutputManagerTest.java` 创建 JUnit4 测试类 `OutputManagerTest`（使用 `@Rule TemporaryFolder`）：`resolveOutputUnderBaseDir`——`new OutputManager(baseDir)`，断言 `manager.resolveOutput("sample.apk")` 与 `new File(baseDir,"sample.apk")` 相等（`assertEquals` canonical path）；`createsBaseDirIfMissing`——传入 `new File(tempDir,"notyet")` 作为 baseDir，调用 `resolveOutput("a.apk")` 后断言 baseDir `exists()` 为 true。先写好且预期失败（OutputManager 类不存在导致编译失败）。
loop: false
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.pipeline.OutputManagerTest"

- [ ] **Step 7: 实现 OutputManager（绿）**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/pipeline/OutputManager.java` 实现：构造器 `public OutputManager(java.io.File baseDir)` 保存字段；公开方法 `public java.io.File resolveOutput(String fileName)`——若 baseDir 不存在则 `baseDir.mkdirs()`，mkdirs 失败（返回 false 且仍不存在）抛 `EngineException`；返回 `new java.io.File(baseDir, fileName)`。不加注释。
loop: until Step 6 测试通过
max_iterations: 3
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.pipeline.OutputManagerTest"

- [ ] **Step 8: 全量引擎回归与 Android 构建**
action: 运行 `./gradlew :engine:test` 确认 Phase 1 冒烟单测、ApkInspectorTest 与本 Phase 全部新测试全绿；再运行 `./gradlew :guard:assembleDebug :app:assembleDebug` 确认 Android 模块构建不受影响（沙箱已配 ANDROID_SDK=/opt/android-sdk，见 local.properties）。
loop: until 全部测试通过且 assembleDebug 成功
max_iterations: 3
verify: ./gradlew :engine:test

- [ ] **Step 9: 人工复核签名与流水线安全逻辑**
action: 人工复核 `KeystoreSigner.java`（密码不落日志、异常消息不含密码、keystore 仅本地读取）与 `ProtectionPipeline.java`（输出路径不越权、失败时不产出半成品 APK、条目复制不丢失）是否符合安全要求。
gate: human
verify:
  type: human-review
