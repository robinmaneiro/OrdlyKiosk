package com.robinmaneiro.orderkiosk.account.model

import com.fasterxml.jackson.annotation.JsonProperty

data class UserDetailsResponse(
    @JsonProperty("userId") val userId: String,
    @JsonProperty("firstName") val firstName: String,
    @JsonProperty("lastName") val lastName: String
)
