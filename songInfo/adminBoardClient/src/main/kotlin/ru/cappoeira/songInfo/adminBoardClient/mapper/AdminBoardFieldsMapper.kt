package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto

object AdminBoardFieldsMapper {

    fun mapFieldsToDto(fields: Map<String, Any>): AdminBoardSongInfoDto? {
        val songName = fields[NAME] as? String ?: return null
        val videoUrl = fields[VIDEO_URL] as? String?
        return AdminBoardSongInfoDto(
            songName = songName,
            videoUrl = videoUrl
        )
    }

    private const val NAME = "Название"
    private const val VIDEO_URL = "Стриминг-ссылка"
}