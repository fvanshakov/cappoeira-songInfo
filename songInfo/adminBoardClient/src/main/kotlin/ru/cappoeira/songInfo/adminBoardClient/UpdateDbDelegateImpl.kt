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

            // Fetch all songs of all types first to build a complete ID→name map
            val songsPerType = SongType.entries.associateWith { songType ->
                val raw = adminBoardClient.retrieveSongTypeInfoFromAdminBoard(songType)
                val dtoSongType = when(songType) {
                    SongType.LADAINHA -> AdminBoardSongInfoDto.SongType.LADAINHA
                    SongType.CORRIDO -> AdminBoardSongInfoDto.SongType.CORRIDO
                }
                raw to raw.map { it.copy(songType = dtoSongType, id = it.id + dtoSongType.toString()) }
            }

            val songIdsToSongNames = songsPerType.values
                .flatMap { (_, withType) -> withType }
                .associate { it.id to it.songName }

            songsPerType.forEach { (songType, pair) ->
                val (songsInfos, songsInfosWithType) = pair
                updateSongTypeInfo(songType, definitions, songsInfos, songsInfosWithType, songIdsToSongNames)
            }
        }
    }

    private fun updateSongTypeInfo(
        songType: SongType,
        definitions: Map<String, String>,
        songsInfos: List<AdminBoardSongInfoDto>,
        songsInfosWithType: List<AdminBoardSongInfoDto>,
        songIdsToSongNames: Map<String, String>
    ) {
        logger.info("songs of type:$songType have been retrieved from airtable (${songsInfos.size} songs)")
        val isTagPluralMap = SongTagEntityMapper.mapTagPluralityMap(songsInfos)
        val type = when(songType) {
            SongType.LADAINHA -> "LADAINHA"
            SongType.CORRIDO -> "CORRIDO"
        }
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