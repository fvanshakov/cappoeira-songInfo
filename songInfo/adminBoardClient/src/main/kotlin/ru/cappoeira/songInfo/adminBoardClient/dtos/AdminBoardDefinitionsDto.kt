package ru.cappoeira.songInfo.adminBoardClient.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

data class AdminBoardDefinitionsDto(
    val clientId: String?,
    val definition: String?
) {
    companion object {

        @JsonCreator
        @JvmStatic
        fun create(@JsonProperty("fields") fields: Map<String, Any>): AdminBoardDefinitionsDto {
            val id = fields["id"] as? String
            val definition = fields["definition"] as? String
            return AdminBoardDefinitionsDto(
                clientId = id,
                definition = definition
            )
        }
    }
}