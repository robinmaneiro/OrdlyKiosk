package com.robinmaneiro.orderkiosk.util.extensions

fun Boolean?.orTrue(): Boolean = this ?: true

fun Boolean?.orFalse(): Boolean = this ?: false

fun <T> List<T>?.isNotNullOrEmpty(): Boolean = !this.isNullOrEmpty()
