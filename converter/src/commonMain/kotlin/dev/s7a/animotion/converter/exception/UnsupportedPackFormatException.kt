package dev.s7a.animotion.converter.exception

class UnsupportedPackFormatException(packFormat: Int) : RuntimeException("Unsupported pack_format: $packFormat (< 13)")
