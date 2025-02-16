package dev.s7a.animotion.convert.exception

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@OptIn(ExperimentalUuidApi::class)
class NotFoundPartException(
    uuid: Uuid,
) : RuntimeException("Not found part: $uuid")
