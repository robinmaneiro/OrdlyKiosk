package com.robinmaneiro.orderkiosk.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuAllItemsUseCase
import com.robinmaneiro.orderkiosk.dashboard.model.MenuCategory
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProduct
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProductExpanded
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetItemInfoUseCase
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuItemsByCategoryUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getMenuItemsUseCase: GetMenuAllItemsUseCase = GetMenuAllItemsUseCase(),
    private val getMenuCategoriesUseCase: GetMenuCategoriesUseCase = GetMenuCategoriesUseCase(),
    private val getMenuItemsByCategoryUserCase: GetMenuItemsByCategoryUseCase = GetMenuItemsByCategoryUseCase(),
    private val getItemInfoUseCase: GetItemInfoUseCase = GetItemInfoUseCase()
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<Actions>(Channel.CONFLATED)
    val actions = _actions.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            val menuItemsResponse = getMenuItemsUseCase() ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }

            val menuCategories = getMenuCategoriesUseCase() ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }


            _uiState.update {
                it.copy(
                    menuCategories = menuCategories,
                    menuProducts = menuItemsResponse.items,
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
                    menuProducts = updatedItemsResponse.items
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

    sealed interface Actions {
        data class OpenProductInfo(val product: MenuProductExpanded): Actions
    }

    data class UiState(
        val isLoading: Boolean = false,
        val menuCategories: List<MenuCategory> = emptyList(),
        val menuProducts: List<MenuProduct> = emptyList()
    )
}
