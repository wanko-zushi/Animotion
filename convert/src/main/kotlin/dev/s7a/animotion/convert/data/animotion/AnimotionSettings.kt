package dev.s7a.animotion.convert.data.animotion

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import java.io.File

@Serializable
data class AnimotionSettings(
    val namespace: String = "animotion",
    val item: String = "stick",
    @SerialName("package")
    val packageName: String = "",
) {
    companion object {
        private val json =
            Json {
                ignoreUnknownKeys = true
            }

        private fun load(file: File) = json.decodeFromString<AnimotionSettings>(file.readText())

        fun loadOrDefault(file: File) = if (file.exists()) load(file) else AnimotionSettings()
    }
}
