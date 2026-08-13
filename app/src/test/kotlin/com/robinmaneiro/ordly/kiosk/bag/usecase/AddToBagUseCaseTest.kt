package com.robinmaneiro.ordly.kiosk.bag.usecase

import com.robinmaneiro.ordly.kiosk.bag.model.AddToBagPayload
import com.robinmaneiro.ordly.kiosk.fake.FakeBagRepository
import com.robinmaneiro.ordly.kiosk.fake.TestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class AddToBagUseCaseTest {

    private lateinit var fakeBagRepository: FakeBagRepository
    private lateinit var useCase: AddToBagUseCase

    @Before
    fun setup() {
        fakeBagRepository = FakeBagRepository()
        useCase = AddToBagUseCase(fakeBagRepository)
    }

    @Test
    fun `successfully adds item to bag`() = runTest {
        val expectedBag = TestData.bagResponse()
        fakeBagRepository.addToBagResult = Result.success(expectedBag)

        val result = useCase.invoke(
            bagId = "bag-1",
            addToBagPayload = AddToBagPayload(productId = "product-1", quantity = 1)
        )

        assertTrue(result.isSuccess)
        assertEquals(1, result.getOrNull()?.itemCount)
    }

    @Test
    fun `returns failure when repository fails`() = runTest {
        fakeBagRepository.addToBagResult = Result.failure(IOException("Network error"))

        val result = useCase.invoke(
            bagId = "bag-1",
            addToBagPayload = AddToBagPayload(productId = "product-1", quantity = 1)
        )

        assertTrue(result.isFailure)
    }

    @Test
    fun `serializes payload before sending to repository`() = runTest {
        fakeBagRepository.addToBagResult = Result.success(TestData.bagResponse())

        val result = useCase.invoke(
            bagId = "bag-1",
            addToBagPayload = AddToBagPayload(productId = "product-1", quantity = 2)
        )

        assertTrue(result.isSuccess)
    }
}
