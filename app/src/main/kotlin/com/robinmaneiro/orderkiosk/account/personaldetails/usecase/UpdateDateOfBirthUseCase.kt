package com.robinmaneiro.orderkiosk.account.personaldetails.usecase

import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.personaldetails.model.UpdateDobPayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class UpdateDateOfBirthUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updateDobPayload: UpdateDobPayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updateDobPayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updateDateOfBirth(payload)
    }
}
