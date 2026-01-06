package uk.co.softlantic.orderkiosk.account.accountdetails

import uk.co.softlantic.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import uk.co.softlantic.orderkiosk.account.repository.AccountRepository

class AccountDetailsUseCase(
    private val accountRepository: AccountRepository
) {
    suspend operator fun invoke(): Result<AccountDetailsResponse> {
        return accountRepository.getAccountDetails()
    }
}
