package ru.cappoeira.songInfo.adminBoardClient

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.cappoeira.songInfo.Response
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient.SongType
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.mapper.SongInfoEntityMapper
import ru.cappoeira.songInfo.adminBoardClient.mapper.SongTagEntityMapper
import ru.cappoeira.songInfo.safeCall
import ru.cappoeira.songInfo.songInfoDB.service.SongInfoService

@Component
open class UpdateDbDelegateImpl(
    private val adminBoardClient: AdminBoardClient,
    private val songInfoService: SongInfoService
) : UpdateDbDelegate {

    private val logger = LoggerFactory.getLogger(UpdateDbDelegateImpl::class.java)

    override fun update(): Response {
        return safeCall {
            val definitions = adminBoardClient.retrieveDefinitions()
                .filter { it.clientId != null && it.definition != null }
                .associate { it.clientId as String to it.definition as String }
            SongType.entries.forEach { updateSongTypeInfo(it, definitions) }
        }
    }

    private fun updateSongTypeInfo(songType: SongType, definitions: Map<String, String>) {

        val songsInfos = adminBoardClient.retrieveSongTypeInfoFromAdminBoard(songType)
        logger.info("songs of type:$songType have been retrieved from airtable, namely $songsInfos")
        val songsInfosWithType = songsInfos.map {
            val dtoSongType = when(songType) {
                SongType.LADAINHA -> AdminBoardSongInfoDto.SongType.LADAINHA
                SongType.CORRIDO -> AdminBoardSongInfoDto.SongType.CORRIDO
            }
            it.copy(songType = dtoSongType, id = it.id + dtoSongType.toString())
        }
        val isTagPluralMap = SongTagEntityMapper.mapTagPluralityMap(songsInfos)
        val type = when(songType) {
            SongType.LADAINHA -> "LADAINHA"
            SongType.CORRIDO -> "CORRIDO"
        }
        val songIdsToSongNames = songsInfosWithType.map { song ->
            song.id to song.songName
        }.toMap()
        try {
            songInfoService.saveSongs(
                songsInfosWithType.map {
                    val tags = SongTagEntityMapper.mapDtoToEntity(it.tags, isTagPluralMap, type)

                    songInfoService.saveTags(tags.map { it.value })

                    SongInfoEntityMapper.mapDtoToEntity(
                        dto = it,
                        tags = tags,
                        definitions = definitions,
                        songIdsToSongNames = songIdsToSongNames
                    )
                }
            )
        } catch (e: Exception) {
            logger.info(e.toString())
        }
    }
}