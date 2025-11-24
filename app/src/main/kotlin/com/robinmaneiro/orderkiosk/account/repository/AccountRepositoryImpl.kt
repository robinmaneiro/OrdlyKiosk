package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.account.login.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import com.robinmaneiro.orderkiosk.util.SERVER_BASE_URL

class AccountRepositoryImpl : AccountRepository {
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

    override suspend fun getAccountDetails(): Result<AccountDetailsResponse> {
        return NetworkManager.getRequest("$SERVER_BASE_URL/api/v1/users/me")
    }

    override suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse> {
        return NetworkManager.getRequest("$SERVER_BASE_URL/api/v1/guests/me")
    }

    override suspend fun updatePhoneNumber(payload: String): Result<AccountDetailsResponse> {
        return NetworkManager.patchRequest("$SERVER_BASE_URL/api/v1/guests/me/phone", stringBody = payload)
    }

    override suspend fun updateDateOfBirth(payload: String): Result<AccountDetailsResponse> {
        return NetworkManager.patchRequest("$SERVER_BASE_URL/api/v1/guests/me/dateOfBirth", stringBody = payload)
    }

    override suspend fun updateEmailAddress(payload: String): Result<AccountDetailsResponse> {
        return NetworkManager.patchRequest("$SERVER_BASE_URL/api/v1/guests/me/email", stringBody = payload)
    }
}
