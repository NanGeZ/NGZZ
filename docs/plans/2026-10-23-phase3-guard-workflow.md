---
intent: 在 :guard 保护壳模块实现全部检测能力（签名校验、反调试 frida/lsposed/xposed/gg、包名检测、抓包检测 VPN/WiFi 代理、Root 检测、网络检测、到期退出）与命中退出策略（退出/日志），并在 :engine 实现 StringFog 式字符串加密（XOR+Base64，可逆）
success_criteria: ./gradlew :guard:test :engine:test 全绿；StringFogTest 证明 encrypt/decrypt 可逆且密文不含原文；GuardPolicyTest 证明各检测项独立开关与命中策略（退出/日志）逻辑；到期退出支持自定义到期时间
risk_level: high
auto_approve: true
---

## Steps

- [ ] **Step 1: 定义保护配置与命中策略数据契约**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/guard/` 创建两个纯 Java 类：`GuardConfig.java`（不可变字段 `boolean checkSignature`、`boolean antiDebug`、`boolean checkPackageName`、`boolean checkCapture`、`boolean checkRoot`、`boolean checkNetwork`、`boolean checkExpiry`、`long expiryEpochMillis`、`boolean exitOnHit`，含 Builder 内部类与 getter，`expiryEpochMillis` 默认 0 表示永不过期）；`GuardHit.java`（枚举 `SIGNATURE`、`DEBUG`、`PACKAGE_NAME`、`CAPTURE`、`ROOT`、`NETWORK`、`EXPIRY`）。包名 `com.tianmen.ngz.engine.guard`，不依赖 Android framework，不加注释。
loop: false
verify:
  type: artifact
  path: engine/src/main/java/com/tianmen/ngz/engine/guard/GuardConfig.java
  assert:
    kind: exists

- [ ] **Step 2: 编写 GuardPolicy 单测（红）**
action: 在 `engine/src/test/java/com/tianmen/ngz/engine/guard/GuardPolicyTest.java` 创建 JUnit4 测试类 `GuardPolicyTest`，使用 `@Rule TemporaryFolder`。`decidesExitWhenExitOnHit`——`new GuardPolicy(new GuardConfig.Builder().checkRoot(true).exitOnHit(true).build())`，`policy.decide(GuardHit.ROOT)` 返回 `GuardPolicy.Decision.EXIT`；`decidesLogWhenExitOnHitFalse`——`exitOnHit(false)` 时 `decide(GuardHit.ROOT)` 返回 `GuardPolicy.Decision.LOG`；`ignoresDisabledCheck`——`checkRoot(false)` 时 `decide(GuardHit.ROOT)` 返回 `GuardPolicy.Decision.IGNORE`；`expiryRespectsCustomTime`——`checkExpiry(true).expiryEpochMillis(<过去时间戳>)` 时 `decide(GuardHit.EXPIRY)` 返回 `EXIT`（exitOnHit true），`expiryEpochMillis(<未来时间戳>)` 时返回 `IGNORE`。`GuardPolicy.Decision` 为 `GuardPolicy` 的公开枚举（EXIT、LOG、IGNORE）。先写好且预期失败（类不存在导致编译失败）。
loop: false
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.guard.GuardPolicyTest"

- [ ] **Step 3: 实现 GuardPolicy（绿）**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/guard/GuardPolicy.java` 实现：公开枚举 `Decision { EXIT, LOG, IGNORE }`；构造器 `public GuardPolicy(GuardConfig config)`；公开方法 `public Decision decide(GuardHit hit)`——先按 hit 对应开关判断是否启用（SIGNATURE→checkSignature、DEBUG→antiDebug、PACKAGE_NAME→checkPackageName、CAPTURE→checkCapture、ROOT→checkRoot、NETWORK→checkNetwork、EXPIRY→checkExpiry），未启用返回 `IGNORE`；EXPIRY 启用且 `expiryEpochMillis > 0` 且 `System.currentTimeMillis() > expiryEpochMillis` 视为命中，`expiryEpochMillis > 0` 且未到期返回 `IGNORE`，`expiryEpochMillis == 0` 视为未命中返回 `IGNORE`；其余 hit 直接视为命中；命中时返回 `config.isExitOnHit() ? Decision.EXIT : Decision.LOG`。不加注释。
loop: until Step 2 测试通过
max_iterations: 3
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.guard.GuardPolicyTest"

