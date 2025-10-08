package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.model.LoginPayload
import com.robinmaneiro.orderkiosk.account.model.LoginResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class LoginUserUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke(loginPayload: LoginPayload): Result<LoginResponse> {
        val payload = Mapper.asSerializedStringResult(loginPayload).getOrElse { exception -> return Result.failure(exception) }

        return accountRepository.login(payload)
            .onSuccess { response ->
                dataStore.saveTokenPair(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            }
    }
}
