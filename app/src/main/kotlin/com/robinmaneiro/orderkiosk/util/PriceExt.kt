package com.robinmaneiro.orderkiosk.util

import java.text.NumberFormat
import java.util.Currency

private const val CENTS_IN_MONETARY_UNIT = 100.0
private const val DEFAULT_NUMBER_OF_DECIMALS = 2

fun Int.getDoublePrice() = div(CENTS_IN_MONETARY_UNIT)

fun Double.getFormattedPrice(currencyCode: String): String {
    return try {
        performFormat(currencyCode)
    } catch (e: IllegalArgumentException) {
        // TODO: Log error here
        try {
            // TODO: Add log
            performFormat(DEFAULT_CURRENCY_CODE)
        }  catch (e: IllegalArgumentException) {
            // TODO: Add log
            toString()
        }
    }
}

private fun Double.performFormat(currencyCode: String): String = NumberFormat.getCurrencyInstance().run {
    val currencyCodesWithoutCurrencySubdivision = listOf("JPY", "KRW")
    val numberOfDecimals = DEFAULT_NUMBER_OF_DECIMALS.takeUnless { currencyCode in currencyCodesWithoutCurrencySubdivision }.orZero()
    maximumFractionDigits = numberOfDecimals
    currency = Currency.getInstance(currencyCode)
    format(this@performFormat)
}
