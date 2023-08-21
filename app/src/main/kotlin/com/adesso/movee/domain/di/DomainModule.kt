package com.adesso.movee.domain.di

import com.adesso.movee.data.NetworkConnectionDispatcherImpl
import com.adesso.movee.domain.NetworkConnectionDispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface DomainModule {

    @Singleton
    @Binds
    fun bindNetworkConnectionDispatcher(
        networkConnectionDispatcherImpl: NetworkConnectionDispatcherImpl
    ): NetworkConnectionDispatcher
}
