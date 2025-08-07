package com.robinmaneiro.orderkiosk.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.dashboard.usecase.GetMenuItemsUseCase
import com.robinmaneiro.orderkiosk.menu.model.MenuItem
import com.robinmaneiro.orderkiosk.menu.model.MenuItems
import com.robinmaneiro.orderkiosk.networking.RequestManager
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel.Actions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val getMenuItemsUseCase: GetMenuItemsUseCase = GetMenuItemsUseCase()
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<Actions>(Channel.CONFLATED)
    val actions = _actions.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.update { it.copy(isLoading = true) }
            val menuItems = getMenuItemsUseCase() ?: run {
                _uiState.update { it.copy(isLoading = false) }
                return@launch
            }
            _uiState.update {
                it.copy(
                    menuItems = menuItems,
                    isLoading = false
                )
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val menuItems: List<MenuItem> = emptyList()
    )
}
