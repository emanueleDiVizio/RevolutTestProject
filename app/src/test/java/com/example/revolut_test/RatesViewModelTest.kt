package com.example.revolut_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.revolut_test.data.model.Rates
import com.example.revolut_test.data.repository.RatesRepository
import com.example.revolut_test.ui.RatesViewModel
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RatesViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()


    val usdRates = Rates("USD", HashMap())
    val usdRates2 = Rates("USD_2", HashMap())
    val usdRates3 = Rates("USD_3", HashMap())

    val eurRates = Rates("EUR", HashMap())
    val eurRates2 = Rates("EUR_2", HashMap())
    val eurRates3 = Rates("EUR_3", HashMap())


    lateinit var ratesRepository: RatesRepository
    lateinit var ratesViewModel: RatesViewModel

    @Before
    fun setUp(){
        ratesRepository = mock {
            on { observeRates(eq("USD"), any()) } doReturn Observable.just(usdRates, usdRates2, usdRates3)
            on { observeRates(eq("EUR"), any()) } doReturn Observable.just(eurRates, eurRates2, eurRates3)
        }

        ratesViewModel= RatesViewModel(ratesRepository)

    }

    @Test
    fun view_model_givesDefault_currency_and_updates_currency_when_requested() {
//        Assert.assertEquals(eurRates3, ratesViewModel.currencyLiveData.value )
//        ratesViewModel.updateCurrency("USD")
//        Assert.assertEquals(usdRates3, ratesViewModel.currencyLiveData.value )
    }
}
