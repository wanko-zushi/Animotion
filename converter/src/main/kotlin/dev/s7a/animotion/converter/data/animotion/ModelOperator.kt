package dev.s7a.animotion.converter.data.animotion

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import dev.s7a.animotion.converter.data.blockbench.BlockBenchModel
import java.io.File

class ModelOperator(
    val itemName: String,
    val model: BlockBenchModel,
    val parts: List<Part>,
) {
    fun generate(parent: File) {
        val animotionClass = ClassName("dev.s7a.animotion", "Animotion")
        val partClass = ClassName("dev.s7a.animotion.data", "Part")
        val materialClass = ClassName("org.bukkit", "Material")

        FileSpec
            .builder("", model.name)
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
                                ).initializer("part(%T.%L)", materialClass, itemName.uppercase())
                                .build()
                        },
                    ).build(),
            ).build()
            .writeTo(parent)
    }
}
