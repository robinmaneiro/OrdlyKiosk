package uk.co.softlantic.orderkiosk.account.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import uk.co.softlantic.orderkiosk.account.registration.model.RegisterPayload
import uk.co.softlantic.orderkiosk.account.registration.usecase.RegisterAccountUseCase

class RegistrationViewModel(
    val registerAccountUseCase: RegisterAccountUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    fun registerAccount(registerPayload: RegisterPayload) {
        viewModelScope.launch {
            registerAccountUseCase.invoke(registerPayload)
        }
    }

    data class UiState(
        val hasError: Boolean = false
    )
}
