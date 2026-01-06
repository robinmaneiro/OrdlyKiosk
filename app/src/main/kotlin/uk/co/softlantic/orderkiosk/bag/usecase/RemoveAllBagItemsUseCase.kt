package uk.co.softlantic.orderkiosk.bag.usecase

import uk.co.softlantic.orderkiosk.bag.model.BagResponse
import uk.co.softlantic.orderkiosk.bag.repository.BagRepository

class RemoveAllBagItemsUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagId: String): Result<BagResponse> {
        return bagRepository.removeAllBagItems(bagId)
    }
}
