package com.terabyte.busstationclient.data.di

import com.terabyte.busstationclient.data.repository.AuthRepositoryImpl
import com.terabyte.busstationclient.data.repository.StationRepositoryImpl
import com.terabyte.busstationclient.data.repository.TokenRepositoryImpl
import com.terabyte.busstationclient.domain.repository.AuthRepository
import com.terabyte.busstationclient.domain.repository.StationRepository
import com.terabyte.busstationclient.domain.repository.TokenRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindTokenRepository(repository: TokenRepositoryImpl): TokenRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(repository: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindStationRepository(repository: StationRepositoryImpl): StationRepository

}
