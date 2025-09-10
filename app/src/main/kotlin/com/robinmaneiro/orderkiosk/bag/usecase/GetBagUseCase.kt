package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagItemResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class GetBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(): BagItemResponse? {
        return bagRepository.getBagItems()
    }
}
