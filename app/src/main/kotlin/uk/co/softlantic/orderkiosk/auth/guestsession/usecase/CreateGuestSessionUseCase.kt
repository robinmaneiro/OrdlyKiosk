package uk.co.softlantic.orderkiosk.auth.guestsession.usecase

import uk.co.softlantic.orderkiosk.auth.repository.AuthRepository
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository
import uk.co.softlantic.orderkiosk.util.extensions.errorLog

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
                        dataStore.saveGuestSessionDetails(guestSessionDetails)
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
