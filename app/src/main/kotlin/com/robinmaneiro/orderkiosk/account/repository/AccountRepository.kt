package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.RegisterPayload

interface AccountRepository {
    suspend fun registerUser(payload: RegisterPayload)

    suspend fun login(userName: String, password: String)
}
