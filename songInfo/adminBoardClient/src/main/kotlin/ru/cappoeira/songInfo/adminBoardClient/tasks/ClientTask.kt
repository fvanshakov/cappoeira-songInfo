package ru.cappoeira.songInfo.adminBoardClient.tasks

import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import ru.cappoeira.songInfo.adminBoardClient.UpdateDbDelegate

@Component
class ClientTask(
    private val updateDbDelegate: UpdateDbDelegate
) {

    @Scheduled(cron = "0 0 21 * * *")
    fun updateSongInfo() {
        updateDbDelegate.update()
    }
}