package ru.cappoeira.songInfo.response

import java.io.Serializable

data class SongTagsResponse(
    val tags: Map<String, List<String>>
): Serializable