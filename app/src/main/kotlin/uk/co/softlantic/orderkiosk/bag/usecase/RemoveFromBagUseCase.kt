package uk.co.softlantic.orderkiosk.bag.usecase

import uk.co.softlantic.orderkiosk.bag.model.BagResponse
import uk.co.softlantic.orderkiosk.bag.repository.BagRepository

class RemoveFromBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagId: String, bagItemId: String): Result<BagResponse> {
        return bagRepository.removeFromBag(bagId, bagItemId)
    }
}
