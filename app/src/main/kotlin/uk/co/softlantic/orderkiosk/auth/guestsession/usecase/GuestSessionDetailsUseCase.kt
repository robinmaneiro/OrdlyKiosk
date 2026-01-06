package uk.co.softlantic.orderkiosk.auth.guestsession.usecase

import uk.co.softlantic.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import uk.co.softlantic.orderkiosk.auth.repository.AuthRepository

class GuestSessionDetailsUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(): Result<GuestSessionDetailsResponse> {
        return authRepository.getGuestSessionDetails()
    }
}
