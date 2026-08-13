package com.robinmaneiro.ordly.kiosk.fake

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.ordly.kiosk.account.repository.AccountRepository

class FakeAccountRepository : AccountRepository {
    var getAccountDetailsResult: Result<AccountDetailsResponse> = Result.failure(IllegalStateException("Not configured"))
    var updatePhoneResult: Result<AccountDetailsResponse> = Result.failure(IllegalStateException("Not configured"))
    var updateDobResult: Result<AccountDetailsResponse> = Result.failure(IllegalStateException("Not configured"))
    var updateEmailResult: Result<AccountDetailsResponse> = Result.failure(IllegalStateException("Not configured"))

    override suspend fun getAccountDetails(): Result<AccountDetailsResponse> = getAccountDetailsResult

    override suspend fun updatePhoneNumber(payload: String): Result<AccountDetailsResponse> = updatePhoneResult

    override suspend fun updateDateOfBirth(payload: String): Result<AccountDetailsResponse> = updateDobResult

    override suspend fun updateEmailAddress(payload: String): Result<AccountDetailsResponse> = updateEmailResult
}
