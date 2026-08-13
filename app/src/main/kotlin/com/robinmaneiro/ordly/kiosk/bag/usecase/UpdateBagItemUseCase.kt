package com.robinmaneiro.ordly.kiosk.bag.usecase

import com.robinmaneiro.ordly.kiosk.bag.model.BagResponse
import com.robinmaneiro.ordly.kiosk.bag.model.UpdateBagItemPayload
import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper

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
