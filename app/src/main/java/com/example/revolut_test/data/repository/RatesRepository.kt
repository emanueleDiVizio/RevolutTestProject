package com.example.revolut_test.data.repository

import android.util.Log
import com.example.revolut_test.base.BaseRepository
import com.example.revolut_test.data.model.Rates
import com.example.revolut_test.data.network.RatesApi
import io.reactivex.Observable
import io.reactivex.Scheduler
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class RatesRepository @Inject constructor(val ratesApi: RatesApi, val intervalScheduler: Scheduler): BaseRepository(){

     fun observeRates(currency: String, updateRate: Long): Observable<Rates> {
         Log.d("Hey","HH")
        return Observable.interval(updateRate, TimeUnit.SECONDS).flatMap { ratesApi.getRates(currency) }
    }
}