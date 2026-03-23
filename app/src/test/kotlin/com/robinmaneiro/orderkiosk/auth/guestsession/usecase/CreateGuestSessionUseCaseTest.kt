package com.robinmaneiro.orderkiosk.auth.guestsession.usecase

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.robinmaneiro.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.auth.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class CreateGuestSessionUseCaseTest {

    // Mock all dependencies — creating a guest session involves auth and data persistence
    private val authRepository = mockk<AuthRepository>()
    private val dataStore = mockk<DataStoreRepository>()
    private val guestSessionDetailsUseCase = mockk<GuestSessionDetailsUseCase>()

    // The use case under test, constructed with all mocked dependencies
    private val useCase = CreateGuestSessionUseCase(authRepository, dataStore, guestSessionDetailsUseCase)

    // Shared test data — reused across multiple tests
    private val tokenPair = TokenPairResponse(accessToken = "guest-access", refreshToken = "guest-refresh")
    private val guestDetails = GuestSessionDetailsResponse(guestBagId = "guest-bag-1", guestWishlistId = "guest-wishlist-1")

    @Test
    fun `invoke - saves guest session pair on success`() = runTest {
        // Arrange: guest session created successfully, details fetch also succeeds
        coEvery { authRepository.createGuestSession() } returns Result.success(tokenPair)
        coJustRun { dataStore.saveGuestSessionPair(tokenPair.accessToken, tokenPair.refreshToken) }
        coEvery { guestSessionDetailsUseCase.invoke() } returns Result.success(guestDetails)
        coJustRun { dataStore.saveGuestSessionDetails(guestDetails) }

        // Execute: create a new guest session
        useCase()

        // Assert: both the access and refresh tokens for the guest session are persisted
        coVerify(exactly = 1) { dataStore.saveGuestSessionPair(tokenPair.accessToken, tokenPair.refreshToken) }
    }

    @Test
    fun `invoke - saves guest session details on success`() = runTest {
        // Arrange: full happy path — session created and details retrieved
        coEvery { authRepository.createGuestSession() } returns Result.success(tokenPair)
        coJustRun { dataStore.saveGuestSessionPair(any(), any()) }
        coEvery { guestSessionDetailsUseCase.invoke() } returns Result.success(guestDetails)
        coJustRun { dataStore.saveGuestSessionDetails(guestDetails) }

        // Execute: create a new guest session
        useCase()

        // Assert: guest bag id and wishlist id are saved for later retrieval
        coVerify(exactly = 1) { dataStore.saveGuestSessionDetails(guestDetails) }
    }

    @Test
    fun `invoke - clears data store when guest session details retrieval fails`() = runTest {
        // Arrange: session tokens are created but details fetch fails — data store must be cleaned up
        coEvery { authRepository.createGuestSession() } returns Result.success(tokenPair)
        coJustRun { dataStore.saveGuestSessionPair(any(), any()) }
        coEvery { guestSessionDetailsUseCase.invoke() } returns Result.failure(Exception("Details failed"))
        coJustRun { dataStore.clearDataStore() }

        // Execute: create a new guest session
        useCase()

        // Assert: the data store is cleared to avoid a partial/inconsistent guest state
        coVerify(exactly = 1) { dataStore.clearDataStore() }
        coVerify(exactly = 0) { dataStore.saveGuestSessionDetails(any()) }
    }

    @Test
    fun `invoke - does not save session pair when create session fails`() = runTest {
        // Arrange: the initial session creation request fails immediately
        coEvery { authRepository.createGuestSession() } returns Result.failure(Exception("Create failed"))

        // Execute: create a new guest session
        useCase()

        // Assert: nothing is persisted and the details use case is never called
        coVerify(exactly = 0) { dataStore.saveGuestSessionPair(any(), any()) }
        coVerify(exactly = 0) { guestSessionDetailsUseCase.invoke() }
    }
}
