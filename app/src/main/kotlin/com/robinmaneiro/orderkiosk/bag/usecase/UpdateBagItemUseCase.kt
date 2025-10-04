package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class UpdateBagItemUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagItemId: String, newQuantity: Int): Result<BagResponse> {
        return bagRepository.updateBagItem(bagItemId, newQuantity)
    }
}
