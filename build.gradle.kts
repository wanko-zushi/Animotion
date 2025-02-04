
plugins {
    alias(libs.plugins.kotlin.jvm) apply false
    alias(libs.plugins.kotlinter) apply false
    alias(libs.plugins.git.version) apply false
}

allprojects {
    afterEvaluate {
        apply(
            plugin =
                libs.plugins.kotlinter
                    .get()
                    .pluginId,
        )
    }

    group = "dev.s7a"
    version = "1.0.0-SNAPSHOT"

    repositories {
        mavenCentral()

        // spigot-api
        maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")

        // PacketEvents
        maven(url = "https://repo.codemc.io/repository/maven-releases/")
    }
}
