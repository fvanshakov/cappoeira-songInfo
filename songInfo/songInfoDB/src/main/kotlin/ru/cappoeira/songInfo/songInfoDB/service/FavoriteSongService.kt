package ru.cappoeira.songInfo.songInfoDB.service

import org.springframework.stereotype.Service
import ru.cappoeira.songInfo.songInfoDB.entity.FavoriteSongEntity
import ru.cappoeira.songInfo.songInfoDB.repository.FavoriteSongRepo
import ru.cappoeira.songInfo.songInfoDB.repository.SongInfoRepo

@Service
class FavoriteSongService(
    private val repo: FavoriteSongRepo,
    private val songRepo: SongInfoRepo
) {

    fun addToFavorite(userId: String, songId: String): Boolean {
        val song = songRepo.findById(songId).orElse(null) ?: return false

        val exists = repo.existsByUserIdAndSongId(userId, songId)
        if (exists) return true

        repo.save(
            FavoriteSongEntity(
                userId = userId,
                song = song
            )
        )

        return true
    }

    fun getFavoriteSongIds(userId: String): Set<String> {
        return repo.findAllByUserId(userId)
            .map { it.song.id }
            .toSet()
    }

    fun removeFromFavorite(userId: String, songId: String): Boolean {
        val exists = repo.existsByUserIdAndSongId(userId, songId)
        if (!exists) return false

        repo.deleteByUserIdAndSongId(userId, songId)
        return true
    }

    fun isFavorite(userId: String, songId: String): Boolean {
        return repo.existsByUserIdAndSongId(userId, songId)
    }
}
