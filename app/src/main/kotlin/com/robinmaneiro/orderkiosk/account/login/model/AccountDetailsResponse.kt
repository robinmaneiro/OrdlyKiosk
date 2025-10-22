package com.robinmaneiro.orderkiosk.account.login.model

import com.fasterxml.jackson.annotation.JsonProperty

data class AccountDetailsResponse(
    @JsonProperty("id") val userId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("firstName") val firstName: String,
    @JsonProperty("lastName") val lastName: String,
    @JsonProperty("email") val emailAddress: String,
    @JsonProperty("dateOfBirth") val dateOfBirth: String?,
    @JsonProperty("phone") val phoneNumber: String
)