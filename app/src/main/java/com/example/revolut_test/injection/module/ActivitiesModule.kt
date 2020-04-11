package com.example.revolut_test.injection.module

import com.example.revolut_test.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {
    @ContributesAndroidInjector(modules =    [MainActivityModule::class])
    abstract fun contributeActivityAndroidInjector(): MainActivity

}