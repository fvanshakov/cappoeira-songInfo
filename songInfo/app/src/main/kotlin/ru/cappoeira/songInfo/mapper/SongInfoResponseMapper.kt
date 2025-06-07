package ru.cappoeira.songInfo.mapper

import ru.cappoeira.songInfo.decodeFromBase64
import ru.cappoeira.songInfo.response.*
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity

object SongInfoResponseMapper {

    fun mapToSongInfoAllSongsResponse(entities: List<SongInfoEntity?>): SongInfoAllSongsResponse {
        return SongInfoAllSongsResponse(
            count = entities.size,
            songs = entities.filterNotNull().map {
                SongInfo(
                    id = it.id,
                    songName = it.name,
                    videoUrl = it.videoUrl,
                    songType = it.songType,
                    songLines = it.songLines.map(::mapSongLine)
                )
            }
        )
    }

    fun mapToSongInfoByIdResponse(entity: SongInfoEntity): SongInfoByIdResponse {
        return SongInfoByIdResponse(
            id = entity.id,
            songName = entity.name,
            videoUrl = entity.videoUrl,
            songType = entity.songType,
            songLines = entity.songLines.map(::mapSongLine)
        )
    }

    private fun mapSongLine(entity: SongLineEntity): SongLine {
        return with(entity) {
            SongLine(
                id = id,
                index = index,
                isChoirPart = isChoirPart,
                text = text,
                translation = translation,
                transcription = transcription
            )
        }
    }

    fun mapToSongInfoBySearchTextResponse(
        entities: List<SongInfoEntity>,
        searchText: String
    ): SongInfoBySearchTextResponse {

        val decodedSearchText = decodeFromBase64(searchText)

        fun isSongLineWithMatchingText(songLine: SongLineEntity): Boolean {
            return songLine.text.contains(decodedSearchText) || songLine.translation.contains(decodedSearchText)
        }

        return SongInfoBySearchTextResponse(
            count = entities.size,
            songs = entities.map { entity ->

                val songLines = mutableListOf<SongLineEntity>()

                val matchingLine = entity.songLines.find { line -> isSongLineWithMatchingText(line) }
                matchingLine?.let {
                    val previousLine = entity.songLines.getOrNull(matchingLine.index - 1)
                    previousLine?.let { songLines.add(it) }
                    songLines.add(matchingLine)
                    val followingLine = entity.songLines.getOrNull(matchingLine.index + 1)
                    followingLine?.let { songLines.add(it) }
                }
                SongInfo(
                    id = entity.id,
                    songName = entity.name,
                    videoUrl = entity.videoUrl,
                    songType = entity.songType,
                    songLines = songLines.map(::mapSongLine)
                )
            }
        )
    }
}