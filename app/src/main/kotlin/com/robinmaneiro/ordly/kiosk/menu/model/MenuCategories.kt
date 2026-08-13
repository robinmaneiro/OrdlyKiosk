package com.robinmaneiro.ordly.kiosk.menu.model

import com.fasterxml.jackson.annotation.JsonProperty

class MenuCategories : ArrayList<MenuCategory>()

data class MenuCategory(
    @JsonProperty("id") val id: String,
    @JsonProperty("categoryName") val categoryName: String,
    @JsonProperty("selected") val isDefault: Boolean,
    @JsonProperty("imageUrl") val imageUrl: String? = null
)
