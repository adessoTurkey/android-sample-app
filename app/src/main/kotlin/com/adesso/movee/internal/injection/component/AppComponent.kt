package com.adesso.movee.internal.injection.component

import com.adesso.movee.internal.injection.DaggerApplication
import com.adesso.movee.internal.injection.module.ActivitiesModule
import com.adesso.movee.internal.injection.module.AppModule
import com.adesso.movee.internal.injection.module.CacheModule
import com.adesso.movee.internal.injection.module.DatabaseModule
import com.adesso.movee.internal.injection.module.FragmentsModule
import com.adesso.movee.internal.injection.module.NetworkModule
import com.adesso.movee.internal.injection.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ActivitiesModule::class,
        FragmentsModule::class,
        AppModule::class,
        NetworkModule::class,
        CacheModule::class,
        ViewModelModule::class,
        DatabaseModule::class
    ]
)
internal interface AppComponent : AndroidInjector<DaggerApplication> {

    @Component.Factory
    abstract class Factory : AndroidInjector.Factory<DaggerApplication>
}
