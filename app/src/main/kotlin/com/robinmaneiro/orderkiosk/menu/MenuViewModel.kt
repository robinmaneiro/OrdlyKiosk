package com.robinmaneiro.orderkiosk.menu

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.menu.model.MenuCategory
import com.robinmaneiro.orderkiosk.menu.model.MenuProduct
import com.robinmaneiro.orderkiosk.menu.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.menu.usecase.GetItemInfoUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.menu.usecase.GetMenuItemsByCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MenuViewModel(
    private val getMenuCategoriesUseCase: GetMenuCategoriesUseCase = GetMenuCategoriesUseCase(),
    private val getMenuItemsByCategoryUserCase: GetMenuItemsByCategoryUseCase = GetMenuItemsByCategoryUseCase(),
    private val getItemInfoUseCase: GetItemInfoUseCase = GetItemInfoUseCase()
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<Actions>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }

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

            _uiState.update {
                it.copy(
                    menuCategories = menuCategories,
                    menuProducts = menuItemsResponse.items + menuItemsResponse.items + menuItemsResponse.items, // TODO: Undo 'tripled' data
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
                    menuProducts = updatedItemsResponse.items + updatedItemsResponse.items + updatedItemsResponse.items
                )
            }
        }
    }

    fun onProductClicked(productId: String) {
        viewModelScope.launch {
            val expandedItemInfo = getItemInfoUseCase(productId) ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            _actions.trySend(Actions.OpenProductInfo(expandedItemInfo))
        }
    }

    fun addToBasket(product: MenuProductExpanded) {
        viewModelScope.launch {
            _uiState.update {
                it.copy(bagProducts = _uiState.value.bagProducts.toMutableList().apply { add(product) })
            }
        }
    }

    fun cancelOrder() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(bagProducts = emptyList())
            }
        }
    }

    sealed interface Actions {
        data class OpenProductInfo(val product: MenuProductExpanded) : Actions
    }

    data class UiState(
        val isLoading: Boolean = false,
        val menuCategories: List<MenuCategory> = emptyList(),
        val menuProducts: List<MenuProduct> = emptyList(),
        val bagProducts: List<MenuProductExpanded> = emptyList()
    )
}
