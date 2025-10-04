package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.model.LoginPayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.util.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class LoginUserUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(payload: LoginPayload) {
        accountRepository.login(Mapper.asSerializedString(payload) ?: return)
            .onSuccess { response ->
                dataStore.saveAccessToken(response.accessToken)
                dataStore.saveRefreshToken(response.refreshToken) // TODO: Save both at once
            }
    }
}
