package com.robinmaneiro.orderkiosk.auth.guestsession.usecase

import com.robinmaneiro.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository

class GuestSessionDetailsUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<GuestSessionDetailsResponse> {
        return authRepository.getGuestSessionDetails()
    }
}
