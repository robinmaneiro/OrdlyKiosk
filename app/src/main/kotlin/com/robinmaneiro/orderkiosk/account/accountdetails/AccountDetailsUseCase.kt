package com.robinmaneiro.orderkiosk.account.accountdetails

import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class AccountDetailsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<AccountDetailsResponse> {
        return accountRepository.getAccountDetails()
    }
}
