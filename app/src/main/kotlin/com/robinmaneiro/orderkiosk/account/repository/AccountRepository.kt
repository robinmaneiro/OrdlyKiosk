package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.account.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.account.model.TokenPairResponse

interface AccountRepository {
    suspend fun registerUser(payload: String): Result<RegistrationResponse>

    suspend fun login(payload: String): Result<TokenPairResponse>

    suspend fun refreshToken(payload: String): Result<TokenPairResponse>

    suspend fun createGuestSession(): Result<TokenPairResponse>

    suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse>

    suspend fun getAccountDetails(): Result<AccountDetailsResponse>

    suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse>
}
