package ru.cappoeira.songInfo.adminBoardClient

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import ru.cappoeira.songInfo.Response
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient.SongType
import ru.cappoeira.songInfo.adminBoardClient.mapper.SongInfoEntityMapper
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
            songInfoService.deleteAllSongs()
            SongType.entries.forEach(::updateSongTypeInfo)
        }
    }

    private fun updateSongTypeInfo(songType: SongType) {
        val songsInfos = adminBoardClient.retrieveSongTypeInfoFromAdminBoard(songType)
        logger.info("songs of type:$songType have been retrieved from airtable, namely $songsInfos")
        songInfoService.saveSongs(songsInfos.map(SongInfoEntityMapper::mapDtoToEntity))
    }
}