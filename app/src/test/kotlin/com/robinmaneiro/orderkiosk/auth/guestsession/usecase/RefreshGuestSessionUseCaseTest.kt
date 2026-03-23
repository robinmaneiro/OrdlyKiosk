package com.robinmaneiro.orderkiosk.auth.guestsession.usecase

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.robinmaneiro.orderkiosk.auth.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class RefreshGuestSessionUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val authRepository = mockk<AuthRepository>()

    // Mock the data store — holds the current guest refresh token and persists new tokens
    private val dataStore = mockk<DataStoreRepository>()

    // The use case under test, constructed with the mocked dependencies
    private val useCase = RefreshGuestSessionUseCase(authRepository, dataStore)

    @Test
    fun `invoke - saves new guest session pair on success`() = runTest {
        // Arrange: data store provides the current guest refresh token; API returns a fresh pair
        val newAccessToken = "new-guest-access"
        val newRefreshToken = "new-guest-refresh"
        coEvery { dataStore.getGuestRefreshToken() } returns "old-guest-refresh"
        coEvery { authRepository.refreshGuestSession(any()) } returns Result.success(
            TokenPairResponse(accessToken = newAccessToken, refreshToken = newRefreshToken)
        )
        coJustRun { dataStore.saveGuestSessionPair(newAccessToken, newRefreshToken) }

        // Execute: refresh the guest session tokens
        useCase()

        // Assert: the new token pair is persisted so subsequent calls use them
        coVerify(exactly = 1) { dataStore.saveGuestSessionPair(newAccessToken, newRefreshToken) }
    }

    @Test
    fun `invoke - does not save tokens on repository failure`() = runTest {
        // Arrange: refresh call fails (e.g. the guest refresh token has expired)
        coEvery { dataStore.getGuestRefreshToken() } returns "old-guest-refresh"
        coEvery { authRepository.refreshGuestSession(any()) } returns Result.failure(Exception("Refresh failed"))

        // Execute: refresh the guest session tokens
        useCase()

        // Assert: no tokens are written — the old tokens remain untouched
        coVerify(exactly = 0) { dataStore.saveGuestSessionPair(any(), any()) }
    }
}
