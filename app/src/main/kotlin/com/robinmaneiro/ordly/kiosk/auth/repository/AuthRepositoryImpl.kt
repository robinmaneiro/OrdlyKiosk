package com.robinmaneiro.ordly.kiosk.auth.repository

import com.robinmaneiro.ordly.kiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.ordly.kiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.ordly.kiosk.auth.model.TokenPairResponse
import com.robinmaneiro.ordly.kiosk.networking.NetworkManager
import com.robinmaneiro.ordly.kiosk.util.SERVER_BASE_URL

class AuthRepositoryImpl(private val networkManager: NetworkManager) : AuthRepository {
    override suspend fun registerUser(payload: String): Result<RegistrationResponse> {
        return networkManager.postRequest<RegistrationResponse>("$SERVER_BASE_URL/api/v1/auth/register", stringBody = payload)
    }

    override suspend fun login(payload: String): Result<TokenPairResponse> {
        return networkManager.postRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/auth/login", stringBody = payload)
    }

    override suspend fun refreshToken(payload: String): Result<TokenPairResponse> {
        return networkManager.postRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/auth/refresh", stringBody = payload)
    }

    override suspend fun createGuestSession(): Result<TokenPairResponse> {
        return networkManager.getRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/guests/create")
    }

    override suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse> {
        return networkManager.postRequest<TokenPairResponse>("$SERVER_BASE_URL/api/v1/guests/refresh", stringBody = payload)
    }

    override suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse> {
        return networkManager.getRequest("$SERVER_BASE_URL/api/v1/guests/me")
    }
}
