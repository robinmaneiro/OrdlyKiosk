package com.robinmaneiro.orderkiosk.account.resetpassword

import androidx.lifecycle.ViewModel
import com.robinmaneiro.orderkiosk.account.registration.usecase.RegisterAccountUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
