package ru.cappoeira.songInfo.songInfoDB.service

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.repository.SongInfoRepo
import ru.cappoeira.songInfo.songInfoDB.repository.fullText.SongInfoFullTextRepo

@Service
class SongInfoService(
    private val repo: SongInfoRepo,
    private val fullTextRepo: SongInfoFullTextRepo
) {

    @PostConstruct
    fun rebuildIndex() {
        fullTextRepo.forceIndex()
    }

    fun saveSongs(songs: List<SongInfoEntity>) {
        repo.saveAll(songs)
    }

    fun getSongByName(name: String): SongInfoEntity? {
        return repo.getReferenceById(name)
    }

    fun getSongsBySearchText(
        searchText: String,
        page: Int,
        size: Int
    ): List<SongInfoEntity> {
        return fullTextRepo.getSongsBySearchText(
            searchText = searchText,
            page = page,
            size = size
        )
    }
}