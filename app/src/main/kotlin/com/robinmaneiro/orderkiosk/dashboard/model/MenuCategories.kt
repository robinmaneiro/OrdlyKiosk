package com.robinmaneiro.orderkiosk.dashboard.model

import com.fasterxml.jackson.annotation.JsonIgnore

class MenuCategories: ArrayList<MenuCategory>()

data class MenuCategory(
    val id: String,
    val categoryName: String,
    @JsonIgnore val isSelected: Boolean = false
)
