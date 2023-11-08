package dev.s7a.animotion.converter.command

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import dev.s7a.animotion.converter.Converter
import dev.s7a.animotion.converter.loader.ResourcePack
import dev.s7a.animotion.converter.util.clikt.path
import kotlinx.serialization.json.Json
import okio.Path

class AnimotionConverterCommand(version: String, commit: String) : CliktCommand(name = "animotion-converter") {
    private val directory: Path? by option("--directory", "-d", completionCandidates = CompletionCandidates.Path, help = "Resource pack path").path(true)
    private val output: Path? by option("--output", "-o", completionCandidates = CompletionCandidates.Path, help = "Output destination path").path(true)

    init {
        versionOption("$version ($commit)", names = setOf("--version", "-v")) { it }
    }

    override fun run() {
        val directory = this.directory
        if (directory != null) {
            val json = Json {
                ignoreUnknownKeys = true
            }
            val resourcePack = ResourcePack.load(directory, json)
            if (output == null) {
                terminal.prompt("Warning: Overwrite the original resource pack?", choices = setOf("yes"))
            }
            val destination = output ?: directory
            terminal.success("Generate resource pack: $destination")
            Converter(resourcePack).save(destination)
        } else {
            echoFormattedHelp()
        }
    }
}
