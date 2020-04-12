package com.example.revolut_test

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.revolut_test.data.model.RatesBook
import com.example.revolut_test.data.repository.RatesBookRepository
import com.example.revolut_test.ui.CurrencyAdapter
import com.example.revolut_test.ui.RatesViewModel
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Observable
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class RatesBookViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testSchedulerRule = RxImmediateSchedulerRule()


    val usdRates = RatesBook("USD", HashMap())
    val usdRates2 = RatesBook("USD_2", HashMap())
    val usdRates3 = RatesBook("USD_3", HashMap())

    val eurRates = RatesBook("EUR", HashMap())
    val eurRates2 = RatesBook("EUR_2", HashMap())
    val eurRates3 = RatesBook("EUR_3", HashMap())


    lateinit var ratesBookRepository: RatesBookRepository
    lateinit var ratesViewModel: RatesViewModel
    lateinit var currencyAdapter: CurrencyAdapter

    @Before
    fun setUp(){
        ratesBookRepository = mock {
            on { observeRates(eq("EUR"), any()) } doReturn Observable.just(eurRates, eurRates2, eurRates3)
        }
        currencyAdapter = mock{

        }

        ratesViewModel= RatesViewModel(ratesBookRepository, currencyAdapter)

    }

    @Test
    fun adapter_is_provided_with_onItemClickListener(){
        verify(currencyAdapter).setOnItemSelectedListener(any())
    }

    @Test
    fun adapter_is_set_with_last_rates() {
        ratesViewModel.subscribeToRates()
        verify(currencyAdapter).updateRates(eurRates3)
    }
}
