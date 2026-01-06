package uk.co.softlantic.orderkiosk.auth.repository

import uk.co.softlantic.orderkiosk.account.registration.model.RegistrationResponse
import uk.co.softlantic.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import uk.co.softlantic.orderkiosk.auth.model.TokenPairResponse

interface AuthRepository {
    suspend fun registerUser(payload: String): Result<RegistrationResponse>

    suspend fun login(payload: String): Result<TokenPairResponse>

    suspend fun refreshToken(payload: String): Result<TokenPairResponse>

    suspend fun createGuestSession(): Result<TokenPairResponse>

    suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse>

    suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse>
}
