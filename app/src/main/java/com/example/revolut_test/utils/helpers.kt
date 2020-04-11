package com.example.revolut_test.utils

import java.util.*

fun getDisplayNameFromCountry(country: String) : String{
    return Locale("", country).displayCountry
}