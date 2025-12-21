package ru.cappoeira.songInfo.songInfoDB.repository

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import ru.cappoeira.songInfo.songInfoDB.entity.FavoriteSongEntity
import ru.cappoeira.songInfo.songInfoDB.entity.FavoriteSongId

interface FavoriteSongRepo : JpaRepository<FavoriteSongEntity, FavoriteSongId> {

    fun existsByIdUserIdAndIdSongId(
        userId: String,
        songId: String
    ): Boolean

    fun findAllByIdUserId(
        userId: String
    ): List<FavoriteSongEntity>

    fun deleteByIdUserIdAndIdSongId(
        userId: String,
        songId: String
    )

    fun findAllByIdUserId(
        userId: String,
        pageable: Pageable
    ): Page<FavoriteSongEntity>
}