package ru.cappoeira.songInfo

import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.mapper.SongInfoResponseMapper
import ru.cappoeira.songInfo.response.SongInfo
import ru.cappoeira.songInfo.response.SongInfoByIdResponse
import ru.cappoeira.songInfo.response.SongInfoBySearchTextResponse
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import kotlin.test.assertEquals

class SongInfoResponseMapperTest {

    @Test
    fun `test response is properly mapped for song by id call`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            videoUrl = "some url"
        )
        val expectedResult = SongInfoByIdResponse(
            id = "some id",
            songName = "some name",
            videoUrl = "some url"
        )

        val result = SongInfoResponseMapper.map(input)

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test response is properly mapped for song by text search call`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            videoUrl = "some url"
        )
        val expectedResult = SongInfoBySearchTextResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url"
                )
            )
        )

        val result = SongInfoResponseMapper.map(listOf(input))

        assertEquals(expected = expectedResult, actual =  result)
    }
}