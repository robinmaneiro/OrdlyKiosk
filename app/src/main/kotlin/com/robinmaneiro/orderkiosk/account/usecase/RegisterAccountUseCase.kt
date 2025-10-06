package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.model.RegisterPayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class RegisterAccountUseCase(
    val accountRepository: AccountRepository
) {
    suspend operator fun invoke(payload: RegisterPayload) {
        accountRepository.registerUser(Mapper.asSerializedStringOrNull(payload) ?: return) // TODO: Need to return some error based on the error responses
        // TODO: Need to track the registration maybe?
    }
}
