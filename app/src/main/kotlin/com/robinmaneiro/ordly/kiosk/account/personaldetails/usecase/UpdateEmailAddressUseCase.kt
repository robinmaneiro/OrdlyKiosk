package com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.ordly.kiosk.account.personaldetails.model.UpdateEmailPayload
import com.robinmaneiro.ordly.kiosk.account.repository.AccountRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper

class UpdateEmailAddressUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updateEmailPayload: UpdateEmailPayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updateEmailPayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updateEmailAddress(payload)
    }
}
