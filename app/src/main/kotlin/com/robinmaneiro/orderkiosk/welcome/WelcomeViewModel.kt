package com.robinmaneiro.orderkiosk.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow

class WelcomeViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<Actions>(Channel.CONFLATED)
    val actions = _actions.receiveAsFlow()

    data class UiState(
        val isLoading: Boolean = false
    )

    sealed interface Actions {
        data object ShowErrorDialog : Actions
        data class ShowToastMessage(val message: String) : Actions
    }
}
