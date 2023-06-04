subprojects {
    apply(plugin = "kotlin")

    repositories {
        maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    }

    dependencies {
        compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
    }
}
