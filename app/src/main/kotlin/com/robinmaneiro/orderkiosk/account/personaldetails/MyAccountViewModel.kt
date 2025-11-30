package com.robinmaneiro.orderkiosk.account.personaldetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.orderkiosk.account.personaldetails.usecase.UpdateDateOfBirthUseCase
import com.robinmaneiro.orderkiosk.account.personaldetails.usecase.UpdateEmailAddressUseCase
import com.robinmaneiro.orderkiosk.account.personaldetails.usecase.UpdatePhoneNumberUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MyAccountViewModel(
    private val accountDetailsUseCase: AccountDetailsUseCase,
    private val updateDateOfBirthUseCase: UpdateDateOfBirthUseCase,
    private val updateEmailAddressUseCase: UpdateEmailAddressUseCase,
    private val updatePhoneNumberUseCase: UpdatePhoneNumberUseCase
) : ViewModel() {

    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            accountDetailsUseCase.invoke()
                .onSuccess { accountDetails ->
                    _uiState.update {
                        it.copy(
                            firstName = accountDetails.firstName,
                            lastName = accountDetails.lastName,
                            dateOfBirth = accountDetails.dateOfBirth.orEmpty(),
                            phoneNumber = accountDetails.phoneNumber
                        )
                    }
                }
                .onFailure {
                    handleError()
                }
        }
    }

    private fun handleError() {
        _uiState.update { it.copy(hasError = true, isLoading = false) }
    }

    data class UiState(
        val firstName: String = "",
        val lastName: String = "",
        val phoneNumber: String = "",
        val dateOfBirth: String = "",
        val isLoading: Boolean = false,
        val hasError: Boolean = true
    )
}
