package ru.cappoeira.songInfo.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.cappoeira.songInfo.response.SongInfoAllSongsResponse

@Tag(name = "admin", description = "API для администрирования песен")
@RestController
@RequestMapping("/admin")
class AdminController {

    @Operation(description = "Реиндексирует БД песен", summary = "Включение переиндексации")
    @PostMapping("/reindex")
    fun reindex(): SongInfoAllSongsResponse? {

        return null
    }
}