package dev.s7a.animotion.convert.serializers

import dev.s7a.animotion.convert.data.Vector3
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

class Vector3Serializer : KSerializer<Vector3> {
    override val descriptor = SerialDescriptor("Vector3", ListSerializer(Double.serializer()).descriptor)

    override fun serialize(
        encoder: Encoder,
        value: Vector3,
    ) {
        encoder.encodeSerializableValue(ListSerializer(Double.serializer()), value.toList())
    }

    override fun deserialize(decoder: Decoder): Vector3 {
        val list = decoder.decodeSerializableValue(ListSerializer(Double.serializer()))
        require(list.size == 3) { "Vector3 must be deserialized from a list of exactly 3 elements" }
        return Vector3(list[0], list[1], list[2])
    }
}
