package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.RegisterPayload

interface AccountRepository {
    suspend fun registerUser(payload: String)

    suspend fun login(userName: String, password: String)
}
