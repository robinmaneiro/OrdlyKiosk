package com.robinmaneiro.ordly.kiosk.menu.usecase

import com.robinmaneiro.ordly.kiosk.menu.model.MenuResponse
import com.robinmaneiro.ordly.kiosk.menu.repository.MenuRepository

class GetProductsByCategoryUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(categoryId: String): Result<MenuResponse> {
        return menuRepository.getProductsByCategory(categoryId)
    }
}
