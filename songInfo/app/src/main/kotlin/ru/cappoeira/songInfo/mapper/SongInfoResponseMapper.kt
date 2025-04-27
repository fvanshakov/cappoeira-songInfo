package ru.cappoeira.songInfo.mapper

import ru.cappoeira.songInfo.response.SongInfo
import ru.cappoeira.songInfo.response.SongInfoAllSongsResponse
import ru.cappoeira.songInfo.response.SongInfoByIdResponse
import ru.cappoeira.songInfo.response.SongInfoBySearchTextResponse
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

object SongInfoResponseMapper {

    fun mapToSongInfoAllSongsResponse(entities: List<SongInfoEntity?>): SongInfoAllSongsResponse {
        return SongInfoAllSongsResponse(
            count = entities.size,
            songs = entities.filterNotNull().map {
                SongInfo(
                    id = it.id,
                    songName = it.name,
                    videoUrl = it.videoUrl
                )
            }
        )
    }

    fun mapToSongInfoByIdResponse(entity: SongInfoEntity): SongInfoByIdResponse {
        return SongInfoByIdResponse(
            id = entity.id,
            songName = entity.name,
            videoUrl = entity.videoUrl
        )
    }

    fun mapToSongInfoBySearchTextResponse(entities: List<SongInfoEntity>): SongInfoBySearchTextResponse {
        return SongInfoBySearchTextResponse(
            count = entities.size,
            songs = entities.map {
                SongInfo(
                    id = it.id,
                    songName = it.name,
                    videoUrl = it.videoUrl
                )
            }
        )
    }
}