package com.robinmaneiro.orderkiosk.account.repository

import com.robinmaneiro.orderkiosk.account.model.LoginResponse
import com.robinmaneiro.orderkiosk.account.model.RegisterResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class AccountRepositoryImpl : AccountRepository {
    override suspend fun registerUser(payload: String) {
        NetworkManager.postRequest<RegisterResponse>("", payload)
    }

    override suspend fun login(email: String, password: String): LoginResponse? {
        val payload = """
            {
            "email": "$email",
            "password": "$password"
            }
            """

        return NetworkManager.postRequest<LoginResponse>("http://192.168.1.162:8080/auth/login", payload)
    }
}
