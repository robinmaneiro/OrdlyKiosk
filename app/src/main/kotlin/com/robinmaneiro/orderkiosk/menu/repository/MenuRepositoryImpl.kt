package com.robinmaneiro.orderkiosk.menu.repository

import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import com.robinmaneiro.orderkiosk.util.SERVER_BASE_URL

class MenuRepositoryImpl : MenuRepository {
    override suspend fun getProductsByCategory(categoryId: String): Result<MenuResponse> {
        return NetworkManager.getRequest<MenuResponse>("$SERVER_BASE_URL/api/v1/menu/categories/$categoryId")
    }

    override suspend fun getProductExtendedInfo(productId: String): Result<MenuItemExpanded> {
        return NetworkManager.getRequest<MenuItemExpanded>("$SERVER_BASE_URL/api/v1/menu/items/$productId")
    }

    override suspend fun getAllCategories(): Result<MenuCategories> {
        return NetworkManager.getRequest<MenuCategories>("$SERVER_BASE_URL/api/v1/menu/categories")
    }
}
