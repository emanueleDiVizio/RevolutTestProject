package com.example.revolut_test.utils

import android.content.Context
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.lang.reflect.Type
import java.util.*


object CurrencyHelper {
    var adapter: JsonAdapter<Map<String, String>>
    var file = "countryMapping.json"
    var currencyMap: Map<String, String>? = mapOf()

    init {
        val type: Type = Types.newParameterizedType(
            Map::class.java,
            String::class.java,
            String()::class.java
        )
        adapter = Moshi.Builder().build().adapter(type)

    }

    fun setUp(context: Context) {
        val myjson = context.assets.open(file).bufferedReader().use { it.readText() }
        currencyMap = adapter.fromJson(myjson)
    }

    fun getFlagFromCurrencyCode(currencyCode: String): String? {
        return currencyMap?.get(currencyCode)
    }

    fun getDisplayNameFromCurrencyCode(currencyCode: String): String {
        return Currency.getInstance(currencyCode).displayName
    }
}

fun convertValueWithRate(value: String?, rate: String?): String {
    var res = ""
    value?.let{
        rate?.let{
            if(value != "" && rate != ""){
                res =  (value.toDouble() * rate.toDouble()).toString()
            }
        }
    }
    return res
}