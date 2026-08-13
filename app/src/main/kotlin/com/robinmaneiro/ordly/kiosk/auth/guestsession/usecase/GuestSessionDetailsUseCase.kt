package com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase

import com.robinmaneiro.ordly.kiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.ordly.kiosk.auth.repository.AuthRepository

class GuestSessionDetailsUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<GuestSessionDetailsResponse> {
        return authRepository.getGuestSessionDetails()
    }
}
