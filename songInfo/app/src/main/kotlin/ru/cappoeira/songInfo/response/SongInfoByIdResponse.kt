package ru.cappoeira.songInfo.response

import java.io.Serializable

data class SongInfoByIdResponse(
    val id: String,
    val songName: String,
    val videoUrl: String?,
    val songType: String,
    val songLines: List<SongLine>,
    val songTags: SongTags,
    val optimalTransitions: List<Transition>
): Serializable

data class SongLine(
    val id: String,
    val index: Int,
    val text: String,
    val translation: List<SongLinesChunks>,
    val transcription: String,
    val isChoirPart: Boolean
): Serializable

data class SongLinesChunks(
    val text: String,
    val definition: String?
)

data class SongTags(
    val tagsWithValues: Map<String, List<String>>
): Serializable