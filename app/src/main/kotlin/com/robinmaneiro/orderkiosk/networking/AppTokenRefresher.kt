package com.robinmaneiro.orderkiosk.networking

import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.orderkiosk.auth.usecase.RefreshTokenUseCase

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
