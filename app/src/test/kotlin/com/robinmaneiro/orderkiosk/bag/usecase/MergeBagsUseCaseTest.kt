package com.robinmaneiro.orderkiosk.bag.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class MergeBagsUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val bagRepository = mockk<BagRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = MergeBagsUseCase(bagRepository)

    @Test
    fun `invoke - delegates to repository with correct bag ids`() = runTest {
        // Arrange: source is the guest bag, target is the authenticated user's bag
        val sourceBagId = "guest-bag-123"
        val targetBagId = "auth-bag-456"
        coEvery { bagRepository.mergeBags(sourceBagId, targetBagId) } returns Result.success(mockk())

        // Execute: merge the guest bag into the auth bag
        useCase(sourceBagId, targetBagId)

        // Assert: repository was called exactly once with both ids in the correct order
        coVerify(exactly = 1) { bagRepository.mergeBags(sourceBagId, targetBagId) }
    }

    @Test
    fun `invoke - does not throw on repository failure`() = runTest {
        // Arrange: repository returns a failure (e.g. network error during merge)
        val exception = Exception("Merge failed")
        coEvery { bagRepository.mergeBags(any(), any()) } returns Result.failure(exception)

        // Execute: use case should not rethrow the exception
        useCase("guest-bag", "auth-bag")

        // Assert: the repository was still called — the use case didn't short-circuit
        coVerify(exactly = 1) { bagRepository.mergeBags("guest-bag", "auth-bag") }
    }
}
