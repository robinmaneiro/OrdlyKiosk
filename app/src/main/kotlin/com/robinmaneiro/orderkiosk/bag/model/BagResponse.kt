package com.robinmaneiro.orderkiosk.bag.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.robinmaneiro.orderkiosk.util.getDoublePrice
import com.robinmaneiro.orderkiosk.util.getFormattedPrice

data class BagResponse(
    @JsonProperty("totalCost") private val _totalCost: PriceData,
    @JsonProperty("items") val items: List<BagItem>
) {
    val totalCost = _totalCost.withTax
    val formattedTotalCost = totalCost.getFormattedPrice(_totalCost.currencyCode)
    val itemCount = items.count()
}

data class BagItem(
    @JsonProperty("itemId") val itemId: String,
    @JsonProperty("productId") val productId: String,
    @JsonProperty("quantity") val quantity: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("price") private val _priceData: ItemPrice,
) {
    val price = _priceData.total.withTax
    val formattedPrice = _priceData.total.formattedWithTax

    val unitPrice = _priceData.unit.withTax
    val formattedUnitPrice = _priceData.unit.formattedWithTax
}

data class ItemPrice(
    @JsonProperty("unit") val unit: PriceData,
    @JsonProperty("total") val total: PriceData
)

data class PriceData(
    @JsonProperty("currencyCode") val currencyCode: String,
    @JsonProperty("withTax") private val _withTax: Int,
    @JsonProperty("withoutTax") private val _withoutTax: Int,
    @JsonProperty("tax") val tax: Tax,
) {
    val withTax = _withTax.getDoublePrice()
    val formattedWithTax = withTax.getFormattedPrice(currencyCode)

    val withoutTax = _withoutTax.getDoublePrice()
    val formattedWithoutTax = withoutTax.getFormattedPrice(currencyCode)
}

data class Tax(
    @JsonProperty("vat") val vat: TaxUnit
)

data class TaxUnit(
    @JsonProperty("amount") val amount: Int,
    @JsonProperty("rate") val rate: Int
)
