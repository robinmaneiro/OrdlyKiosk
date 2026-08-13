package com.robinmaneiro.ordly.kiosk.bag.usecase

import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository

class BagSelectorUseCase(
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(): String {
        return if (dataStore.isUserLoggedIn()) dataStore.getAuthBagId() else dataStore.getGuestBagId()
    }
}
