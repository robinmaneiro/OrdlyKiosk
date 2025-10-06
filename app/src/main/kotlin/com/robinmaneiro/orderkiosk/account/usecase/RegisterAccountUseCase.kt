package com.robinmaneiro.orderkiosk.account.usecase

import com.robinmaneiro.orderkiosk.account.model.RegisterPayload
import com.robinmaneiro.orderkiosk.account.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class RegisterAccountUseCase(
    val accountRepository: AccountRepository
) {
    suspend operator fun invoke(payload: RegisterPayload): Result<RegistrationResponse> {
        return accountRepository.registerUser(Mapper.asSerializedStringResult(payload).getOrElse { return Result.failure(it) })
        // TODO: Need to track the registration maybe?
    }
}
