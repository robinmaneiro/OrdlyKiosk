package com.robinmaneiro.orderkiosk.menu.usecase

import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class GetMenuCategoriesUseCase {
    suspend operator fun invoke(): MenuCategories? {
        return NetworkManager.getRequest<MenuCategories>("http://192.168.1.162:8080/menu/categories") // TODO: Hardcoded string
    }
}
