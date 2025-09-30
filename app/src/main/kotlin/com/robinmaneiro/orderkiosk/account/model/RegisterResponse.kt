package com.robinmaneiro.orderkiosk.account.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegisterResponse(
    @JsonProperty("userId") val userId: String
)
