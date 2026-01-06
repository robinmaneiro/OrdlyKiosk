package uk.co.softlantic.orderkiosk.bag.usecase

import uk.co.softlantic.orderkiosk.bag.model.BagResponse
import uk.co.softlantic.orderkiosk.bag.model.UpdateBagItemPayload
import uk.co.softlantic.orderkiosk.bag.repository.BagRepository
import uk.co.softlantic.orderkiosk.util.Mapper

class UpdateBagItemUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagId: String, updateBagItemPayload: UpdateBagItemPayload): Result<BagResponse> {
        val payload = Mapper.asSerializedStringResult(updateBagItemPayload).getOrElse { exception -> return Result.failure(exception) }
        return bagRepository.updateBagItem(
            bagId = bagId,
            payload = payload,
            bagItemId = updateBagItemPayload.bagItemId
        )
    }
}
