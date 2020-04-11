package com.example.revolut_test.ui

import android.text.Editable
import android.text.TextWatcher
import android.util.Log.d
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations.map
import com.example.revolut_test.base.BaseViewModel
import com.example.revolut_test.data.model.Currency
import com.example.revolut_test.data.model.Rates
import com.example.revolut_test.utils.getDisplayNameFromCountry


class CurrencyViewModel: BaseViewModel() {
    val currencyCode = MutableLiveData<String>()
    val currencyName = MutableLiveData<String>()
    val currencyLiveData = MutableLiveData<Currency>()
    private lateinit var ratesLiveData: MutableLiveData<Rates>
    private lateinit var userInputLiveData: MutableLiveData<String>

    fun bind(currency: Currency, ratesLiveData: MutableLiveData<Rates>, userInputLiveData: MutableLiveData<String>){
        this.ratesLiveData = ratesLiveData
        this.userInputLiveData = userInputLiveData
        currencyLiveData.value = currency
        currencyCode.value = currency.code
        currencyName.value = getDisplayNameFromCountry(currency.code)
    }



    fun getInputTextWatcher(): TextWatcher? {
        return object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence,
                start: Int,
                count: Int,
                after: Int
            ) {
                // Do nothing.
            }

            override fun onTextChanged(
                s: CharSequence,
                start: Int,
                before: Int,
                count: Int
            ) {
                setUserInput(s.toString())
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        }
    }

    fun getConvertedRate(): LiveData<String> {
        return map(userInputLiveData) { input -> input}

//        return map(userInputLiveData) {input ->
//            d("RATE", ratesLiveData.value.toString())
//            val rate: Long? = ratesLiveData.value?.rates?.get(currencyLiveData.value?.code)?.toLong()
//            d("NEW_RATE", rate.toString())
//            rate?.times(input.toLong()).toString()
//        }
    }

    fun setUserInput(input: String?){
        d("INPUT", input)
        userInputLiveData.value = input
    }
}