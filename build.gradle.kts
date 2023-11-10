import groovy.lang.Closure

plugins {
    kotlin("multiplatform") version "1.9.20" apply false
    kotlin("jvm") version "1.9.20" apply false
    id("org.jmailen.kotlinter") version "4.0.0" apply false
    id("com.palantir.git-version") version "3.0.0" apply false
}

val gitVersion: Closure<String> by extra

allprojects {
    apply(plugin = "org.jmailen.kotlinter")
    apply(plugin = "com.palantir.git-version")

    group = "dev.s7a"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()
    }
}

project(":api") {
    apply(plugin = "kotlin-multiplatform")
}

project(":converter") {
    apply(plugin = "kotlin-multiplatform")
}

project(":spigot-api") {
    apply(plugin = "kotlin")
}

project(":spigot-plugin") {
    apply(plugin = "kotlin")
}
