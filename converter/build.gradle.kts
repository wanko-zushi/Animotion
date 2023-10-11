plugins {
    kotlin("plugin.serialization") version "1.8.22"
    id("org.jetbrains.kotlinx.kover") version "0.7.4"
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
                implementation("com.github.ajalt.clikt:clikt:4.2.1")
                implementation("com.squareup.okio:okio:3.4.0")
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
