plugins {
    kotlin("plugin.serialization") version "1.8.21"
    id("org.jmailen.kotlinter") version "3.15.0"
    id("org.jetbrains.kotlinx.kover") version "0.7.1"
}

kotlin {
    jvm()
    linuxX64()
    mingwX64()
    macosX64()
    macosArm64()

    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
                implementation("com.github.ajalt.clikt:clikt:3.5.2")
                implementation("com.squareup.okio:okio:3.3.0")
                implementation("app.softwork:kotlinx-uuid-core:0.0.20")
            }
        }

        val commonTest by getting {
            dependencies {
                implementation(kotlin("test"))
            }
        }
    }
}
