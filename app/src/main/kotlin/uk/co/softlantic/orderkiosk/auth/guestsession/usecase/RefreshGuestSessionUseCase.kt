package uk.co.softlantic.orderkiosk.auth.guestsession.usecase

import uk.co.softlantic.orderkiosk.auth.model.RefreshTokenPayload
import uk.co.softlantic.orderkiosk.auth.repository.AuthRepository
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository
import uk.co.softlantic.orderkiosk.util.Mapper
import uk.co.softlantic.orderkiosk.util.extensions.errorLog

class RefreshGuestSessionUseCase(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke() {
        val payload = Mapper.asSerializedStringResult(RefreshTokenPayload(dataStore.getGuestRefreshToken())).getOrElse { exception ->
            errorLog(exception) { "Failed to serialize guest refresh token" }
            return
        }
        authRepository.refreshGuestSession(payload)
            .onSuccess { response ->
                dataStore.saveGuestSessionPair(
                    accessToken = response.accessToken,
                    refreshToken = response.refreshToken
                )
            }
            .onFailure { exception ->
                errorLog(exception) { "Failed to refresh guest token" }
            }
    }
}
