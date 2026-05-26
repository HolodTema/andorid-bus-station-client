package com.terabyte.busstationclient.data.di

import dagger.Binds
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Binds
    @Singleton
    fun bind
}