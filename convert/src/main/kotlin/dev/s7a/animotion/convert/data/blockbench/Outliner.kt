package dev.s7a.animotion.convert.data.blockbench

import dev.s7a.animotion.convert.model.Vector3
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
@OptIn(ExperimentalUuidApi::class)
data class Outliner(
    val name: String,
    val origin: Vector3,
    val rotation: Vector3 = Vector3(),
    val uuid: Uuid,
    val children: List<Child>,
) {
    @Serializable(with = Child.Serializer::class)
    sealed interface Child {
        @Serializable
        @JvmInline
        value class UsePart(
            val uuid: Uuid,
        ) : Child

        @Serializable
        @JvmInline
        value class UseOutliner(
            val outliner: Outliner,
        ) : Child

        object Serializer : JsonContentPolymorphicSerializer<Child>(Child::class) {
            override fun selectDeserializer(element: JsonElement): DeserializationStrategy<Child> =
                if (element is JsonPrimitive && element.isString) {
                    UsePart.serializer()
                } else {
                    UseOutliner.serializer()
                }
        }
    }
}
