package ru.cappoeira.songInfo

import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.mapper.SongInfoResponseMapper
import ru.cappoeira.songInfo.response.SongInfo
import ru.cappoeira.songInfo.response.SongInfoAllSongsResponse
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
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA"
        )
        val expectedResult = SongInfoByIdResponse(
            id = "some id",
            songName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA"
        )

        val result = SongInfoResponseMapper.mapToSongInfoByIdResponse(input)

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test response is properly mapped for all songs call`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA"
        )
        val expectedResult = SongInfoAllSongsResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA"
                )
            )
        )

        val result = SongInfoResponseMapper.mapToSongInfoAllSongsResponse(listOf(input))

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test response is properly mapped for song by text search call`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA"
        )
        val expectedResult = SongInfoBySearchTextResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA"
                )
            )
        )

        val result = SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(listOf(input))

        assertEquals(expected = expectedResult, actual =  result)
    }
}