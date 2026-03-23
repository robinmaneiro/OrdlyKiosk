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

class GetBagUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val bagRepository = mockk<BagRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = GetBagUseCase(bagRepository)

    @Test
    fun `invoke - returns bag response on success`() = runTest {
        // Arrange: stub the repository to return a successful response
        val bagId = "bag-123"
        val expected = mockk<BagResponse>()
        coEvery { bagRepository.getBagItems(bagId) } returns Result.success(expected)

        // Execute: fetch the bag contents
        val result = useCase(bagId)

        // Assert: the result is successful and matches what the repository returned
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - passes correct bag id to repository`() = runTest {
        // Arrange: stub repository to return a response for the given bag id
        val bagId = "bag-456"
        coEvery { bagRepository.getBagItems(bagId) } returns Result.success(mockk())

        // Execute: fetch the bag contents
        useCase(bagId)

        // Assert: the repository was called exactly once with the correct bag id
        coVerify(exactly = 1) { bagRepository.getBagItems(bagId) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure
        val exception = Exception("Bag not found")
        coEvery { bagRepository.getBagItems(any()) } returns Result.failure(exception)

        // Execute: fetch the bag contents
        val result = useCase("bag-123")

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
