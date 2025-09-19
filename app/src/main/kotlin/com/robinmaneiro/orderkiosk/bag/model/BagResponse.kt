package com.robinmaneiro.orderkiosk.bag.model

import com.fasterxml.jackson.annotation.JsonProperty

data class BagResponse(
    @JsonProperty("totalCost") private val _totalCost: PriceData,
    @JsonProperty("items") val items: List<BagItem>
) {
    val totalCost = _totalCost.withTax.div(100.toDouble())
    val formattedTotalCost = "£${ "%.2f".format(totalCost) }"
    val itemCount = items.count()
}

data class PriceData(
    @JsonProperty("currencyCode") val currencyCode: String,
    @JsonProperty("withTax") val withTax: Int,
    @JsonProperty("withoutTax") val withoutTax: Int,
    @JsonProperty("tax") val tax: Tax,
)

data class Tax(
    @JsonProperty("vat") val vat: TaxUnit
)

data class TaxUnit(
    @JsonProperty("amount") val amount: Int,
    @JsonProperty("rate") val rate: Int
)

data class BagItem(
    @JsonProperty("itemId") val itemId: String,
    @JsonProperty("productId") val productId: String,
    @JsonProperty("quantity") val quantity: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("price") private val _priceData: ItemPrice,
) {
    val price = _priceData.total.withTax.div(100.toDouble())
    val unitPrice = _priceData.unit.withTax.div(100.toDouble())
}

data class ItemPrice(
    @JsonProperty("unit") val unit: PriceData,
    @JsonProperty("total") val total: PriceData
)
