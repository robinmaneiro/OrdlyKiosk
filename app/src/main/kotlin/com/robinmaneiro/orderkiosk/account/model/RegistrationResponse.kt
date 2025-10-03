package com.robinmaneiro.orderkiosk.account.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationResponse(
    @JsonProperty("id") val id: String
)
