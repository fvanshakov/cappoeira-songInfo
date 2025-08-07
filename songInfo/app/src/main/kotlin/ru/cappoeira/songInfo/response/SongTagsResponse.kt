package ru.cappoeira.songInfo.response

import java.io.Serializable

data class SongTagsResponse(
    val tags: Map<String, SongTag>
): Serializable

data class SongTag(
    val values: List<String>,
    val isPlural: Boolean
)