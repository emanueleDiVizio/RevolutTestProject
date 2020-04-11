package com.example.revolut_test

import com.example.revolut_test.data.model.Rates
import com.example.revolut_test.data.model.RatesModel
import com.example.revolut_test.data.network.RatesApi
import com.example.revolut_test.data.repository.RatesRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit


class RatesRepositoryTest {
    val usdRates = RatesModel("USD", Rates())
    val usdRates2 = RatesModel("USD_2", Rates())
    val usdRates3 = RatesModel("USD_3", Rates())

    val testScheduler = TestScheduler()

    val ratesApi: RatesApi = mock {
        on { getRates(eq("USD")) } doReturn Observable.just(usdRates) doReturn Observable.just(usdRates2) doReturn Observable.just(usdRates3)
    }

    val ratesRepositoryI: RatesRepository = RatesRepository(ratesApi, testScheduler)



    @Test
    fun observer_emits_every_n_seconds() {
        val observer= ratesRepositoryI.observeRates("USD", 1).test()
        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)
        observer.assertValues(usdRates, usdRates2, usdRates3)
    }
}
