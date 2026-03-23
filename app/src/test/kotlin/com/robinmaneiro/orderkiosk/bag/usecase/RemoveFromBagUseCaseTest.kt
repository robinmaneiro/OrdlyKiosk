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

class RemoveFromBagUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val bagRepository = mockk<BagRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = RemoveFromBagUseCase(bagRepository)

    @Test
    fun `invoke - returns updated bag on success`() = runTest {
        // Arrange: both the bag id and the specific line-item id are needed to remove an item
        val bagId = "bag-123"
        val bagItemId = "item-456"
        val expected = mockk<BagResponse>()
        coEvery { bagRepository.removeFromBag(bagId, bagItemId) } returns Result.success(expected)

        // Execute: remove the item from the bag
        val result = useCase(bagId, bagItemId)

        // Assert: the result is successful and contains the updated bag state
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - passes correct bag id and item id to repository`() = runTest {
        // Arrange: stub repository to accept the bag id and item id combination
        val bagId = "bag-abc"
        val bagItemId = "item-xyz"
        coEvery { bagRepository.removeFromBag(bagId, bagItemId) } returns Result.success(mockk())

        // Execute: remove the item from the bag
        useCase(bagId, bagItemId)

        // Assert: repository was called exactly once with both identifiers
        coVerify(exactly = 1) { bagRepository.removeFromBag(bagId, bagItemId) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. item no longer exists)
        val exception = Exception("Item not found")
        coEvery { bagRepository.removeFromBag(any(), any()) } returns Result.failure(exception)

        // Execute: remove the item from the bag
        val result = useCase("bag-123", "item-456")

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
