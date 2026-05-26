package com.terabyte.busstationclient.data.storage.remote.model

sealed class RequestError : Throwable() {

    class UnknownError : RequestError()

    class TokenExpiredError : RequestError()
}