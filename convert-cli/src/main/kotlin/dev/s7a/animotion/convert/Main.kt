@file:JvmName("Main")

package dev.s7a.animotion.convert

import com.github.ajalt.clikt.core.main

fun main(args: Array<String>) =
    AnimotionConverterCommand(
        AnimotionConverterCommand::class.java.`package`.implementationVersion,
    ).main(args)
