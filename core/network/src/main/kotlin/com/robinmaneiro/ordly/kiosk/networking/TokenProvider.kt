package com.robinmaneiro.ordly.kiosk.networking

import kotlinx.coroutines.flow.StateFlow

interface TokenProvider {
    val currentToken: StateFlow<String>
    suspend fun isUserLoggedIn(): Boolean
}
