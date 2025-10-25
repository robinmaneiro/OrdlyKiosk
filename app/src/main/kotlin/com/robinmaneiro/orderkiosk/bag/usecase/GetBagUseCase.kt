package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class GetBagUseCase(
    private val bagRepository: BagRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(): Result<BagResponse> {
        val bagId = dataStore.getAuthBagId().takeIf(String::isNotEmpty) ?: dataStore.getGuestBagId()
        return bagRepository.getBagItems()
    }
}
