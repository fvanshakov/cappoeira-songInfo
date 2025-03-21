package ru.cappoeira.songInfo.songInfoDB.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

interface SongInfoRepo : JpaRepository<SongInfoEntity?, String> {

    fun findByName(name: String): SongInfoEntity?
}