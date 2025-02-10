package dev.s7a.animotion.converter.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.s7a.animotion.converter.loader.ResourcePack
import java.io.File

class CodeGenerator(
    private val resourcePack: ResourcePack,
) {
    fun save(parent: File) {
        val animotionClass = ClassName("dev.s7a.animotion", "Animotion")
        val partClass = ClassName("dev.s7a.animotion.data", "Part")
        val materialClass = ClassName("org.bukkit", "Material")

        resourcePack.animotion.models.forEach { (model, parts) ->
            FileSpec
                .builder(resourcePack.animotion.settings.`package`, model.name)
                .addType(
                    TypeSpec
                        .classBuilder(model.name)
                        .primaryConstructor(
                            FunSpec
                                .constructorBuilder()
                                .addParameter("animotion", animotionClass)
                                .build(),
                        ).addProperties(
                            parts.map { part ->
                                PropertySpec
                                    .builder(
                                        part.name,
                                        partClass,
                                    ).initializer("part(%T.%L)", materialClass, resourcePack.animotion.settings.item.bukkit)
                                    .build()
                            },
                        ).build(),
                ).build()
                .writeTo(parent)
        }
    }
}
