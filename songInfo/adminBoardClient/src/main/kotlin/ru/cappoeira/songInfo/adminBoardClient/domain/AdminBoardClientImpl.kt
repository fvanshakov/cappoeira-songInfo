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
        var offset: String? = null
        val songInfos = mutableListOf<AdminBoardSongInfoDto>()

        val tableId = when(songType) {
            SongType.CORRIDO -> CORRIDOS_TABLE_ID
            SongType.LADAINHA -> LADAINHA_TABLE_ID
        }

        do {
            val uriBuilder = StringBuilder(AIRTABLE_URL).append(tableId)
            offset?.let { uriBuilder.append(OFFSET_QUERY).append(offset) }
            val result = client.get()
                .uri(uriBuilder.toString())
                .header(AUTHORIZATION_HEADER, configService.token)
                .retrieve()
                .bodyToMono(AdminBoardSongsWebCallResultDto::class.java)
                .doOnError { e ->
                    logger.error(e.message)
                }
                .block()
            result ?: break
            offset = result.offset
            songInfos.addAll(result.records)
        } while (offset != null)
        return songInfos
    }

    override fun retrieveDefinitions(): List<AdminBoardDefinitionsDto> {
        var offset: String? = null
        val definitions = mutableListOf<AdminBoardDefinitionsDto>()
        do {
            val uriBuilder = StringBuilder(AIRTABLE_URL).append(DEFINITIONS)
            offset?.let { uriBuilder.append(OFFSET_QUERY).append(offset) }
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
            offset = result.offset
            definitions.addAll(result.records)
        } while (offset != null)
        return definitions
    }

    companion object {
        const val OFFSET_QUERY = "?offset="

        private const val AUTHORIZATION_HEADER = "Authorization"

        const val CORRIDOS_TABLE_ID: String = "tblFMbr2a0A1l100R"
        const val LADAINHA_TABLE_ID: String = "tblGrqKosuW7rE9HW"
        const val DEFINITIONS: String = "tbl0P9JKi3jLt0yNY"
        const val AIRTABLE_URL = "https://api.airtable.com/v0/appt0ENQQQIbPyOD2/"
    }
}