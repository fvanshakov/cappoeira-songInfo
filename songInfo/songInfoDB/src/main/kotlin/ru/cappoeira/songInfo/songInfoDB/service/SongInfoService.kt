package ru.cappoeira.songInfo.songInfoDB.service

import jakarta.transaction.Transactional
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import ru.cappoeira.songInfo.normalizeString
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity
import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity.Companion.NORMALIZED_NAME
import ru.cappoeira.songInfo.songInfoDB.entity.SongTagEntity
import ru.cappoeira.songInfo.songInfoDB.repository.SongInfoRepo
import ru.cappoeira.songInfo.songInfoDB.repository.SongTagValueRepository
import ru.cappoeira.songInfo.songInfoDB.repository.SongTagsRepo
import ru.cappoeira.songInfo.songInfoDB.repository.fullText.SongInfoFullTextRepo

@Service
open class SongInfoService(
    private val repo: SongInfoRepo,
    private val tagsRepo: SongTagsRepo,
    private val tagsValueRepo: SongTagValueRepository,
    private val fullTextRepo: SongInfoFullTextRepo
) {

    open fun saveSongs(
        songs: List<SongInfoEntity>
    ) {
        repo.saveAll(songs)
    }

    open fun saveTags(tags: List<SongTagEntity>) {
        tagsRepo.saveAll(tags)
    }

    @Transactional
    open fun deleteAllSongs() {
        tagsValueRepo.deleteAllInBatch()
        tagsRepo.deleteAllInBatch()
        repo.deleteAllInBatch()
    }

    open fun getSongById(id: String): SongInfoEntity? {
        return try {
            repo.getReferenceById(id)
        } catch (e: Exception) {
            null
        }
    }

    open fun getAllSongs(
        page: Int,
        size: Int,
        songType: String,
        tags: List<String>
    ): List<SongInfoEntity> {
        return try {
            if (tags.isEmpty()) {
                val res = repo
                    .findBySongTypeAndIsVisible(songType, PageRequest.of(page, size, Sort.by(NORMALIZED_NAME).ascending()))
                res.content
            } else {
                val res = repo
                    .findBySongTypeAndAllTags(
                        songType = songType,
                        pageable = PageRequest.of(page, size, Sort.by(NORMALIZED_NAME).ascending()),
                        tagValues = tags.toSet(),
                    )
                res.content
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    open fun getSongsBySearchText(
        searchText: String,
        page: Int,
        tags: List<String>,
        size: Int,
        songType: String
    ): List<SongInfoEntity> {

        return fullTextRepo.getSongsBySearchText(
            searchText = normalizeString(searchText),
            songType = songType,
            page = page,
            tags = tags,
            size = size
        )
    }

    open fun getTags(filterType: String): List<SongTagEntity> {
        return try {
            tagsValueRepo
                .findAllWithTags(filterType)
        } catch (e: Exception) {
            emptyList()
        }
    }
}