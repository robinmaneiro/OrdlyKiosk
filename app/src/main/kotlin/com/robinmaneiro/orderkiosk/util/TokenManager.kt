package com.robinmaneiro.orderkiosk.util

import com.robinmaneiro.orderkiosk.account.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

object TokenManager {
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
