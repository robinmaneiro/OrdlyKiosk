package com.robinmaneiro.orderkiosk.account.guestsession.usecase

import com.robinmaneiro.orderkiosk.account.guestsession.model.GuestSessionDetailsResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class GuestSessionDetailsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<GuestSessionDetailsResponse> {
        return accountRepository.getGuestSessionDetails()
    }
}