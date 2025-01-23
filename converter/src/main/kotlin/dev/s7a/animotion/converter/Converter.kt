package dev.s7a.animotion.converter

import dev.s7a.animotion.AnimotionModel
import dev.s7a.animotion.AnimotionModelLoader
import dev.s7a.animotion.converter.exception.MinecraftItemNotFoundException
import dev.s7a.animotion.converter.exception.ModelNotFoundException
import dev.s7a.animotion.converter.json.animotion.Part
import dev.s7a.animotion.converter.json.blockbench.BlockBenchModel
import dev.s7a.animotion.converter.json.blockbench.Keyframes
import dev.s7a.animotion.converter.json.blockbench.Outliner
import dev.s7a.animotion.converter.json.minecraft.item.MinecraftItem
import dev.s7a.animotion.converter.json.minecraft.item.Override
import dev.s7a.animotion.converter.json.minecraft.item.Predicate
import dev.s7a.animotion.converter.loader.ResourcePack
import dev.s7a.animotion.model.Animation
import dev.s7a.animotion.model.AnimationLoopType
import dev.s7a.animotion.model.Animator
import dev.s7a.animotion.model.AnimatorFrame
import dev.s7a.animotion.model.AnimatorFrameType
import dev.s7a.animotion.model.Vector3
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.io.File
import dev.s7a.animotion.converter.json.blockbench.Animation.LoopType as BlockbenchAnimationLoopType
import dev.s7a.animotion.converter.json.blockbench.Animator.Type as BlockbenchAnimatorType
import dev.s7a.animotion.model.Part as AnimotionModelPart

class Converter(
    private val resourcePack: ResourcePack,
) {
    private val loader = AnimotionModelLoader<String>()

    val items: Map<Pair<String, MinecraftItem>, List<Pair<BlockBenchModel, List<Part>>>>

    init {
        val modelByName = resourcePack.animotion.models.associateBy(BlockBenchModel::name)
        items =
            resourcePack.animotion.settings.items.entries.associate { (itemName, modelNames) ->
                val item = resourcePack.animotion.base[itemName] ?: throw MinecraftItemNotFoundException(itemName)
                val models =
                    modelNames.map { name ->
                        modelByName[name] ?: throw ModelNotFoundException(name)
                    }
                itemName to item to
                    models.map { model ->
                        model to model.toParts(resourcePack.animotion.settings)
                    }
            }
    }

    fun save(destination: File) {
        // Key(Model): name & outliner name
        // Value(Item): material & customModelData
        val modelToItem = mutableMapOf<Pair<String, String>, Pair<String, Int>>()

        items.forEach { (key, value) ->
            val (itemName, item) = key
            var customModelData = 0
            val overrides =
                value.flatMap { (bbModel, parts) ->
                    bbModel.textures.forEachIndexed { index, texture ->
                        val file = texture.getDestinationFile(destination, resourcePack.animotion.settings.namespace, bbModel.name, index)
                        texture.saveTo(file)
                    }
                    parts.mapIndexed { index, part ->
                        customModelData += 1
                        part.save(destination, resourcePack.animotion.settings.namespace, bbModel.name, index)
                        modelToItem[bbModel.name to part.name] = itemName to customModelData
                        Override(Predicate(customModelData), "${resourcePack.animotion.settings.namespace}:${bbModel.name}/$index")
                    }
                }
            destination.resolve("assets/minecraft/models/item/$itemName.json").run {
                parentFile?.mkdirs()
                writeText(Json.encodeToString(item.copy(overrides = overrides)))
            }
        }
        resourcePack.animotion.models.forEach { model ->
            val parts =
                model.outliner.map { outliner ->
                    val (material, customModelData) =
                        modelToItem[model.name to outliner.name] ?: throw RuntimeException(
                            "${model.name} & ${outliner.name} is not assigned to any item",
                        )
                    if (outliner.origin.size != 3) {
                        throw RuntimeException(
                            "Invalid outliner origin length: (Model: ${model.name}, Outliner: ${outliner.name})",
                        )
                    }
                    val position = Vector3(outliner.origin[0], outliner.origin[1], outliner.origin[2])
                    val rotation =
                        outliner.rotation?.let {
                            if (outliner.rotation.size != 3) {
                                throw RuntimeException(
                                    "Invalid outliner rotation length: ${outliner.rotation.size} (Model: ${model.name}, Outliner: ${outliner.name})",
                                )
                            }
                            Vector3(outliner.rotation[0], outliner.rotation[1], outliner.rotation[2])
                        } ?: Vector3.Zero
                    AnimotionModelPart(outliner.name, material, customModelData, position, rotation)
                }
            val animations =
                model.animations.associate { animation ->
                    if (animation.animTimeUpdate != "") throw RuntimeException("Unsupported option: anim_time_update")
                    if (animation.blendWeight != "") throw RuntimeException("Unsupported option: blend_weight")
                    if (animation.startDelay != "") throw RuntimeException("Unsupported option: start_delay")
                    if (animation.loopDelay != "") throw RuntimeException("Unsupported option: loop_delay")
                    val loopType =
                        when (animation.loop) {
                            BlockbenchAnimationLoopType.Loop -> AnimationLoopType.Loop
                            BlockbenchAnimationLoopType.Once -> AnimationLoopType.Once
                            BlockbenchAnimationLoopType.Hold -> AnimationLoopType.Hold
                        }
                    val animators =
                        animation.animators.entries.associate { (outlinerUuid, animator) ->
                            val index = model.outliner.map(Outliner::uuid).indexOf(outlinerUuid)
                            if (index == -1) throw RuntimeException("Not found outliner: $outlinerUuid")
                            if (animator.type != BlockbenchAnimatorType.Bone) {
                                throw RuntimeException(
                                    "Unsupported animator type: ${animator.type}",
                                )
                            }
                            val frames =
                                animator.keyframes.map { frame ->
                                    if (frame.interpolation != Keyframes.Interpolation.Linear) {
                                        throw RuntimeException(
                                            "Unsupported interpolation: ${frame.interpolation}",
                                        )
                                    }
                                    if (frame.dataPoints.size != 1) {
                                        throw RuntimeException(
                                            "Unsupported data_points length: ${frame.dataPoints.size} (Model: ${model.name}, Animation: ${animation.name}, Animator: ${animator.name})",
                                        )
                                    }
                                    val frameType =
                                        when (frame.channel) {
                                            Keyframes.Channel.Rotation -> AnimatorFrameType.Rotation
                                            Keyframes.Channel.Position -> AnimatorFrameType.Position
                                            Keyframes.Channel.Scale -> AnimatorFrameType.Scale
                                        }
                                    val point = Vector3(frame.dataPoints[0].x, frame.dataPoints[0].y, frame.dataPoints[0].z)
                                    AnimatorFrame(frameType, point, frame.time)
                                }
                            index to Animator(frames)
                        }
                    animation.name to Animation(loopType, animation.length, animators)
                }
            destination.resolve("animotion/${model.name}.anim").run {
                parentFile?.mkdirs()
                writeText(loader.save(AnimotionModel.new(parts, animations)))
            }
        }
    }
}
