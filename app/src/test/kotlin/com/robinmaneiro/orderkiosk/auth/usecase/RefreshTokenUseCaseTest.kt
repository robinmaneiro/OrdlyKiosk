package com.robinmaneiro.orderkiosk.auth.usecase

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.robinmaneiro.orderkiosk.auth.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class RefreshTokenUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val authRepository = mockk<AuthRepository>()

    // Mock the data store — holds the current refresh token and persists new tokens
    private val dataStore = mockk<DataStoreRepository>()

    // The use case under test, constructed with the mocked dependencies
    private val useCase = RefreshTokenUseCase(authRepository, dataStore)

    @Test
    fun `invoke - saves new token pair on success`() = runTest {
        // Arrange: data store provides the current refresh token; API returns a fresh token pair
        val newAccessToken = "new-access-token"
        val newRefreshToken = "new-refresh-token"
        coEvery { dataStore.getAuthRefreshToken() } returns "old-refresh-token"
        coEvery { authRepository.refreshToken(any()) } returns Result.success(
            TokenPairResponse(accessToken = newAccessToken, refreshToken = newRefreshToken)
        )
        coJustRun { dataStore.saveAuthTokenPair(newAccessToken, newRefreshToken) }

        // Execute: refresh the auth token using the stored refresh token
        useCase()

        // Assert: both new tokens are persisted so subsequent requests use them
        coVerify(exactly = 1) { dataStore.saveAuthTokenPair(newAccessToken, newRefreshToken) }
    }

    @Test
    fun `invoke - does not save tokens on repository failure`() = runTest {
        // Arrange: refresh call fails (e.g. the refresh token has expired)
        coEvery { dataStore.getAuthRefreshToken() } returns "old-refresh-token"
        coEvery { authRepository.refreshToken(any()) } returns Result.failure(Exception("Refresh failed"))

        // Execute: refresh the auth token using the stored refresh token
        useCase()

        // Assert: no tokens are written — the old tokens remain untouched
        coVerify(exactly = 0) { dataStore.saveAuthTokenPair(any(), any()) }
    }
}
