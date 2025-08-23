package com.robinmaneiro.orderkiosk.dashboard.model

class MenuCategories: ArrayList<MenuCategory>()

data class MenuCategory(
    val categoryId: String,
    val categoryName: String,
)
