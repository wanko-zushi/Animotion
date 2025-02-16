import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.plugins.signing.SigningExtension

fun Project.applyPublishingConfig(publishName: String) {
    val sourceJar =
        tasks.register("sourceJar", Jar::class.java) {
            archiveClassifier.set("sources")
            from(layout.projectDirectory.dir("src/main/kotlin"))
        }

    extensions.configure(PublishingExtension::class.java) {
        repositories {
            maven {
                url =
                    uri(
                        if (version.toString().endsWith("SNAPSHOT")) {
                            "https://s01.oss.sonatype.org/content/repositories/snapshots/"
                        } else {
                            "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
                        },
                    )
                credentials {
                    username = project.findProperty("sonatypeUsername")?.toString()
                    password = project.findProperty("sonatypePassword")?.toString()
                }
            }
        }
        publications {
            register("maven", MavenPublication::class.java) {
                groupId = "dev.s7a"
                artifactId = publishName
                from(components.findByName("kotlin"))
                artifact(sourceJar.get())
                pom {
                    name.set(publishName)
                    description.set("Spigot library and CLI tool to animate BlockBench models in Minecraft Java Edition")
                    url.set("https://github.com/wanko-zushi/Animotion")
                    licenses {
                        license {
                            name.set("MIT License")
                            url.set("https://github.com/wanko-zushi/Animotion/blob/master/LICENSE")
                        }
                    }
                    developers {
                        developer {
                            id.set("sya-ri")
                            name.set("sya-ri")
                            email.set("contact@s7a.dev")
                        }
                    }
                    scm {
                        url.set("https://github.com/wanko-zushi/Animotion")
                    }
                }
            }
        }
    }
    extensions.configure(SigningExtension::class.java) {
        val key = project.findProperty("signingKey")?.toString()?.replace("\\n", "\n")
        val password = project.findProperty("signingPassword")?.toString()
        useInMemoryPgpKeys(key, password)
        sign(extensions.getByType(PublishingExtension::class.java).publications.getAt("maven"))
    }
}
