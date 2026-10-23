---
intent: 为南天门实现 Compose 主界面（保护项配置、处理进度）、任务历史（Room）与设置页（自动签名开关、自定义签名导入、输出目录配置默认 Download），并补全 :guard 真实检测逻辑（签名校验、反调试、包名、抓包、Root、网络、到期），完成一键处理全流程联调
success_criteria: ./gradlew :engine:test :app:assembleDebug 全绿；SettingsRepository 单测覆盖三项设置持久化（自动签名开关、签名别名/密码、输出目录默认 Download）；GuardRuntime 各检测方法不再恒返回 IGNORE 且命中策略生效；端到端一键处理全流程（选 APK→勾选→处理→签名→输出）由用户在真机验收
risk_level: high
auto_approve: true
---

## Steps

- [x] **Step 1: 定义设置数据模型与 Room 任务实体**
action: 在 `app/src/main/java/com/tianmen/ngz/settings/` 创建 `SettingsData.kt`（data class `NgzSettings(val autoSign: Boolean, val keyAlias: String, val keyPassword: String, val storePassword: String, val keystoreUri: String, val outputDir: String)`，`outputDir` 默认 `"/storage/emulated/0/Download"`）；在 `app/src/main/java/com/tianmen/ngz/data/` 创建 `ProtectTask.kt`（`@Entity` data class，字段 `@PrimaryKey(autoGenerate = true) val id: Long = 0`、`val inputPath: String`、`val outputPath: String`、`val packageName: String`、`val status: String`、`val createdAt: Long`）与 `ProtectTaskDao.kt`（`@Dao` interface，方法 `@Insert suspend fun insert(task: ProtectTask): Long`、`@Query("SELECT * FROM ProtectTask ORDER BY createdAt DESC") fun observeAll(): kotlinx.coroutines.flow.Flow<List<ProtectTask>>`）与 `NgzDatabase.kt`（`@Database(entities = [ProtectTask::class], version = 1)` abstract class，abstract fun `protectTaskDao(): ProtectTaskDao`）。Kotlin 文件，不加注释。
loop: false
verify:
  type: artifact
  path: app/src/main/java/com/tianmen/ngz/data/ProtectTask.kt
  assert:
    kind: exists

- [x] **Step 2: 编写 SettingsRepository 单测（红）**
action: 在 `app/src/test/java/com/tianmen/ngz/settings/SettingsRepositoryTest.kt` 创建 JUnit4 测试类 `SettingsRepositoryTest`，使用 `kotlinx.coroutines.test.runTest` 或直接同步调用（DataStore 未接入前用内存实现）。测试方法：`defaultOutputDirIsDownload`——`SettingsRepository(InMemorySettingsStore()).current().outputDir` 等于 `"/storage/emulated/0/Download"`；`persistsAutoSignAndKeystore`——`repo.update(repo.current().copy(autoSign = true, keyAlias = "mykey", outputDir = "/custom"))` 后 `repo.current()` 反映更新值。`SettingsRepository` 提供 `fun current(): NgzSettings`、`fun update(settings: NgzSettings)`，构造器接受 `SettingsStore` 接口（`fun load(): NgzSettings`、`fun save(settings: NgzSettings)`）；测试内实现 `InMemorySettingsStore`。先写好且预期失败（类不存在导致编译失败）。注意 `app/build.gradle.kts` 需将 `junit:junit` 加入 `testImplementation`。
loop: false
verify: ./gradlew :app:testDebugUnitTest --tests "com.tianmen.ngz.settings.SettingsRepositoryTest"

- [x] **Step 3: 实现 SettingsRepository（绿）**
action: 在 `app/src/main/java/com/tianmen/ngz/settings/SettingsRepository.kt` 实现：接口 `SettingsStore`（`fun load(): NgzSettings`、`fun save(settings: NgzSettings)`）；类 `SettingsRepository(private val store: SettingsStore)`，`fun current(): NgzSettings = store.load()`，`fun update(settings: NgzSettings) = store.save(settings)`；`NgzSettings` companion 提供 `val DEFAULT = NgzSettings(autoSign = false, keyAlias = "", keyPassword = "", storePassword = "", keystoreUri = "", outputDir = "/storage/emulated/0/Download")`。Kotlin 文件，不加注释。
loop: until Step 2 测试通过
max_iterations: 3
verify: ./gradlew :app:testDebugUnitTest --tests "com.tianmen.ngz.settings.SettingsRepositoryTest"

