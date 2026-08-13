package com.robinmaneiro.ordly.kiosk.auth.usecase

import com.robinmaneiro.ordly.kiosk.auth.model.RefreshTokenPayload
import com.robinmaneiro.ordly.kiosk.auth.repository.AuthRepository
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper
import com.robinmaneiro.ordly.kiosk.util.extensions.errorLog

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
