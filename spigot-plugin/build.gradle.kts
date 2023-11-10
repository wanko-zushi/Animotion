import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    id("net.minecrell.plugin-yml.bukkit") version "0.6.0"
}

val versionDetails: Closure<VersionDetails> by extra
val details = versionDetails()

repositories {
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    api(project(":spigot-api"))
    compileOnly("org.spigotmc:spigot-api:1.19.4-R0.1-SNAPSHOT")
}

configure<BukkitPluginDescription> {
    main = "dev.s7a.animotion.spigot.AnimotionPlugin"
    version = "${project.version} (${details.gitHash})"
    apiVersion = "1.19"
    author = "sya_ri"
}
