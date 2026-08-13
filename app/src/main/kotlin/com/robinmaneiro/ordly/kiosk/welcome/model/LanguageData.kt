package com.robinmaneiro.ordly.kiosk.welcome.model

data class LanguageData(
    val countryFlag: Int,
    val languageAlpha2Code: String,
    val languageLabel: String,
    val isSelected: Boolean
)
