package com.robinmaneiro.ordly.kiosk.account.registration.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationResponse(
    @JsonProperty("id") val id: String
)
