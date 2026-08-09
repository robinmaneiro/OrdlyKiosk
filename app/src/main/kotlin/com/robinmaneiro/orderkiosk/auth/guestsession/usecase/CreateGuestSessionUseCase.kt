package com.robinmaneiro.orderkiosk.auth.guestsession.usecase

import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.datastore.GuestSessionDetails
import com.robinmaneiro.orderkiosk.util.extensions.errorLog

class CreateGuestSessionUseCase(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreRepository,
    private val guestSessionDetailsUseCase: GuestSessionDetailsUseCase
) {
    suspend operator fun invoke() {
        authRepository.createGuestSession()
            .onSuccess { guestSessionResponse ->
                dataStore.saveGuestSessionPair(
                    accessToken = guestSessionResponse.accessToken,
                    refreshToken = guestSessionResponse.refreshToken
                )
                guestSessionDetailsUseCase.invoke()
                    .onSuccess { guestSessionDetails ->
                        dataStore.saveGuestSessionDetails(
                            GuestSessionDetails(
                                guestBagId = guestSessionDetails.guestBagId,
                                guestWishlistId = guestSessionDetails.guestWishlistId
                            )
                        )
                    }
                    .onFailure { exception ->
                        errorLog(exception) { "There has been an issue when attempting to retrieve guest session data" }
                        dataStore.clearDataStore()
                    }
            }
            .onFailure { exception ->
                errorLog(exception) { "Something went wrong when creating a guest session" }
            }
    }
}
