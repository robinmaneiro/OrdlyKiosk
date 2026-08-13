package com.robinmaneiro.ordly.kiosk.bag.usecase

import com.robinmaneiro.ordly.kiosk.bag.model.AddToBagPayload
import com.robinmaneiro.ordly.kiosk.bag.model.BagResponse
import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper

class AddToBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagId: String, addToBagPayload: AddToBagPayload): Result<BagResponse> {
        val payload = Mapper.asSerializedStringResult(addToBagPayload).getOrElse { exception -> return Result.failure(exception) }
        return bagRepository.addToBag(bagId, payload)
    }
}
