package uk.co.softlantic.orderkiosk.account.personaldetails.usecase

import uk.co.softlantic.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import uk.co.softlantic.orderkiosk.account.personaldetails.model.UpdateEmailPayload
import uk.co.softlantic.orderkiosk.account.repository.AccountRepository
import uk.co.softlantic.orderkiosk.util.Mapper

class UpdateEmailAddressUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(updateEmailPayload: UpdateEmailPayload): Result<AccountDetailsResponse> {
        val payload = Mapper.asSerializedStringResult(updateEmailPayload).getOrElse { exception -> return Result.failure(exception) }
        return accountRepository.updateEmailAddress(payload)
    }
}
