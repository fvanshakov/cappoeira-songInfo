package ru.cappoeira.songInfo.songInfoDB.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.cappoeira.songInfo.songInfoDB.entity.FavoriteSongEntity

interface FavoriteSongRepo : JpaRepository<FavoriteSongEntity, String> {

    fun existsByUserIdAndSongId(userId: String, songId: String): Boolean

    fun findAllByUserId(userId: String): List<FavoriteSongEntity>

    fun deleteByUserIdAndSongId(userId: String, songId: String)
}