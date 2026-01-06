package uk.co.softlantic.orderkiosk.menu.usecase

import uk.co.softlantic.orderkiosk.menu.model.MenuItemExpanded
import uk.co.softlantic.orderkiosk.menu.repository.MenuRepository

class GetProductExtendedInfoUseCase(
    private val menuRepository: MenuRepository
) {
    suspend operator fun invoke(productId: String): Result<MenuItemExpanded> {
        return menuRepository.getProductExtendedInfo(productId)
    }
}
