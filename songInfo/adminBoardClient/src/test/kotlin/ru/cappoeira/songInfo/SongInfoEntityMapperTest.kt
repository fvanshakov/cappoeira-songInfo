package ru.cappoeira.songInfo

import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagsDao
import ru.cappoeira.songInfo.adminBoardClient.mapper.SongInfoEntityMapper
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import kotlin.test.assertEquals

class SongInfoEntityMapperTest {

    private val dto = AdminBoardSongInfoDto(
        songName = "Первая песня",
        songType = AdminBoardSongInfoDto.SongType.LADAINHA,
        videoUrl = "some url",
        tags = AdminBoardTagsDao(
            tagsWithKeys = emptyMap()
        ),
        songLines = emptyList(),
        optimalTransitions = emptyList(),
        isVisible = true,
        isWithAlerts = false
    )
    private val expectedResult = SongInfoEntity().apply {
        id = encodeToBase64("Первая песня")
        name = "Первая песня"
        normalizedName = "Первая песня"
        videoUrl = "some url"
        songType = "LADAINHA"
        songLines = mutableListOf()
    }

    @Test
    fun `test mapper returns right entity`() {
        val actualResult = SongInfoEntityMapper.mapDtoToEntity(
            dto = dto,
            tags = emptyMap(),
            definitions = emptyMap()
        )

        assertEquals(expectedResult, actualResult)
    }
}