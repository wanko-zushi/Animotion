package dev.s7a.animotion.converter.util.path

import okio.Path
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

@OptIn(ExperimentalEncodingApi::class)
fun Path.saveImage(base64: String) {
    require(base64.startsWith("data:image/png;base64,"))
    writeBytes(Base64.decode(base64.drop(22)))
}
