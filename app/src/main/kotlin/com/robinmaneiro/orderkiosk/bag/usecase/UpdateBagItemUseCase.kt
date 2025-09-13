package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagItemResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class UpdateBagItemUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(bagItemId: String, newQuantity: Int): BagItemResponse? {
        return bagRepository.updateBagItem(bagItemId, newQuantity)
    }
}
