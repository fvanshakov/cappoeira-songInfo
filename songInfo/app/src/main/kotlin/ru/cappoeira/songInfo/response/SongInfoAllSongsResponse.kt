package ru.cappoeira.songInfo.response

data class SongInfoAllSongsResponse(
    val count: Int,
    val songs: List<SongInfo>
)
