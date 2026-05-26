package com.terabyte.busstationclient.data.repository

import com.terabyte.busstationclient.data.storage.remote.NetworkStorage
import com.terabyte.busstationclient.data.storage.remote.model.UserAuthResponse
import com.terabyte.busstationclient.data.storage.remote.model.UserLoginRequest
import com.terabyte.busstationclient.data.storage.remote.model.UserRegisterRequest
import com.terabyte.busstationclient.domain.model.auth.LoginCredentialsModel
import com.terabyte.busstationclient.domain.model.auth.RegisterCredentialsModel
import com.terabyte.busstationclient.domain.model.auth.TokenModel
import com.terabyte.busstationclient.domain.repository.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val networkStorage: NetworkStorage
) : AuthRepository {

    override suspend fun login(loginCredentialsModel: LoginCredentialsModel): Result<TokenModel> {
        val request = mapToUserLoginRequest(loginCredentialsModel)

        val result = networkStorage.login(request)
        return result.map {
            mapToTokenModel(it)
        }
    }

    override suspend fun register(registerCredentialsModel: RegisterCredentialsModel): Result<TokenModel> {
        val request = mapToUserRegisterRequest(registerCredentialsModel)

        val result = networkStorage.register(request)
        return result.map {
            mapToTokenModel(it)
        }
    }

    private fun mapToUserLoginRequest(loginCredentialsModel: LoginCredentialsModel): UserLoginRequest {
        return UserLoginRequest(
            email = loginCredentialsModel.email,
            password = loginCredentialsModel.password
        )
    }

    private fun mapToTokenModel(userAuthResponse: UserAuthResponse): TokenModel {
        return TokenModel(
            token = userAuthResponse.token
        )
    }

    private fun mapToUserRegisterRequest(registerCredentialsModel: RegisterCredentialsModel): UserRegisterRequest {
        return UserRegisterRequest(
            name = registerCredentialsModel.name,
            surname = registerCredentialsModel.surname,
            email = registerCredentialsModel.email,
            password = registerCredentialsModel.password
        )
    }
}