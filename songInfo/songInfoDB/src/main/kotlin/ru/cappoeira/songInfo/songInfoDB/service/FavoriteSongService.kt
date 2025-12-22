package ru.cappoeira.songInfo.songInfoDB.service

import jakarta.transaction.Transactional
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.cappoeira.songInfo.songInfoDB.entity.FavoriteSongEntity
import ru.cappoeira.songInfo.songInfoDB.entity.FavoriteSongId
import ru.cappoeira.songInfo.songInfoDB.repository.FavoriteSongRepo
import ru.cappoeira.songInfo.songInfoDB.repository.SongInfoRepo

@Service
open class FavoriteSongService(
    private val repo: FavoriteSongRepo,
    private val songRepo: SongInfoRepo
) {

    @Transactional
    open fun addToFavorite(userId: String, songId: String): Boolean {
        val song = songRepo.findById(songId).orElse(null) ?: return false

        val exists = repo.existsByIdUserIdAndIdSongId(userId, songId)
        if (exists) return true

        repo.save(
            FavoriteSongEntity(
                id = FavoriteSongId(
                    userId = userId,
                    songId = song.id
                ),
                song = song
            )
        )

        return true
    }

    open fun getFavoriteSongIds(userId: String): Set<String> {
        return repo.findAllByIdUserId(userId)
            .map { it.song.id }
            .toSet()
    }

    @Transactional
    open fun removeFromFavorite(userId: String, songId: String): Boolean {
        val exists = repo.existsByIdUserIdAndIdSongId(userId, songId)
        if (!exists) return false

        repo.deleteByIdUserIdAndIdSongId(userId, songId)
        return true
    }

    open fun isFavorite(userId: String, songId: String): Boolean {
        return repo.existsByIdUserIdAndIdSongId(userId, songId)
    }

    open fun getFavoriteSongs(
        userId: String,
        page: Int,
        size: Int,
        sortType: String
    ): Page<FavoriteSongEntity> {

        val sort = when (sortType) {
            "oldest" -> Sort.by("addedAt").ascending()
            else -> Sort.by("addedAt").descending()
        }

        val pageable = PageRequest.of(page, size, sort)

        return repo.findAllByIdUserId(userId, pageable)
    }
}
