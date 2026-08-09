package com.robinmaneiro.orderkiosk.fake

import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuResponse
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class FakeMenuRepository : MenuRepository {
    var getCategoriesResult: Result<MenuCategories> = Result.failure(IllegalStateException("Not configured"))
    var getProductsByCategoryResult: Result<MenuResponse> = Result.failure(IllegalStateException("Not configured"))
    var getProductExtendedInfoResult: Result<MenuItemExpanded> = Result.failure(IllegalStateException("Not configured"))

    override suspend fun getProductsByCategory(categoryId: String): Result<MenuResponse> {
        return getProductsByCategoryResult
    }

    override suspend fun getProductExtendedInfo(productId: String): Result<MenuItemExpanded> {
        return getProductExtendedInfoResult
    }

    override suspend fun getAllCategories(): Result<MenuCategories> {
        return getCategoriesResult
    }
}
