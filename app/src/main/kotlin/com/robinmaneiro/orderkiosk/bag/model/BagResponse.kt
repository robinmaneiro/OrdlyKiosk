package com.robinmaneiro.orderkiosk.bag.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.robinmaneiro.orderkiosk.util.extensions.getDoublePrice
import com.robinmaneiro.orderkiosk.util.extensions.getFormattedPrice
import kotlinx.collections.immutable.toPersistentList
import kotlinx.collections.immutable.toPersistentSet

data class BagResponse(
    @JsonProperty("totalCost") private val _totalCost: PriceData,
    @JsonProperty("items") private val _items: List<BagItem>
) {
    val totalCost = _totalCost.withTax
    val formattedTotalCost = totalCost.getFormattedPrice(_totalCost.currencyCode)
    val items = _items.toPersistentList()
    val itemCount = _items.count()
}

data class BagItem(
    @JsonProperty("itemId") val itemId: String,
    @JsonProperty("productId") val productId: String,
    @JsonProperty("quantity") val quantity: Int,
    @JsonProperty("title") val title: String,
    @JsonProperty("description") val description: String,
    @JsonProperty("price") private val _itemPrice: ItemPrice,
) {
    val price = _itemPrice.total.withTax
    val formattedPrice = _itemPrice.total.formattedWithTax

    val unitPrice = _itemPrice.unit.withTax
    val formattedUnitPrice = _itemPrice.unit.formattedWithTax
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
    @JsonProperty("vat") val vat: TaxUnit = TaxUnit(0, 0)
)

data class TaxUnit(
    @JsonProperty("amount") val amount: Int,
    @JsonProperty("rate") val rate: Int
)
