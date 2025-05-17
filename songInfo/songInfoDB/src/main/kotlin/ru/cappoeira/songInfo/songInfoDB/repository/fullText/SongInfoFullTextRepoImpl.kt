package ru.cappoeira.songInfo.songInfoDB.repository.fullText

import jakarta.persistence.EntityManager
import org.hibernate.search.mapper.orm.Search
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

@Component
open class SongInfoFullTextRepoImpl(
    private val entityManager: EntityManager
): SongInfoFullTextRepo {

    @Transactional
    override fun getSongsBySearchText(
        searchText: String,
        songType: String,
        size: Int,
        page: Int
    ): List<SongInfoEntity> {
        val searchSession = Search.session(entityManager)
        return searchSession.search(SongInfoEntity::class.java)
            .where { f ->
                f.bool { b ->
                    b.must(f.match().fields(SongInfoEntity.NORMALIZED_NAME).matching(searchText))
                    b.must(f.match().fields(SongInfoEntity.SONG_TYPE).matching(songType))
                }
            }
            .fetchHits(size * page, size)
            .filterIsInstance<SongInfoEntity>()
    }
}