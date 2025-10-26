package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.model.UpdateBagItemPayload
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.util.Mapper

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
