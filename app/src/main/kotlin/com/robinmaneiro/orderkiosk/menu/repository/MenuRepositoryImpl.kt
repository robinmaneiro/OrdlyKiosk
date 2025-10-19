package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class MenuRepositoryImpl : MenuRepository {
    override suspend fun getProductsByCategory(categoryId: String): Result<MenuResponse> {
        return NetworkManager.getRequest<MenuResponse>("http://192.168.1.162:8080/api/v1/menu/categories/$categoryId") // TODO: Hardcoded string
    }

    override suspend fun getProductExtendedInfo(productId: String): Result<MenuItemExpanded> {
        return NetworkManager.getRequest<MenuItemExpanded>("http://192.168.1.162:8080/api/v1/menu/items/$productId") // TODO: Hardcoded string
    }

    override suspend fun getAllCategories(): Result<MenuCategories> {
        return NetworkManager.getRequest<MenuCategories>("http://192.168.1.162:8080/api/v1/menu/categories")
    }
}
