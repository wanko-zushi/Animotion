import com.palantir.gradle.gitversion.VersionDetails
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask
import dev.s7a.gradle.minecraft.server.tasks.LaunchMinecraftServerTask.JarUrl
import groovy.lang.Closure
import net.minecrell.pluginyml.bukkit.BukkitPluginDescription
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.git.version)
    alias(libs.plugins.plugin.yml.bukkit)
    alias(libs.plugins.minecraft.server)
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

java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

kotlin {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_1_8)
    }
}

configure<BukkitPluginDescription> {
    main = "dev.s7a.animotion.spigot.AnimotionPlugin"
    version = "${project.version} (${details.gitHash})"
    apiVersion = "1.19"
    author = "sya_ri"
}

listOf(
    "19" to "1.19.4",
    "20" to "1.20.2"
).forEach { (name, version) ->
    task<LaunchMinecraftServerTask>("testPlugin$name") {
        dependsOn("build")

        doFirst {
            copy {
                from(layout.buildDirectory.asFile.get().resolve("libs/${project.name}.jar"))
                into(layout.buildDirectory.asFile.get().resolve("MinecraftServer$name/plugins"))
            }
        }

        serverDirectory.set(layout.buildDirectory.asFile.get().resolve("MinecraftServer$name").absolutePath)
        jarUrl.set(JarUrl.Paper(version))
        agreeEula.set(true)
    }
}
