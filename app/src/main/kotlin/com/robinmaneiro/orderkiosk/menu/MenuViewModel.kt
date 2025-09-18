package com.robinmaneiro.orderkiosk.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.model.BagItemResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.bag.repository.BagRepositoryImpl
import com.robinmaneiro.orderkiosk.bag.usecase.AddToBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.menu.model.DiningOption
import com.robinmaneiro.orderkiosk.menu.model.MenuCategory
import com.robinmaneiro.orderkiosk.menu.model.MenuItem
import com.robinmaneiro.orderkiosk.menu.model.MenuItemExpanded
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductExtendedInfoUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetProductsByCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val bagRepository: BagRepository,
    private val getMenuCategoriesUseCase: GetMenuCategoriesUseCase,
    private val getMenuItemsByCategoryUserCase: GetProductsByCategoryUseCase,
    private val getProductExtendedInfoUseCase: GetProductExtendedInfoUseCase,
    private val addToBagUseCase: AddToBagUseCase,
    private val getBagUseCase: GetBagUseCase,
    private val diningOptionString: String
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = combine(_uiState, bagRepository.bag) { state, bag ->
        state.copy(
            bagProducts = bag?.items?.toList().orEmpty()
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

            getBagUseCase()

            val menuCategories = getMenuCategoriesUseCase() ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val defaultCategoryId = menuCategories.run {
                find { it.isDefault } ?: firstOrNull()
            }?.id ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val menuItemsResponse = getMenuItemsByCategoryUserCase(defaultCategoryId) ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val diningOption = runCatching { DiningOption.valueOf(diningOptionString) }.getOrNull()

            _uiState.update {
                it.copy(
                    menuCategories = menuCategories,
                    menuItems = menuItemsResponse.items + menuItemsResponse.items + menuItemsResponse.items, // TODO: Undo 'tripled' data
                    diningOption = diningOption ?: it.diningOption,
                    isLoading = false
                )
            }
        }
    }

    fun updateItemsOnCategorySelected(categoryId: String) {
        viewModelScope.launch {
            val updatedItemsResponse = getMenuItemsByCategoryUserCase(categoryId) ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            _uiState.update {
                it.copy(
                    menuCategories = it.menuCategories.map { category -> category.copy(isDefault = category.id == categoryId) },
                    menuItems = updatedItemsResponse.items + updatedItemsResponse.items + updatedItemsResponse.items
                )
            }
        }
    }

    fun onProductClicked(productId: String) {
        viewModelScope.launch {
            val expandedItemInfo = getProductExtendedInfoUseCase(productId) ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            _actions.trySend(Actions.OpenProductInfo(expandedItemInfo))
        }
    }

    fun addToBasket(product: MenuItemExpanded) {
        viewModelScope.launch {
            addToBagUseCase.invoke(product.productId)
        }
    }

    fun toggleDiningOption() {
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

    sealed interface Actions {
        data class OpenProductInfo(val product: MenuItemExpanded) : Actions
    }

    data class UiState(
        val isLoading: Boolean = false,
        val menuCategories: List<MenuCategory> = emptyList(),
        val menuItems: List<MenuItem> = emptyList(),
        val bagProducts: List<BagItem> = emptyList(),
        val diningOption: DiningOption = DiningOption.TAKE_AWAY
    )
}
