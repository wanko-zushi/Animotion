import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import org.gradle.configurationcache.extensions.capitalized
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.kotlin.plugin.serialization)
    alias(libs.plugins.git.version)
    alias(libs.plugins.kotlinx.kover)
    alias(libs.plugins.shadow)
}

val versionDetails: Closure<VersionDetails> by extra
val details = versionDetails()

kotlin {
    jvm {
        @Suppress("OPT_IN_USAGE")
        mainRun {
            mainClass.set("dev.s7a.animotion.converter.MainKt")
        }

        compilations.all {
            kotlinOptions.jvmTarget = "1.8"
        }

        registerShadowJar("animotion-converter", "dev.s7a.animotion.converter.MainKt")
    }

    listOf(
        linuxX64(),
        mingwX64(),
        macosX64(),
        macosArm64(),
    ).forEach {
        it.binaries {
            executable {
                baseName = "animotion-converter"
                entryPoint("dev.s7a.animotion.converter.main")
            }
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                api(project(":api"))
                implementation(libs.kotlinx.serialization.json)
                implementation(libs.clikt)
                implementation(libs.okio)
                implementation(libs.kotlinx.uuid.core)
            }
        }

        commonTest {
            dependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

kotlin.targets.forEach {
    tasks.named("compileKotlin" + it.targetName.capitalized()) {
        doFirst {
            replaceVersion(project.version.toString(), details.gitHash)
        }
        doLast {
            replaceVersion("dev", "")
        }
    }
}

fun replaceVersion(version: String, commit: String) {
    val templateFile = projectDir.resolve("templates/Main.kt")
    val destinationFile = projectDir.resolve("src/commonMain/kotlin/dev/s7a/animotion/converter/Main.kt")

    templateFile.inputStream().use { inputStream ->
        destinationFile.printWriter().use { writer ->
            inputStream.bufferedReader().forEachLine { line ->
                writer.println(line.replace("{VERSION}", version).replace("{COMMIT}", commit))
            }
        }
    }
}

/**
 * https://stackoverflow.com/questions/63426211/kotlin-multiplatform-shadowjar-gradle-plugin-creates-empty-jar
 */
fun KotlinJvmTarget.registerShadowJar(baseName: String, mainClassName: String) {
    val targetName = name
    compilations.named("main") {
        tasks {
            val shadowJar = register<ShadowJar>("${targetName}ShadowJar") {
                group = "build"
                from(output)
                configurations = listOf(runtimeDependencyFiles)
                archiveFileName.set("$baseName.jar")
                manifest {
                    attributes("Main-Class" to mainClassName)
                }
                mergeServiceFiles()
            }

            getByName("${targetName}Jar") {
                finalizedBy(shadowJar)
            }
        }
    }
}
