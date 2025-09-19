package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class GetBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(): BagResponse? {
        return bagRepository.getBagItems()
    }
}
