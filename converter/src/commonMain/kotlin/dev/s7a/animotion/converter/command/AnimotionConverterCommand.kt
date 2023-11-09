package dev.s7a.animotion.converter.command

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import dev.s7a.animotion.converter.Converter
import dev.s7a.animotion.converter.loader.ResourcePack
import dev.s7a.animotion.converter.util.clikt.directory
import dev.s7a.animotion.converter.util.clikt.existingDirectory
import dev.s7a.animotion.converter.util.clikt.path
import dev.s7a.animotion.converter.util.path.FileAlreadyExistsException
import dev.s7a.animotion.converter.util.path.NoSuchFileException
import dev.s7a.animotion.converter.util.path.copyRecursively
import dev.s7a.animotion.converter.util.path.deleteRecursively
import dev.s7a.animotion.converter.util.path.exists
import kotlinx.serialization.json.Json
import okio.IOException
import okio.Path

class AnimotionConverterCommand(version: String, commit: String) : CliktCommand(name = "animotion-converter") {
    private val directory: Path? by option("--directory", "-d", completionCandidates = CompletionCandidates.Path, help = "Resource pack path").path(true).existingDirectory()
    private val output: Path? by option("--output", "-o", completionCandidates = CompletionCandidates.Path, help = "Output destination path").path(true).directory()
    private val force: Boolean by option("--force", "-f", help = "Ignore warnings").flag()
    private val convertOnly: Boolean by option("--convert-only", help = "Without a copy of the resource pack").flag()

    init {
        versionOption("$version ($commit)", names = setOf("--version", "-v")) { it }
    }

    override fun run() {
        val directory = this.directory
        if (directory != null) {
            if (directory.exists().not()) {
                existWithErrorMessage("The directory doesn't exists: $directory")
            }

            val json = Json {
                ignoreUnknownKeys = true
            }
            val resourcePack = ResourcePack.load(directory, json)

            if (!force) {
                val output = output
                if (output == null) {
                    if (terminal.prompt("Overwrite the original resource pack?", choices = setOf("yes")) == null) {
                        return
                    }
                    terminal.println()
                } else if (output.exists()) {
                    if (terminal.prompt("Output directory already exists, overwrite it?", choices = setOf("yes")) == null) {
                        return
                    }
                    terminal.println()
                }
            }

            val destination = output ?: directory
            if (directory != destination) {
                if (destination.exists() && !destination.deleteRecursively()) {
                    existWithErrorMessage("Failed to delete output directory.")
                }
                if (convertOnly) {
                    terminal.info("INFO: The resource pack will not be copied because --convertOnly is enabled.")
                    terminal.println()
                } else {
                    try {
                        directory.copyRecursively(destination)
                    } catch (error: NoSuchFileException) {
                        existWithErrorMessage("Copy failed. The source file doesn't exists: ${error.file}")
                    } catch (error: FileAlreadyExistsException) {
                        existWithErrorMessage("Copy failed. The destination file already exists: ${error.file} -> ${error.other}")
                    } catch (error: IOException) {
                        existWithErrorMessage("Copy failed. ${error.message}")
                    }
                }
            } else if (convertOnly) {
                terminal.warning("WARN: --convertOnly is ignored unless --output is specified.")
                terminal.println()
            }

            Converter(resourcePack).save(destination)
            terminal.success("Generate resource pack: $destination")
        } else {
            echoFormattedHelp()
        }
    }

    private fun existWithErrorMessage(message: String): Nothing {
        terminal.danger("ERROR: $message")
        throw CliktError()
    }
}
