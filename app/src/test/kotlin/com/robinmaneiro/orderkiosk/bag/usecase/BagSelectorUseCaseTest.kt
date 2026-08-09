package com.robinmaneiro.orderkiosk.bag.usecase

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import com.robinmaneiro.orderkiosk.fake.FakeDataStoreRepository

class BagSelectorUseCaseTest {

    private lateinit var fakeDataStore: FakeDataStoreRepository
    private lateinit var useCase: BagSelectorUseCase

    @Before
    fun setup() {
        fakeDataStore = FakeDataStoreRepository()
        useCase = BagSelectorUseCase(fakeDataStore)
    }

    @Test
    fun `returns auth bag id when user is logged in`() = runTest {
        fakeDataStore.setUserLoggedIn(true)

        val bagId = useCase.invoke()

        assertEquals("auth-bag-id", bagId)
    }

    @Test
    fun `returns guest bag id when user is not logged in`() = runTest {
        fakeDataStore.setUserLoggedIn(false)

        val bagId = useCase.invoke()

        assertEquals("guest-bag-id", bagId)
    }
}
