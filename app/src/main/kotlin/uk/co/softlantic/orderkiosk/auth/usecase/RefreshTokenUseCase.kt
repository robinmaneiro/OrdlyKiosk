package uk.co.softlantic.orderkiosk.auth.usecase

import uk.co.softlantic.orderkiosk.auth.model.RefreshTokenPayload
import uk.co.softlantic.orderkiosk.auth.repository.AuthRepository
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository
import uk.co.softlantic.orderkiosk.util.Mapper
import uk.co.softlantic.orderkiosk.util.extensions.errorLog

class RefreshTokenUseCase(
    private val authRepository: AuthRepository,
    private val dataStore: DataStoreRepository
) {
    suspend operator fun invoke() {
        val payload = Mapper.asSerializedStringResult(RefreshTokenPayload(dataStore.getAuthRefreshToken())).getOrElse { exception ->
            errorLog(exception) { "Failed to deserialize refresh token" }
            return
        }
        authRepository.refreshToken(payload)
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
