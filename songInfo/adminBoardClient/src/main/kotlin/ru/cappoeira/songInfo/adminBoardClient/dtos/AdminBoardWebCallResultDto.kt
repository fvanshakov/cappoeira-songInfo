package ru.cappoeira.songInfo.adminBoardClient.dtos

data class AdminBoardWebCallResultDto(
    val offset: String?,
    val records: List<AdminBoardSongInfoDto>
)