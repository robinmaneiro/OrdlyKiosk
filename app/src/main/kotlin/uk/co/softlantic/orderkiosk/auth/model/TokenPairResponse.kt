package uk.co.softlantic.orderkiosk.auth.model

import com.fasterxml.jackson.annotation.JsonProperty

data class TokenPairResponse(
    @JsonProperty("accessToken") val accessToken: String,
    @JsonProperty("refreshToken") val refreshToken: String
)
