package com.robinmaneiro.ordly.kiosk.account.registration.model

data class RegisterPayload(
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
