package com.robinmaneiro.orderkiosk.account.personaldetails.model

data class UpdatePhonePayload(
    val phoneNumber: String,
    val dialingCode: String,
    val countryCode: String
)
