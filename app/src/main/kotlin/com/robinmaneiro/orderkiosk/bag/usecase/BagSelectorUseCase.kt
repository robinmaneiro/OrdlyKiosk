package com.robinmaneiro.orderkiosk.bag.usecase

import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class BagSelectorUseCase(
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(): String {
        return if (dataStore.isUserLoggedIn()) dataStore.getAuthBagId() else dataStore.getGuestBagId()
    }
}
