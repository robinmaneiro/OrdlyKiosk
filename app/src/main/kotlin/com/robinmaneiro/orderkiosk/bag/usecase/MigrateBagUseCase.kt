package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class MigrateBagUseCase(
    private val bagRepository: BagRepository
) {
    suspend operator fun invoke(sourceBagId: String, targetBagId: String) {
        bagRepository.migrateBag(sourceBagId, targetBagId)
    }
}