package ru.cappoeira.songInfo.mapper

import ru.cappoeira.songInfo.decodeFromBase64
import ru.cappoeira.songInfo.encodeToBase64
import ru.cappoeira.songInfo.response.*
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagValueEntity

object SongInfoResponseMapper {

    fun mapSongTagsResponse(
        tags: List<SongTagEntity>
    ): SongTagsResponse {
        val tagsMap = mutableMapOf<String, SongTag>()
        tags.map { tag ->
            tagsMap[tag.tag] = SongTag(
                values = tag.tagValues.map { it.tagValue }.distinct(),
                isPlural = tag.isPlural
            )
        }
        return SongTagsResponse(
            tags = tagsMap
        )
    }

    fun mapToSongInfoAllSongsResponse(entities: List<SongInfoEntity?>): SongInfoAllSongsResponse {
        return SongInfoAllSongsResponse(
            count = entities.size,
            songs = entities.filterNotNull().map {
                SongInfo(
                    id = it.id,
                    songName = it.name,
                    videoUrl = it.videoUrl,
                    songType = it.songType,
                    songLines = it.songLines.map(::mapSongLine),
                    songTags = it.tagValues.let(::mapSongTag),
                    optimalTransitions = it.optimalTransitions.map { songName ->
                        Transition(
                            songName = songName,
                            songId = encodeToBase64(songName)
                        )
                    }
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
            songLines = entity.songLines.map(::mapSongLine),
            songTags = entity.tagValues.let(::mapSongTag),
            optimalTransitions = entity.optimalTransitions.map { songName ->
                Transition(
                    songName = songName,
                    songId = encodeToBase64(songName)
                )
            }
        )
    }

    private fun mapSongLine(entity: SongLineEntity): SongLine {
        return with(entity) {
            SongLine(
                id = id,
                index = index,
                isChoirPart = isChoirPart,
                text = text,
                translation = translationChunks.map {
                    SongLinesChunks(
                        text = it.text,
                        definition = it.definition
                    )
                },
                transcription = transcriptionChunks.map {
                    SongLinesChunks(
                        text = it.transcription,
                        definition = it.definition
                    )
                }
            )
        }
    }

    private fun mapSongTag(entities: MutableList<SongTagValueEntity>): SongTags {
        val finalMap = mutableMapOf<String, MutableList<String>>()
        entities.map {
            val key = it.tagStringValues
            val initialValue = finalMap[key] ?: mutableListOf()
            initialValue.add(it.tagValue)
            finalMap[key] = initialValue
        }
        return SongTags(finalMap)
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
                    songLines = songLines.map(::mapSongLine),
                    songTags = entity.tagValues.let(::mapSongTag),
                    optimalTransitions = entity.optimalTransitions.map { songName ->
                        Transition(
                            songName = songName,
                            songId = encodeToBase64(songName)
                        )
                    }
                )
            }
        )
    }
}