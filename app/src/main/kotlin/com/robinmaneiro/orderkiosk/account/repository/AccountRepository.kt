package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.account.login.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.account.registration.model.RegistrationResponse

interface AccountRepository {
    suspend fun registerUser(payload: String): Result<RegistrationResponse>

    suspend fun login(payload: String): Result<TokenPairResponse>

    suspend fun refreshToken(payload: String): Result<TokenPairResponse>

    suspend fun createGuestSession(): Result<TokenPairResponse>

    suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse>

    suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse>

    suspend fun getAccountDetails(): Result<AccountDetailsResponse>

    suspend fun updatePhoneNumber(payload: String): Result<AccountDetailsResponse>

    suspend fun updateDateOfBirth(payload: String): Result<AccountDetailsResponse>

    suspend fun updateEmailAddress(payload: String): Result<AccountDetailsResponse>
}
