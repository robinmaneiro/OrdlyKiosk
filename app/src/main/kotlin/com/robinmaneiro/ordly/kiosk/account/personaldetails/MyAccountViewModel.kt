package com.robinmaneiro.ordly.kiosk.account.personaldetails

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.ordly.kiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase.UpdateDateOfBirthUseCase
import com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase.UpdateEmailAddressUseCase
import com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase.UpdatePhoneNumberUseCase
import com.robinmaneiro.ordly.kiosk.util.ErrorMapper
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
                .onFailure { throwable ->
                    handleError(throwable)
                }
        }
    }

    private fun handleError(throwable: Throwable) {
        _uiState.update { it.copy(errorMessage = ErrorMapper.getErrorMessage(throwable), isLoading = false) }
    }

    data class UiState(
        val firstName: String = "",
        val lastName: String = "",
        val phoneNumber: String = "",
        val dateOfBirth: String = "",
        val isLoading: Boolean = false,
        @StringRes val errorMessage: Int? = null
    )
}
