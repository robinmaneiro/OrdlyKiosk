package com.robinmaneiro.orderkiosk.account.login

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.robinmaneiro.orderkiosk.account.login.model.LoginPayload
import com.robinmaneiro.orderkiosk.auth.usecase.LoginUserUseCase
import com.robinmaneiro.orderkiosk.util.ErrorMapper
import com.robinmaneiro.orderkiosk.util.InputValidator

class LoginViewModel(
    private val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _actions: Channel<Actions> = Channel(Channel.UNLIMITED)
    val actions = _actions.receiveAsFlow()

    fun loginUser(payload: LoginPayload) {
        val emailError = InputValidator.validateEmail(payload.email)
        val passwordError = InputValidator.validatePassword(payload.password)

        if (emailError != null || passwordError != null) {
            _uiState.update { it.copy(emailError = emailError, passwordError = passwordError) }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            loginUserUseCase.invoke(payload)
                .onSuccess {
                    _actions.trySend(Actions.NavigateBack)
                }
                .onFailure { throwable ->
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = ErrorMapper.getErrorMessage(throwable)
                        )
                    }
                }
        }
    }

    fun clearEmailError() = _uiState.update { it.copy(emailError = null) }
    fun clearPasswordError() = _uiState.update { it.copy(passwordError = null) }

    sealed interface Actions {
        data object NavigateBack : Actions
    }

    data class UiState(
        val isLoading: Boolean = false,
        @StringRes val errorMessage: Int? = null,
        @StringRes val emailError: Int? = null,
        @StringRes val passwordError: Int? = null
    )
}
