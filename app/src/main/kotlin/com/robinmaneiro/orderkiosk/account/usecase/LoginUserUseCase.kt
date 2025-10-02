package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class LoginUserUseCase(
    val accountRepository: AccountRepository
) {
    suspend operator fun invoke(userName: String, password: String) {
        accountRepository.login(userName, password)
    }
}
