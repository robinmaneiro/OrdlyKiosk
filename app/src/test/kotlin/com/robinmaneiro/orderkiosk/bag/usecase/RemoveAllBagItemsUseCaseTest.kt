package com.robinmaneiro.orderkiosk.bag.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class RemoveAllBagItemsUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val bagRepository = mockk<BagRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = RemoveAllBagItemsUseCase(bagRepository)

    @Test
    fun `invoke - returns empty bag response on success`() = runTest {
        // Arrange: stub repository to return a successful (now-empty) bag response
        val bagId = "bag-123"
        val expected = mockk<BagResponse>()
        coEvery { bagRepository.removeAllBagItems(bagId) } returns Result.success(expected)

        // Execute: clear all items from the bag
        val result = useCase(bagId)

        // Assert: the result is successful and contains the cleared bag state
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - passes correct bag id to repository`() = runTest {
        // Arrange: stub repository to accept the bag id
        val bagId = "bag-abc"
        coEvery { bagRepository.removeAllBagItems(bagId) } returns Result.success(mockk())

        // Execute: clear all items from the bag
        useCase(bagId)

        // Assert: the repository was called exactly once with the correct bag id
        coVerify(exactly = 1) { bagRepository.removeAllBagItems(bagId) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. network error)
        val exception = Exception("Failed to clear bag")
        coEvery { bagRepository.removeAllBagItems(any()) } returns Result.failure(exception)

        // Execute: clear all items from the bag
        val result = useCase("bag-123")

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
