package ru.cappoeira.songInfo.adminBoardClient.di

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

@Component
class WebClientConfigService(
    @Value("\${webclient.token}") private val _token: String,
) {
    val token
        get() = _token
}