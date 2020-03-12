package com.example.movee.internal.injection.module

import android.app.Application
import android.content.Context
import com.example.movee.internal.injection.DaggerApplication
import com.example.movee.internal.util.DateAdapter
import com.example.movee.internal.util.ImageJsonAdapter
import com.serjltt.moshi.adapters.Wrapped
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
internal class AppModule {

    @Provides
    @Singleton
    internal fun provideApplicationContext(application: DaggerApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    internal fun provideApplication(application: DaggerApplication): Application {
        return application
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .add(ImageJsonAdapter())
            .add(DateAdapter())
            .add(Wrapped.ADAPTER_FACTORY)
            .build()
    }
}
