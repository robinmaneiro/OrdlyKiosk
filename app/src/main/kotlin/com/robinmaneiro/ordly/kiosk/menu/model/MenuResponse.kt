package com.robinmaneiro.ordly.kiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.robinmaneiro.ordly.kiosk.util.extensions.getDoublePrice
import com.robinmaneiro.ordly.kiosk.util.extensions.getFormattedPrice

data class MenuResponse(
    @JsonProperty("itemCount") val itemCount: Int,
    @JsonProperty("items") val items: List<MenuProduct>
)

data class MenuProduct(
    @JsonProperty("id") val productId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("price") private val _priceData: PriceData,
    @JsonProperty("description") val description: String,
    @JsonProperty("categories") val categories: List<String>,
    @JsonProperty("imageUrl") val imageUrl: String? = null
) {
    val price = _priceData.withTax
    val formattedPrice = _priceData.formattedWithTax
}

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
