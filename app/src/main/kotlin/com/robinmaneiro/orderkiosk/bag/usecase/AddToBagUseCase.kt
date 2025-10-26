package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.AddToBagPayload
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class AddToBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagId: String, addToBagPayload: AddToBagPayload): Result<BagResponse> {
        val payload = Mapper.asSerializedStringResult(addToBagPayload).getOrElse { exception -> return Result.failure(exception) }
        return bagRepository.addToBag(bagId, payload)
    }
}
