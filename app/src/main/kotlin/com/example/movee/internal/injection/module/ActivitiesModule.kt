package com.example.movee.internal.injection.module

import com.example.movee.scene.main.MainActivity
import com.example.movee.internal.injection.scope.MainScope
import com.example.movee.scene.main.MainModule
import com.example.movee.scene.splash.SplashActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
 abstract class ActivitiesModule {

    @MainScope
    @ContributesAndroidInjector(modules = [MainModule::class])
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector()
    internal abstract fun contributeSplashActivity(): SplashActivity
}
