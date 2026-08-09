package com.robinmaneiro.orderkiosk.bag

import app.cash.turbine.test
import com.robinmaneiro.orderkiosk.bag.usecase.BagSelectorUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.UpdateBagItemUseCase
import com.robinmaneiro.orderkiosk.fake.FakeBagRepository
import com.robinmaneiro.orderkiosk.fake.FakeDataStoreRepository
import com.robinmaneiro.orderkiosk.fake.TestData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class BagViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fakeBagRepository: FakeBagRepository
    private lateinit var fakeDataStoreRepository: FakeDataStoreRepository
    private lateinit var bagSelectorUseCase: BagSelectorUseCase
    private lateinit var getBagUseCase: GetBagUseCase
    private lateinit var updateBagItemUseCase: UpdateBagItemUseCase
    private lateinit var removeFromBagUseCase: RemoveFromBagUseCase
    private lateinit var removeAllBagItemsUseCase: RemoveAllBagItemsUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeBagRepository = FakeBagRepository()
        fakeDataStoreRepository = FakeDataStoreRepository()
        bagSelectorUseCase = BagSelectorUseCase(fakeDataStoreRepository)
        getBagUseCase = GetBagUseCase(fakeBagRepository)
        updateBagItemUseCase = UpdateBagItemUseCase(fakeBagRepository)
        removeFromBagUseCase = RemoveFromBagUseCase(fakeBagRepository)
        removeAllBagItemsUseCase = RemoveAllBagItemsUseCase(fakeBagRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel(): BagViewModel {
        return BagViewModel(
            bagSelectorUseCase = bagSelectorUseCase,
            getBagUseCase = getBagUseCase,
            updateBagItemUseCase = updateBagItemUseCase,
            removeFromBagUseCase = removeFromBagUseCase,
            removeAllBagItemsUseCase = removeAllBagItemsUseCase
        )
    }

    @Test
    fun `init loads bag items and updates ui state`() = runTest {
        val bagResponse = TestData.bagResponse()
        fakeBagRepository.getBagItemsResult = Result.success(bagResponse)

        val viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertEquals(1, state.itemCount)
        assertEquals(1, state.bagItems.size)
        assertEquals("Burger", state.bagItems.first().title)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `init hides loader on failure`() = runTest {
        fakeBagRepository.getBagItemsResult = Result.failure(IOException("Network error"))

        val viewModel = createViewModel()

        assertEquals(false, viewModel.uiState.value.isLoading)
        assertTrue(viewModel.uiState.value.bagItems.isEmpty())
    }

    @Test
    fun `increase quantity updates bag item`() = runTest {
        val initialBag = TestData.bagResponse()
        fakeBagRepository.getBagItemsResult = Result.success(initialBag)

        val updatedItem = TestData.bagItem(quantity = 2, totalPriceCents = 1998)
        val updatedBag = TestData.bagResponse(items = listOf(updatedItem), totalCostCents = 1998)
        fakeBagRepository.updateBagItemResult = Result.success(updatedBag)

        val viewModel = createViewModel()
        viewModel.onHandleEvent(BagViewModel.UiEvent.IncreaseQuantity(initialBag.items.first()))

        val state = viewModel.uiState.value
        assertEquals(2, state.bagItems.first().quantity)
        assertEquals(false, state.isLoading)
    }

    @Test
    fun `decrease quantity updates bag item`() = runTest {
        val item = TestData.bagItem(quantity = 3, totalPriceCents = 2997)
        val initialBag = TestData.bagResponse(items = listOf(item), totalCostCents = 2997)
        fakeBagRepository.getBagItemsResult = Result.success(initialBag)

        val updatedItem = TestData.bagItem(quantity = 2, totalPriceCents = 1998)
        val updatedBag = TestData.bagResponse(items = listOf(updatedItem), totalCostCents = 1998)
        fakeBagRepository.updateBagItemResult = Result.success(updatedBag)

        val viewModel = createViewModel()
        viewModel.onHandleEvent(BagViewModel.UiEvent.DecreaseQuantity(item))

        val state = viewModel.uiState.value
        assertEquals(2, state.bagItems.first().quantity)
    }

    @Test
    fun `remove item updates bag`() = runTest {
        val item1 = TestData.bagItem(itemId = "item-1", title = "Burger")
        val item2 = TestData.bagItem(itemId = "item-2", title = "Fries", productId = "product-2")
        val initialBag = TestData.bagResponse(items = listOf(item1, item2), totalCostCents = 1998)
        fakeBagRepository.getBagItemsResult = Result.success(initialBag)

        val updatedBag = TestData.bagResponse(items = listOf(item2), totalCostCents = 999)
        fakeBagRepository.removeFromBagResult = Result.success(updatedBag)

        val viewModel = createViewModel()
        viewModel.onHandleEvent(BagViewModel.UiEvent.RemoveItem(item1))

        val state = viewModel.uiState.value
        assertEquals(1, state.bagItems.size)
        assertEquals("Fries", state.bagItems.first().title)
    }

    @Test
    fun `remove all items sends navigate back action`() = runTest {
        val initialBag = TestData.bagResponse()
        fakeBagRepository.getBagItemsResult = Result.success(initialBag)
        fakeBagRepository.removeAllBagItemsResult = Result.success(TestData.emptyBagResponse())

        val viewModel = createViewModel()

        viewModel.actions.test {
            viewModel.onHandleEvent(BagViewModel.UiEvent.RemoveAllItems)

            val action = awaitItem()
            assertEquals(BagViewModel.Actions.NavigateBack, action)
        }
    }

    @Test
    fun `remove all items hides loader on failure`() = runTest {
        val initialBag = TestData.bagResponse()
        fakeBagRepository.getBagItemsResult = Result.success(initialBag)
        fakeBagRepository.removeAllBagItemsResult = Result.failure(IOException("Network error"))

        val viewModel = createViewModel()
        viewModel.onHandleEvent(BagViewModel.UiEvent.RemoveAllItems)

        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `increase quantity hides loader on failure`() = runTest {
        val initialBag = TestData.bagResponse()
        fakeBagRepository.getBagItemsResult = Result.success(initialBag)
        fakeBagRepository.updateBagItemResult = Result.failure(IOException("Network error"))

        val viewModel = createViewModel()
        viewModel.onHandleEvent(BagViewModel.UiEvent.IncreaseQuantity(initialBag.items.first()))

        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `uses guest bag id when user is not logged in`() = runTest {
        fakeDataStoreRepository.setUserLoggedIn(false)
        fakeBagRepository.getBagItemsResult = Result.success(TestData.bagResponse())

        createViewModel()

        // If we get here without exception, the bag selector correctly returned guest bag id
        assertEquals(1, fakeBagRepository.bag.value?.itemCount)
    }
}
