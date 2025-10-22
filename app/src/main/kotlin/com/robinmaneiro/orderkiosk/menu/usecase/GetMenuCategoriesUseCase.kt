package com.robinmaneiro.orderkiosk.menu.usecase

import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class GetMenuCategoriesUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(): Result<MenuCategories> {
        return menuRepository.getAllCategories()
    }
}
