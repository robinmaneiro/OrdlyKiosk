package com.robinmaneiro.orderkiosk.account.login.usecase

import com.robinmaneiro.orderkiosk.account.login.model.LoginPayload
import com.robinmaneiro.orderkiosk.account.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class LoginUserUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository,
    private val accountDetailsUseCase: AccountDetailsUseCase,
    private val getBagUseCase: GetBagUseCase
) {
    // TODO: What's a better approach for this? Move it to its own AuthUseCase with all of the integrated operations within? And just call other use cases from here?
    suspend operator fun invoke(loginPayload: LoginPayload): Result<TokenPairResponse> {
        val payload = Mapper.asSerializedStringResult(loginPayload).getOrElse { exception -> return Result.failure(exception) }

        return accountRepository.login(payload)
            .onSuccess { loginResponse ->
                dataStore.saveAuthTokenPair(
                    accessToken = loginResponse.accessToken,
                    refreshToken = loginResponse.refreshToken
                )
                accountDetailsUseCase.invoke()
                    .onSuccess { accountDetailsResponse ->
                        dataStore.saveAccountDetails(accountDetailsResponse)
                        getBagUseCase.invoke(dataStore.getAuthBagId())
                    }
                    .onFailure {
                        // TODO: handle account details failure (removing auth tokens too)
                    }
            }
            .onFailure {
                // TODO: handle login failure
            }
    }
}
