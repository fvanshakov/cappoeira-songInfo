package ru.cappoeira.songInfo.songInfoDB.repository.fullText

import jakarta.persistence.EntityManager
import org.hibernate.search.mapper.orm.Search
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongLineEntity.Companion.TEXT
import ru.cappoeira.songInfo.songInfoDB.nGram.NgramLuceneAnalysisConfigurer.Companion.NGRAM_NAME

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
                    b.must(
                        f.bool { b1 ->
                            b1.should(
                                f.match().fields(SongInfoEntity.NORMALIZED_NAME).matching(searchText).analyzer(NGRAM_NAME)
                            )
                            b1.should(
                                f.nested()
                                    .objectField("songLines")
                                    .nest(
                                        f.phrase()
                                            .field("songLines.$TEXT")
                                            .matching(searchText)
                                            .analyzer(NGRAM_NAME)
                                    )
                            )
                        }
                    )
                    b.must(f.match().fields(SongInfoEntity.SONG_TYPE).matching(songType))
                }
            }
            .fetchHits(size * page, size)
            .filterIsInstance<SongInfoEntity>()
    }
}