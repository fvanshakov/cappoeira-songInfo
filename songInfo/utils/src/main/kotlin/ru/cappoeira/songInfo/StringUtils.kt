package ru.cappoeira.songInfo

import java.util.*

fun emptyString() = ""

fun encodeToBase64(input: String): String {
    return Base64.getEncoder().encodeToString(input.toByteArray(Charsets.UTF_8))
}

fun decodeFromBase64(encoded: String): String {
    return String(Base64.getDecoder().decode(encoded), Charsets.UTF_8)
}