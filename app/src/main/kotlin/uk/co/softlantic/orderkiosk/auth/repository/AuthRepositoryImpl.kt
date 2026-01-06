package uk.co.softlantic.orderkiosk.auth.repository

import uk.co.softlantic.orderkiosk.account.registration.model.RegistrationResponse
import uk.co.softlantic.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import uk.co.softlantic.orderkiosk.auth.model.TokenPairResponse
import uk.co.softlantic.orderkiosk.networking.NetworkManager
import uk.co.softlantic.orderkiosk.util.SERVER_BASE_URL

class AuthRepositoryImpl : AuthRepository {
    override suspend fun registerUser(payload: String): Result<RegistrationResponse> {
        return NetworkManager.postRequest<RegistrationResponse>("$SERVER_BASE_URL/api/v1/auth/register", stringBody = payload)
    }

    override suspend fun login(payload: String): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/auth/login", stringBody = payload)
    }

    override suspend fun refreshToken(payload: String): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/auth/refresh", stringBody = payload)
    }

    override suspend fun createGuestSession(): Result<TokenPairResponse> {
        return NetworkManager.getRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/guests/create")
    }

    override suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/guests/refresh", stringBody = payload)
    }

    override suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse> {
        return NetworkManager.getRequest("$SERVER_BASE_URL/api/v1/guests/me")
    }
}
