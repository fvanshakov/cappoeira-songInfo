package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

object SongInfoEntityMapper {

    fun mapDtoToEntity(dto: AdminBoardSongInfoDto): SongInfoEntity {
        return SongInfoEntity(
            id = dto.songName,
            name = dto.songName
        )
    }
}