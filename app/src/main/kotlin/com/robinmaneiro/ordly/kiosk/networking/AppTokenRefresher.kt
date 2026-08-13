package com.robinmaneiro.ordly.kiosk.networking

import com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.ordly.kiosk.auth.usecase.RefreshTokenUseCase

class AppTokenRefresher(
    private val refreshTokenUseCase: Lazy<RefreshTokenUseCase>,
    private val refreshGuestSessionUseCase: Lazy<RefreshGuestSessionUseCase>
) : TokenRefresher {
    override suspend fun refreshAuthToken() {
        refreshTokenUseCase.value.invoke()
    }

    override suspend fun refreshGuestSession() {
        refreshGuestSessionUseCase.value.invoke()
    }
}
