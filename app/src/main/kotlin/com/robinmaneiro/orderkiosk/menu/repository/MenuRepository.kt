package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuProducts

interface MenuRepository {
    suspend fun getProductsByCategory(categoryId: String): MenuProducts?
    suspend fun getProductExtendedInfo(productId: String): MenuItemExpanded?
}
