package dev.s7a.animotion.convert.data

import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import java.io.File
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
data class BlockbenchModel(
    // TODO モデルをロードする前に Meta をチェックして必要に応じてローダーを変える
    val meta: Meta,
    val name: String,
    val resolution: Resolution,
    val elements: List<Element>,
    val outliner: List<Outliner>,
    val textures: List<Texture>,
    val animations: List<Animation>,
) {
    @Serializable
    data class Meta(
        @SerialName("format_version")
        val formatVersion: String,
        @SerialName("model_format")
        val modelFormat: ModelFormat,
    ) {
        @Serializable
        enum class ModelFormat {
            @SerialName("free")
            Free,
        }
    }

    @Serializable
    data class Resolution(
        val width: Int,
        val height: Int,
    )

    @Serializable
    @OptIn(ExperimentalUuidApi::class)
    data class Element(
        val from: Vector3,
        val to: Vector3,
        val rotation: Vector3 = Vector3(),
        val origin: Vector3,
        val faces: Map<MinecraftModel.Element.FaceType, Face>,
        val type: Type,
        val uuid: Uuid,
    ) {
        @Serializable
        data class Face(
            val uv: List<Double>,
            val texture: Int? = null,
        )

        @Serializable
        enum class Type {
            @SerialName("cube")
            Cube,
        }
    }

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

    @Serializable
    data class Texture(
        @SerialName("uv_width")
        val uvWidth: Double = 64.0,
        @SerialName("uv_height")
        val uvHeight: Double = 64.0,
        val source: String,
    ) {
        init {
            require(source.startsWith("data:image/png;base64,"))
        }

        @OptIn(ExperimentalEncodingApi::class)
        fun toImage() = Base64.decode(source.drop(22))
    }

    @Serializable
    @OptIn(ExperimentalUuidApi::class)
    data class Animation(
        val name: String,
        val loop: LoopType,
        val length: Double,
        @SerialName("anim_time_update") val animTimeUpdate: String = "",
        @SerialName("blend_weight") val blendWeight: String = "",
        @SerialName("start_delay") val startDelay: String = "",
        @SerialName("loop_delay") val loopDelay: String = "",
        @SerialName("animators") val animators: Map<Uuid, Animator> = mapOf(),
    ) {
        @Serializable
        enum class LoopType {
            @SerialName("loop")
            Loop,

            @SerialName("once")
            Once,

            @SerialName("hold")
            Hold,
        }

        @Serializable
        data class Animator(
            val name: String,
            val type: Type,
            val keyframes: List<Keyframe>,
        ) {
            @Serializable
            enum class Type {
                @SerialName("bone")
                Bone,
            }

            @Serializable
            data class Keyframe(
                val channel: Channel,
                @SerialName("data_points") val dataPoints: List<DataPoints>,
                val time: Double,
                val interpolation: Interpolation,
            ) {
                @Serializable
                enum class Channel {
                    @SerialName("rotation")
                    Rotation,

                    @SerialName("position")
                    Position,

                    @SerialName("scale")
                    Scale,
                }

                @Serializable
                data class DataPoints(
                    val x: Double,
                    val y: Double,
                    val z: Double,
                )

                @Serializable
                enum class Interpolation {
                    @SerialName("linear")
                    Linear,

                    @SerialName("catmullrom")
                    Catmullrom,
                }
            }
        }
    }

    companion object {
        private val json =
            Json {
                ignoreUnknownKeys = true
            }

        fun load(file: File) = json.decodeFromString<BlockbenchModel>(file.readText())
    }
}
