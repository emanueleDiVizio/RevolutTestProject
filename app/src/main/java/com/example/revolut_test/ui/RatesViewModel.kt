package com.example.revolut_test.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.revolut_test.base.BaseViewModel
import com.example.revolut_test.data.model.Currency
import com.example.revolut_test.data.model.Rates
import com.example.revolut_test.data.repository.RatesRepository
import com.example.revolut_test.utils.DEFAULT_CURRENCY
import com.example.revolut_test.utils.DEFAULT_RATES_UPDATE_RATE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RatesViewModel @Inject constructor(val ratesRepository: RatesRepository): BaseViewModel() {


    private lateinit var subscription: Disposable

    private val currencySubject: BehaviorSubject<String> = BehaviorSubject.createDefault(DEFAULT_CURRENCY)
    private var ratesLiveData: MutableLiveData<Rates> = MutableLiveData()


    val currencyAdapter: CurrencyAdapter = CurrencyAdapter(ratesLiveData, MutableLiveData())


    init{
        currencyAdapter.setOnItemSelectedListener{ currency -> updateCurrency(currency) }
    }

    private fun updateCurrency(currency: Currency){
        currencySubject.onNext(currency.code)
    }

    fun subscribeToRates() {
        subscription =
            currencySubject.flatMap { currency: String -> ratesRepository.observeRates(currency, DEFAULT_RATES_UPDATE_RATE) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { rates -> onRetrieveRatesSuccess(rates) },
                    { error -> onRetrieveRatesError(error) }
                )


    }


    private fun onRetrieveRatesSuccess(rates: Rates) {
        ratesLiveData.value = rates
        currencyAdapter.updateRates(buildCurrencyListFromRates(rates)) // Could be done only once
    }

    private fun onRetrieveRatesError(error: Throwable) {
        Log.d("H", error.toString())

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun buildCurrencyListFromRates(rates: Rates): List<Currency>{
        val mutableList = mutableListOf<Currency>()
        mutableList.add(Currency(rates.baseCurrency))
        rates.rates.keys.forEach { code -> mutableList.add(Currency(code)) }
        return mutableList
    }
}