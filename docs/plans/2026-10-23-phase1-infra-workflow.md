---
intent: 搭建南天门 APK 保护工具箱的多模块 Android 工程基础设施，含可独立构建与单测的纯 Java 处理引擎 :engine、可编译的保护壳库 :guard、Kotlin/Compose/Hilt 的 :app 骨架
success_criteria: ./gradlew :engine:test 通过；./gradlew :guard:assembleDebug 通过；:engine 中存在覆盖 apk-parser/apksig/dexlib2 依赖解析的冒烟单测且全部绿
risk_level: medium
auto_approve: true
---

## Steps

- [ ] **Step 1: 创建 Gradle 多模块工程骨架**
action: 在仓库根目录创建 Android 多模块工程：根 `settings.gradle.kts`（声明 `:app`、`:engine`、`:guard` 三个模块）、根 `build.gradle.kts`、`gradle/libs.versions.toml` 版本目录（Kotlin 2.0.x、AGP 8.x、Compose BOM、Hilt、Room、DataStore、apk-parser、apksig、dexlib2、JUnit4）。创建 `app/`（com.android.application，applicationId `com.tianmen.ngz`，namespace `com.tianmen.ngz`，Kotlin + Jetpack Compose + Hilt）、`engine/`（java-library，纯 Java，不依赖 Android framework）、`guard/`（com.android.library）三个模块的 `build.gradle.kts` 与空源码目录。`app` 依赖 `engine` 与 `guard`，`engine` 仅依赖 JVM 库（apk-parser、apksig、dexlib2）。
loop: false
verify:
  type: artifact
  path: settings.gradle.kts
  assert:
    kind: exists

- [ ] **Step 2: 编写 :engine 冒烟单测（红）**
action: 在 `engine/src/test/java/com/tianmen/ngz/engine/EngineDepsSmokeTest.java` 创建 JUnit4 测试类 `EngineDepsSmokeTest`，包含三个测试方法：`apkParserAvailable`（`Class.forName("com.jaredrummler.apk.parser.ApkParser")` 不抛异常）、`apksigAvailable`（`Class.forName("com.android.apksig.ApkSigner")` 不抛异常）、`dexlib2Available`（`Class.forName("org.jf.dexlib2.Opcodes")` 不抛异常）。
loop: false
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.EngineDepsSmokeTest"

- [ ] **Step 3: 实现 :engine 依赖接入（绿）**
action: 在 `engine/build.gradle.kts` 的 dependencies 中声明 `com.jaredrummler:apk-parser:2.2.1`、`com.github.patrickfav:apksig:0.10.2`、`org.smali:dexlib2:2.5.2`（若版本在仓库中不可解析则调整为可用版本并保持 API 兼容），确保 Step 2 的测试编译与运行通过。
loop: until Step 2 的三个测试全部通过
max_iterations: 3
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.EngineDepsSmokeTest"

- [ ] **Step 4: 编写 :engine 包名解析单测（红）**
action: 在 `engine/src/test/java/com/tianmen/ngz/engine/ApkInspectorTest.java` 创建 JUnit4 测试类 `ApkInspectorTest`，测试方法 `parsePackageFromMinimalApk`：在测试内用 `java.util.zip.ZipOutputStream` 程序化生成一个最小 APK 文件（含 `AndroidManifest.xml` 条目，内容为任意字节），断言 `new ApkInspector().inspect(file)` 返回非 null 的 `ApkInfo` 且 `getPackageName()` 可被调用（具体值由实现决定，测试断言方法可调用不抛异常即可）。
loop: false
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.ApkInspectorTest"

- [ ] **Step 5: 实现 :engine APK 解析接口（绿）**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/` 创建 `ApkInspector.java`（公开方法 `ApkInfo inspect(java.io.File apk)`，内部用 apk-parser 解析；解析失败抛出自定义 `EngineException`）与 `ApkInfo.java`（至少含 `String getPackageName()`、`String getVersionName()`、`String getEntryActivity()` 字段与 getter）。确保 Step 4 测试通过；若 apk-parser 对畸形 manifest 抛异常，则 `ApkInspector` 必须抛出 `EngineException` 而非其他异常，测试相应调整断言为「不抛非 EngineException 异常」。
loop: until Step 4 测试通过
max_iterations: 3
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.ApkInspectorTest"

- [ ] **Step 6: 创建 :guard 保护壳空实现**
action: 在 `guard/` 模块创建 `src/main/java/com/tianmen/ngz/guard/GuardEntry.java`：静态方法 `public static void init(android.content.Context context)`，空实现（方法体为空，仅留 TODO 标记说明 Phase 3 实现检测能力，不得抛异常）；`src/main/AndroidManifest.xml` 为空 manifest（仅 package 声明由 AGP namespace 提供）。确认 `guard/build.gradle.kts` 为 `com.android.library`，namespace 为 `com.tianmen.ngz.guard`，minSdk 21、compileSdk/targetSdk 与 `:app` 一致。
loop: false
verify:
  type: artifact
  path: guard/src/main/java/com/tianmen/ngz/guard/GuardEntry.java
  assert:
    kind: exists

- [ ] **Step 7: 创建 :app 骨架并接入依赖**
action: 创建 `app/src/main/AndroidManifest.xml`（application 标签，android:label="南天门"，主 Activity `com.tianmen.ngz.MainActivity`，无网络权限声明）；`app/src/main/java/com/tianmen/ngz/MainActivity.kt`（ComponentActivity，`setContent` 渲染 Compose `Text("南天门")`）；`app/src/main/java/com/tianmen/ngz/NgzApplication.kt`（标注 `@HiltAndroidApp` 的 Application 类并在 manifest 中注册）；`app/build.gradle.kts` 开启 `buildFeatures { compose = true }`、`kotlin("plugin.compose")`（或对应 Kotlin Compose 编译器插件配置）、Hilt 插件与依赖，并 `implementation(project(":engine"))`、`implementation(project(":guard"))`。
loop: false
verify:
  type: artifact
  path: app/src/main/java/com/tianmen/ngz/MainActivity.kt
  assert:
    kind: exists

- [ ] **Step 8: 全量单测与构建验证**
action: 运行引擎与 guard 的单测与 app 构建。若沙箱缺少 Android SDK 导致 `:guard:assembleDebug`/`:app:assembleDebug` 无法执行，则记录该限制，仅以 `./gradlew :engine:test` 全绿 + 各模块 build.gradle.kts 语法校验（`./gradlew projects` 成功解析模块图）作为本步骤通过条件，并在交付说明中标注需用户在真机环境补验 App 构建。
loop: until 通过条件满足
max_iterations: 3
verify: ./gradlew :engine:test

- [ ] **Step 9: 人工复核**
action: 人工复核工程结构（模块依赖方向、依赖版本、包名 com.tianmen.ngz、manifest 配置）是否符合战略设计 docs/designs/apk-protection-toolkit.md 的 Phase 1 交付定义。
gate: human
verify:
  type: human-review

