import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    kotlin("jvm") version "1.8.21"
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
    id("org.jmailen.kotlinter") version "3.15.0"
}

repositories {
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
}

configure<BukkitPluginDescription> {
    main = "dev.s7a.animotion.spigot.AnimotionPlugin"
    version = rootProject.version.toString()
    apiVersion = "1.19"
    author = "sya_ri"
}
