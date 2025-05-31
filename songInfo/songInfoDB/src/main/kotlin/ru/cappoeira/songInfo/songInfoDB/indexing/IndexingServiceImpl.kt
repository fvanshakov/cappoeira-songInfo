package ru.cappoeira.songInfo.songInfoDB.indexing

import jakarta.persistence.EntityManager
import org.hibernate.search.mapper.orm.Search
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
open class IndexingServiceImpl(
    private val entityManager: EntityManager
) : IndexingService {

    @Transactional
    override fun reindex() {
        val session = entityManager.unwrap(org.hibernate.Session::class.java)
        val searchSession = Search.session(session)

        searchSession
            .massIndexer()
            .startAndWait()
    }
}
