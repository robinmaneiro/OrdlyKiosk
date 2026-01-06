package uk.co.softlantic.orderkiosk.account.personaldetails.usecase

import uk.co.softlantic.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import uk.co.softlantic.orderkiosk.account.personaldetails.model.UpdateDobPayload
import uk.co.softlantic.orderkiosk.account.repository.AccountRepository
import uk.co.softlantic.orderkiosk.util.Mapper

class UpdateDateOfBirthUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updateDobPayload: UpdateDobPayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updateDobPayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updateDateOfBirth(payload)
    }
}
