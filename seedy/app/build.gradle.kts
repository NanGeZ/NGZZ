import java.util.zip.ZipFile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.seedy.tool"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.seedy.tool"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
}

// ---------------------------------------------------------------------------
// hook.dex 载荷构建：src/hook/java -> javac(android.jar) -> d8 -> assets/hook.dex
// 载荷随过签处理注入目标 APK，免 root，由目标进程内入口加载。
// ---------------------------------------------------------------------------
val sdkDir: String = (project.findProperty("sdk.dir") as String?)
    ?: System.getenv("ANDROID_HOME")
    ?: "/opt/android-sdk"
val buildTools = file("$sdkDir/build-tools/34.0.0")
val androidJar = file("$sdkDir/platforms/android-34/android.jar")

val hookClassesDir = layout.buildDirectory.dir("hook/classes").get().asFile
val hookDexDir = layout.buildDirectory.dir("hook/dex").get().asFile
val hookAssetsDir = layout.buildDirectory.dir("generated/hookAssets").get().asFile

val compileHook by tasks.registering(Exec::class) {
    description = "编译过签载荷 Java 源码"
    inputs.dir("src/hook/java")
    outputs.dir(hookClassesDir)
    doFirst {
        hookClassesDir.deleteRecursively()
        hookClassesDir.mkdirs()
    }
    executable = "${System.getProperty("java.home")}/bin/javac"
    args(
        "-classpath", androidJar.absolutePath,
        "-d", hookClassesDir.absolutePath,
        "-encoding", "UTF-8",
        "-nowarn"
    )
    // 收集所有 .java
    doFirst {
        fileTree("src/hook/java") { include("**/*.java") }.files.forEach {
            args(it.absolutePath)
        }
    }
}

val dexHook by tasks.registering(Exec::class) {
    dependsOn(compileHook)
    description = "d8 打包载荷为 dex"
    inputs.dir(hookClassesDir)
    outputs.dir(hookDexDir)
    doFirst {
        hookDexDir.deleteRecursively()
        hookDexDir.mkdirs()
    }
    executable = "$buildTools/d8"
    args(
        "--min-api", "24",
        "--output", hookDexDir.absolutePath
    )
    doFirst {
        fileTree(hookClassesDir) { include("**/*.class") }.files.forEach {
            args(it.absolutePath)
        }
    }
}

val prepareHookAsset by tasks.registering(Copy::class) {
    dependsOn(dexHook)
    description = "将 classes.dex 作为 assets/hook.dex 输出"
    from(hookDexDir) { include("classes.dex"); rename { "hook.dex" } }
    into(hookAssetsDir)
}

android.sourceSets.getByName("main").assets.srcDir(hookAssetsDir)
tasks.named("preBuild").configure { dependsOn(prepareHookAsset) }

dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.09.03")
    implementation(composeBom)
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.activity:activity-compose:1.9.2")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.6")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material:material-icons-extended")
    debugImplementation("androidx.compose.ui:ui-tooling")
}
