package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuResponse

interface MenuRepository {
    suspend fun getProductsByCategory(categoryId: String): Result<MenuResponse>
    suspend fun getProductExtendedInfo(productId: String): Result<MenuItemExpanded>
}
