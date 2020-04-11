package com.example.revolut_test.base


import androidx.lifecycle.ViewModel
import com.example.revolut_test.injection.component.DaggerViewModelInjector
import com.example.revolut_test.injection.component.ViewModelInjector
import com.example.revolut_test.injection.module.NetworkModule
import com.example.revolut_test.ui.RatesViewModel

abstract class BaseViewModel: ViewModel(){
    private val injector: ViewModelInjector = DaggerViewModelInjector
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    /**
     * Injects the required dependencies
     */
    private fun inject() {
        when (this) {
            is RatesViewModel -> injector.inject(this)
        }
    }
}