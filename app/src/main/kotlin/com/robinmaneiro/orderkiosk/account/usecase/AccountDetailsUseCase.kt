package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class AccountDetailsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke() {
        accountRepository.getAccountDetails()
    }
}