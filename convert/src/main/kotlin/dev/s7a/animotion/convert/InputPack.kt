package dev.s7a.animotion.convert

import dev.s7a.animotion.convert.data.PackMeta
import dev.s7a.animotion.convert.exception.UnsupportedPackFormatException
import kotlinx.serialization.json.Json
import java.io.File

data class InputPack(
    val meta: PackMeta,
    val animotion: Animotion,
) {
    companion object {
        private val json =
            Json {
                ignoreUnknownKeys = true
            }

        fun load(
            directory: File,
            onErrorAction: (Exception) -> Unit = { exception -> throw exception },
        ): InputPack {
            val meta = json.decodeFromString<PackMeta>(directory.resolve("pack.mcmeta").readText())
            if (meta.pack.packFormat < 13) {
                onErrorAction(UnsupportedPackFormatException(meta.pack.packFormat, "< 13"))
            }
            val animotion = Animotion.load(directory.resolve("animotion"))
            return InputPack(meta, animotion)
        }
    }
}
