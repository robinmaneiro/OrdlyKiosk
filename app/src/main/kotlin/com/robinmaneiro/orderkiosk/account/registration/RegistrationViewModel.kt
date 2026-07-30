package com.robinmaneiro.orderkiosk.account.registration

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.robinmaneiro.orderkiosk.account.registration.model.RegisterPayload
import com.robinmaneiro.orderkiosk.account.registration.usecase.RegisterAccountUseCase
import com.robinmaneiro.orderkiosk.util.InputValidator

class RegistrationViewModel(
    val registerAccountUseCase: RegisterAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun registerAccount(registerPayload: RegisterPayload) {
        val firstNameError = InputValidator.validateRequired(registerPayload.firstName)
        val lastNameError = InputValidator.validateRequired(registerPayload.lastName)
        val emailError = InputValidator.validateEmail(registerPayload.email)
        val passwordError = InputValidator.validatePassword(registerPayload.password)

        if (firstNameError != null || lastNameError != null || emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(
                    firstNameError = firstNameError,
                    lastNameError = lastNameError,
                    emailError = emailError,
                    passwordError = passwordError
                )
            }
            return
        }

        viewModelScope.launch {
            registerAccountUseCase.invoke(registerPayload)
        }
    }

    fun clearFirstNameError() = _uiState.update { it.copy(firstNameError = null) }
    fun clearLastNameError() = _uiState.update { it.copy(lastNameError = null) }
    fun clearEmailError() = _uiState.update { it.copy(emailError = null) }
    fun clearPasswordError() = _uiState.update { it.copy(passwordError = null) }

    data class UiState(
        val hasError: Boolean = false,
        @StringRes val firstNameError: Int? = null,
        @StringRes val lastNameError: Int? = null,
        @StringRes val emailError: Int? = null,
        @StringRes val passwordError: Int? = null
    )
}
