package com.robinmaneiro.ordly.kiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty

data class MenuItemExpanded(
    @JsonProperty("id") val productId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("price") private val _priceData: PriceData,
    @JsonProperty("description") val description: String,
    @JsonProperty("categories") val categories: List<String>,
    @JsonProperty("imageUrl") val imageUrl: String? = null
) {
    val price = _priceData.withTax
    val formattedPrice = _priceData.formattedWithTax
    val currencyCode = _priceData.currencyCode
}
