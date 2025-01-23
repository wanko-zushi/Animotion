package dev.s7a.animotion.exception

public open class UnsupportedVersionException(
    public val version: Int,
) : IllegalArgumentException("Unsupported version: $version")
