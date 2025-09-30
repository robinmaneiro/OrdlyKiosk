package com.robinmaneiro.orderkiosk.account.model

data class RegisterPayload(
    val title: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)
