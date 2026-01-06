package com.robinmaneiro.orderkiosk.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

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
