package ru.cappoeira.songInfo.songInfoDB.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagEntity

@Repository
interface SongTagValueRepository : JpaRepository<SongTagEntity, String> {

    @Query("""
        SELECT DISTINCT t FROM SongTagEntity t
        LEFT JOIN FETCH t.tagValues
        ORDER BY t.tag
    """)
    fun findAllWithTags(): List<SongTagEntity>
}