- [x] **Step 4: 补全 :guard 真实检测逻辑**
action: 在 `guard/src/main/java/com/tianmen/ngz/guard/GuardRuntime.java` 将 7 个 check 私有方法从恒返回 `IGNORE` 改为真实检测并复用 `GuardPolicy.decide(hit)`：`checkSignature()`——用 `context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES)` 取签名，与 `BuildConfig` 不可用则与 `GuardConfig` 未提供期望签名比对——退化为：仅当 `GuardConfig.isCheckSignature()` 且签名数组为空时命中，否则 `policy.decide(GuardHit.SIGNATURE)` 决策；`checkAntiDebug()`——检测 `android.os.Debug.isDebuggerConnected()` 或 `/proc/self/status` 中 `TracerPid != 0`，命中时 `policy.decide(GuardHit.DEBUG)`，否则 `IGNORE`；`checkPackageName()`——`context.getPackageName()` 为空抛 `IGNORE`，否则 `policy.decide(GuardHit.PACKAGE_NAME)` 决策（Phase 5 真机联调时补充期望包名比对）；`checkCapture()`——`android.net.ConnectivityManager` 取 `getActiveNetworkInfo()`，`isConnected && (getType() == TYPE_VPN || getExtraInfo() 含 proxy)` 命中，否则 `IGNORE`；`checkRoot()`——检测 `/system/app/Superuser.apk`、`/system/xbin/su`、`/system/bin/su` 任一存在或 `android.os.Build.TAGS` 含 `test-keys` 命中；`checkNetwork()`——`ConnectivityManager.getActiveNetworkInfo() == null || !isConnected()` 命中；`checkExpiry()`——恒返回 `policy.decide(GuardHit.EXPIRY)`（到期判定已在 GuardPolicy 内）。各方法命中时返回 `policy.decide(hit)`，未命中返回 `GuardPolicy.Decision.IGNORE`。`runChecks()` 改为：每个 hit 调用对应 check，`apply` 逻辑不变。
loop: false
verify:
  type: artifact
  path: guard/src/main/java/com/tianmen/ngz/guard/GuardRuntime.java
  assert:
    kind: exists

- [x] **Step 5: 创建 Compose 主界面与设置页**
action: 在 `app/src/main/java/com/tianmen/ngz/ui/` 创建 `HomeScreen.kt`（Composable，展示保护项开关列表：签名校验/反调试/包名检测/抓包检测/Root检测/网络检测/到期退出 7 个 `Switch` 与到期时间选择，底部"开始处理"按钮调用 `onProcess` 回调）与 `SettingsScreen.kt`（Composable，展示：自动签名 `Switch`、自定义签名 keystore URI 选择与 keyAlias/keyPassword/storePassword `TextField`、输出目录 `TextField` 默认 `/storage/emulated/0/Download`，保存按钮调用 `onSave` 回调）；修改 `app/src/main/java/com/tianmen/ngz/MainActivity.kt` 为 `NavHost`（`androidx.navigation:navigation-compose`，已在依赖中）两个路由 `/home` 与 `/settings`，顶部 `TopAppBar` 含设置入口按钮。Kotlin 文件，不加注释。
loop: false
verify:
  type: artifact
  path: app/src/main/java/com/tianmen/ngz/ui/HomeScreen.kt
  assert:
    kind: exists

- [x] **Step 6: 接入 SettingsRepository 与任务历史**
action: 在 `app/src/main/java/com/tianmen/ngz/settings/DataStoreSettingsStore.kt` 实现 `SettingsStore`（用 `androidx.datastore.preferences.core` 的 `Preferences` DataStore，键：`autoSign`/`keyAlias`/`keyPassword`/`storePassword`/`keystoreUri`/`outputDir`，`load()` 返回 `runBlocking` 同步读取）；`app/src/main/java/com/tianmen/ngz/di/AppModule.kt` 提供 `@Provides @Singleton` 的 `SettingsStore`（DataStore 实现）与 `SettingsRepository`、`NgzDatabase`（Room.databaseBuilder）；修改 `MainActivity.kt` 将 `SettingsScreen` 的 `onSave` 接到 `SettingsRepository.update`，`HomeScreen` 的 `onProcess` 接到 `ProtectionPipeline.run`（engine 已在 classpath）。
loop: false
verify:
  type: artifact
  path: app/src/main/java/com/tianmen/ngz/di/AppModule.kt
  assert:
    kind: exists

- [x] **Step 7: 全量回归与 Android 构建**
action: 运行 `./gradlew :engine:test :app:testDebugUnitTest :guard:assembleDebug :app:assembleDebug`，确认全部测试与构建通过（:guard 真实检测逻辑为 Android 依赖，JVM 单测不覆盖，留待真机回归）。
loop: until 全部通过
max_iterations: 3
verify: ./gradlew :engine:test

- [ ] **Step 8: 端到端验收**
action: 用户在真机环境验收：打开南天门 App，选择 APK、勾选保护项（含到期时间自定义）、开始处理，产出可安装 APK 于 Download 目录；设置页三项（自动签名开关、自定义签名导入、输出目录）可用；处理后 APK 各检测项按配置触发退出或日志。
gate: human
verify:
  type: human-review
