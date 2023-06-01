plugins {
    kotlin("jvm") version "1.8.21"
}

group = "dev.s7a"
version = "1.0.0"

subprojects {
    apply(plugin = "kotlin")

    repositories {
        mavenCentral()
    }

    dependencies {
    }
}
