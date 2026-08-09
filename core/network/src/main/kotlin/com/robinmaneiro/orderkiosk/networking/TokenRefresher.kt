package com.robinmaneiro.orderkiosk.networking

interface TokenRefresher {
    suspend fun refreshAuthToken()
    suspend fun refreshGuestSession()
}
