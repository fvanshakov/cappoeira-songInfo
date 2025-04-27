package ru.cappoeira.songInfo.adminBoardClient

import ru.cappoeira.songInfo.Response

interface UpdateDbDelegate {

    fun update(): Response
}