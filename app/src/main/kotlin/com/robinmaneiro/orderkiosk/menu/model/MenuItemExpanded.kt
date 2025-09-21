package com.robinmaneiro.orderkiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MenuItemExpanded(
    @JsonProperty("id") val productId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("price") private val _priceData: PriceData,
    @JsonProperty("description") val description: String,
    @JsonProperty("categories") val categories: List<String>
) {
    val price = _priceData.withTax
    val formattedPrice = _priceData.formattedWithTax
}
