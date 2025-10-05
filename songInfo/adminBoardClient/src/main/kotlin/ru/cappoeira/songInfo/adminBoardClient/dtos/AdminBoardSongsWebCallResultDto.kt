package ru.cappoeira.songInfo.adminBoardClient.dtos

data class AdminBoardSongsWebCallResultDto(
    val offset: String?,
    val records: List<AdminBoardSongInfoDto>
)