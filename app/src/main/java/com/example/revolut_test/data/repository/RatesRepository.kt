package com.example.revolut_test.data.repository

import com.example.revolut_test.base.BaseRepository
import com.example.revolut_test.data.model.RatesModel
import com.example.revolut_test.data.network.RatesApi
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class RatesRepository @Inject constructor(val ratesApi: RatesApi, val intervalScheduler: Scheduler): BaseRepository(){

     fun observeRates(currency: String, updateRate: Long): Observable<RatesModel> {
        return Observable.interval(updateRate, TimeUnit.SECONDS, intervalScheduler).flatMap { ratesApi.getRates(currency) }
    }
}