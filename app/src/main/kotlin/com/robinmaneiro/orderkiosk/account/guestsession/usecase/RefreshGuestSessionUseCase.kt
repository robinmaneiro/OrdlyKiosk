package com.robinmaneiro.orderkiosk.account.guestsession.usecase

import com.robinmaneiro.orderkiosk.account.login.model.RefreshTokenPayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class RefreshGuestSessionUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke() {
        val payload = Mapper.asSerializedStringResult(RefreshTokenPayload(dataStore.getGuestRefreshToken())).getOrElse { return } // TODO: Better approach for exception?
        accountRepository.refreshGuestSession(payload)
            .onSuccess { response ->
                dataStore.saveGuestSessionPair(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            }
            .onFailure {
                // TODO: Handle failure
            }
    }
}