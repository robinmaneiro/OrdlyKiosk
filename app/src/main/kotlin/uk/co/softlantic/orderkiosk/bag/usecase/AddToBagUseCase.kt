package uk.co.softlantic.orderkiosk.bag.usecase

import uk.co.softlantic.orderkiosk.bag.model.AddToBagPayload
import uk.co.softlantic.orderkiosk.bag.model.BagResponse
import uk.co.softlantic.orderkiosk.bag.repository.BagRepository
import uk.co.softlantic.orderkiosk.util.Mapper

class AddToBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagId: String, addToBagPayload: AddToBagPayload): Result<BagResponse> {
        val payload = Mapper.asSerializedStringResult(addToBagPayload).getOrElse { exception -> return Result.failure(exception) }
        return bagRepository.addToBag(bagId, payload)
    }
}
