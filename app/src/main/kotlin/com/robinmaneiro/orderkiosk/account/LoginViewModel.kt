package com.robinmaneiro.orderkiosk.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.account.model.LoginPayload
import com.robinmaneiro.orderkiosk.account.usecase.LoginUserUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _actions: Channel<Actions> = Channel(Channel.UNLIMITED)
    val actions = _actions.receiveAsFlow()

    fun loginUser(payload: LoginPayload) {
        viewModelScope.launch {
            loginUserUseCase.invoke(payload)
                .onSuccess {
                    _actions.trySend(Actions.NavigateBack)
                }
                .onFailure {
                    _uiState.update {
                        it.copy(
                            hasError = true // TODO: action is better?
                        )
                    }
                }
        }
    }

    sealed interface Actions {
        data object NavigateBack : Actions
    }

    data class UiState(
        val hasError: Boolean = false
    )
}
