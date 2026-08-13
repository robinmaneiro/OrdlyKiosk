package com.robinmaneiro.ordly.kiosk.account.repository

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.ordly.kiosk.networking.NetworkManager
import com.robinmaneiro.ordly.kiosk.util.SERVER_BASE_URL

class AccountRepositoryImpl(private val networkManager: NetworkManager) : AccountRepository {
    override suspend fun getAccountDetails(): Result<AccountDetailsResponse> {
        return networkManager.getRequest("$SERVER_BASE_URL/api/v1/users/me")
    }

    override suspend fun updatePhoneNumber(payload: String): Result<AccountDetailsResponse> {
        return networkManager.patchRequest("$SERVER_BASE_URL/api/v1/guests/me/phone", stringBody = payload)
    }

    override suspend fun updateDateOfBirth(payload: String): Result<AccountDetailsResponse> {
        return networkManager.patchRequest("$SERVER_BASE_URL/api/v1/guests/me/dateOfBirth", stringBody = payload)
    }

    override suspend fun updateEmailAddress(payload: String): Result<AccountDetailsResponse> {
        return networkManager.patchRequest("$SERVER_BASE_URL/api/v1/guests/me/email", stringBody = payload)
    }
}
