package uk.co.softlantic.orderkiosk.util

import uk.co.softlantic.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object SessionManager {
    fun initialize(
        dataStore: DataStoreRepository,
        guestSessionDetailsUseCase: CreateGuestSessionUseCase
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val hasAuthAccessToken = dataStore.getAuthAccessToken().isNotEmpty()
            val hasGuestSessionToken = dataStore.getGuestAccessToken().isNotEmpty()

            if (hasAuthAccessToken || hasGuestSessionToken) return@launch

            guestSessionDetailsUseCase.invoke() // Create a brand-new guest session
        }
    }
}
