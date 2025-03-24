package ru.cappoeira.songInfo.mapper

import ru.cappoeira.songInfo.response.SongInfo
import ru.cappoeira.songInfo.response.SongInfoByIdResponse
import ru.cappoeira.songInfo.response.SongInfoBySearchTextResponse
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

object SongInfoResponseMapper {

    fun map(entity: SongInfoEntity): SongInfoByIdResponse {
        return SongInfoByIdResponse(
            id = entity.id,
            songName = entity.name
        )
    }

    fun map(entities: List<SongInfoEntity>): SongInfoBySearchTextResponse {
        return SongInfoBySearchTextResponse(
            count = entities.size,
            songs = entities.map {
                SongInfo(
                    id = it.id,
                    songName = it.name
                )
            }
        )
    }
}