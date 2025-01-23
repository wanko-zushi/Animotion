@file:JvmName("Main")

package dev.s7a.animotion.converter

import com.github.ajalt.clikt.core.main
import dev.s7a.animotion.converter.command.AnimotionConverterCommand

fun main(args: Array<String>) =
    AnimotionConverterCommand(
        AnimotionConverterCommand::class.java.`package`.implementationVersion,
    ).main(args)
