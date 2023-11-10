plugins {
    alias(libs.plugins.kotlin.jvm)
}

repositories {
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    api(project(":api"))
    compileOnly(libs.spigot.api)
}
