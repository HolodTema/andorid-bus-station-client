package com.terabyte.busstationclient.domain.repository

import com.terabyte.busstationclient.domain.model.auth.LoginCredentialsModel
import com.terabyte.busstationclient.domain.model.auth.RegisterCredentialsModel
import com.terabyte.busstationclient.domain.model.auth.TokenModel

interface AuthRepository {

    suspend fun login(loginCredentialsModel: LoginCredentialsModel): Result<TokenModel>

    suspend fun register(registerCredentialsModel: RegisterCredentialsModel): Result<TokenModel>

}