package ru.cappoeira.songInfo.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.cappoeira.songInfo.decodeFromBase64
import ru.cappoeira.songInfo.mapper.SongInfoResponseMapper
import ru.cappoeira.songInfo.response.SongInfoAllSongsResponse
import ru.cappoeira.songInfo.response.SongInfoByIdResponse
import ru.cappoeira.songInfo.response.SongInfoBySearchTextResponse
import ru.cappoeira.songInfo.songInfoDB.service.SongInfoService

@Tag(name = "songInfo", description = "API для получения информации по песням")
@RestController
@RequestMapping("/api")
class SongInfoController(
    private val service: SongInfoService
) {

    @Operation(description = "Возвращает информацию по конкретной песне", summary = "Получение песни по id")
    @GetMapping("/id/{id}")
    fun getSongById(
        @Parameter(description = "Закодированное в base64 название песни")
        @PathVariable id: String
    ): SongInfoByIdResponse? {
        return service.getSongById(id)?.let { SongInfoResponseMapper.mapToSongInfoByIdResponse(it) }
    }

    @Operation(description = "Возвращает песни, подходящие под поисковый запрос", summary = "Песни подходящие под запрос")
    @GetMapping("/searchText/{searchText}")
    fun getSongsBySearchText(
        @Parameter(description = "Текст поискового запроса")
        @PathVariable
        searchText: String,
        @Parameter(description = "Тип песни")
        @RequestParam
        songType: String,
        @Parameter(description = "Страница, используемая при пагинации (размер страницы 10 песен)")
        @RequestParam
        page: Int,
    ): SongInfoBySearchTextResponse {
        return service.getSongsBySearchText(
            searchText = decodeFromBase64(searchText),
            songType = songType,
            page = page,
            size = SIZE,
        ).let { SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(it, searchText) }
    }

    @Operation(description = "Возвращает информацию по всем песням", summary = "Получение всех песен")
    @GetMapping("/allSongs")
    fun getAllSongsInfos(
        @Parameter(description = "Страница, используемая при пагинации (размер страницы 10 песен)")
        @RequestParam
        page: Int,
        @Parameter(description = "Тип песни")
        @RequestParam
        songType: String,
    ): SongInfoAllSongsResponse {
        return service.getAllSongs(
            songType = songType,
            page = page,
            size = SIZE
        ).let (SongInfoResponseMapper::mapToSongInfoAllSongsResponse)
    }

    companion object {
        private const val SIZE = 10
    }
}