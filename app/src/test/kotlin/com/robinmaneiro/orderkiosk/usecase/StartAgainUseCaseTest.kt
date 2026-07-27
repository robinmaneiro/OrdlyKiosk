package com.robinmaneiro.orderkiosk.usecase

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Test
import com.robinmaneiro.orderkiosk.auth.guestsession.usecase.CreateGuestSessionUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class StartAgainUseCaseTest {

    // Mock all dependencies — "start again" touches the bag, the data store, and session creation
    private val dataStoreRepository = mockk<DataStoreRepository>()
    private val clearAllBagItemsUseCase = mockk<RemoveAllBagItemsUseCase>()
    private val createGuestSessionUseCase = mockk<CreateGuestSessionUseCase>()

    // The use case under test, constructed with all mocked dependencies
    private val useCase = StartAgainUseCase(dataStoreRepository, clearAllBagItemsUseCase, createGuestSessionUseCase)

    @Test
    fun `invoke - clears bag items with guest bag id`() = runTest {
        // Arrange: retrieve the current guest bag id so we know which bag to clear
        val guestBagId = "guest-bag-123"
        coEvery { dataStoreRepository.getGuestBagId() } returns guestBagId
        coEvery { clearAllBagItemsUseCase.invoke(guestBagId) } returns Result.success(mockk())
        coJustRun { dataStoreRepository.clearDataStore() }
        coJustRun { createGuestSessionUseCase.invoke() }

        // Execute: reset the session from scratch
        useCase()

        // Assert: the bag is cleared using the correct guest bag id
        coVerify(exactly = 1) { clearAllBagItemsUseCase.invoke(guestBagId) }
    }

    @Test
    fun `invoke - clears data store`() = runTest {
        // Arrange: full happy path setup so the use case can complete
        coEvery { dataStoreRepository.getGuestBagId() } returns "guest-bag-123"
        coEvery { clearAllBagItemsUseCase.invoke(any()) } returns Result.success(mockk())
        coJustRun { dataStoreRepository.clearDataStore() }
        coJustRun { createGuestSessionUseCase.invoke() }

        // Execute: reset the session from scratch
        useCase()

        // Assert: all stored session and bag data is wiped to reset the app state
        coVerify(exactly = 1) { dataStoreRepository.clearDataStore() }
    }

    @Test
    fun `invoke - creates new guest session`() = runTest {
        // Arrange: full happy path setup so the use case can complete
        coEvery { dataStoreRepository.getGuestBagId() } returns "guest-bag-123"
        coEvery { clearAllBagItemsUseCase.invoke(any()) } returns Result.success(mockk())
        coJustRun { dataStoreRepository.clearDataStore() }
        coJustRun { createGuestSessionUseCase.invoke() }

        // Execute: reset the session from scratch
        useCase()

        // Assert: a fresh guest session is created so the user can continue as a guest
        coVerify(exactly = 1) { createGuestSessionUseCase.invoke() }
    }
}
