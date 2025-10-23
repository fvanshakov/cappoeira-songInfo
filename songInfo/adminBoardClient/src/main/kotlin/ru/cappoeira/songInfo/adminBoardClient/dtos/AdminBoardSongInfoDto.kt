package ru.cappoeira.songInfo.adminBoardClient.dtos

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import ru.cappoeira.songInfo.adminBoardClient.mapper.AdminBoardFieldsMapper

data class AdminBoardSongInfoDto(
    val songName: String,
    val videoUrl: String?,
    val songType: SongType,
    val songLines: List<AdminBoardSongLineDto>,
    val tags: AdminBoardTagsDao,
    val optimalTransitions: List<String>,
    val isVisible: Boolean,
    val warning: String?
) {

    enum class SongType { CORRIDO, LADAINHA }

    companion object {

        @JsonCreator
        @JvmStatic
        fun create(@JsonProperty("fields") fields: Map<String, Any>): AdminBoardSongInfoDto {
            return AdminBoardFieldsMapper.mapFieldsToDto(fields)
        }
    }
}