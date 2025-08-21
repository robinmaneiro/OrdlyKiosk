package com.robinmaneiro.orderkiosk.menu.model

class MenuCategories: ArrayList<MenuCategory>()

data class MenuCategory(
    val id: String,
    val name: String,
    val productIds: List<Int>
)
