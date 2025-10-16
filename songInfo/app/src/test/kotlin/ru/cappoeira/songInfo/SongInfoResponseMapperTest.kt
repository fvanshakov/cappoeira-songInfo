package ru.cappoeira.songInfo

import org.junit.jupiter.api.Test
import ru.cappoeira.songInfo.mapper.SongInfoResponseMapper
import ru.cappoeira.songInfo.response.*
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity
import kotlin.test.assertEquals

class SongInfoResponseMapperTest {

    @Test
    fun `test response is properly mapped for song by id call`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA",
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = emptyList(),
            isVisible = true

        )
        val expectedResult = SongInfoByIdResponse(
            id = "some id",
            songName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA",
            songLines = emptyList(),
            songTags = SongTags(
                tagsWithValues = emptyMap()
            ),
            optimalTransitions = emptyList()
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
            songType = "LADAINHA",
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = emptyList(),
            isVisible = true

        )
        val expectedResult = SongInfoAllSongsResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA",
                    songLines = emptyList(),
                    songTags = SongTags(
                        tagsWithValues = emptyMap()
                    ),
                    optimalTransitions = emptyList()
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
            songType = "LADAINHA",
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = emptyList(),
            isVisible = true
        )
        val expectedResult = SongInfoBySearchTextResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA",
                    songLines = emptyList(),
                    songTags = SongTags(
                        tagsWithValues = emptyMap()
                    ),
                    optimalTransitions = emptyList()
                )
            )
        )

        val result = SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(listOf(input), "dGV4dA==")

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test response is properly mapped for song with matching text in lines`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA",
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = emptyList(),
            isVisible = true
        )
        input.apply {
            songLines.addAll(
                listOf(
                    SongLineEntity(
                        id = "song name 1",
                        index = 0,
                        isChoirPart = false,
                        text = "not-matching text 1",
                        translation = "translation 1",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 2",
                        index = 1,
                        isChoirPart = true,
                        text = "not-matching text 2",
                        translation = "translation 2",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 3",
                        index = 2,
                        isChoirPart = false,
                        text = "right text",
                        translation = "translation 3",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 4",
                        index = 3,
                        isChoirPart = false,
                        text = "not-matching text 4",
                        translation = "translation 4",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 5",
                        index = 4,
                        isChoirPart = false,
                        text = "not-matching text 5",
                        translation = "translation 5",
                        translationChunks = mutableListOf(),
                        transcriptionChunks = mutableListOf(),
                        song = input
                    )
                )
            )
        }
        val expectedResult = SongInfoBySearchTextResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA",
                    songTags = SongTags(
                        tagsWithValues = emptyMap()
                    ),
                    songLines = listOf(
                        SongLine(
                            id = "song name 2",
                            index = 1,
                            isChoirPart = true,
                            text = "not-matching text 2",
                            translation = listOf(),
                            transcription = emptyList(),
                        ),
                        SongLine(
                            id = "song name 3",
                            index = 2,
                            isChoirPart = false,
                            text = "right text",
                            translation = listOf(),
                            transcription = emptyList(),
                        ),
                        SongLine(
                            id = "song name 4",
                            index = 3,
                            isChoirPart = false,
                            text = "not-matching text 4",
                            translation = listOf(),
                            transcription = emptyList(),
                        )
                    ),
                    optimalTransitions = emptyList()
                )
            )
        )

        val result = SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(listOf(input), "cmlnaHQ=")

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test response is properly mapped for song with matching translation in lines`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA",
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = emptyList(),
            isVisible = true
        )
        input.apply {
            songLines.addAll(
                listOf(
                    SongLineEntity(
                        id = "song name 1",
                        index = 0,
                        isChoirPart = false,
                        text = "not-matching text 1",
                        translation = "translation 1",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 2",
                        index = 1,
                        isChoirPart = true,
                        text = "not-matching text 2",
                        translation = "translation 2",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 3",
                        index = 2,
                        isChoirPart = false,
                        text = "not-matching text",
                        translation = "right translation 3",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 4",
                        index = 3,
                        isChoirPart = false,
                        text = "not-matching text 4",
                        translation = "translation 4",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 5",
                        index = 4,
                        isChoirPart = false,
                        text = "not-matching text 5",
                        translation = "translation 5",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    )
                )
            )
        }
        val expectedResult = SongInfoBySearchTextResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA",
                    songTags = SongTags(
                        tagsWithValues = emptyMap()
                    ),
                    optimalTransitions = emptyList(),
                    songLines = listOf(
                        SongLine(
                            id = "song name 2",
                            index = 1,
                            isChoirPart = true,
                            text = "not-matching text 2",
                            translation = emptyList(),
                            transcription = emptyList(),
                        ),
                        SongLine(
                            id = "song name 3",
                            index = 2,
                            isChoirPart = false,
                            text = "not-matching text",
                            translation = emptyList(),
                            transcription = emptyList(),
                        ),
                        SongLine(
                            id = "song name 4",
                            index = 3,
                            isChoirPart = false,
                            text = "not-matching text 4",
                            translation = emptyList(),
                            transcription = emptyList(),
                        )
                    )
                )
            )
        )

        val result = SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(listOf(input), "cmlnaHQ=")

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test response is properly mapped for song with no matching in lines`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA",
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = emptyList(),
            isVisible = true
        )
        input.apply {
            songLines.addAll(
                listOf(
                    SongLineEntity(
                        id = "song name 1",
                        index = 0,
                        isChoirPart = false,
                        text = "not-matching text 1",
                        translation = "translation 1",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 2",
                        index = 1,
                        isChoirPart = true,
                        text = "not-matching text 2",
                        translation = "translation 2",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 3",
                        index = 2,
                        isChoirPart = false,
                        text = "not-matching text",
                        translation = "translation 3",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 4",
                        index = 3,
                        isChoirPart = false,
                        text = "not-matching text 4",
                        translation = "translation 4",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 5",
                        index = 4,
                        isChoirPart = false,
                        text = "not-matching text 5",
                        translation = "translation 5",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    )
                )
            )
        }
        val expectedResult = SongInfoBySearchTextResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA",
                    songLines = emptyList(),
                    songTags = SongTags(
                        tagsWithValues = emptyMap()
                    ),
                    optimalTransitions = emptyList()
                )
            )
        )

        val result = SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(listOf(input), "cmlnaHQ=")

        assertEquals(expected = expectedResult, actual =  result)
    }

    @Test
    fun `test response is properly mapped for song with first or last matching in lines`() {
        val input = SongInfoEntity(
            id = "some id",
            name = "some name",
            normalizedName = "some name",
            videoUrl = "some url",
            songType = "LADAINHA",
            songLines = mutableListOf(),
            tagValues = mutableListOf(),
            optimalTransitions = emptyList(),
            isVisible = true
        )
        input.apply {
            songLines.addAll(
                listOf(
                    SongLineEntity(
                        id = "song name 1",
                        index = 0,
                        isChoirPart = false,
                        text = "not-matching text 1",
                        translation = "translation 1",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 2",
                        index = 1,
                        isChoirPart = true,
                        text = "not-matching text 2",
                        translation = "translation 2",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 3",
                        index = 2,
                        isChoirPart = false,
                        text = "not-matching text",
                        translation = "translation 3",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 4",
                        index = 3,
                        isChoirPart = false,
                        text = "not-matching text 4",
                        translation = "translation 4",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    ),
                    SongLineEntity(
                        id = "song name 5",
                        index = 4,
                        isChoirPart = false,
                        text = "right text 5",
                        translation = "translation 5",
                        transcriptionChunks = mutableListOf(),
                        translationChunks = mutableListOf(),
                        song = input
                    )
                )
            )
        }
        val expectedResult = SongInfoBySearchTextResponse(
            count = 1,
            songs = listOf(
                SongInfo(
                    id = "some id",
                    songName = "some name",
                    videoUrl = "some url",
                    songType = "LADAINHA",
                    songTags = SongTags(
                        tagsWithValues = emptyMap()
                    ),
                    optimalTransitions = emptyList(),
                    songLines = listOf(
                        SongLine(
                            id = "song name 4",
                            index = 3,
                            isChoirPart = false,
                            text = "not-matching text 4",
                            translation = emptyList(),
                            transcription = emptyList(),
                        ),
                        SongLine(
                            id = "song name 5",
                            index = 4,
                            isChoirPart = false,
                            text = "right text 5",
                            translation = emptyList(),
                            transcription = emptyList(),
                        )
                    )
                )
            )
        )

        val result = SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(listOf(input), "cmlnaHQ=")

        assertEquals(expected = expectedResult, actual =  result)
    }
}