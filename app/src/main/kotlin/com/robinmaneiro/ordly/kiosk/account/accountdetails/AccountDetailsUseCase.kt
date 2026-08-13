package com.robinmaneiro.ordly.kiosk.account.accountdetails

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.ordly.kiosk.account.repository.AccountRepository

class AccountDetailsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<AccountDetailsResponse> {
        return accountRepository.getAccountDetails()
    }
}
