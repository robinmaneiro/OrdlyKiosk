package com.robinmaneiro.ordly.kiosk.bag.usecase

import com.robinmaneiro.ordly.kiosk.bag.model.BagResponse
import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepository

class GetBagUseCase(
    private val bagRepository: BagRepository,
) {
    suspend operator fun invoke(bagId: String): Result<BagResponse> {
        return bagRepository.getBagItems(bagId)
    }
}
