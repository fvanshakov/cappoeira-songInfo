package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.encodeToBase64
import ru.cappoeira.songInfo.normalizeString
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

object SongInfoEntityMapper {

    fun mapDtoToEntity(dto: AdminBoardSongInfoDto): SongInfoEntity {
        val normalizedName = normalizeString(dto.songName)
        val songInfoEntity = SongInfoEntity(
            id = encodeToBase64(dto.songName),
            name = dto.songName,
            videoUrl = dto.videoUrl,
            normalizedName = normalizedName,
            songType = dto.songType.toString(),
            songLines = mutableListOf()
        )
        val songLines = dto.songLines.map { SongLineMapper.mapDtoToEntity(it, songInfoEntity) }
        songInfoEntity.songLines.addAll(songLines)
        return songInfoEntity
    }
}