package ru.cappoeira.songInfo

import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.mapper.AdminBoardFieldsMapper
import kotlin.test.assertEquals

class AdminBoardMapperTest {

    val jsonMap = loadJsonAsMap("airtableResultWithOffset.json")
    val records = jsonMap["records"] as? List<Map<String, Any>>

    @Test
    fun `test mapper returns right dto when there is a song name`() {
        val result = (records?.get(0)?.get("fields") as? Map<String, Any>)
            ?.let(AdminBoardFieldsMapper::mapFieldsToDto)

        val expectedResult = AdminBoardSongInfoDto(
            songName = "Первая песня",
            songType = AdminBoardSongInfoDto.SongType.CORRIDO,
            videoUrl = "https://youtu.be/VHPVlgFINGs?si=_3SeGiWYN2PQ6VXk"
        )

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test mapper returns null when there is no song name`() {
        val result = (records?.get(1)?.get("fields") as? Map<String, Any>)
            ?.let(AdminBoardFieldsMapper::mapFieldsToDto)

        val expectedResult = null

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test mapper returns right dto when there is no video url`() {
        val result = (records?.get(2)?.get("fields") as? Map<String, Any>)
            ?.let(AdminBoardFieldsMapper::mapFieldsToDto)

        val expectedResult = AdminBoardSongInfoDto(
            songName = "Первая песня",
            songType = AdminBoardSongInfoDto.SongType.CORRIDO,
            videoUrl = null
        )

        assertEquals(expected = expectedResult, actual =  result)
    }
}