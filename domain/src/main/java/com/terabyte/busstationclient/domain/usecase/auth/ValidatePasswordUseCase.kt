package com.terabyte.busstationclient.domain.usecase.auth

import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class ValidatePasswordUseCase @Inject constructor() {

    operator fun invoke(password: String): Boolean {
        if (password.length < 8) {
            return false
        }

        val alphabet = "abcdefghijkmnlopqrstuvwxyz"
        for (ch in password.lowercase()) {
            if (ch !in alphabet) {
                return false
            }
        }
        return true
    }
}