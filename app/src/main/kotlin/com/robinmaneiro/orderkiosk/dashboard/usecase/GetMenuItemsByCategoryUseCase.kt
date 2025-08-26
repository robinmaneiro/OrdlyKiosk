package com.robinmaneiro.orderkiosk.dashboard.usecase

import com.robinmaneiro.orderkiosk.dashboard.model.MenuProducts
import com.robinmaneiro.orderkiosk.networking.RequestManager

class GetMenuItemsByCategoryUseCase {
    suspend operator fun invoke(categoryId: String): MenuProducts? {
        return RequestManager.getRequest<MenuProducts>("http://192.168.1.162:8080/menu/categories/$categoryId") // TODO: Hardcoded string
    }
}
