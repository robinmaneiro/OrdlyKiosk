package com.robinmaneiro.orderkiosk.menu.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.menu.model.MenuResponse
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class GetProductsByCategoryUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val menuRepository = mockk<MenuRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = GetProductsByCategoryUseCase(menuRepository)

    @Test
    fun `invoke - returns products for given category on success`() = runTest {
        // Arrange: stub repository to return products for a specific category
        val categoryId = "cat1"
        val expected = MenuResponse(itemCount = 1, items = emptyList())
        coEvery { menuRepository.getProductsByCategory(categoryId) } returns Result.success(expected)

        // Execute: fetch products for the given category
        val result = useCase(categoryId)

        // Assert: the result is successful and contains the expected products
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - passes correct category id to repository`() = runTest {
        // Arrange: stub repository to return an empty response for the given category
        val categoryId = "cat-drinks"
        coEvery { menuRepository.getProductsByCategory(categoryId) } returns Result.success(MenuResponse(0, emptyList()))

        // Execute: fetch products for the given category
        useCase(categoryId)

        // Assert: the repository was called exactly once with the correct category id
        coVerify(exactly = 1) { menuRepository.getProductsByCategory(categoryId) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. category no longer exists)
        val exception = Exception("Not found")
        coEvery { menuRepository.getProductsByCategory(any()) } returns Result.failure(exception)

        // Execute: fetch products for the given category
        val result = useCase("cat1")

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
