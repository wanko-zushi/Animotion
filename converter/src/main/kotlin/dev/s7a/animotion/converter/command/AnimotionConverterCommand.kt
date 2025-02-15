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
import dev.s7a.animotion.converter.exception.UnsupportedPackFormatException
import dev.s7a.animotion.converter.generator.CodeGenerator
import dev.s7a.animotion.converter.generator.PackGenerator
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
    private val code by option(
        "--code",
        "-c",
        completionCandidates = CompletionCandidates.Path,
        help = "Code destination path",
    ).file(false, canBeFile = false, canBeDir = true)
    private val force by option("--force", "-f", help = "Answer yes to all confirmations").flag()
    private val ignorePackFormat by option("--ignore-pack-format", help = "Ignore unsupported pack_format error").flag()

    init {
        versionOption(version, names = setOf("--version", "-v"))
    }

    override fun run() {
        val directory = this.directory
        val output = this.output
        val code = this.code
        if (directory == null || (output == null && code == null)) {
            echoFormattedHelp()
            return
        }

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

        if (output != null) {
            if (directory != output) {
                if (output.exists()) {
                    if (force.not()) {
                        promptConfirm("Output directory already exists, overwrite it?")
                    }

                    if (output.deleteRecursively().not()) {
                        existWithErrorMessage("Failed to delete output directory.")
                    }
                }

                try {
                    listOf("assets", "pack.mcmeta", "pack.png").forEach {
                        val file = directory.resolve(it)
                        if (file.exists()) {
                            val outputFile = output.resolve(it)
                            file.copyRecursively(outputFile)
                            infoMessage("Copy file: $file -> $outputFile", newline = false)
                        }
                    }
                } catch (error: NoSuchFileException) {
                    existWithErrorMessage("Copy failed. The source file doesn't exists: ${error.file}")
                } catch (error: FileAlreadyExistsException) {
                    existWithErrorMessage("Copy failed. The destination file already exists: ${error.file} -> ${error.other}")
                } catch (error: IOException) {
                    existWithErrorMessage("Copy failed. ${error.message}")
                }
            } else {
                if (force.not()) {
                    promptConfirm("Overwrite the original resource pack?")
                }
            }

            PackGenerator(resourcePack).save(output)
            successMessage("Generate resource pack: $output")
        }

        if (code != null) {
            if (code.exists()) {
                if (force.not()) {
                    promptConfirm("Code directory already exists, overwrite it?")
                }

                if (code.deleteRecursively().not()) {
                    existWithErrorMessage("Failed to delete code directory.")
                }
            }

            CodeGenerator(resourcePack).save(code)
            successMessage("Generate code: $code")
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
