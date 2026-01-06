package uk.co.softlantic.orderkiosk.menu.usecase

import uk.co.softlantic.orderkiosk.menu.model.MenuResponse
import uk.co.softlantic.orderkiosk.menu.repository.MenuRepository

class GetProductsByCategoryUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(categoryId: String): Result<MenuResponse> {
        return menuRepository.getProductsByCategory(categoryId)
    }
}
