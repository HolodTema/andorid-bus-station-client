package com.terabyte.busstationclient.data.storage.remote

import com.terabyte.busstationclient.domain.repository.TokenRepository
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenHttpInterceptor @Inject constructor(
    private val tokenRepository: TokenRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val httpPath = originalRequest.url.encodedPath

        val isRequestToAuth = httpPath.contains("/api/auth")

        if (isRequestToAuth) {
            return chain.proceed(originalRequest)
        }

        val tokenModel = runBlocking {
            tokenRepository.getToken()
        }

        if (tokenModel == null) {
            return chain.proceed(originalRequest)
        }

        val requestWithAuth = originalRequest.newBuilder()
            .header("Authorization", "Bearer ${tokenModel.token}")
            .build()
        return chain.proceed(requestWithAuth)
    }

}