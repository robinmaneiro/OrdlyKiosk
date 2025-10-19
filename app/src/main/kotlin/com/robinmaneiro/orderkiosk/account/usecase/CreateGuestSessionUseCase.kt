package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class CreateGuestSessionUseCase(
    private val accountRepository: AccountRepository,
    private val dataStore: DataStoreRepository,
    private val guestSessionDetailsUseCase: GuestSessionDetailsUseCase
) {
    suspend operator fun invoke() {
        accountRepository.createGuestSession()
            .onSuccess { guestSessionResponse ->
                dataStore.saveGuestSessionPair(
                    accessToken = guestSessionResponse.accessToken,
                    refreshToken = guestSessionResponse.refreshToken
                ) // TODO: Remove if the next fails?
                guestSessionDetailsUseCase.invoke()
                    .onSuccess{guestDetailsResponse ->
                    }
                    .onFailure {
                        // TODO: handle on getting guest details use case
                    }

            }
            .onFailure {
                // TODO: handle create guest session failure
            }
    }
}
