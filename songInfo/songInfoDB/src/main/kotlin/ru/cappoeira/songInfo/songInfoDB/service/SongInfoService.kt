package ru.cappoeira.songInfo.songInfoDB.service

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
class SongInfoService(
    private val repo: SongInfoRepo,
    private val tagsRepo: SongTagsRepo,
    private val tagsValueRepo: SongTagValueRepository,
    private val fullTextRepo: SongInfoFullTextRepo
) {

    fun saveSongs(
        songs: List<SongInfoEntity>
    ) {
        repo.saveAll(songs)
    }

    fun saveTags(tags: List<SongTagEntity>) {
        tagsRepo.saveAll(tags)
    }

    fun deleteAllSongs() {
        repo.deleteAll(repo.findAll())
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
        songType: String,
        tags: List<String>
    ): List<SongInfoEntity> {
        return try {
            if (tags.isEmpty()) {
                repo
                    .findBySongTypeAndIsVisible(songType, PageRequest.of(page, size, Sort.by(NORMALIZED_NAME).ascending()))
                    .content
            } else {
                repo
                    .findBySongTypeAndAllTags(
                        songType = songType,
                        pageable = PageRequest.of(page, size, Sort.by(NORMALIZED_NAME).ascending()),
                        tagValues = tags.toSet(),
                    )
                    .content
            }
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun getSongsBySearchText(
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

    fun getTags(filterType: String): List<SongTagEntity> {
        return try {
            tagsValueRepo
                .findAllWithTags(filterType)
        } catch (e: Exception) {
            emptyList()
        }
    }
}