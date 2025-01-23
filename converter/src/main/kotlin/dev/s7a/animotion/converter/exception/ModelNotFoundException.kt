package dev.s7a.animotion.converter.exception

class ModelNotFoundException(
    model: String,
) : RuntimeException("animotion/$model.bbmodel not found")
