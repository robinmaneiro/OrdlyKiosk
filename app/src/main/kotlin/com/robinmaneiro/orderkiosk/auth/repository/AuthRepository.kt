package com.robinmaneiro.orderkiosk.auth.repository

import com.robinmaneiro.orderkiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.auth.model.TokenPairResponse

interface AuthRepository {
    suspend fun registerUser(payload: String): Result<RegistrationResponse>

    suspend fun login(payload: String): Result<TokenPairResponse>

    suspend fun refreshToken(payload: String): Result<TokenPairResponse>

    suspend fun createGuestSession(): Result<TokenPairResponse>

    suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse>

    suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse>
}
