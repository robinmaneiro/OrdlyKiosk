package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.account.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class AccountRepositoryImpl : AccountRepository {
    override suspend fun registerUser(payload: String): Result<RegistrationResponse> {
        return NetworkManager.postRequest<RegistrationResponse>("http://192.168.1.162:8080/auth/register", stringBody = payload)
    }

    override suspend fun login(payload: String): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("http://192.168.1.162:8080/auth/login", stringBody = payload)
    }

    override suspend fun createGuestSession(): Result<TokenPairResponse> {
        return NetworkManager.getRequest<TokenPairResponse>("http://192.168.1.162:8080/api/v1/guest-session/create")
    }

    override suspend fun refreshGuestSession(): Result<TokenPairResponse> {
        return NetworkManager.postRequest<TokenPairResponse>("http://192.168.1.162:8080/api/v1/guest-session/refresh", stringBody = "") // TODO: Add body
    }

    override suspend fun getAccountDetails(): Result<AccountDetailsResponse> {
        return NetworkManager.getRequest("http://192.168.1.162:8080/account/details")
    }
}
