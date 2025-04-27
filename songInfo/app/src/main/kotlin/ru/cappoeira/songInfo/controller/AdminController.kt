package ru.cappoeira.songInfo.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.cappoeira.songInfo.response.AdminControllerReindexingResponse
import ru.cappoeira.songInfo.safeCall
import ru.cappoeira.songInfo.songInfoDB.indexing.IndexingService

@Tag(name = "admin", description = "API для администрирования песен")
@RestController
@RequestMapping("/admin")
class AdminController(
    private val indexingService: IndexingService
) {

    @Operation(description = "Реиндексирует БД песен", summary = "Включение переиндексации")
    @PostMapping("/reindex")
    fun reindex(): AdminControllerReindexingResponse {
        return safeCall {
            indexingService.reindex()
        }.let { AdminControllerReindexingResponse(it) }
    }
}