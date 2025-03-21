package ru.cappoeira.songInfo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.scheduling.annotation.EnableScheduling

@EnableScheduling
@SpringBootApplication(scanBasePackages = ["ru.cappoeira.songInfo"])
open class SongInfoApplication

fun main(args: Array<String>) {
	runApplication<SongInfoApplication>(*args)
}
