package com.robinmaneiro.orderkiosk.util.extensions

fun Int?.orZero() = this ?: 0

fun Float?.orZero() = this ?: 0F

fun Double?.orZero() = this ?: 0.0

fun Long?.orZero() = this ?: 0L
