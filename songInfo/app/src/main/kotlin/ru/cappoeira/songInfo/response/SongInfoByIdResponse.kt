package ru.cappoeira.songInfo.response

import java.io.Serializable

data class SongInfoByIdResponse(
    val id: String,
    val songName: String,
    val videoUrl: String?,
    val songType: String,
    val songLines: List<SongLine>
): Serializable

data class SongLine(
    val id: String,
    val index: Int,
    val text: String,
    val translation: String,
    val transcription: String,
    val isChoirPart: Boolean
): Serializable