package com.example.revolut_test.data.model

data class CurrencyEntry(
    val code: String,
    val value: String,
    val isBase: Boolean = false
)
