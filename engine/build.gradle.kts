plugins {
    `java-library`
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
}

dependencies {
    implementation(libs.apk.parser)
    implementation(libs.apksig)
    implementation(libs.dexlib2)

    testImplementation(libs.junit)
}
