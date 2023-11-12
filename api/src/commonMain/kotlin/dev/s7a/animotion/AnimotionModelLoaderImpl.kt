package dev.s7a.animotion

import dev.s7a.animotion.exception.UnsupportedVersionException
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

internal class AnimotionModelLoaderImpl<Material>(
    val json: Json,
    private val materialSerializer: KSerializer<Material>,
) : AnimotionModelLoader<Material> {
    init {
        require(json.configuration.ignoreUnknownKeys) {
            "json must be enabled ignoreUnknownKeys"
        }
    }

    @Serializable
    class AnimotionModelBase(val version: Int)

    override fun load(text: String): AnimotionModel<Material> {
        return when (val version = json.decodeFromString(AnimotionModelBase.serializer(), text).version) {
            1 -> {
                json.decodeFromString(AnimotionModel.serializer(materialSerializer), text)
            }
            else -> {
                throw UnsupportedVersionException(version)
            }
        }
    }

    override fun save(value: AnimotionModel<Material>): String {
        return json.encodeToString(AnimotionModel.serializer(materialSerializer), value)
    }
}
