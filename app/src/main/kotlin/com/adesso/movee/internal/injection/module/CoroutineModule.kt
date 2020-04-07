package com.adesso.movee.internal.injection.module

import com.adesso.movee.internal.util.DefaultDispatcherProvider
import com.adesso.movee.internal.util.DispatcherProvider
import dagger.Binds
import dagger.Module

@Module
interface CoroutineModule {

    @Binds
    fun providesDispatcherProvider(provider: DefaultDispatcherProvider): DispatcherProvider
}
