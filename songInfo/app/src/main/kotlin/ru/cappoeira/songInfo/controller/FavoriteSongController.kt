package ru.cappoeira.songInfo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import ru.cappoeira.songInfo.response.FavoriteSongItem
import ru.cappoeira.songInfo.response.FavoriteSongsResponse
import ru.cappoeira.songInfo.songInfoDB.service.FavoriteSongService

@RestController
@RequestMapping("/api")
class FavoriteSongController(
    private val favoriteService: FavoriteSongService
) {

    data class FavoriteRequest(
        val userId: String,
        val songId: String
    )

    @PostMapping("/favorite")
    fun addToFavorite(@RequestBody request: FavoriteRequest): ResponseEntity<Void> {
        val ok = favoriteService.addToFavorite(request.userId, request.songId)
        return if (ok) ResponseEntity.ok().build()
        else ResponseEntity.badRequest().build()
    }

    @DeleteMapping("/favorite")
    fun removeFromFavorite(@RequestBody request: FavoriteRequest): ResponseEntity<Void> {
        val ok = favoriteService.removeFromFavorite(request.userId, request.songId)
        return if (ok) ResponseEntity.ok().build()
        else ResponseEntity.notFound().build()
    }

    @GetMapping("/favorite")
    fun getFavoriteSongs(
        @RequestParam userId: String,
        @RequestParam page: Int,
        @RequestParam(defaultValue = "newest") sortType: String
    ): FavoriteSongsResponse {

        val pageResult = favoriteService.getFavoriteSongs(
            userId = userId,
            page = page,
            size = 10,
            sortType = sortType
        )

        return FavoriteSongsResponse(
            songs = pageResult.content.map {
                FavoriteSongItem(
                    id = it.song.id,
                    name = it.song.name,
                    addedAt = it.addedAt
                )
            },
            page = page,
            totalPages = pageResult.totalPages
        )
    }
}