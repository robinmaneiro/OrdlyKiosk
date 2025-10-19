package com.robinmaneiro.orderkiosk.menu.usecase

import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepositoryImpl
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class GetMenuCategoriesUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(): Result<MenuCategories> {
        return menuRepository.getAllCategories()
    }
}
