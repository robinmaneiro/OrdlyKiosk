package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.login.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.account.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class AccountRepositoryImpl : AccountRepository {
    override suspend fun registerUser(payload: String): Result<RegistrationResponse> {
        return NetworkManager.postRequest<RegistrationResponse>("http://192.168.1.162:8080/api/v1/auth/register", stringBody = payload)
    }

    override suspend fun login(payload: String): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("http://192.168.1.162:8080/api/v1/auth/login", stringBody = payload)
    }

    override suspend fun refreshToken(payload: String): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("http://192.168.1.162:8080/api/v1/auth/refresh", stringBody = payload)
    }

    override suspend fun createGuestSession(): Result<TokenPairResponse> {
        return NetworkManager.getRequest<TokenPairResponse>("http://192.168.1.162:8080/api/v1/guests/create")
    }

    override suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("http://192.168.1.162:8080/api/v1/guests/refresh", stringBody = payload)
    }

    override suspend fun getAccountDetails(): Result<AccountDetailsResponse> {
        return NetworkManager.getRequest("http://192.168.1.162:8080/api/v1/users/me")
    }

    override suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse> {
        return NetworkManager.getRequest("http://192.168.1.162:8080/api/v1/guests/me")
    }
}
