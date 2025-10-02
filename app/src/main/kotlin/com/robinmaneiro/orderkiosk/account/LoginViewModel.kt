package com.robinmaneiro.orderkiosk.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.account.usecase.LoginUserUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    val loginUserUseCase: LoginUserUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    fun loginUser(user: String, pass: String) {
        viewModelScope.launch {
            loginUserUseCase.invoke(user, pass)
        }
    }

    data class UiState(
        val hasError: Boolean = false
    )
}

