package com.example.revolut_test.data.network

import com.example.revolut_test.data.model.Rates
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * The interface which provides methods to get data from API
 */
interface RatesApi {
    /**
     * Get the currencies data from API
     */
    @GET("/api/android/latest")
    fun getRates(@Query("base") currency: String): Observable<Rates>
}