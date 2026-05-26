package com.terabyte.busstationclient.domain.usecase.auth

import com.terabyte.busstationclient.domain.model.auth.RegisterCredentialsModel
import com.terabyte.busstationclient.domain.model.auth.RegistrationError
import com.terabyte.busstationclient.domain.repository.AuthRepository
import com.terabyte.busstationclient.domain.repository.TokenRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val authRepository: AuthRepository,
    private val tokenRepository: TokenRepository
) {

    // if error is null, then registration is successful
    suspend operator fun invoke(registerCredentialsModel: RegisterCredentialsModel): RegistrationError? {
        val result = authRepository.register(registerCredentialsModel)
        if (result.isFailure) {
            return result.exceptionOrNull() as RegistrationError?
        }

        tokenRepository.saveToken(result.getOrThrow())
        return null
    }
}