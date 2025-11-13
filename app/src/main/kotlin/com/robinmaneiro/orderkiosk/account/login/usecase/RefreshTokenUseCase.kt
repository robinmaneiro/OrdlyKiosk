package com.robinmaneiro.orderkiosk.account.login.usecase

import com.robinmaneiro.orderkiosk.account.login.model.RefreshTokenPayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper
import com.robinmaneiro.orderkiosk.util.extensions.errorLog

class RefreshTokenUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke() {
        val payload = Mapper.asSerializedStringResult(RefreshTokenPayload(dataStore.getAuthRefreshToken())).getOrElse { exception ->
            errorLog(exception) { "Failed to deserialize refresh token" }
            return
        }
        accountRepository.refreshToken(payload)
            .onSuccess { response ->
                dataStore.saveAuthTokenPair(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            }
            .onFailure { e ->
                errorLog(e) { "Failed to refresh token" }
            }
    }
}
