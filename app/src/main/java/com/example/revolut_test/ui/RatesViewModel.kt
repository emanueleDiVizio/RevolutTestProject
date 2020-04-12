package com.example.revolut_test.ui

import android.util.Log.d
import com.example.revolut_test.base.BaseViewModel
import com.example.revolut_test.data.model.CurrencyEntry
import com.example.revolut_test.data.model.RatesBook
import com.example.revolut_test.data.repository.RatesBookRepository
import com.example.revolut_test.utils.DEFAULT_CURRENCY
import com.example.revolut_test.utils.DEFAULT_RATES_UPDATE_RATE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RatesViewModel @Inject constructor(private val ratesBookRepository: RatesBookRepository, val currencyAdapter: CurrencyAdapter): BaseViewModel() {
    private var onClickListener: () -> Unit = {}

    private lateinit var subscription: Disposable
    private val currencySubject: BehaviorSubject<String> = BehaviorSubject.createDefault(DEFAULT_CURRENCY)


    init{
        currencyAdapter.setOnItemSelectedListener{ currency -> onItemClick(currency)  }
    }

    private fun updateCurrency(currencyEntry: CurrencyEntry){
        currencySubject.onNext(currencyEntry.code)
    }

    fun subscribeToRates() {
        subscription =
            currencySubject.switchMap { currency: String -> ratesBookRepository.observeRates(currency, DEFAULT_RATES_UPDATE_RATE) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { rates -> onRetrieveRatesSuccess(rates) },
                    { error -> onRetrieveRatesError(error) }
                )


    }

    fun setOnItemClickListener(listener: () -> Unit){
        onClickListener = listener
    }

    private fun onItemClick(currencyEntry: CurrencyEntry){
        updateCurrency(currencyEntry)
        onClickListener.invoke()
    }


    private fun onRetrieveRatesSuccess(ratesBook: RatesBook) {
        currencyAdapter.updateRates(ratesBook)
    }

    private fun onRetrieveRatesError(error: Throwable) {
        // Do something with error.

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }




}