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
                guestSessionDetailsUseCase.invoke()
                    .onSuccess{guestDetailsResponse ->
                        dataStore.saveGuestSessionPair(
                            accessToken = guestSessionResponse.accessToken,
                            refreshToken = guestSessionResponse.refreshToken
                        )

                        dataStore
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
