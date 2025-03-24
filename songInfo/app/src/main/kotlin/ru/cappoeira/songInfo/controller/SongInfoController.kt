package ru.cappoeira.songInfo.controller

import org.springframework.web.bind.annotation.*
import ru.cappoeira.songInfo.decodeFromBase64
import ru.cappoeira.songInfo.mapper.SongInfoResponseMapper
import ru.cappoeira.songInfo.response.SongInfoByIdResponse
import ru.cappoeira.songInfo.response.SongInfoBySearchTextResponse
import ru.cappoeira.songInfo.songInfoDB.service.SongInfoService

@RestController
@RequestMapping("/api")
class SongInfoController(
    private val service: SongInfoService
) {

    @GetMapping("/id/{id}")
    fun getSongById(@PathVariable id: String): SongInfoByIdResponse? {
        return service.getSongById(id)?.let { SongInfoResponseMapper.map(it) }
    }

    @GetMapping("/searchText/{searchText}")
    fun getSongsBySearchText(
        @PathVariable searchText: String,
        @RequestParam page: Int
    ): SongInfoBySearchTextResponse {
        return service.getSongsBySearchText(
            searchText = decodeFromBase64(searchText),
            page = page,
            size = SIZE
        ).let { SongInfoResponseMapper.map(it) }
    }

    companion object {
        private const val SIZE = 10
    }
}