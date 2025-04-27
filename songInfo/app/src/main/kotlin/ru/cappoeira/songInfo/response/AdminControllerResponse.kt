package ru.cappoeira.songInfo.response

import ru.cappoeira.songInfo.Response
import java.io.Serializable

data class AdminControllerResponse(
    val response: Response
): Serializable
