package ru.cappoeira.songInfo.controller

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*
import ru.cappoeira.songInfo.decodeFromBase64
import ru.cappoeira.songInfo.mapper.SongInfoResponseMapper
import ru.cappoeira.songInfo.response.SongInfoAllSongsResponse
import ru.cappoeira.songInfo.response.SongInfoByIdResponse
import ru.cappoeira.songInfo.response.SongInfoBySearchTextResponse
import ru.cappoeira.songInfo.response.SongTagsResponse
import ru.cappoeira.songInfo.songInfoDB.service.FavoriteSongService
import ru.cappoeira.songInfo.songInfoDB.service.SongInfoService

@Tag(name = "songInfo", description = "API для получения информации по песням")
@RestController
@RequestMapping("/api")
class SongInfoController(
    private val songInfoService: SongInfoService,
    private val favoriteSongService: FavoriteSongService
) {

    @Operation(description = "Возвращает информацию по конкретной песне", summary = "Получение песни по id")
    @GetMapping("/id/{id}")
    fun getSongById(
        @Parameter(description = "Закодированное в base64 название песни")
        @PathVariable
        id: String,
        @Parameter(description = "Id пользователя")
        @RequestParam(required = false)
        userId: String?
    ): SongInfoByIdResponse? {
        return songInfoService.getSongById(id)?.let {
            SongInfoResponseMapper.mapToSongInfoByIdResponse(it)
        }
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
        @Parameter(description = "Тэги для песни")
        @RequestParam
        tags: String?,
        @Parameter(description = "Страница, используемая при пагинации (размер страницы 10 песен)")
        @RequestParam
        page: Int,
        @Parameter(description = "Id пользователя")
        @RequestParam(required = false)
        userId: String?
    ): SongInfoBySearchTextResponse {
        return songInfoService.getSongsBySearchText(
            searchText = decodeFromBase64(searchText),
            songType = songType,
            tags = tags?.let { jacksonObjectMapper().readValue(decodeFromBase64(it), List::class.java) as List<String> } ?: emptyList(),
            page = page,
            size = SIZE,
        ).let {
            val favouriteSongs = userId?.let(favoriteSongService::getFavoriteSongIds)
            SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(it, searchText, favouriteSongs)
        }
    }

    @Operation(description = "Возвращает песни, подходящие под поисковый запрос", summary = "Песни подходящие под запрос")
    @GetMapping("/searchText")
    fun getSongsBySearchText2(
        @Parameter(description = "Текст поискового запроса")
        @RequestParam
        searchText: String,
        @Parameter(description = "Тип песни")
        @RequestParam
        songType: String,
        @Parameter(description = "Тэги для песни")
        @RequestParam
        tags: String?,
        @Parameter(description = "Страница, используемая при пагинации (размер страницы 10 песен)")
        @RequestParam
        page: Int,
        @Parameter(description = "Id пользователя")
        @RequestParam(required = false)
        userId: String?
    ): SongInfoBySearchTextResponse {
        return songInfoService.getSongsBySearchText(
            searchText = decodeFromBase64(searchText),
            songType = songType,
            tags = tags?.let { jacksonObjectMapper().readValue(decodeFromBase64(it), List::class.java) as List<String> } ?: emptyList(),
            page = page,
            size = SIZE,
        ).let {
            val favouriteSongs = userId?.let(favoriteSongService::getFavoriteSongIds)
            SongInfoResponseMapper.mapToSongInfoBySearchTextResponse(it, searchText, favouriteSongs)
        }
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
        @Parameter(description = "Тэги для песни")
        @RequestParam
        tags: String?,
        @Parameter(description = "Id пользователя")
        @RequestParam(required = false)
        userId: String?
    ): SongInfoAllSongsResponse {
        return songInfoService.getAllSongs(
            songType = songType,
            page = page,
            size = SIZE,
            tags = tags?.let { jacksonObjectMapper().readValue(decodeFromBase64(it), List::class.java) as List<String> } ?: emptyList(),
        ).let {
            val favouriteSongs = userId?.let(favoriteSongService::getFavoriteSongIds)
            SongInfoResponseMapper.mapToSongInfoAllSongsResponse(it, favouriteSongs)
        }
    }

    @Operation(description = "Возвращает информацию по возможным тэгам", summary = "Получение всех вариантов тэгов")
    @GetMapping("/tags")
    fun getTags(
        @Parameter(description = "Тип песни по которому ищем тэги")
        @RequestParam
        filterType: String,
    ): SongTagsResponse {
        return songInfoService.getTags(filterType).let(SongInfoResponseMapper::mapSongTagsResponse)
    }

    companion object {
        private const val SIZE = 10
    }
}