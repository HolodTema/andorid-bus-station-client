package com.terabyte.busstationclient.domain.model.auth

sealed class RegistrationError : Throwable() {
    class EmailBusy : RegistrationError()
    class UnknownError : RegistrationError()
}