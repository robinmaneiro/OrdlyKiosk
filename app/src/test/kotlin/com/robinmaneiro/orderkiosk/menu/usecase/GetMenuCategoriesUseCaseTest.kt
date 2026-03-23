package com.robinmaneiro.orderkiosk.menu.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.menu.model.MenuCategory
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class GetMenuCategoriesUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val menuRepository = mockk<MenuRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = GetMenuCategoriesUseCase(menuRepository)

    @Test
    fun `invoke - returns categories from repository on success`() = runTest {
        // Arrange: build a list of real category objects to verify the result is passed through intact
        val expected = MenuCategories().apply {
            add(MenuCategory(id = "cat1", categoryName = "Burgers", isDefault = true))
            add(MenuCategory(id = "cat2", categoryName = "Drinks", isDefault = false))
        }
        coEvery { menuRepository.getAllCategories() } returns Result.success(expected)

        // Execute: fetch all menu categories
        val result = useCase()

        // Assert: the result is successful and the category list matches exactly
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. network error)
        val exception = Exception("Network error")
        coEvery { menuRepository.getAllCategories() } returns Result.failure(exception)

        // Execute: fetch all menu categories
        val result = useCase()

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
