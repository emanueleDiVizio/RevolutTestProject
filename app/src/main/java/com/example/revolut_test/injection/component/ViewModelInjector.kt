package com.example.revolut_test.injection.component

import com.example.revolut_test.injection.module.NetworkModule
import com.example.revolut_test.injection.module.RatesModule
import com.example.revolut_test.ui.RatesViewModel
import dagger.Component
import javax.inject.Singleton

/**
 * Component providing inject() methods for ViewModels.
 */
@Singleton
@Component(modules = [(NetworkModule::class), (RatesModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified CurrenciesViewModel.
     * @param ratesViewModel CurrenciesViewModel in which to inject the dependencies
     */
    fun inject(ratesViewModel: RatesViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder

        fun ratesModule(ratesModule: RatesModule): Builder
    }
}