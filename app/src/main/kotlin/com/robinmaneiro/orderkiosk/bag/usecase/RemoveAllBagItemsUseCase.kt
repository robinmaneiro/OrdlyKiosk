package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class RemoveAllBagItemsUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagId: String): Result<BagResponse> {
        return bagRepository.removeAllBagItems(bagId)
    }
}
