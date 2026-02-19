package ru.cappoeira.songInfo.adminBoardClient.dtos

data class AdminBoardSongInfoDto(
    val songName: String,
    val videoUrl: String?,
    val songType: SongType,
    val songLines: List<AdminBoardSongLineDto>,
    val tags: AdminBoardTagsDao,
    val optimalTransitions: MutableList<String>,
    val isVisible: Boolean,
    val warning: String?,
    val id: String,

) {

    enum class SongType { CORRIDO, LADAINHA }
}