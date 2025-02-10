import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
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
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.kotlinpoet)
    implementation(libs.clikt)
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

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.withType<Jar>().configureEach {
    manifest {
        attributes(
            "Main-Class" to "dev.s7a.animotion.converter.Main",
            "Implementation-Version" to "${project.version} (${details.version})",
        )
    }
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveBaseName.set("animotion-converter")
}
