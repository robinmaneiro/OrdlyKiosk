package com.robinmaneiro.orderkiosk.dashboard.usecase

import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.networking.RequestManager

class GetItemInfoUseCase {
    suspend operator fun invoke(itemId: String): MenuProductExpanded? {
        return RequestManager.getRequest<MenuProductExpanded>("http://192.168.1.162:8080/menu/items/$itemId") // TODO: Find a better path
    }
}
