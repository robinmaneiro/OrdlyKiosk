package com.robinmaneiro.orderkiosk.bag.usecase

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class BagSelectorUseCaseTest {

    // Mock the data store — this holds the current auth/guest session state
    private val dataStore = mockk<DataStoreRepository>()

    // The use case under test: picks either the auth bag or guest bag depending on login state
    private val useCase = BagSelectorUseCase(dataStore)

    @Test
    fun `invoke - returns auth bag id when user is logged in`() = runTest {
        // Arrange: simulate a logged-in user
        val authBagId = "auth-bag-123"
        coEvery { dataStore.isUserLoggedIn() } returns true
        coEvery { dataStore.getAuthBagId() } returns authBagId

        // Execute: determine the active bag id based on login state
        val result = useCase()

        // Assert: the authenticated user's bag id is returned
        assertEquals(authBagId, result)
    }

    @Test
    fun `invoke - returns guest bag id when user is not logged in`() = runTest {
        // Arrange: simulate a guest (unauthenticated) session
        val guestBagId = "guest-bag-456"
        coEvery { dataStore.isUserLoggedIn() } returns false
        coEvery { dataStore.getGuestBagId() } returns guestBagId

        // Execute: determine the active bag id based on login state
        val result = useCase()

        // Assert: the guest bag id is returned instead of the auth one
        assertEquals(guestBagId, result)
    }
}
