plugins {
    kotlin("plugin.serialization") version "1.8.21"
    application
}

dependencies {
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.5.1")
    implementation("com.github.ajalt.clikt:clikt:3.5.2")
    testImplementation(kotlin("test"))
}

application {
    mainClass.set("dev.s7a.animotion.converter.Main")
}
