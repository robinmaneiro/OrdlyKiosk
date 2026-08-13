package com.robinmaneiro.ordly.kiosk.networking

import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository
import kotlinx.coroutines.flow.StateFlow

class DataStoreTokenProvider(
    private val dataStoreRepository: DataStoreRepository
) : TokenProvider {
    override val currentToken: StateFlow<String> = dataStoreRepository.currentToken
    override suspend fun isUserLoggedIn(): Boolean = dataStoreRepository.isUserLoggedIn()
}
