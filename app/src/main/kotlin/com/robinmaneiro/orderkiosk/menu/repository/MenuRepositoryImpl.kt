package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuProducts
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class MenuRepositoryImpl : MenuRepository {
    override suspend fun getProductsByCategory(categoryId: String): MenuProducts? {
        return NetworkManager.getRequest<MenuProducts>("http://192.168.1.162:8080/menu/categories/$categoryId") // TODO: Hardcoded string
    }

    override suspend fun getProductExtendedInfo(productId: String): MenuItemExpanded? {
        return NetworkManager.getRequest<MenuItemExpanded>("http://192.168.1.162:8080/menu/items/$productId") // TODO: Find a better path
    }
}
