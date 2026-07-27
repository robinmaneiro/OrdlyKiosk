package com.robinmaneiro.orderkiosk.bag.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.model.UpdateBagItemPayload
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class UpdateBagItemUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val bagRepository = mockk<BagRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = UpdateBagItemUseCase(bagRepository)

    @Test
    fun `invoke - returns bag response on success`() = runTest {
        // Arrange: payload carries both the line-item id and the new quantity
        val bagId = "bag-123"
        val payload = UpdateBagItemPayload(bagItemId = "item-1", quantity = 3)
        val expected = mockk<BagResponse>()
        coEvery { bagRepository.updateBagItem(bagId, any(), payload.bagItemId) } returns Result.success(expected)

        // Execute: update the item quantity in the bag
        val result = useCase(bagId, payload)

        // Assert: the result is successful and contains the updated bag state
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - passes correct bag id and item id to repository`() = runTest {
        // Arrange: stub repository to accept the bag id and item id combination
        val bagId = "bag-456"
        val payload = UpdateBagItemPayload(bagItemId = "item-abc", quantity = 2)
        coEvery { bagRepository.updateBagItem(bagId, any(), payload.bagItemId) } returns Result.success(mockk())

        // Execute: update the item quantity in the bag
        useCase(bagId, payload)

        // Assert: repository was called exactly once with both the bag id and the item id
        coVerify(exactly = 1) { bagRepository.updateBagItem(bagId, any(), payload.bagItemId) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. item no longer exists)
        val exception = Exception("Failed to update item")
        coEvery { bagRepository.updateBagItem(any(), any(), any()) } returns Result.failure(exception)

        // Execute: update the item quantity in the bag
        val result = useCase("bag-123", UpdateBagItemPayload(bagItemId = "item-1", quantity = 1))

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
