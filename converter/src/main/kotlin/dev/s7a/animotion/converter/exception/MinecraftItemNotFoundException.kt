package dev.s7a.animotion.converter.exception

class MinecraftItemNotFoundException(
    item: String,
) : RuntimeException("animotion/base/$item.json not found")
