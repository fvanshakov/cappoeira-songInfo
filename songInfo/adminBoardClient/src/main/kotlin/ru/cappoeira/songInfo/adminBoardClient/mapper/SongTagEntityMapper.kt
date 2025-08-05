package ru.cappoeira.songInfo.adminBoardClient.mapper

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagsDao
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagValueEntity

object SongTagEntityMapper {

    fun mapDtoToValueEntity(
        song: SongInfoEntity,
        dto: AdminBoardTagsDao,
        tags: Map<String, SongTagEntity>
    ): List<SongTagValueEntity> {
        val tagValues = dto.tagsWithKeys.flatMap { (key, values) ->
            values.tagValues.map { value ->
                val tag = tags[key]!!
                val result = SongTagValueEntity(
                    id = value + song.name,
                    tag = tag,
                    tagStringValues = tag.tag,
                    tagValue = value,
                    song = song
                )
                tag.tagValues.add(result)
                result
            }
        }
        return tagValues
    }

    fun mapTagPluralityMap(songsInfos: List<AdminBoardSongInfoDto>): Map<String, Boolean> {
        val isTagPluralMap = mutableMapOf<String, Boolean>()
        songsInfos.forEach {
            it.tags.tagsWithKeys.forEach { (key, value) ->
                val initialValue = isTagPluralMap[key] ?: false
                if (!initialValue) {
                    isTagPluralMap[key] = value.isPlural
                }
            }
        }
        return isTagPluralMap
    }

    fun mapDtoToEntity(
        dto: AdminBoardTagsDao,
        isTagPluralMap: Map<String, Boolean>
    ): Map<String, SongTagEntity> {
        return dto.tagsWithKeys.map { (key, _) ->
            key to SongTagEntity(
                tag = key,
                isPlural = isTagPluralMap[key] ?: false,
                tagValues = mutableListOf()
            )
        }.toMap()
    }
}