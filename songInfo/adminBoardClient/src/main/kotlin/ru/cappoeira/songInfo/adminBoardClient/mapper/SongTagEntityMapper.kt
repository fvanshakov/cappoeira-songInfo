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
        isTagPluralMap: Map<String, Boolean>
    ): List<SongTagValueEntity> {
        val tags = mapDtoToEntity(
            dto = dto,
            isTagPluralMap = isTagPluralMap
        )
        val tagValues = dto.tagsWithKeys.flatMap { (key, values) ->
            values.tagValues.map { value ->
                SongTagValueEntity(
                    tag = tags[key]!!,
                    tagValue = value,
                    song = song
                )
            }
        }
        tagValues.forEach {
            it.tag.tagValues.add(it)
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

    private fun mapDtoToEntity(
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