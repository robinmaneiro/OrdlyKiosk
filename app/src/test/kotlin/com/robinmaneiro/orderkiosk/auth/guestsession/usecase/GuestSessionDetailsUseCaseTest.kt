package com.robinmaneiro.orderkiosk.auth.guestsession.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.auth.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository

class GuestSessionDetailsUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val authRepository = mockk<AuthRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = GuestSessionDetailsUseCase(authRepository)

    @Test
    fun `invoke - returns guest session details on success`() = runTest {
        // Arrange: stub repository to return a valid details response (bag id + wishlist id)
        val expected = GuestSessionDetailsResponse(guestBagId = "guest-bag-1", guestWishlistId = "guest-wishlist-1")
        coEvery { authRepository.getGuestSessionDetails() } returns Result.success(expected)

        // Execute: fetch the guest session details
        val result = useCase()

        // Assert: the result is successful and contains the expected session details
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. guest session has expired)
        val exception = Exception("Failed to get guest session details")
        coEvery { authRepository.getGuestSessionDetails() } returns Result.failure(exception)

        // Execute: fetch the guest session details
        val result = useCase()

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
