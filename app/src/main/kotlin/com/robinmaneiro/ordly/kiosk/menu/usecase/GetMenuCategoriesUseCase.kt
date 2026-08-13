package com.robinmaneiro.ordly.kiosk.menu.usecase

import com.robinmaneiro.ordly.kiosk.menu.model.MenuCategories
import com.robinmaneiro.ordly.kiosk.menu.repository.MenuRepository

class GetMenuCategoriesUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(): Result<MenuCategories> {
        return menuRepository.getAllCategories()
    }
}
