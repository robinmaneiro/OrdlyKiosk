package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class AddToBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(productId: String) { // TODO: Add return
        bagRepository.addToBag(productId, 1)
    }
}