package com.example.revolut_test

import android.app.Activity
import android.app.Application
import com.example.revolut_test.injection.component.DaggerApplicationComponent
import com.example.revolut_test.utils.CurrencyHelper
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector


    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>


    override fun onCreate() {
        super.onCreate()
        CurrencyHelper.setUp(this)
        DaggerApplicationComponent.builder()
            .applicationBind(this)
            .build()
            .inject(this)

    }


}