package ru.cappoeira.songInfo.songInfoDB.repository

import org.springframework.data.jpa.repository.JpaRepository
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagValueEntity

interface SongTagsRepo : JpaRepository<SongTagValueEntity?, String>