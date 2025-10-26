package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class GetBagUseCase(
    private val bagRepository: BagRepository,
) {
    suspend operator fun invoke(bagId: String): Result<BagResponse> {
        return bagRepository.getBagItems(bagId)
    }
}
