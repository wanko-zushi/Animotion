import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

plugins {
    kotlin("plugin.serialization") version "1.9.20"
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

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

    linuxX64 {
        binaries {
            executable {
                baseName = "animotion-converter"
                entryPoint("dev.s7a.animotion.converter.main")
            }
        }
    }

    mingwX64 {
        binaries {
            executable {
                baseName = "animotion-converter"
                entryPoint("dev.s7a.animotion.converter.main")
            }
        }
    }

    macosX64 {
        binaries {
            executable {
                baseName = "animotion-converter"
                entryPoint("dev.s7a.animotion.converter.main")
            }
        }
    }

    macosArm64 {
        binaries {
            executable {
                baseName = "animotion-converter"
                entryPoint("dev.s7a.animotion.converter.main")
            }
        }
    }

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
                implementation("com.github.ajalt.clikt:clikt:4.2.1")
                implementation("com.squareup.okio:okio:3.6.0")
                implementation("app.softwork:kotlinx-uuid-core:0.0.22")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
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
