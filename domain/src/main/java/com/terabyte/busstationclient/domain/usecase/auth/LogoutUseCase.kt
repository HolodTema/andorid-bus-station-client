package com.terabyte.busstationclient.domain.usecase.auth

import com.terabyte.busstationclient.domain.repository.TokenRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke() {
        tokenRepository.deleteToken()
    }
}