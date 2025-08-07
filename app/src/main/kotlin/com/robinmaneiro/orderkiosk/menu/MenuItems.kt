package com.robinmaneiro.orderkiosk.menu

import com.fasterxml.jackson.annotation.JsonProperty

class MenuItems: ArrayList<MenuItem>()

data class MenuItem(
    @JsonProperty("title") val title: String,
    @JsonProperty("price") val price: Double
)
