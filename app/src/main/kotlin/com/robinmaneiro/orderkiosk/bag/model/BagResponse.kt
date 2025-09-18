package com.robinmaneiro.orderkiosk.bag.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BagItemResponse(
    @JsonProperty("items") val items: List<BagItem>,
    @JsonProperty("totalPrice") private val _totalPrice: Int
) {
    val totalPrice = _totalPrice.div(100.toDouble())
}

data class BagItem(
    @JsonProperty("itemId") val itemId: String,
    @JsonProperty("productId") val productId: String,
    @JsonProperty("quantity") val quantity: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("unitPrice") private val _unitPrice: Int,
    @JsonProperty("price") private val _price: Int
) {
    val price = _price.div(100.toDouble())
    val unitPrice = _unitPrice.div(100.toDouble())
}
