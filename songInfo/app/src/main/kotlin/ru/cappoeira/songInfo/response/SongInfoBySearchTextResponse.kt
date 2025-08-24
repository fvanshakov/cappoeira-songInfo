package ru.cappoeira.songInfo.response

import java.io.Serializable

data class SongInfoBySearchTextResponse(
    val count: Int,
    val songs: List<SongInfo>
): Serializable

data class SongInfo(
    val id: String,
    val songName: String,
    val videoUrl: String?,
    val songType: String,
    val songLines: List<SongLine>,
    val songTags: SongTags,
    val optimalTransitions: List<Transition>
): Serializable

data class Transition(
    val songName: String,
    val songId: String
)