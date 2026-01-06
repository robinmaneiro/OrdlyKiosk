package com.robinmaneiro.orderkiosk.account.resetpassword

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import com.robinmaneiro.orderkiosk.account.registration.usecase.RegisterAccountUseCase

class ResetPasswordViewModel(
    val registerAccountUseCase: RegisterAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    // TODO: Declare methods

    data class UiState(
        val hasError: Boolean = false
    )
}
