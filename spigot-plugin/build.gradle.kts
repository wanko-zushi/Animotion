import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar
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
    alias(libs.plugins.shadow)
}

val versionDetails: Closure<VersionDetails> by extra
val details = versionDetails()

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
    name = "Animotion"
    main = "dev.s7a.animotion.spigot.AnimotionPlugin"
    version = "${project.version} (${details.gitHash})"
    apiVersion = "1.19"
    author = "sya_ri"
}

tasks.jar {
    enabled = false
}

tasks.build {
    dependsOn("shadowJar")
}

tasks.withType<ShadowJar> {
    archiveClassifier.set("")
    archiveBaseName.set("Animotion")
}

listOf(
    "19" to "1.19.4",
    "20" to "1.20.4",
    "21" to "1.21.4",
).forEach { (name, version) ->
    task<LaunchMinecraftServerTask>("testPlugin$name") {
        dependsOn("build")

        doFirst {
            copy {
                from(
                    layout.buildDirectory.asFile
                        .get()
                        .resolve("libs/Animotion-${project.version}.jar"),
                )
                into(
                    layout.buildDirectory.asFile
                        .get()
                        .resolve("MinecraftServer$name/plugins"),
                )
            }
        }

        serverDirectory.set(
            layout.buildDirectory.asFile
                .get()
                .resolve("MinecraftServer$name")
                .absolutePath,
        )
        jarUrl.set(JarUrl.Paper(version))
        agreeEula.set(true)
    }
}
