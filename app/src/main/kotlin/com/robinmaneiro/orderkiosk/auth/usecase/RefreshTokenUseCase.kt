package com.robinmaneiro.orderkiosk.auth.usecase

import com.robinmaneiro.orderkiosk.auth.model.RefreshTokenPayload
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.util.Mapper
import com.robinmaneiro.orderkiosk.util.extensions.errorLog

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
