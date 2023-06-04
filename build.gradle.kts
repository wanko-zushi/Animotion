plugins {
    kotlin("multiplatform") version "1.8.21" apply false
    kotlin("jvm") version "1.8.21" apply false
}

allprojects {
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
