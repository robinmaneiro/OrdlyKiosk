package com.robinmaneiro.ordly.kiosk.networking

interface TokenRefresher {
    suspend fun refreshAuthToken()
    suspend fun refreshGuestSession()
}
