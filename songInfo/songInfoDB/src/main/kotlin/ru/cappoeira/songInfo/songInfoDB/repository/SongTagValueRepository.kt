package ru.cappoeira.songInfo.songInfoDB.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagEntity

@Repository
interface SongTagValueRepository : JpaRepository<SongTagEntity, String> {

    @Query("""
        SELECT DISTINCT t FROM SongTagEntity t
        LEFT JOIN FETCH t.tagValues tv
        JOIN tv.song s
        WHERE t.type = :filterType 
        AND s.isVisible = true
        ORDER BY t.tag
    """)
    fun findAllWithTags(@Param("filterType") filterType: String): List<SongTagEntity>
}