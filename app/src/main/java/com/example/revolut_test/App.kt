package com.example.revolut_test

import android.app.Activity
import android.app.Application
import com.example.revolut_test.injection.component.DaggerApplicationComponent
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
        DaggerApplicationComponent.builder()
            .applicationBind(this)
            .build()
            .inject(this)

    }


}