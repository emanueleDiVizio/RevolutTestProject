package com.example.revolut_test.data.model

data class RatesBook(
    val baseCurrency: String,
    val rates: Map<String, String>
)
