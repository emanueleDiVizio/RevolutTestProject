package com.example.revolut_test.ui

import androidx.lifecycle.MutableLiveData
import com.example.revolut_test.base.BaseViewModel
import com.example.revolut_test.data.model.RatesModel
import com.example.revolut_test.data.repository.RatesRepository
import com.example.revolut_test.utils.DEFAULT_CURRENCY
import com.example.revolut_test.utils.DEFAULT_RATES_UPDATE_RATE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import javax.inject.Inject

class RatesViewModel @Inject constructor(val ratesRepositoryI: RatesRepository): BaseViewModel() {


    private lateinit var subscription: Disposable

    private val currencySubject: BehaviorSubject<String> = BehaviorSubject.createDefault(DEFAULT_CURRENCY)
    val currencyLiveData: MutableLiveData<RatesModel> = MutableLiveData()


    init{
        subscribeToRates()
    }

    fun updateCurrency(currency: String){
        currencySubject.onNext(currency)
    }

    private fun subscribeToRates() {
        subscription =
            currencySubject.flatMap { currency: String -> ratesRepositoryI.observeRates(currency, DEFAULT_RATES_UPDATE_RATE) }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { rates -> onRetrieveRatesSuccess(rates) },
                    { onRetrieveRatesError() }
                )


    }


    private fun onRetrieveRatesSuccess(rates: RatesModel) {
        currencyLiveData.value = rates

    }

    private fun onRetrieveRatesError() {

    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }
}