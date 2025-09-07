package com.robinmaneiro.orderkiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MenuItemExpanded(
    @JsonProperty("id") val productId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("price") val price: Double,
    @JsonProperty("description") val description: String,
    @JsonProperty("categories") val categories: List<String>
)
