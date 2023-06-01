import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.5.3"
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
