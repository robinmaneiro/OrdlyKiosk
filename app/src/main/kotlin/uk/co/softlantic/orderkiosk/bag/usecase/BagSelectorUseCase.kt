package uk.co.softlantic.orderkiosk.bag.usecase

import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository

class BagSelectorUseCase(
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(): String {
        return if (dataStore.isUserLoggedIn()) dataStore.getAuthBagId() else dataStore.getGuestBagId()
    }
}
