package ru.cappoeira.songInfo.adminBoardClient.dtos

data class AdminBoardTagsDao(
    val tagsWithKeys: Map<String, AdminBoardTagDao>
)

data class AdminBoardTagDao(
    val tagValues: List<String>,
    val isPlural: Boolean
)