- [ ] **Step 4: 编写 StringFog 单测（红）**
action: 在 `engine/src/test/java/com/tianmen/ngz/engine/guard/StringFogTest.java` 创建 JUnit4 测试类 `StringFogTest`。`encryptDecryptRoundTrip`——对明文 `"hello 南天门 secret"` 与密钥 `"ngzz-key"`，`StringFog.encrypt(plain, key)` 返回密文字符串，`StringFog.decrypt(cipher, key)` 还原为原文（assertEquals）；`cipherHidesPlain`——密文不包含原文子串 `"hello"`（assertFalse）；`differentKeysProduceDifferentCipher`——同明文不同密钥密文不等（assertNotEquals）。`StringFog` 为纯静态工具类（`public static String encrypt(String plain, String key)`、`public static String decrypt(String cipher, String key)`），算法：UTF-8 字节与密钥字节循环异或后 Base64 编码/解码。先写好且预期失败（类不存在导致编译失败）。
loop: false
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.guard.StringFogTest"

- [ ] **Step 5: 实现 StringFog（绿）**
action: 在 `engine/src/main/java/com/tianmen/ngz/engine/guard/StringFog.java` 实现纯静态工具类：`public static String encrypt(String plain, String key)`——取 UTF-8 字节，与 key 的 UTF-8 字节循环异或，`java.util.Base64.getEncoder().encodeToString` 返回；`public static String decrypt(String cipher, String key)`——Base64 解码后同样循环异或还原，返回 UTF-8 字符串。key 为空抛 `IllegalArgumentException`。不加注释。
loop: until Step 4 测试通过
max_iterations: 3
verify: ./gradlew :engine:test --tests "com.tianmen.ngz.engine.guard.StringFogTest"

- [ ] **Step 6: 在 :guard 实现 GuardRuntime 骨架（Android 依赖）**
action: 在 `guard/src/main/java/com/tianmen/ngz/guard/GuardRuntime.java` 创建 Android 依赖类：构造器 `public GuardRuntime(android.content.Context context, com.tianmen.ngz.engine.guard.GuardConfig config)` 保存字段并创建 `com.tianmen.ngz.engine.guard.GuardPolicy`；公开方法 `public void runChecks()`——依次调用私有方法 `checkSignature()`、`checkAntiDebug()`、`checkPackageName()`、`checkCapture()`、`checkRoot()`、`checkNetwork()`、`checkExpiry()`，各方法返回 `GuardPolicy.Decision`，若为 `EXIT` 则调用 `android.os.Process.killProcess(android.os.Process.myPid())`；`LOG` 调用 `android.util.Log.w("NgzGuard", "hit: " + hitName)`；各 check 私有方法本 Phase 仅返回 `GuardPolicy.Decision.IGNORE`（Phase 4 联调时补真实检测实现）。`guard/build.gradle.kts` 需添加 `implementation(project(":engine"))` 依赖。
loop: false
verify:
  type: artifact
  path: guard/src/main/java/com/tianmen/ngz/guard/GuardRuntime.java
  assert:
    kind: exists

- [ ] **Step 7: 全量回归与 Android 构建**
action: 运行 `./gradlew :engine:test :guard:assembleDebug :app:assembleDebug`，确认 :engine 全部测试（Phase 1/2/3）全绿，且 :guard/:app 构建成功（:guard:test 在纯 JVM 层无测试可跳过，Android 仪器测试留待真机）。
loop: until 全部通过
max_iterations: 3
verify: ./gradlew :engine:test

- [ ] **Step 8: 人工复核保护逻辑与加密实现**
action: 人工复核 `GuardPolicy.java`（命中判定逻辑、到期时间校验）与 `StringFog.java`（加密可逆性、密钥处理）是否符合安全要求，确认未引入密钥硬编码泄漏或可被轻易绕过的逻辑。
gate: human
verify:
  type: human-review
