package com.robinmaneiro.orderkiosk.dashboard.usecase

import com.robinmaneiro.orderkiosk.dashboard.model.MenuCategories
import com.robinmaneiro.orderkiosk.networking.RequestManager

class GetMenuCategoriesUseCase {
    suspend operator fun invoke(): MenuCategories? {
        return RequestManager.getRequest<MenuCategories>("http://192.168.1.162:8080/menu/categories") // TODO: Hardcoded string
    }
}
