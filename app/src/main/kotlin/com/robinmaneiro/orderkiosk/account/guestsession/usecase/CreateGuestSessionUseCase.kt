package com.robinmaneiro.orderkiosk.account.guestsession.usecase

import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.extensions.errorLog

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
                    .onSuccess { guestSessionDetails ->
                        dataStore.saveGuestSessionDetails(guestSessionDetails)
                    }
                    .onFailure {
                        errorLog(it) { "There has been an issue when attempting to retrieve guest session data" }
                        dataStore.clearDataStore()
                    }
            }
            .onFailure {
                // TODO: handle create guest session failure
            }
    }
}
