package ru.cappoeira.songInfo

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.core.io.ClassPathResource

fun loadJsonAsMap(fileName: String): Map<String, Any> {
    val resource = ClassPathResource(fileName)
    val json = resource.inputStream.bufferedReader().use { it.readText() }
    val objectMapper = ObjectMapper().findAndRegisterModules()
    return objectMapper.readValue(json, object : TypeReference<Map<String, Any>>() {})
}