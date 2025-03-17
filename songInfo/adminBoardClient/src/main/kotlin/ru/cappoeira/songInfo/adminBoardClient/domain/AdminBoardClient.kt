package ru.cappoeira.songInfo.adminBoardClient.domain

import ru.cappoeira.songInfo.adminBoardClient.dtos.AdminBoardSongInfoDto

interface AdminBoardClient {

    fun retrieveSongTypeInfoFromAdminBoard(songType: SongType): List<AdminBoardSongInfoDto>

    enum class SongType { CORRIDO, LADAINHA }
}