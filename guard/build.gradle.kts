plugins {
    alias(libs.plugins.android.library)
}

android {
    namespace = "com.tianmen.ngz.guard"
    compileSdk = 35

    defaultConfig {
        minSdk = 21
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(project(":engine"))
}
