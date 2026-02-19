package ru.cappoeira.songInfo.adminBoardClient.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class AdminBoardDefinitionsDto(
    @JsonProperty("Id")
    val clientId: String?,
    @JsonProperty("definition")
    val definition: String?
)