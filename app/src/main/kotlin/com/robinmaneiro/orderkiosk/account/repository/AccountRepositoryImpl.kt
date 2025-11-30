package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import com.robinmaneiro.orderkiosk.util.SERVER_BASE_URL

class AccountRepositoryImpl : AccountRepository {
    override suspend fun getAccountDetails(): Result<AccountDetailsResponse> {
        return NetworkManager.getRequest("$SERVER_BASE_URL/api/v1/users/me")
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
