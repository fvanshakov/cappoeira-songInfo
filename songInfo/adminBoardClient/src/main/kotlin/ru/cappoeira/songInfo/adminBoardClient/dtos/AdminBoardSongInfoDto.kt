package ru.cappoeira.songInfo.adminBoardClient.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import ru.cappoeira.songInfo.adminBoardClient.mapper.AdminBoardFieldsMapper

data class AdminBoardSongInfoDto(
    val songName: String,
    val videoUrl: String?
) {

    companion object {

        @JsonCreator
        @JvmStatic
        fun create(@JsonProperty("fields") fields: Map<String, Any>): AdminBoardSongInfoDto? {
            return AdminBoardFieldsMapper.mapFieldsToDto(fields)
        }
    }
}