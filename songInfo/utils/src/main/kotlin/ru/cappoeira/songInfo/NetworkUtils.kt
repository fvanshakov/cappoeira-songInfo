package ru.cappoeira.songInfo

import java.io.Serializable

fun safeCall(resultMessage: String = "ok", call:() -> Unit): Response {
    return try {
        call()
        Response.Result(resultMessage)
    } catch (ex: Exception) {
        Response.Error(ex.message.orEmpty())
    }
}

sealed class Response: Serializable {
    class Result(val message: String): Response()
    class Error(val errorMessage: String): Response()
}