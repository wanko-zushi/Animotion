plugins {
    kotlin("multiplatform") version "1.8.22" apply false
    kotlin("jvm") version "1.9.20" apply false
    id("org.jmailen.kotlinter") version "3.16.0" apply false
}

allprojects {
    apply(plugin = "org.jmailen.kotlinter")

    group = "dev.s7a"
    version = "1.0.0"

    repositories {
        mavenCentral()
    }
}

project(":converter") {
    apply(plugin = "kotlin-multiplatform")
}

project(":spigot") {
    apply(plugin = "kotlin")
}
