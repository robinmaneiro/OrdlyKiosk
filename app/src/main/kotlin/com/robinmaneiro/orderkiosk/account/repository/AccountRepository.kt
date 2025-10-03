package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.LoginResponse
import com.robinmaneiro.orderkiosk.account.model.RegistrationResponse

interface AccountRepository {
    suspend fun registerUser(payload: String): RegistrationResponse?

    suspend fun login(payload: String): LoginResponse?
}
