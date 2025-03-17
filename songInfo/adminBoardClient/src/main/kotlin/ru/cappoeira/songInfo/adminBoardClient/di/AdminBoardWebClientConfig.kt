package ru.cappoeira.songInfo.adminBoardClient.di

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
open class AdminBoardWebClientConfig {

    @Bean
    open fun adminBoardWebClient() = WebClient.builder().build()
}