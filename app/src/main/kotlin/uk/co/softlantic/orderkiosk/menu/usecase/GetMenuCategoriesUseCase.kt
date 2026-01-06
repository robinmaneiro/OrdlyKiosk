package uk.co.softlantic.orderkiosk.menu.usecase

import uk.co.softlantic.orderkiosk.menu.model.MenuCategories
import uk.co.softlantic.orderkiosk.menu.repository.MenuRepository

class GetMenuCategoriesUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(): Result<MenuCategories> {
        return menuRepository.getAllCategories()
    }
}
