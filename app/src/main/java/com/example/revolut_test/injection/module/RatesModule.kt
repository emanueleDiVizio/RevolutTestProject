package com.example.revolut_test.injection.module

import com.example.revolut_test.data.network.RatesBookApi
import com.example.revolut_test.data.repository.RatesBookRepository
import dagger.Module
import dagger.Provides
import dagger.Reusable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit


/**
 * Module which provides all required dependencies about network
 */
@Module
object RatesModule {
    /**
     * Provides the Post service implementation.
     * @param retrofit the Retrofit object used to instantiate the service
     * @return the Post service implementation.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun providePostApi(retrofit: Retrofit): RatesBookApi {
        return retrofit.create(RatesBookApi::class.java)
    }

    /**
     * Provides the RatesBook repository.
     * @param ratesBookApi the API interface.
     * @return the RatesBookRepository.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRatesBookRepository(ratesBookApi: RatesBookApi): RatesBookRepository {
        return RatesBookRepository(ratesBookApi, Schedulers.io())
    }
}