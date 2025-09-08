package com.robinmaneiro.orderkiosk.menu.usecase

import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class GetProductExtendedInfoUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(productId: String): MenuItemExpanded? {
        return menuRepository.getProductExtendedInfo(productId)
    }
}
