package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.LoginResponse
import com.robinmaneiro.orderkiosk.account.model.RegisterResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class AccountRepositoryImpl : AccountRepository {
    override suspend fun registerUser(payload: String) {
        NetworkManager.postRequest<RegisterResponse>("", payload)
    }

    override suspend fun login(userName: String, password: String) {
        val payload = """
            userName: $userName,
            password: $password
            """

        NetworkManager.postRequest<LoginResponse>("", payload)
    }
}
