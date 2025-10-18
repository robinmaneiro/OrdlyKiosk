package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class GuestSessionDetailsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<GuestSessionDetailsResponse> {
        return accountRepository.getGuestSessionDetails()
    }
}