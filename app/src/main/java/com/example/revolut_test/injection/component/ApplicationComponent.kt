package com.example.revolut_test.injection.component

import android.app.Application
import com.example.revolut_test.App
import com.example.revolut_test.injection.module.ActivitiesModule
import com.example.revolut_test.injection.module.NetworkModule
import com.example.revolut_test.injection.module.RatesModule
import com.example.revolut_test.injection.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivitiesModule::class,  ViewModelModule::class, RatesModule::class, NetworkModule::class])
interface ApplicationComponent {

    fun inject(application: App)

    @Component.Builder
    interface Builder {

        fun build(): ApplicationComponent

        @BindsInstance
        fun applicationBind(application: App): Builder

    }
}