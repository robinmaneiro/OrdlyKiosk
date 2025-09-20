package com.robinmaneiro.orderkiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.robinmaneiro.orderkiosk.util.getDoublePrice
import com.robinmaneiro.orderkiosk.util.getFormattedPrice

data class MenuResponse(
    @JsonProperty("itemCount") val itemCount: Int,
    @JsonProperty("items") val items: List<MenuProduct>
)

data class MenuProduct(
    @JsonProperty("id") val productId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("price") private val _price: Int,
    @JsonProperty("description") val description: String,
    @JsonProperty("categories") val categories: List<String>
) {
    val price = _price.getDoublePrice()
    val formattedPrice = price.getFormattedPrice("") // TODO: Fill with actual currency code when provided in the response
}
