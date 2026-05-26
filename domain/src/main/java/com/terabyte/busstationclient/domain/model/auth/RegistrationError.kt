package com.terabyte.busstationclient.domain.model.auth

sealed class RegistrationError : Throwable() {
    class UsernameBusy : RegistrationError()
    class UnknownError : RegistrationError()
}