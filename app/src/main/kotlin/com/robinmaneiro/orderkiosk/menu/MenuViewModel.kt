package com.robinmaneiro.orderkiosk.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.bag.model.AddToBagPayload
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.bag.usecase.AddToBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.BagSelectorUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import com.robinmaneiro.orderkiosk.menu.model.DiningOption
import com.robinmaneiro.orderkiosk.menu.model.MenuCategories
import com.robinmaneiro.orderkiosk.menu.model.MenuCategory
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.model.MenuProduct
import com.robinmaneiro.orderkiosk.menu.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductExtendedInfoUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductsByCategoryUseCase
import com.robinmaneiro.orderkiosk.util.extensions.errorLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("LongParameterList")
class MenuViewModel(
    private val dataStore: DataStoreRepository,
    private val bagRepository: BagRepository,
    private val getMenuCategoriesUseCase: GetMenuCategoriesUseCase,
    private val getMenuItemsByCategoryUserCase: GetProductsByCategoryUseCase,
    private val getProductExtendedInfoUseCase: GetProductExtendedInfoUseCase,
    private val bagSelectorUseCase: BagSelectorUseCase,
    private val addToBagUseCase: AddToBagUseCase,
    private val getBagUseCase: GetBagUseCase,
    private val diningOptionString: String
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = combine(_uiState, bagRepository.bag, dataStore.loggedInStatus()) { state, bag, isLoggedIn ->
        state.copy(
            bagResponse = bag,
            isLoggedIn = isLoggedIn
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState()
    )

    private val _actions = Channel<Actions>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            loadCategories()
        }
    }

    private suspend fun loadCategories() {
        getMenuCategoriesUseCase()
            .onSuccess { menuCategories ->
                val defaultCategoryId = menuCategories.run {
                    find { it.isDefault } ?: firstOrNull()
                }?.id ?: run {
                    handleError()
                    return
                }

                getBagUseCase.invoke(
                    bagId = bagSelectorUseCase.invoke()
                )

                loadProducts(defaultCategoryId, menuCategories)
            }
            .onFailure {
                handleError()
                return
            }
    }

    private suspend fun loadProducts(defaultCategoryId: String, menuCategories: MenuCategories) {
        getMenuItemsByCategoryUserCase(defaultCategoryId)
            .onSuccess { menuItemsResponse ->
                val diningOption = runCatching { DiningOption.valueOf(diningOptionString) }.getOrElse { uiState.value.diningOption }

                _uiState.update {
                    it.copy(
                        menuCategories = menuCategories,
                        menuProducts = menuItemsResponse.items + menuItemsResponse.items + menuItemsResponse.items, // TODO: Undo 'tripled' data
                        diningOption = diningOption,
                        isLoading = false
                    )
                }
            }
            .onFailure {
                _uiState.update { it.copy(isLoading = false) }
            }
    }

    private fun handleError() = _uiState.update {
        it.copy(
            isLoading = false,
            hasError = true
        )
    }

    private fun updateItemsOnCategorySelected(categoryId: String) {
        viewModelScope.launch {
            getMenuItemsByCategoryUserCase(categoryId)
                .onSuccess { updatedItemsResponse ->
                    _uiState.update {
                        it.copy(
                            menuCategories = it.menuCategories.map { category -> category.copy(isDefault = category.id == categoryId) },
                            menuProducts = updatedItemsResponse.items + updatedItemsResponse.items + updatedItemsResponse.items // TODO: Remove triple items
                        )
                    }

                    _actions.trySend(Actions.ResetLazyGridState)
                }
                .onFailure {
                    handleError()
                }
        }
    }

    private fun onProductClick(productId: String) {
        viewModelScope.launch {
            getProductExtendedInfoUseCase(productId)
                .onSuccess { expandedItemInfo ->
                    _actions.trySend(Actions.OpenProductInfo(expandedItemInfo))
                }
                .onFailure {
                    handleError()
                }
        }
    }

    private fun addToBasket(productId: String) {
        viewModelScope.launch {
            val addToBagPayload = AddToBagPayload(
                productId,
                1
            )

            // TODO: Add some logic here so that if adding the same product calls update instead.
            addToBagUseCase.invoke(
                bagId = bagSelectorUseCase.invoke(),
                addToBagPayload = addToBagPayload
            ).onFailure { exception ->
                errorLog(exception) { "There was an issue adding product to the bag" }
            }
        }
    }

    private fun toggleDiningOption() {
        val updatedDiningOption = when (uiState.value.diningOption) {
            DiningOption.TAKE_AWAY -> DiningOption.EAT_IN
            DiningOption.EAT_IN -> DiningOption.TAKE_AWAY
        }

        _uiState.update {
            it.copy(
                diningOption = updatedDiningOption
            )
        }
    }

    fun onHandleEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            UiEvent.ToggleDiningOption -> toggleDiningOption()
            is UiEvent.OnCategoryClick -> updateItemsOnCategorySelected(uiEvent.categoryId)
            is UiEvent.OnProductClick -> onProductClick(uiEvent.productId)
            is UiEvent.AddToBasket -> addToBasket(uiEvent.productId)
        }
    }

    sealed interface UiEvent {
        data object ToggleDiningOption : UiEvent
        data class AddToBasket(val productId: String) : UiEvent
        data class OnProductClick(val productId: String) : UiEvent
        data class OnCategoryClick(val categoryId: String) : UiEvent
    }

    sealed interface Actions {
        data class OpenProductInfo(val product: MenuItemExpanded) : Actions
        data object ResetLazyGridState : Actions
    }

    data class UiState(
        val isLoading: Boolean = false,
        val menuCategories: List<MenuCategory> = emptyList(),
        val menuProducts: List<MenuProduct> = emptyList(),
        val bagResponse: BagResponse? = null,
        val diningOption: DiningOption = DiningOption.TAKE_AWAY,
        val hasError: Boolean = false,
        val isLoggedIn: Boolean = false
    )
}
