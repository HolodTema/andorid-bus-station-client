package com.terabyte.busstationclient.domain.repository

import com.terabyte.busstationclient.domain.model.auth.TokenModel

interface TokenRepository {

    suspend fun saveToken(tokenModel: TokenModel)

    suspend fun getToken(): TokenModel?

    suspend fun deleteToken()

}
