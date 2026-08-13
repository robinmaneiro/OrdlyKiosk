package com.robinmaneiro.ordly.kiosk.util

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper

object Mapper {
    private val objectMapper = jacksonObjectMapper()

    /**
     * Attempts to deserialize the [json] into the specified [clazz] - returning [T] if successful, and 'null' if there's an exception.
     */
    fun <T> map(json: String, clazz: Class<T>): T? = runCatching {
        objectMapper.readValue(json, clazz)
    }.getOrNull()

    fun <T> asSerializedStringOrNull(obj: T) = asSerializedStringResult(obj).getOrNull()

    fun <T> asSerializedStringResult(obj: T) = runCatching {
        objectMapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(obj)
    }
}
