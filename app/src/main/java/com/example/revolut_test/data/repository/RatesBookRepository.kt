package com.example.revolut_test.data.repository

import com.example.revolut_test.base.BaseRepository
import com.example.revolut_test.data.model.RatesBook
import com.example.revolut_test.data.network.RatesBookApi
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class RatesBookRepository @Inject constructor(val ratesBookApi: RatesBookApi, val intervalScheduler: Scheduler): BaseRepository(){

     fun observeRates(currency: String, updateRate: Long): Observable<RatesBook> {
        return Observable.interval(updateRate, TimeUnit.SECONDS, intervalScheduler).flatMap { ratesBookApi.getRates(currency) }
    }
}