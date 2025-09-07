package com.robinmaneiro.orderkiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty

class MenuCategories: ArrayList<MenuCategory>()

data class MenuCategory(
    @JsonProperty("id") val id: String,
    @JsonProperty("categoryName") val categoryName: String,
    @JsonProperty("selected") val isDefault: Boolean
)
