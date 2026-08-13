package com.robinmaneiro.ordly.kiosk.datastore

data class AccountDetails(
    val userId: String,
    val firstName: String,
    val lastName: String,
    val emailAddress: String,
    val dateOfBirth: String?,
    val phoneNumber: String,
    val bagId: String,
    val wishlistId: String
)
