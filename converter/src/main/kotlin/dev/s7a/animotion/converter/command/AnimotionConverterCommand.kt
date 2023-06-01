package dev.s7a.animotion.converter.command

import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.PrintHelpMessage
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.file
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json

class AnimotionConverterCommand : CliktCommand(name = "animotion-converter") {
    private val directory by option("--directory", "-d", help = "Resource pack path").file(mustExist = true, canBeFile = false)

    override fun run() {
        val directory = this.directory
        if (directory != null) {
            val json = Json {
                ignoreUnknownKeys = true
            }
            println(ResourcePack.load(directory, json))
        }
        throw PrintHelpMessage(this)
    }
}
