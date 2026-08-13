package com.robinmaneiro.ordly.kiosk.account.personaldetails.model

data class UpdatePhonePayload(
    val phoneNumber: String,
    val dialingCode: String,
    val countryCode: String
)
