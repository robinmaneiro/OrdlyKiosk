package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.util.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.DataStoreRepositoryImpl

class LoginUserUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(userName: String, password: String) {
        val response = accountRepository.login(userName, password)
        response?.let {
            dataStore.saveAccessToken(response.accessToken)
            dataStore.saveRefreshToken(response.refreshToken) // TODO: Save both at once
        }
    }
}
