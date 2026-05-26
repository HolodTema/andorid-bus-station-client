package com.terabyte.busstationclient.data.repository

import com.terabyte.busstationclient.data.storage.local.TokenDataStore
import com.terabyte.busstationclient.domain.model.auth.TokenModel
import com.terabyte.busstationclient.domain.repository.TokenRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenRepositoryImpl @Inject constructor(
    private val tokenDataStore: TokenDataStore
) : TokenRepository {

    override suspend fun saveToken(tokenModel: TokenModel) {
        tokenDataStore.saveToken(tokenModel.token)
    }

    override suspend fun getToken(): TokenModel? {
        val token = tokenDataStore.getToken()
        token?.let {
            return TokenModel(token)
        }
        return null
    }

    override suspend fun deleteToken() {
        tokenDataStore.deleteToken()
    }
}