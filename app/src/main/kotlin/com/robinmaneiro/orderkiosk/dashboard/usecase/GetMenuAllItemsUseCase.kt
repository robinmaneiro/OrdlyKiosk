package com.robinmaneiro.orderkiosk.dashboard.usecase

import com.robinmaneiro.orderkiosk.dashboard.model.MenuProducts
import com.robinmaneiro.orderkiosk.networking.RequestManager

class GetMenuAllItemsUseCase() {
    suspend operator fun invoke(): MenuProducts? {
        return RequestManager.getRequest<MenuProducts>("http://192.168.1.162:8080/menu/items/all") // TODO: Hardcoded string
    }
}
