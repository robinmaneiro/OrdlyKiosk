package com.robinmaneiro.ordly.kiosk.account.repository

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse

interface AccountRepository {
    suspend fun getAccountDetails(): Result<AccountDetailsResponse>

    suspend fun updatePhoneNumber(payload: String): Result<AccountDetailsResponse>

    suspend fun updateDateOfBirth(payload: String): Result<AccountDetailsResponse>

    suspend fun updateEmailAddress(payload: String): Result<AccountDetailsResponse>
}
