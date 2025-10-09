package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.AccountResponse
import com.robinmaneiro.orderkiosk.account.model.LoginResponse
import com.robinmaneiro.orderkiosk.account.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class AccountRepositoryImpl : AccountRepository {
    override suspend fun registerUser(payload: String): Result<RegistrationResponse> {
        return NetworkManager.postRequest<RegistrationResponse>("http://192.168.1.162:8080/auth/register", payload)
    }

    override suspend fun login(payload: String): Result<LoginResponse> {
        return NetworkManager.postRequest<LoginResponse>("http://192.168.1.162:8080/auth/login", payload)
    }

    override suspend fun getAccountDetails(): Result<AccountResponse> {
        return NetworkManager.getRequest("http://192.168.162.8080/auth/user")
    }
}
