package ru.cappoeira.songInfo.response

import java.io.Serializable

data class SongInfoByIdResponse(
    val id: String,
    val songName: String,
    val videoUrl: String?,
    val songType: String
): Serializable