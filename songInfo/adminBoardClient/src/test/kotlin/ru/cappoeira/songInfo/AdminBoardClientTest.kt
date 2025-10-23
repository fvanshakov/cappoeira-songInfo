package ru.cappoeira.songInfo

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.WebClient.ResponseSpec
import reactor.core.publisher.Mono
import ru.cappoeira.songInfo.adminBoardClient.di.WebClientConfigService
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClientImpl
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClientImpl.Companion.AIRTABLE_URL
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClientImpl.Companion.CORRIDOS_TABLE_ID
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClientImpl.Companion.OFFSET_QUERY
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardTagsDao
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongsWebCallResultDto
import kotlin.test.DefaultAsserter.assertEquals

class AdminBoardClientTest {

    private val webClientConfigService = WebClientConfigService("rndmtkn")
    private val clientMock = mockk<WebClient>()
    private val requestMock = mockk<WebClient.RequestHeadersUriSpec<*>>()
    private val requestMockWithoutOffset = mockk<WebClient.RequestHeadersUriSpec<*>>()
    private val responseMock = mockk<ResponseSpec>()
    private val responseMockWithoutOffset = mockk<ResponseSpec>()
    private val adminBoardClient: AdminBoardClient = AdminBoardClientImpl(
        clientMock,
        webClientConfigService
    )
    private val firstSong = AdminBoardSongInfoDto(
        songName = "first song",
        videoUrl = "some url",
        songType = AdminBoardSongInfoDto.SongType.LADAINHA,
        songLines = emptyList(),
        tags = AdminBoardTagsDao(
            tagsWithKeys = emptyMap()
        ),
        optimalTransitions = emptyList(),
        isVisible = true,
        isWithAlerts = false
    )
    private val secondSong = AdminBoardSongInfoDto(
        "second song",
        "some other url",
        AdminBoardSongInfoDto.SongType.LADAINHA,
        songLines = emptyList(),
        tags = AdminBoardTagsDao(
            tagsWithKeys = emptyMap()
        ),
        optimalTransitions = emptyList(),
        isVisible = true,
        isWithAlerts = false

    )
    private val offset = "offset"

    @BeforeEach
    fun setup() {
        every { clientMock.get() } returns requestMock
        every { requestMock.header(any(), any()) } returns requestMock
        every { requestMockWithoutOffset.header(any(), any()) } returns requestMockWithoutOffset
        every { requestMock.uri(AIRTABLE_URL + CORRIDOS_TABLE_ID) } returns requestMock
        every { requestMock.uri(AIRTABLE_URL + CORRIDOS_TABLE_ID + OFFSET_QUERY + offset) } returns requestMockWithoutOffset
        every { requestMockWithoutOffset.retrieve() } returns responseMockWithoutOffset
        every { requestMock.retrieve() } returns responseMock
        every { responseMockWithoutOffset.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) } returns Mono.just(
            AdminBoardSongsWebCallResultDto(
                offset = null,
                records = listOf(
                    secondSong
                )
            )
        )
    }

    @Test
    fun `when result is successful and offset is empty return unappended result without additional call`() {
        val expectedResult = listOf(
            firstSong
        )
        every { responseMock.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) } returns Mono.just(
            AdminBoardSongsWebCallResultDto(
                offset = null,
                records = listOf(
                    firstSong
                )
            )
        )

        val result = adminBoardClient.retrieveSongTypeInfoFromAdminBoard(AdminBoardClient.SongType.CORRIDO)

        assertEquals(actual = result, expected = expectedResult, message = "admin board client call result equals expected result")

        verify(exactly = 1) { clientMock.get() }
        verify(exactly = 1) { requestMock.uri(any<String>()) }
        verify(exactly = 1) { requestMock.retrieve() }
        verify(exactly = 1) { responseMock.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) }
    }

    @Test
    fun `when result is successful and offset is not empty return appended result with additional call`() {
        val expectedResult = listOf(
            firstSong, secondSong
        )
        every { responseMock.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) } returns Mono.just(
            AdminBoardSongsWebCallResultDto(
                offset = offset,
                records = listOf(
                    firstSong
                )
            )
        )

        val result = adminBoardClient.retrieveSongTypeInfoFromAdminBoard(AdminBoardClient.SongType.CORRIDO)

        assertEquals(actual = result, expected = expectedResult, message = "admin board client call result equals expected result")

        verify(exactly = 2) { clientMock.get() }
        verify(exactly = 2) { requestMock.uri(any<String>()) }
        verify(exactly = 1) { requestMock.retrieve() }
        verify(exactly = 1) { responseMock.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) }
        verify(exactly = 1) { requestMockWithoutOffset.retrieve() }
        verify(exactly = 1) { responseMockWithoutOffset.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) }
    }

    @Test
    fun `when result is successful and empty and offset is empty return empty result without additional call`() {
        val expectedResult = emptyList<AdminBoardSongsWebCallResultDto>()
        every { responseMock.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) } returns Mono.just(
            AdminBoardSongsWebCallResultDto(
                offset = null,
                records = emptyList()
            )
        )

        val result = adminBoardClient.retrieveSongTypeInfoFromAdminBoard(AdminBoardClient.SongType.CORRIDO)

        assertEquals(actual = result, expected = expectedResult, message = "admin board client call result equals expected result")

        verify(exactly = 1) { clientMock.get() }
        verify(exactly = 1) { requestMock.uri(any<String>()) }
        verify(exactly = 1) { requestMock.retrieve() }
        verify(exactly = 1) { responseMock.bodyToMono(AdminBoardSongsWebCallResultDto::class.java) }
    }
}