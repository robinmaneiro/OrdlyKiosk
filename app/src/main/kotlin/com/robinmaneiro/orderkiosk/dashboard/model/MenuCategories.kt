package com.robinmaneiro.orderkiosk.dashboard.model

class MenuCategories: ArrayList<MenuCategory>()

data class MenuCategory(
    val id: String,
    val categoryName: String,
)
