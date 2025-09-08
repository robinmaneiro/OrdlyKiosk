package com.robinmaneiro.orderkiosk.menu.usecase

import com.robinmaneiro.orderkiosk.menu.model.MenuProducts
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class GetProductsByCategoryUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(categoryId: String): MenuProducts? {
        return menuRepository.getProductsByCategory(categoryId)
    }
}
