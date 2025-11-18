package ru.cappoeira.songInfo.response

data class FavoriteSongsResponse(
    val songs: List<FavoriteSongItem>,
    val page: Int,
    val totalPages: Int
)

data class FavoriteSongItem(
    val id: String,
    val name: String,
    val addedAt: Long
)