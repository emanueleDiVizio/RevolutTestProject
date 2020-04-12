package com.example.revolut_test

import com.example.revolut_test.data.model.RatesBook
import com.example.revolut_test.data.network.RatesBookApi
import com.example.revolut_test.data.repository.RatesBookRepository
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.Observable
import io.reactivex.schedulers.TestScheduler
import org.junit.Test
import java.util.concurrent.TimeUnit


class RatesBookRepositoryTest {
    val usdRates = RatesBook("USD", HashMap())
    val usdRates2 = RatesBook("USD_2", HashMap())
    val usdRates3 = RatesBook("USD_3", HashMap())

    val testScheduler = TestScheduler()

    val ratesBookApi: RatesBookApi = mock {
        on { getRates(eq("USD")) } doReturn Observable.just(usdRates) doReturn Observable.just(usdRates2) doReturn Observable.just(usdRates3)
    }

    val ratesBookRepositoryI: RatesBookRepository = RatesBookRepository(ratesBookApi, testScheduler)



    @Test
    fun observer_emits_every_n_seconds() {
        val observer= ratesBookRepositoryI.observeRates("USD", 1).test()
        testScheduler.advanceTimeBy(3, TimeUnit.SECONDS)
        observer.assertValues(usdRates, usdRates2, usdRates3)
    }
}
