package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.encodeToBase64
import ru.cappoeira.songInfo.normalizeString
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagEntity

object SongInfoEntityMapper {

    fun mapDtoToEntity(
        dto: AdminBoardSongInfoDto,
        tags: Map<String, SongTagEntity>,
        definitions: Map<String, String>
    ): SongInfoEntity {
        val normalizedName = normalizeString(dto.songName)
        val songInfoEntity = SongInfoEntity(
            id = encodeToBase64(dto.songName),
            name = dto.songName,
            videoUrl = dto.videoUrl,
            normalizedName = normalizedName,
            songType = dto.songType.toString(),
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = dto.optimalTransitions
        )
        val songLines = dto.songLines.map { SongLineMapper.mapDtoToEntity(it, songInfoEntity, definitions) }
        songInfoEntity.songLines.addAll(songLines)
        val tagValues = SongTagEntityMapper.mapDtoToValueEntity(
            song = songInfoEntity,
            dto = dto.tags,
            tags = tags
        )
        songInfoEntity.tagValues.addAll(tagValues)
        return songInfoEntity
    }
}