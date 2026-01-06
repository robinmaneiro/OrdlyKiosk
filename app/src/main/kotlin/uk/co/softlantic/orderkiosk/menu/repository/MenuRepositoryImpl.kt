package uk.co.softlantic.orderkiosk.menu.repository

import uk.co.softlantic.orderkiosk.menu.model.MenuCategories
import uk.co.softlantic.orderkiosk.menu.model.MenuItemExpanded
import uk.co.softlantic.orderkiosk.menu.model.MenuResponse
import uk.co.softlantic.orderkiosk.networking.NetworkManager
import uk.co.softlantic.orderkiosk.util.SERVER_BASE_URL

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
