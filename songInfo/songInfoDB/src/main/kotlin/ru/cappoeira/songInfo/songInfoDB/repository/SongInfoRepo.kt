package ru.cappoeira.songInfo.songInfoDB.repository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

interface SongInfoRepo : JpaRepository<SongInfoEntity?, String> {

    fun findByNormalizedName(normalizedName: String): SongInfoEntity?

    @Query("SELECT s FROM SongInfoEntity s WHERE s.songType = :songType AND s.isVisible = true")
    fun findBySongTypeAndIsVisible(
        @Param("songType") songType: String,
        pageable: Pageable
    ): Page<SongInfoEntity>

    @Query("""
    SELECT s FROM SongInfoEntity s
    JOIN s.tagValues tv
    WHERE s.songType = :songType
    AND tv.tagValue IN :tagValues
    AND s.isVisible = true
    GROUP BY s
    HAVING COUNT(DISTINCT tv.tagValue) = :tagCount
    """)
    fun findBySongTypeAndAllTags(
        @Param("songType") songType: String,
        @Param("tagValues") tagValues: Set<String>,
        @Param("tagCount") tagCount: Long = tagValues.size.toLong(),
        pageable: Pageable
    ): Page<SongInfoEntity>
}