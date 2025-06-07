package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongLineDto
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity

object SongLineMapper {

    fun mapDtoToEntity(dto: AdminBoardSongLineDto, song: SongInfoEntity): SongLineEntity {
        return SongLineEntity(
            id = song.id + dto.index,
            text = dto.text,
            translation = dto.translation,
            transcription = dto.transcription,
            isChoirPart = dto.isChoirPart,
            song = song,
            index = dto.index
        )
    }
}