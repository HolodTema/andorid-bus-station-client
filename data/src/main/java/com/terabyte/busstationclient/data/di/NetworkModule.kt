package com.terabyte.busstationclient.data.di

import com.terabyte.busstationclient.data.storage.remote.NetworkStorage
import com.terabyte.busstationclient.data.storage.remote.NetworkStorageImpl
import com.terabyte.busstationclient.data.storage.remote.RetrofitService
import com.terabyte.busstationclient.data.storage.remote.TokenHttpInterceptor
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
abstract class NetworkModule {

    @Binds
    @Singleton
    abstract fun bindNetworkStorage(storage: NetworkStorageImpl): NetworkStorage

    companion object {

        @Provides
        @Singleton
        fun provideLoggingInterceptor(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                //BODY = log url, http method, response code, handle-time,
                //headers + request/response body
                //
                //logs will be in AndroidStudio Logcat.
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        @Singleton
        fun provideTokenHttpInterceptor(): TokenHttpInterceptor {
            return TokenHttpInterceptor()
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(
            loggingInterceptor: HttpLoggingInterceptor,
            tokenHttpInterceptor: TokenHttpInterceptor
        ): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .addInterceptor(tokenHttpInterceptor)
                .build()
        }

        @Provides
        @Singleton
        fun provideKotlinxSerializationJson(): Json {
            return Json {
                coerceInputValues = true
                ignoreUnknownKeys = true
            }
        }

        @Provides
        @Singleton
        fun provideRetrofit(client: OkHttpClient, json: Json): Retrofit {
            return Retrofit.Builder()
                .baseUrl("http://185.154.52.200:8000")
                .client(client)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .build()
        }

        @Provides
        @Singleton
        fun provideRetrofitService(retrofit: Retrofit): RetrofitService {
            return retrofit.create<RetrofitService>()
        }
    }
}