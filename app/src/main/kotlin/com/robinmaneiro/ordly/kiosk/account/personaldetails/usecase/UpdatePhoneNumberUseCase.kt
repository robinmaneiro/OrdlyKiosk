package com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.ordly.kiosk.account.personaldetails.model.UpdatePhonePayload
import com.robinmaneiro.ordly.kiosk.account.repository.AccountRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper

class UpdatePhoneNumberUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updatePhonePayload: UpdatePhonePayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updatePhonePayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updatePhoneNumber(payload)
    }
}
