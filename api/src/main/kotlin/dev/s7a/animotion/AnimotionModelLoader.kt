package dev.s7a.animotion

import kotlinx.serialization.KSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.serializer

@Suppress("ktlint:standard:function-naming")
public inline fun <reified Material> AnimotionModelLoader(
    json: Json = Json { ignoreUnknownKeys = true },
    materialSerializer: KSerializer<Material> = serializer<Material>(),
): AnimotionModelLoader<Material> = AnimotionModelLoader.new(json, materialSerializer)

public interface AnimotionModelLoader<Material> {
    public companion object {
        public fun <Material> new(
            json: Json,
            materialSerializer: KSerializer<Material>,
        ): AnimotionModelLoader<Material> = AnimotionModelLoaderImpl(json, materialSerializer)
    }

    public fun load(text: String): AnimotionModel<Material>

    public fun save(value: AnimotionModel<Material>): String
}
