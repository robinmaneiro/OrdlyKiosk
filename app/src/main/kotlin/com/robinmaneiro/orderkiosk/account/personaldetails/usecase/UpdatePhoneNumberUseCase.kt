package com.robinmaneiro.orderkiosk.account.personaldetails.usecase

import com.robinmaneiro.orderkiosk.account.login.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.personaldetails.model.UpdatePhonePayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class UpdatePhoneNumberUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updatePhonePayload: UpdatePhonePayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updatePhonePayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updatePhoneNumber(payload)
    }
}
