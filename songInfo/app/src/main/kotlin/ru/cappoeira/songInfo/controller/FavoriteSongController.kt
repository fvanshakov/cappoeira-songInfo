package ru.cappoeira.songInfo.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
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
}