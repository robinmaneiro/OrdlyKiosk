package com.robinmaneiro.orderkiosk.networking

import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import kotlinx.coroutines.flow.StateFlow

class DataStoreTokenProvider(
    private val dataStoreRepository: DataStoreRepository
) : TokenProvider {
    override val currentToken: StateFlow<String> = dataStoreRepository.currentToken
    override suspend fun isUserLoggedIn(): Boolean = dataStoreRepository.isUserLoggedIn()
}
