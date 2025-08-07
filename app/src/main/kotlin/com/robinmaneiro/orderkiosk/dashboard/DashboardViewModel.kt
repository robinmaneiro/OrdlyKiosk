package com.robinmaneiro.orderkiosk.dashboard

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.menu.MenuItem
import com.robinmaneiro.orderkiosk.menu.MenuItems
import com.robinmaneiro.orderkiosk.networking.RequestManager
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel
import com.robinmaneiro.orderkiosk.welcome.WelcomeViewModel.Actions
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DashboardViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<Actions>(Channel.CONFLATED)
    val actions = _actions.receiveAsFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val menuItems = RequestManager.getRequest<MenuItems>() ?: return@launch
            _uiState.update {
                it.copy(
                    menuItems = menuItems
                )
            }
        }
    }

    data class UiState(
        val isLoading: Boolean = false,
        val menuItems: List<MenuItem> = emptyList()
    )
}
