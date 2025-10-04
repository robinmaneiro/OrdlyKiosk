package com.robinmaneiro.orderkiosk.menu.usecase

import com.robinmaneiro.orderkiosk.menu.model.MenuResponse
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class GetProductsByCategoryUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(categoryId: String): Result<MenuResponse> {
        return menuRepository.getProductsByCategory(categoryId)
    }
}
