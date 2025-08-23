package com.robinmaneiro.orderkiosk.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuCategoriesUseCase
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuItemsUseCase
import com.robinmaneiro.orderkiosk.dashboard.model.MenuCategory
import com.robinmaneiro.orderkiosk.dashboard.model.MenuProduct
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel.Actions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getMenuItemsUseCase: GetMenuItemsUseCase = GetMenuItemsUseCase(),
    private val getMenuCategoriesUseCase: GetMenuCategoriesUseCase = GetMenuCategoriesUseCase()
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

    data class UiState(
        val isLoading: Boolean = false,
        val menuCategories: List<MenuCategory> = emptyList(),
        val menuProducts: List<MenuProduct> = emptyList()
    )
}
