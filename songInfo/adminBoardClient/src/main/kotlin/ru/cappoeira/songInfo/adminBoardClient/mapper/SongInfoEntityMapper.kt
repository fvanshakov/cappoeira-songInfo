package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.encodeToBase64
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

object SongInfoEntityMapper {

    fun mapDtoToEntity(dto: AdminBoardSongInfoDto): SongInfoEntity {
        return SongInfoEntity(
            id = encodeToBase64(dto.songName),
            name = dto.songName
        )
    }
}