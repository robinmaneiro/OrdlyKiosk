package com.robinmaneiro.orderkiosk.dashboard.usecase

import com.robinmaneiro.orderkiosk.menu.model.MenuProducts
import com.robinmaneiro.orderkiosk.networking.RequestManager

class GetMenuItemsUseCase() {
    suspend operator fun invoke(): MenuProducts? {
        return RequestManager.getRequest<MenuProducts>("http://192.168.1.162:8080/menu_items") // TODO: Hardcoded string
    }
}
