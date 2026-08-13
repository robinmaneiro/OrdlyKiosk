package com.robinmaneiro.ordly.kiosk.menu.repository

import com.robinmaneiro.ordly.kiosk.menu.model.MenuCategories
import com.robinmaneiro.ordly.kiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.ordly.kiosk.menu.model.MenuResponse
import com.robinmaneiro.ordly.kiosk.networking.NetworkManager
import com.robinmaneiro.ordly.kiosk.util.SERVER_BASE_URL

class MenuRepositoryImpl(private val networkManager: NetworkManager) : MenuRepository {
    override suspend fun getProductsByCategory(categoryId: String): Result<MenuResponse> {
        return networkManager.getRequest<MenuResponse>("$SERVER_BASE_URL/api/v1/menu/categories/$categoryId")
    }

    override suspend fun getProductExtendedInfo(productId: String): Result<MenuItemExpanded> {
        return networkManager.getRequest<MenuItemExpanded>("$SERVER_BASE_URL/api/v1/menu/items/$productId")
    }

    override suspend fun getAllCategories(): Result<MenuCategories> {
        return networkManager.getRequest<MenuCategories>("$SERVER_BASE_URL/api/v1/menu/categories")
    }
}
