package dev.s7a.animotion.converter.util

import dev.s7a.animotion.converter.util.path.writeBytes
import okio.Path
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun saveImage(base64: String, destination: Path) {
    require(base64.startsWith("data:image/png;base64,"))
    destination.writeBytes(Base64.decode(base64.drop(22)))
}
