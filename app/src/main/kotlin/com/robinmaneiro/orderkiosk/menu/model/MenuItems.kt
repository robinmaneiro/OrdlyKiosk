package com.robinmaneiro.orderkiosk.menu.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

class MenuItems: ArrayList<MenuItem>()

@JsonIgnoreProperties(ignoreUnknown = true) // TODO: Change to do this globally
data class MenuItem(
    @JsonProperty("title") val title: String,
    @JsonProperty("price") val price: Double,
    @JsonProperty("description") val description: String
)
