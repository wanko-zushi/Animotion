import com.vanniktech.maven.publish.JavadocJar
import com.vanniktech.maven.publish.KotlinJvm
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.dokka)
    alias(libs.plugins.dokka.javadoc)
    alias(libs.plugins.mavenPublish)
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

mavenPublishing {
    publishToMavenCentral()
    signAllPublications()
    coordinates("dev.s7a", "animotion-common", version.toString())
    configure(
        KotlinJvm(
            javadocJar = JavadocJar.Dokka("dokkaGeneratePublicationJavadoc"),
            sourcesJar = true,
        ),
    )
    pom {
        name.set("animotion-common")
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
