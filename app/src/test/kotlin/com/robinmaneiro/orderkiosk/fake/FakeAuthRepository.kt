package com.robinmaneiro.orderkiosk.fake

import com.robinmaneiro.orderkiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.auth.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository

class FakeAuthRepository : AuthRepository {
    var registerUserResult: Result<RegistrationResponse> = Result.failure(IllegalStateException("Not configured"))
    var loginResult: Result<TokenPairResponse> = Result.failure(IllegalStateException("Not configured"))
    var refreshTokenResult: Result<TokenPairResponse> = Result.failure(IllegalStateException("Not configured"))
    var createGuestSessionResult: Result<TokenPairResponse> = Result.failure(IllegalStateException("Not configured"))
    var refreshGuestSessionResult: Result<TokenPairResponse> = Result.failure(IllegalStateException("Not configured"))
    var getGuestSessionDetailsResult: Result<GuestSessionDetailsResponse> = Result.failure(IllegalStateException("Not configured"))

    override suspend fun registerUser(payload: String): Result<RegistrationResponse> = registerUserResult

    override suspend fun login(payload: String): Result<TokenPairResponse> = loginResult

    override suspend fun refreshToken(payload: String): Result<TokenPairResponse> = refreshTokenResult

    override suspend fun createGuestSession(): Result<TokenPairResponse> = createGuestSessionResult

    override suspend fun refreshGuestSession(payload: String): Result<TokenPairResponse> = refreshGuestSessionResult

    override suspend fun getGuestSessionDetails(): Result<GuestSessionDetailsResponse> = getGuestSessionDetailsResult
}
