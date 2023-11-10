import com.palantir.gradle.gitversion.VersionDetails
import groovy.lang.Closure
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.git.version)
    alias(libs.plugins.plugin.yml.bukkit)
}

val versionDetails: Closure<VersionDetails> by extra
val details = versionDetails()

repositories {
    maven(url = "https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
}

dependencies {
    api(project(":spigot-api"))
    compileOnly(libs.spigot.api)
}

configure<BukkitPluginDescription> {
    main = "dev.s7a.animotion.spigot.AnimotionPlugin"
    version = "${project.version} (${details.gitHash})"
    apiVersion = "1.19"
    author = "sya_ri"
}
