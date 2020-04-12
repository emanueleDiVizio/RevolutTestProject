package com.example.revolut_test.ui

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.example.revolut_test.base.BaseViewModel
import com.example.revolut_test.data.model.CurrencyEntry
import com.example.revolut_test.utils.CurrencyHelper.getDisplayNameFromCurrencyCode
import com.example.revolut_test.utils.CurrencyHelper.getFlagFromCurrencyCode


class CurrencyViewModel : BaseViewModel() {
    var onUserInputListener: (String) -> Unit = {}
    var onUserFocusListener: () -> Unit = {}
    val currencyCode = MutableLiveData<String>()
    val currencyName = MutableLiveData<String>()
    var currencyValue = MutableLiveData<String>()
    var currencyFlag = MutableLiveData<String>()
    var isBase = MutableLiveData<Boolean>()

    fun bind(
        currencyEntry: CurrencyEntry
    ) {

        if(currencyCode.value != currencyEntry.code){
            isBase.value = currencyEntry.isBase
            currencyCode.value = currencyEntry.code
            currencyName.value = getDisplayNameFromCurrencyCode(currencyEntry.code)
            currencyFlag.value = getFlagFromCurrencyCode(currencyEntry.code)
        }
        currencyValue.value = currencyEntry.value

    }

    fun getOnFocusChanged(): () -> Unit {
        return {
            onUserFocusListener.invoke() }
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
                if(isBase.value ==  true){
                    setUserInput(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable) {
                // Do nothing.
            }
        }
    }

    private fun setUserInput(input: String = "") {
        if ("" != input) onUserInputListener.invoke(input);
    }


}