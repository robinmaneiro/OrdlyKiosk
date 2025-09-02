package com.robinmaneiro.orderkiosk.dashboard.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

data class MenuProducts(
    @JsonProperty("itemCount") val itemCount: Int,
    @JsonProperty("items") val items: List<MenuProduct>
)

data class MenuProduct(
    @JsonProperty("id") val productId: String,
    @JsonProperty("title") val title: String,
    @JsonProperty("price") val price: Double,
    @JsonProperty("description") val description: String,
    @JsonProperty("categories") val categories: List<String>
)
