package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto

object AdminBoardFieldsMapper {

    fun mapFieldsToDto(fields: Map<String, Any>): AdminBoardSongInfoDto? {
        val songName = fields[NAME] as? String ?: return null
        return AdminBoardSongInfoDto(
            songName = songName
        )
    }

    private const val NAME = "Название"
}