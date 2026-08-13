package com.robinmaneiro.ordly.kiosk.bag.usecase

import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepository

class MergeBagsUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(sourceBagId: String, targetBagId: String) {
        bagRepository.mergeBags(sourceBagId, targetBagId)
    }
}
