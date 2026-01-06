package uk.co.softlantic.orderkiosk.account.registration.model

import com.fasterxml.jackson.annotation.JsonProperty

data class RegistrationResponse(
    @JsonProperty("id") val id: String
)
