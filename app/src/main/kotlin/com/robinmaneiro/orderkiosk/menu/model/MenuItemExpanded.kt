package com.robinmaneiro.orderkiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MenuItemExpanded(
    @JsonProperty("id") val productId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("price") private val _price: Int,
    @JsonProperty("description") val description: String,
    @JsonProperty("categories") val categories: List<String>
) {
    val price = _price.div(100.toDouble())
}
