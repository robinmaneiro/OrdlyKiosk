package com.robinmaneiro.orderkiosk.account.personaldetails.usecase

import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.personaldetails.model.UpdateEmailPayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class UpdateEmailAddressUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updateEmailPayload: UpdateEmailPayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updateEmailPayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updateEmailAddress(payload)
    }
}
