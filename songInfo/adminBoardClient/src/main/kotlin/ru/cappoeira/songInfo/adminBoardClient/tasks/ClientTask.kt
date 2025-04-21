package ru.cappoeira.songInfo.adminBoardClient.tasks

import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient.SongType
import ru.cappoeira.songInfo.adminBoardClient.mapper.SongInfoEntityMapper
import ru.cappoeira.songInfo.songInfoDB.service.SongInfoService

@Component
class ClientTask(
    private val adminBoardClient: AdminBoardClient,
    private val songInfoService: SongInfoService
) {

    private val logger = LoggerFactory.getLogger(ClientTask::class.java)

    @Scheduled(cron = "0 57 20 * * *")
    fun updateSongInfo() {
        SongType.entries.forEach(::updateSongTypeInfo)
    }

    private fun updateSongTypeInfo(songType: SongType) {
        val songsInfos = adminBoardClient.retrieveSongTypeInfoFromAdminBoard(songType)
        logger.info("songs of type:$songType have been retrieved from airtable, namely $songsInfos")
        songInfoService.deleteAllSongs()
        songInfoService.saveSongs(songsInfos.map(SongInfoEntityMapper::mapDtoToEntity))
    }
}