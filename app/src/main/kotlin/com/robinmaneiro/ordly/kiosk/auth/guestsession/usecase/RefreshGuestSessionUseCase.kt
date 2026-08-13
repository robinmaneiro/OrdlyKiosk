package com.robinmaneiro.ordly.kiosk.auth.guestsession.usecase

import com.robinmaneiro.ordly.kiosk.auth.model.RefreshTokenPayload
import com.robinmaneiro.ordly.kiosk.auth.repository.AuthRepository
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper
import com.robinmaneiro.ordly.kiosk.util.extensions.errorLog

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
