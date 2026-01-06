package uk.co.softlantic.orderkiosk.account.personaldetails.model

data class UpdatePhonePayload(
    val phoneNumber: String,
    val dialingCode: String,
    val countryCode: String
)
