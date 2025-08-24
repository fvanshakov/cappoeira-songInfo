package ru.cappoeira.songInfo

import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongLineDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagDao
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagsDao
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
            videoUrl = "https://youtu.be/VHPVlgFINGs?si=_3SeGiWYN2PQ6VXk",
            tags = AdminBoardTagsDao(
                tagsWithKeys = mapOf(
                    "Гармония" to AdminBoardTagDao(
                        tagValues = listOf("Мажор"),
                        isPlural = false,
                    ),
                    "Скорость" to AdminBoardTagDao(
                        tagValues = listOf("Медленный темп", "Средний темп"),
                        isPlural = true
                    ),
                    "Сложность солисту" to AdminBoardTagDao(
                        tagValues = listOf("Средне для солиста"),
                        isPlural = false
                    ),
                    "Сложность хора" to AdminBoardTagDao(
                        tagValues = emptyList(),
                        isPlural = false,
                    ),
                    "Темы текста" to AdminBoardTagDao(
                        tagValues = emptyList(),
                        isPlural = false,
                    )
                )
            ),
            songLines = listOf(
                AdminBoardSongLineDto(
                    index = 0,
                    text = "Ai meu joelho  ",
                    isChoirPart = false,
                    translation = "Ай, моё колено  ",
                    transcription = "Ai meu joelho  ",
                ),
                AdminBoardSongLineDto(
                    index = 1,
                    text = "Ai meu joelho dindinha  ",
                    isChoirPart = false,
                    translation = "Ай, моё колено, крёстная  ",
                    transcription = "Ai meu joelho dindinha  ",
                ),
                AdminBoardSongLineDto(
                    index = 2,
                    text = "Ai meu joelho  ",
                    isChoirPart = true,
                    translation = "Припев: Ай, моё колено  ",
                    transcription = "Ai meu joelho  ",
                )
            ),
            optimalTransitions = emptyList()
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
            videoUrl = null,
            tags = AdminBoardTagsDao(
                tagsWithKeys = mapOf(
                    "Гармония" to AdminBoardTagDao(
                        tagValues = listOf("Мажор"),
                        isPlural = false,
                    ),
                    "Скорость" to AdminBoardTagDao(
                        tagValues = listOf("Медленный темп", "Средний темп"),
                        isPlural = true
                    ),
                    "Сложность солисту" to AdminBoardTagDao(
                        tagValues = listOf("Средне для солиста"),
                        isPlural = false
                    ),
                    "Сложность хора" to AdminBoardTagDao(
                        tagValues = listOf("Сложно для хора"),
                        isPlural = false,
                    ),
                    "Темы текста" to AdminBoardTagDao(
                        tagValues = emptyList(),
                        isPlural = false,
                    )
                )
            ),
            songLines = listOf(
                AdminBoardSongLineDto(
                    index = 0,
                    text = "Ai meu joelho  ",
                    isChoirPart = false,
                    translation = "Ай, моё колено  ",
                    transcription = "Ai meu joelho  ",
                ),
                AdminBoardSongLineDto(
                    index = 1,
                    text = "Ai meu joelho dindinha  ",
                    isChoirPart = false,
                    translation = "Ай, моё колено, крёстная  ",
                    transcription = "Ai meu joelho dindinha  ",
                ),
                AdminBoardSongLineDto(
                    index = 2,
                    text = "Ai meu joelho  ",
                    isChoirPart = true,
                    translation = "Припев: Ай, моё колено  ",
                    transcription = "Ai meu joelho  ",
                )
            ),
            optimalTransitions = emptyList()
        )

        assertEquals(expected = expectedResult, actual =  result)
    }
}