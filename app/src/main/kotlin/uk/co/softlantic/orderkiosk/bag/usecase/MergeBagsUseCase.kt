package uk.co.softlantic.orderkiosk.bag.usecase

import uk.co.softlantic.orderkiosk.bag.repository.BagRepository

class MergeBagsUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(sourceBagId: String, targetBagId: String) {
        bagRepository.mergeBags(sourceBagId, targetBagId)
    }
}
