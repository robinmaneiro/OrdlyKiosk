package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class CreateGuestSessionUseCase(
    val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository,
    ) {
    suspend operator fun invoke() {
        accountRepository.createGuestSession()
            .onSuccess {guestSessionResponse ->
//                dataStore.saveTokenPair( TODO: Create new methods in the datastore
//                    accessToken = guestSessionResponse.accessToken,
//                    refreshToken = guestSessionResponse.refreshToken
//                )
        }
    }
}