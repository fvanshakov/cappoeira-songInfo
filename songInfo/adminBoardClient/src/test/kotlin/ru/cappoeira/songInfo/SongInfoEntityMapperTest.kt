package ru.cappoeira.songInfo

import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.mapper.SongInfoEntityMapper
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import kotlin.test.assertEquals

class SongInfoEntityMapperTest {

    val dto = AdminBoardSongInfoDto("Первая песня")
    val expectedResult = SongInfoEntity().apply {
        id = "Первая песня"
        name = "Первая песня"
    }

    @Test
    fun `test mapper returns right entity`() {
        val actualResult = SongInfoEntityMapper.mapDtoToEntity(dto)

        assertEquals(expectedResult, actualResult)
    }
}