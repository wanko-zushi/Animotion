package dev.s7a.animotion.converter.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.parameters.options.option
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import okio.Path.Companion.toPath

class AnimotionConverterCommand : CliktCommand(name = "animotion-converter") {
    private val directory: String? by option("--directory", "-d", help = "Resource pack path")

    override fun run() {
        val directory = this.directory
        if (directory != null) {
            val json = Json {
                ignoreUnknownKeys = true
            }
            println(ResourcePack.load(directory.toPath(), json))
        }
        throw PrintHelpMessage(this)
    }
}
