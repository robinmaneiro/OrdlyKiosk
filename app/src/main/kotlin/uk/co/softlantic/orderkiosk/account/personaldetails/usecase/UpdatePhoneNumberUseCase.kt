package uk.co.softlantic.orderkiosk.account.personaldetails.usecase

import uk.co.softlantic.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import uk.co.softlantic.orderkiosk.account.personaldetails.model.UpdatePhonePayload
import uk.co.softlantic.orderkiosk.account.repository.AccountRepository
import uk.co.softlantic.orderkiosk.util.Mapper

class UpdatePhoneNumberUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updatePhonePayload: UpdatePhonePayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updatePhonePayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updatePhoneNumber(payload)
    }
}
