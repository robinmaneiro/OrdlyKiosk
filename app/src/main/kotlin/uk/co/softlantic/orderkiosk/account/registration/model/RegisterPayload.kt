package uk.co.softlantic.orderkiosk.account.registration.model

data class RegisterPayload(
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
