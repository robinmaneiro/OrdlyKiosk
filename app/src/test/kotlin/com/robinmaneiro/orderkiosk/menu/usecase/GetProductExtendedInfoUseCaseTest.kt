package com.robinmaneiro.orderkiosk.menu.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.PriceData
import com.robinmaneiro.orderkiosk.menu.model.Tax
import com.robinmaneiro.orderkiosk.menu.model.TaxUnit
import com.robinmaneiro.orderkiosk.menu.repository.MenuRepository

class GetProductExtendedInfoUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val menuRepository = mockk<MenuRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = GetProductExtendedInfoUseCase(menuRepository)

    // A realistic expanded product used across tests — includes pricing with VAT breakdown
    private val expandedItem = MenuItemExpanded(
        productId = "prod1",
        title = "Cheeseburger",
        _priceData = PriceData(
            currencyCode = "GBP",
            _withTax = 999,      // price in pence including VAT
            _withoutTax = 849,   // price in pence excluding VAT
            tax = Tax(vat = TaxUnit(amount = 150, rate = 20))
        ),
        description = "A classic cheeseburger",
        categories = listOf("cat1")
    )

    @Test
    fun `invoke - returns expanded product info on success`() = runTest {
        // Arrange: stub repository to return the full product details
        coEvery { menuRepository.getProductExtendedInfo("prod1") } returns Result.success(expandedItem)

        // Execute: fetch the extended product details
        val result = useCase("prod1")

        // Assert: the result is successful and contains the full product object
        assertTrue(result.isSuccess)
        assertEquals(expandedItem, result.getOrNull())
    }

    @Test
    fun `invoke - passes correct product id to repository`() = runTest {
        // Arrange: stub repository to return the expanded item for the given product id
        val productId = "prod-42"
        coEvery { menuRepository.getProductExtendedInfo(productId) } returns Result.success(expandedItem)

        // Execute: fetch the extended product details
        useCase(productId)

        // Assert: the repository was called exactly once with the correct product id
        coVerify(exactly = 1) { menuRepository.getProductExtendedInfo(productId) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. product no longer exists in the menu)
        val exception = Exception("Product not found")
        coEvery { menuRepository.getProductExtendedInfo(any()) } returns Result.failure(exception)

        // Execute: fetch the extended product details
        val result = useCase("prod1")

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
