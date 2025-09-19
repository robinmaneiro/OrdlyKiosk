package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class RemoveFromBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagItemId: String): BagResponse? {
        return bagRepository.removeFromBag(bagItemId)
    }
}
