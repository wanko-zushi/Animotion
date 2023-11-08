package dev.s7a.animotion.converter.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

class AnimotionConverterCommand(version: String, commit: String) : CliktCommand(name = "animotion-converter") {
    private val directory: String? by option("--directory", "-d", help = "Resource pack path")

    init {
        versionOption("$version ($commit)", names = setOf("--version", "-v")) { it }
    }

    override fun run() {
        val directory = this.directory
        if (directory != null) {
            val json = Json {
                ignoreUnknownKeys = true
            }
            println(ResourcePack.load(directory.toPath(), json))
        }
        echoFormattedHelp()
    }
}
