package ru.cappoeira.songInfo.adminBoardClient.dtos

data class AdminBoardSongLineDto(
    val index: Int,
    val text: String,
    val translation: String,
    val transcription: String,
    val isChoirPart: Boolean,
)