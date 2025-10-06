package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.AddToBagPayload
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class AddToBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(payload: AddToBagPayload): Result<BagResponse> {
        return bagRepository.addToBag(Mapper.asSerializedStringResult(payload).getOrElse { return Result.failure(it) })
    }
}
