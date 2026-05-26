package com.terabyte.busstationclient.domain.usecase.auth

import com.terabyte.busstationclient.domain.model.auth.LoginCredentialsModel
import com.terabyte.busstationclient.domain.repository.AuthRepository
import com.terabyte.busstationclient.domain.repository.TokenRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {

    suspend operator fun invoke(loginCredentialsModel: LoginCredentialsModel): Boolean {
        val result = authRepository.login(loginCredentialsModel)
        if (result.isFailure) {
            return false
        }

        tokenRepository.saveToken(result.getOrThrow())
        return true
    }
}