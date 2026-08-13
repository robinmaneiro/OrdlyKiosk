package com.robinmaneiro.ordly.kiosk.auth.usecase

import com.robinmaneiro.ordly.kiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.ordly.kiosk.account.login.model.LoginPayload
import com.robinmaneiro.ordly.kiosk.auth.model.TokenPairResponse
import com.robinmaneiro.ordly.kiosk.auth.repository.AuthRepository
import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepository
import com.robinmaneiro.ordly.kiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.MergeBagsUseCase
import com.robinmaneiro.ordly.kiosk.datastore.AccountDetails
import com.robinmaneiro.ordly.kiosk.datastore.DataStoreRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper
import com.robinmaneiro.ordly.kiosk.util.extensions.errorLog

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
                        dataStore.saveAccountDetails(
                            AccountDetails(
                                userId = accountDetailsResponse.userId,
                                firstName = accountDetailsResponse.firstName,
                                lastName = accountDetailsResponse.lastName,
                                emailAddress = accountDetailsResponse.emailAddress,
                                dateOfBirth = accountDetailsResponse.dateOfBirth,
                                phoneNumber = accountDetailsResponse.phoneNumber,
                                bagId = accountDetailsResponse.bagId,
                                wishlistId = accountDetailsResponse.wishlistId
                            )
                        )

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
