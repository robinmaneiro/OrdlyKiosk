package uk.co.softlantic.orderkiosk.menu.repository

import uk.co.softlantic.orderkiosk.menu.model.MenuCategories
import uk.co.softlantic.orderkiosk.menu.model.MenuItemExpanded
import uk.co.softlantic.orderkiosk.menu.model.MenuResponse

interface MenuRepository {
    suspend fun getProductsByCategory(categoryId: String): Result<MenuResponse>
    suspend fun getProductExtendedInfo(productId: String): Result<MenuItemExpanded>
    suspend fun getAllCategories(): Result<MenuCategories>
}
