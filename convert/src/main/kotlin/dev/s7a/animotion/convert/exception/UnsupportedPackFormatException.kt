package dev.s7a.animotion.convert.exception

class UnsupportedPackFormatException(
    val packFormat: Int,
    val required: String,
) : RuntimeException(
        "Unsupported pack_format: $packFormat ($required)",
    )
