package com.example.movee.internal.injection.component

import com.example.movee.internal.injection.DaggerApplication
import com.example.movee.internal.injection.module.*
import com.example.movee.internal.injection.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    ActivitiesModule::class,
    FragmentsModule::class,
    AppModule::class,
    NetworkModule::class,
    CacheModule::class,
    ViewModelModule::class
])
internal interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<DaggerApplication>()
}