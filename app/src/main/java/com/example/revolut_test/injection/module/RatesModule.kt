package com.example.revolut_test.injection.module

import com.example.revolut_test.data.network.RatesApi
import com.example.revolut_test.data.repository.RatesRepository
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
    internal fun providePostApi(retrofit: Retrofit): RatesApi {
        return retrofit.create(RatesApi::class.java)
    }

    /**
     * Provides the Rates repository.
     * @param ratesApi the API interface.
     * @return the RatesRepository.
     */
    @Provides
    @Reusable
    @JvmStatic
    internal fun provideRatesRepository(ratesApi: RatesApi): RatesRepository {
        return RatesRepository(ratesApi, Schedulers.io())
    }
}