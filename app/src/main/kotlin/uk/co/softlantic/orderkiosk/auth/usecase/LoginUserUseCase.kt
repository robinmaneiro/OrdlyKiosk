package uk.co.softlantic.orderkiosk.auth.usecase

import uk.co.softlantic.orderkiosk.account.accountdetails.AccountDetailsUseCase
import uk.co.softlantic.orderkiosk.account.login.model.LoginPayload
import uk.co.softlantic.orderkiosk.auth.model.TokenPairResponse
import uk.co.softlantic.orderkiosk.auth.repository.AuthRepository
import uk.co.softlantic.orderkiosk.bag.repository.BagRepository
import uk.co.softlantic.orderkiosk.bag.usecase.GetBagUseCase
import uk.co.softlantic.orderkiosk.bag.usecase.MergeBagsUseCase
import uk.co.softlantic.orderkiosk.datastore.DataStoreRepository
import uk.co.softlantic.orderkiosk.util.Mapper
import uk.co.softlantic.orderkiosk.util.extensions.errorLog

class LoginUserUseCase(
    private val authRepository: AuthRepository,
    private val bagRepository: BagRepository,
    private val dataStore: DataStoreRepository,
    private val accountDetailsUseCase: AccountDetailsUseCase,
    private val getBagUseCase: GetBagUseCase,
    private val mergeBagsUseCase: MergeBagsUseCase
) {
    suspend operator fun invoke(loginPayload: LoginPayload): Result<TokenPairResponse> {
        val payload = Mapper.asSerializedStringResult(loginPayload).getOrElse { exception -> return Result.failure(exception) }

        return authRepository.login(payload)
            .onSuccess { loginResponse ->
                dataStore.saveAuthTokenPair( // Save auth tokens first, so they can be used on the next call to retrieve user details.
                    accessToken = loginResponse.accessToken,
                    refreshToken = loginResponse.refreshToken
                )
                accountDetailsUseCase.invoke()
                    .onSuccess { accountDetailsResponse ->
                        dataStore.saveAccountDetails(accountDetailsResponse)

                        if (bagRepository.bag.value?.items.orEmpty().isNotEmpty()) { // There's Guest bag items to be merged to the Logged-in bag
                            mergeBagsUseCase.invoke(dataStore.getGuestBagId(), dataStore.getAuthBagId())
                        } else { // just retrieve auth bag as normal
                            getBagUseCase.invoke(dataStore.getAuthBagId())
                        }

                        dataStore.clearGuestSessionData()
                    }
                    .onFailure { exception ->
                        dataStore.removeAuthTokenPair()
                        errorLog(exception) { "Failed to retrieve user details" }
                    }
            }
            .onFailure { exception ->
                errorLog(exception) { "Failed to authenticate user" }
            }
    }
}
