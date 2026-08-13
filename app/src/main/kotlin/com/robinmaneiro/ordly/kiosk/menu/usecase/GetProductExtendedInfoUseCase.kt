package com.robinmaneiro.ordly.kiosk.menu.usecase

import com.robinmaneiro.ordly.kiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.ordly.kiosk.menu.repository.MenuRepository

class GetProductExtendedInfoUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(productId: String): Result<MenuItemExpanded> {
        return menuRepository.getProductExtendedInfo(productId)
    }
}
