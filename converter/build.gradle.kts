import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.git.version)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.shadow)
}

val versionDetails: Closure<VersionDetails> by extra
val details = versionDetails()

dependencies {
    api(project(":api"))
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.clikt)
    implementation(libs.kotlinx.uuid.core)
    testImplementation(libs.kotlin.test)
}

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}
