package com.robinmaneiro.orderkiosk.bag.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.bag.model.AddToBagPayload
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class AddToBagUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val bagRepository = mockk<BagRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = AddToBagUseCase(bagRepository)

    @Test
    fun `invoke - returns bag response on success`() = runTest {
        // Arrange: set up a successful repository response
        val bagId = "bag-123"
        val payload = AddToBagPayload(productId = "prod-1", quantity = 2)
        val expected = mockk<BagResponse>()
        coEvery { bagRepository.addToBag(bagId, any()) } returns Result.success(expected)

        // Execute: add the item to the bag
        val result = useCase(bagId, payload)

        // Assert: the result is successful and matches what the repository returned
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - passes correct bag id to repository`() = runTest {
        // Arrange: stub repository to accept any item added to the given bag id
        val bagId = "bag-456"
        val payload = AddToBagPayload(productId = "prod-2", quantity = 1)
        coEvery { bagRepository.addToBag(bagId, any()) } returns Result.success(mockk())

        // Execute: add the item to the bag
        useCase(bagId, payload)

        // Assert: the repository was called exactly once with the expected bag id
        coVerify(exactly = 1) { bagRepository.addToBag(bagId, any()) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure
        val exception = Exception("Failed to add item")
        coEvery { bagRepository.addToBag(any(), any()) } returns Result.failure(exception)

        // Execute: add the item to the bag
        val result = useCase("bag-123", AddToBagPayload(productId = "prod-1", quantity = 1))

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
