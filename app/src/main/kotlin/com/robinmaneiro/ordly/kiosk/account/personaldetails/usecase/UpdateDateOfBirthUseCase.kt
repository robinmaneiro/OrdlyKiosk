package com.robinmaneiro.ordly.kiosk.account.personaldetails.usecase

import com.robinmaneiro.ordly.kiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.ordly.kiosk.account.personaldetails.model.UpdateDobPayload
import com.robinmaneiro.ordly.kiosk.account.repository.AccountRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper

class UpdateDateOfBirthUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updateDobPayload: UpdateDobPayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updateDobPayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updateDateOfBirth(payload)
    }
}
