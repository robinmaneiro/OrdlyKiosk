package com.robinmaneiro.orderkiosk.bag.model

import com.fasterxml.jackson.annotation.JsonProperty

class BagItemResponse: ArrayList<BagItem>()

data class BagItem(
    @JsonProperty("itemId") val itemId: String,
    @JsonProperty("productId") val productId: String,
    @JsonProperty("quantity") val quantity: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("price") private val _price: Int
) {
    val price = _price.div(100.toDouble())
}
