package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.model.LoginPayload
import com.robinmaneiro.orderkiosk.account.model.LoginResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class LoginUserUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository,
    private val accountDetailsUseCase: AccountDetailsUseCase
) {
    // TODO: What's a better approach for this? Move it to its own AuthUseCase with all of the integrated operations within? And just call other use cases from here?
    suspend operator fun invoke(loginPayload: LoginPayload): Result<LoginResponse> {
        val payload = Mapper.asSerializedStringResult(loginPayload).getOrElse { exception -> return Result.failure(exception) }

        return accountRepository.login(payload)
            .onSuccess { loginResponse ->
                accountDetailsUseCase.invoke()
                    .onSuccess { accountDetailsResponse ->
                        dataStore.saveTokenPair(
                            accessToken = loginResponse.accessToken,
                            refreshToken = loginResponse.refreshToken
                        )

                        dataStore.saveAccountDetails(accountDetailsResponse)
                    }
            }
    }
}
