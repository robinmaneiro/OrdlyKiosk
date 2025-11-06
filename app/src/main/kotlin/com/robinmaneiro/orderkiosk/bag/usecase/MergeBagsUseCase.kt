package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class MergeBagsUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(sourceBagId: String, targetBagId: String) {
        bagRepository.mergeBags(sourceBagId, targetBagId)
    }
}