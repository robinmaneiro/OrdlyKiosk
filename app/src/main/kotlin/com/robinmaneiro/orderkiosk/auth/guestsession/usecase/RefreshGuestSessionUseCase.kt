package com.robinmaneiro.orderkiosk.auth.guestsession.usecase

import com.robinmaneiro.orderkiosk.auth.model.RefreshTokenPayload
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper
import com.robinmaneiro.orderkiosk.util.extensions.errorLog

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
