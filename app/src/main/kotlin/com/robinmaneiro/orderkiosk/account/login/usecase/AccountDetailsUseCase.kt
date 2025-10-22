package com.robinmaneiro.orderkiosk.account.login.usecase

import com.robinmaneiro.orderkiosk.account.login.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class AccountDetailsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<AccountDetailsResponse> {
        return accountRepository.getAccountDetails()
    }
}