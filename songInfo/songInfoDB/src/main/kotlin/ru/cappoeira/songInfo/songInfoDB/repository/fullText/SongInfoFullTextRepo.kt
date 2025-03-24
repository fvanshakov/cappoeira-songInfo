package ru.cappoeira.songInfo.songInfoDB.repository.fullText

import ru.cappoeira.songInfo.songInfoDB.entity.SongInfoEntity

interface SongInfoFullTextRepo {

    fun getSongsBySearchText(
        searchText: String,
        size: Int,
        page: Int
    ): List<SongInfoEntity>

    fun forceIndex()
}