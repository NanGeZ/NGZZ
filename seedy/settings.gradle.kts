pluginManagement {
    repositories {
        // 镜像测速结论（2026-11）：阿里云 google 源最快(~5.5MB/s)，
        // 阿里云 public 可代理 Maven Central(直连被限流)，腾讯 maven-public 备选聚合 google 构件。
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        google()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://mirrors.cloud.tencent.com/nexus/repository/maven-public")
        google()
    }
}

rootProject.name = "Seedy"
include(":app")
