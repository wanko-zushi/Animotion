import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

dependencies {
    api(project(":spigot:library"))
}

configure<BukkitPluginDescription> {
    main = "dev.s7a.animotion.spigot.AnimotionPlugin"
    version = rootProject.version.toString()
    apiVersion = "1.19"
    author = "sya_ri"
}
