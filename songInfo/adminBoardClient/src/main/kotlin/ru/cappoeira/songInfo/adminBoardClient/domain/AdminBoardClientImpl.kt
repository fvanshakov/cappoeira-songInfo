package ru.cappoeira.songInfo.adminBoardClient.domain

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import ru.cappoeira.songInfo.adminBoardClient.di.WebClientConfigService
import ru.cappoeira.songInfo.adminBoardClient.domain.AdminBoardClient.SongType
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardDefinitionsDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardDefinitionsWebCallResultDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto
import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongsWebCallResultDto

@Component
internal class AdminBoardClientImpl(
    private val client: WebClient,
    private val configService: WebClientConfigService
): AdminBoardClient {

    private val logger = LoggerFactory.getLogger(AdminBoardClient::class.java)

    override fun retrieveSongTypeInfoFromAdminBoard(songType: SongType): List<AdminBoardSongInfoDto> {
        var offset: Int = 0
        val songInfos = mutableListOf<AdminBoardSongInfoDto>()

        val tableId = when(songType) {
            SongType.CORRIDO -> CORRIDOS_TABLE_ID
            SongType.LADAINHA -> LADAINHA_TABLE_ID
        }

        var lastBatchSize = 20
        do {
            val uriBuilder = StringBuilder(AIRTABLE_URL).append(tableId)
            uriBuilder
                .append(OFFSET_QUERY)
                .append(offset)
                .append(LIMIT_QUERY)
                .append(20)
            val result = try {
                client.get()
                    .uri(uriBuilder.toString())
                    .header(AUTHORIZATION_HEADER, configService.token)
                    .retrieve()
                    .bodyToMono(AdminBoardSongsWebCallResultDto::class.java)
                    .block()
            } catch (e: Exception) {
                logger.error("Error fetching songs at offset $offset: ${e.message}")
                null
            }
            result ?: break
            result.mapRecords()
            lastBatchSize = result.list.size
            songInfos.addAll(result.records)
            offset = songInfos.size
        } while (lastBatchSize == 20)
        return songInfos
    }

    override fun retrieveDefinitions(): List<AdminBoardDefinitionsDto> {
        var offset = 0
        val definitions = mutableListOf<AdminBoardDefinitionsDto>()
        do {
            val uriBuilder = StringBuilder(AIRTABLE_URL).append(DEFINITIONS)
            uriBuilder
                .append(OFFSET_QUERY)
                .append(offset)
                .append(LIMIT_QUERY)
                .append(20)
            val result = client.get()
                .uri(uriBuilder.toString())
                .header(AUTHORIZATION_HEADER, configService.token)
                .retrieve()
                .bodyToMono(AdminBoardDefinitionsWebCallResultDto::class.java)
                .doOnError { e ->
                    logger.error(e.message)
                }
                .block()
            result ?: break
            definitions.addAll(result.list)
            offset = definitions.size
        } while (offset % 20 == 0)
        return definitions
    }

    companion object {
        const val OFFSET_QUERY = "?offset="
        const val LIMIT_QUERY = "&limit="

        private const val AUTHORIZATION_HEADER = "xc-token"

        const val CORRIDOS_TABLE_ID: String = "md4sc6j7tq7le1r"
        const val LADAINHA_TABLE_ID: String = "mgjo8frspaimktv"
        const val DEFINITIONS: String = "mnxqwg7kohwwg0s"
        const val AIRTABLE_URL = "http://cappoeira.ru:8080/api/v1/db/data/v1/p5wgzcu5eawij7x/"
    }
}