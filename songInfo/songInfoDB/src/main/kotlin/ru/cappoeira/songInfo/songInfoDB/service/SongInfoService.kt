package ru.cappoeira.songInfo.songInfoDB.service

import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.ListPagingAndSortingRepository
import org.springframework.stereotype.Service
import ru.cappoeira.songInfo.normalizeString
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity.Companion.NORMALIZED_NAME
import ru.cappoeira.songInfo.songInfoDB.repository.SongInfoRepo
import ru.cappoeira.songInfo.songInfoDB.repository.fullText.SongInfoFullTextRepo

@Service
class SongInfoService(
    private val repo: SongInfoRepo,
    private val fullTextRepo: SongInfoFullTextRepo
) {

    fun saveSongs(songs: List<SongInfoEntity>) {
        repo.saveAll(songs)
    }

    fun deleteAllSongs() {
        repo.deleteAll()
    }

    fun getSongById(id: String): SongInfoEntity? {
        return try {
            repo.getReferenceById(id)
        } catch (e: Exception) {
            null
        }
    }

    fun getAllSongs(
        page: Int,
        size: Int,
        songType: String
    ): List<SongInfoEntity> {
        return try {
            repo
                .findBySongType(songType, PageRequest.of(page, size, Sort.by(NORMALIZED_NAME).ascending()))
                .content
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getSongsBySearchText(
        searchText: String,
        page: Int,
        size: Int,
        songType: String
    ): List<SongInfoEntity> {

        return fullTextRepo.getSongsBySearchText(
            searchText = normalizeString(searchText),
            songType = songType,
            page = page,
            size = size
        )
    }
}