package ru.cappoeira.songInfo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.adminBoardClient.UpdateDbDelegateImpl
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagsDao
import ru.cappoeira.songInfo.adminBoardClient.mapper.SongInfoEntityMapper
import ru.cappoeira.songInfo.songInfoDB.service.SongInfoService

class UpdateDbDelegateImplTest {

    private val adminBoardClient = mockk<AdminBoardClient>()
    private val songInfoService = mockk<SongInfoService>()

    private val updateDbDelegateImpl = UpdateDbDelegateImpl(adminBoardClient, songInfoService)

    private val corridoSong = AdminBoardSongInfoDto(
        songName = "corrido song",
        videoUrl = null,
        songType = AdminBoardSongInfoDto.SongType.CORRIDO,
        songLines = emptyList(),
        tags = AdminBoardTagsDao(
            tagsWithKeys = emptyMap()
        )
    )
    private val ladainhaSong = AdminBoardSongInfoDto(
        songName = "ladainha song",
        videoUrl = null,
        songType = AdminBoardSongInfoDto.SongType.LADAINHA,
        songLines = emptyList(),
        tags = AdminBoardTagsDao(
            tagsWithKeys = emptyMap()
        )
    )

    @BeforeEach
    fun setup() {
        every { adminBoardClient.retrieveSongTypeInfoFromAdminBoard(AdminBoardClient.SongType.CORRIDO) } returns
                listOf(corridoSong)
        every { adminBoardClient.retrieveSongTypeInfoFromAdminBoard(AdminBoardClient.SongType.LADAINHA) } returns
                listOf(ladainhaSong)
        every { songInfoService.saveSongs(any()) } returns Unit
        every { songInfoService.deleteAllSongs() } returns Unit
        every { songInfoService.saveTags(any()) } returns Unit
    }

    @Test
    fun `when task triggered both songs are retrieved and saved`() {
        updateDbDelegateImpl.update()

        val mappedCorridoSong = SongInfoEntityMapper.mapDtoToEntity(
            dto = corridoSong,
            tags = emptyMap()
        )
        val mappedLadainhaSong = SongInfoEntityMapper.mapDtoToEntity(
            dto = ladainhaSong,
            tags = emptyMap()
        )

        verify(exactly = 1) { songInfoService.deleteAllSongs() }
        verify(exactly = 1) { adminBoardClient.retrieveSongTypeInfoFromAdminBoard(AdminBoardClient.SongType.CORRIDO) }
        verify(exactly = 1) { adminBoardClient.retrieveSongTypeInfoFromAdminBoard(AdminBoardClient.SongType.LADAINHA) }
        verify(exactly = 1) { songInfoService.saveSongs(listOf(mappedCorridoSong)) }
        verify(exactly = 1) { songInfoService.saveSongs(listOf(mappedLadainhaSong)) }
    }
}