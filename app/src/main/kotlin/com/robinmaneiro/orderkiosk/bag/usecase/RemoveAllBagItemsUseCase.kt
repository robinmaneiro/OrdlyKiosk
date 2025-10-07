package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class RemoveAllBagItemsUseCase(
    val bagRepository: BagRepository
) {
    suspend operator fun invoke(): Result<BagResponse> {
        return bagRepository.removeAllBagItems()
    }
}
