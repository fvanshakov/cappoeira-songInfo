package ru.cappoeira.songInfo.response

import java.io.Serializable

data class SongInfoAllSongsResponse(
    val count: Int,
    val songs: List<SongInfo>
): Serializable
