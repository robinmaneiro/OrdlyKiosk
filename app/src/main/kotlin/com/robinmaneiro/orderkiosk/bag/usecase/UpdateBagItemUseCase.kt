package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.model.UpdateBagItemPayload
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class UpdateBagItemUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(updateBagItemPayload: UpdateBagItemPayload): Result<BagResponse> {
        return bagRepository.updateBagItem(
            payload = Mapper.asSerializedStringResult(updateBagItemPayload).getOrElse { return Result.failure(it) },
            bagItemId = updateBagItemPayload.bagItemId
        )
    }
}
