package dev.s7a.animotion.converter.command

import com.github.ajalt.clikt.completion.CompletionCandidates
import com.github.ajalt.clikt.core.CliktCommand
import com.github.ajalt.clikt.core.CliktError
import com.github.ajalt.clikt.core.terminal
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.options.versionOption
import com.github.ajalt.clikt.parameters.types.file
import com.github.ajalt.mordant.terminal.danger
import com.github.ajalt.mordant.terminal.info
import com.github.ajalt.mordant.terminal.prompt
import com.github.ajalt.mordant.terminal.success
import com.github.ajalt.mordant.terminal.warning
import dev.s7a.animotion.converter.Converter
import dev.s7a.animotion.converter.exception.UnsupportedPackFormatException
import dev.s7a.animotion.converter.loader.ResourcePack
import kotlinx.serialization.json.Json
import java.io.IOException

class AnimotionConverterCommand(
    version: String,
) : CliktCommand(name = "animotion-converter") {
    private val directory by option(
        "--directory",
        "-d",
        completionCandidates = CompletionCandidates.Path,
        help = "Resource pack path",
    ).file(true, canBeFile = false, canBeDir = true)
    private val output by option(
        "--output",
        "-o",
        completionCandidates = CompletionCandidates.Path,
        help = "Output destination path",
    ).file(false, canBeFile = false, canBeDir = true)
    private val force by option("--force", "-f", help = "Answer yes to all confirmations").flag()
    private val convertOnly by option("--convert-only", help = "Without a copy of the resource pack").flag()
    private val ignorePackFormat by option("--ignore-pack-format", help = "Ignore unsupported pack_format error").flag()

    init {
        versionOption(version, names = setOf("--version", "-v")) { it }
    }

    override fun run() {
        val directory = this.directory
        if (directory != null) {
            if (directory.exists().not()) {
                existWithErrorMessage("The directory doesn't exists: $directory")
            }

            val json =
                Json {
                    ignoreUnknownKeys = true
                }
            val resourcePack =
                ResourcePack.load(directory, json) { error ->
                    when (error) {
                        is UnsupportedPackFormatException -> {
                            if (ignorePackFormat) {
                                warnMessage("Unsupported pack_format: ${error.packFormat} (${error.required})", newline = false)
                            } else {
                                existWithErrorMessage(
                                    "Unsupported pack_format: ${error.packFormat} (${error.required})",
                                    "Use --ignore-pack-format to force processing",
                                )
                            }
                        }
                        else -> {
                            throw error
                        }
                    }
                }

            if (!force) {
                val output = output
                if (output == null) {
                    promptConfirm("Overwrite the original resource pack?")
                } else if (output.exists()) {
                    promptConfirm("Output directory already exists, overwrite it?")
                }
            }

            val destination = output ?: directory
            if (directory != destination) {
                if (destination.exists() && !destination.deleteRecursively()) {
                    existWithErrorMessage("Failed to delete output directory.")
                }
                if (convertOnly) {
                    infoMessage("The resource pack will not be copied because --convertOnly is enabled.")
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
                warnMessage("--convertOnly is ignored unless --output is specified.")
            }

            Converter(resourcePack).save(destination)
            successMessage("Generate resource pack: $destination")
        } else {
            echoFormattedHelp()
        }
    }

    private fun promptConfirm(message: String) {
        if (terminal.prompt(message, choices = setOf("yes")) == null) {
            return
        }
        terminal.println()
    }

    private fun successMessage(message: String) {
        terminal.success(message)
    }

    private fun infoMessage(
        message: String,
        newline: Boolean = true,
    ) {
        terminal.info("INFO: $message")
        if (newline) terminal.println()
    }

    private fun warnMessage(
        message: String,
        newline: Boolean = true,
    ) {
        terminal.warning("WARN: $message")
        if (newline) terminal.println()
    }

    private fun existWithErrorMessage(vararg message: String): Nothing {
        terminal.danger("ERROR: ${message.joinToString("\n")}")
        throw CliktError()
    }
}
