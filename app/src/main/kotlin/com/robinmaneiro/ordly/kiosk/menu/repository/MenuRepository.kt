package com.robinmaneiro.ordly.kiosk.menu.repository

import com.robinmaneiro.ordly.kiosk.menu.model.MenuCategories
import com.robinmaneiro.ordly.kiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.ordly.kiosk.menu.model.MenuResponse

interface MenuRepository {
    suspend fun getProductsByCategory(categoryId: String): Result<MenuResponse>
    suspend fun getProductExtendedInfo(productId: String): Result<MenuItemExpanded>
    suspend fun getAllCategories(): Result<MenuCategories>
}
