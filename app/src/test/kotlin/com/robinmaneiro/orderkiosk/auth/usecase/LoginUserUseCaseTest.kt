package com.robinmaneiro.orderkiosk.auth.usecase

import io.mockk.coEvery
import io.mockk.coJustRun
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.login.model.LoginPayload
import com.robinmaneiro.orderkiosk.auth.model.TokenPairResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.MergeBagsUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository

class LoginUserUseCaseTest {

    // All dependencies are mocked — login involves several collaborators
    private val authRepository = mockk<AuthRepository>()
    private val bagRepository = mockk<BagRepository>()
    private val dataStore = mockk<DataStoreRepository>()
    private val accountDetailsUseCase = mockk<AccountDetailsUseCase>()
    private val getBagUseCase = mockk<GetBagUseCase>()
    private val mergeBagsUseCase = mockk<MergeBagsUseCase>()

    // The use case under test, wired with all mocked dependencies
    private val useCase = LoginUserUseCase(
        authRepository = authRepository,
        bagRepository = bagRepository,
        dataStore = dataStore,
        accountDetailsUseCase = accountDetailsUseCase,
        getBagUseCase = getBagUseCase,
        mergeBagsUseCase = mergeBagsUseCase
    )

    // Shared test data — reused across multiple tests to reduce noise
    private val loginPayload = LoginPayload(email = "user@test.com", password = "password123")
    private val tokenPair = TokenPairResponse(accessToken = "access-token", refreshToken = "refresh-token")
    private val accountDetails = mockk<AccountDetailsResponse>()

    @Test
    fun `invoke - returns failure when login fails`() = runTest {
        // Arrange: auth repository rejects the credentials
        val exception = Exception("Invalid credentials")
        coEvery { authRepository.login(any()) } returns Result.failure(exception)

        // Execute: attempt to log in with the provided credentials
        val result = useCase(loginPayload)

        // Assert: the failure propagates without further processing
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }

    @Test
    fun `invoke - saves auth tokens on login success`() = runTest {
        // Arrange: set up a full happy-path scenario with no guest bag items
        setupSuccessfulLoginWithEmptyBag()

        // Execute: attempt to log in with the provided credentials
        useCase(loginPayload)

        // Assert: both access and refresh tokens were persisted to the data store
        coVerify(exactly = 1) { dataStore.saveAuthTokenPair(tokenPair.accessToken, tokenPair.refreshToken) }
    }

    @Test
    fun `invoke - gets auth bag when guest bag is empty`() = runTest {
        // Arrange: guest bag is null (no items), so no merge is needed
        val authBagId = "auth-bag-123"
        setupSuccessfulLoginWithEmptyBag(authBagId = authBagId)

        // Execute: attempt to log in with the provided credentials
        useCase(loginPayload)

        // Assert: the auth bag is fetched directly and merging is skipped
        coVerify(exactly = 1) { getBagUseCase.invoke(authBagId) }
        coVerify(exactly = 0) { mergeBagsUseCase.invoke(any(), any()) }
    }

    @Test
    fun `invoke - merges bags when guest bag has items`() = runTest {
        // Arrange: guest bag contains at least one item, so it must be merged into the auth bag
        val guestBagId = "guest-bag-123"
        val authBagId = "auth-bag-456"
        val bagResponseWithItems = mockk<BagResponse>()
        every { bagResponseWithItems.items } returns persistentListOf(mockk<BagItem>(relaxed = true))
        every { bagRepository.bag } returns MutableStateFlow(bagResponseWithItems)
        coEvery { authRepository.login(any()) } returns Result.success(tokenPair)
        coJustRun { dataStore.saveAuthTokenPair(any(), any()) }
        coEvery { accountDetailsUseCase.invoke() } returns Result.success(accountDetails)
        coJustRun { dataStore.saveAccountDetails(accountDetails) }
        coEvery { dataStore.getGuestBagId() } returns guestBagId
        coEvery { dataStore.getAuthBagId() } returns authBagId
        coJustRun { mergeBagsUseCase.invoke(guestBagId, authBagId) }
        coJustRun { dataStore.clearGuestSessionData() }

        // Execute: attempt to log in with the provided credentials
        useCase(loginPayload)

        // Assert: merge is called instead of a plain getBag
        coVerify(exactly = 1) { mergeBagsUseCase.invoke(guestBagId, authBagId) }
        coVerify(exactly = 0) { getBagUseCase.invoke(any()) }
    }

    @Test
    fun `invoke - removes auth token pair when account details retrieval fails`() = runTest {
        // Arrange: login succeeds but fetching account details fails — tokens should be rolled back
        every { bagRepository.bag } returns MutableStateFlow(null)
        coEvery { authRepository.login(any()) } returns Result.success(tokenPair)
        coJustRun { dataStore.saveAuthTokenPair(any(), any()) }
        coEvery { accountDetailsUseCase.invoke() } returns Result.failure(Exception("Account details failed"))
        coJustRun { dataStore.removeAuthTokenPair() }

        // Execute: attempt to log in with the provided credentials
        useCase(loginPayload)

        // Assert: tokens are removed so the user isn't left in a partial auth state
        coVerify(exactly = 1) { dataStore.removeAuthTokenPair() }
        coVerify(exactly = 0) { dataStore.saveAccountDetails(any()) }
    }

    @Test
    fun `invoke - clears guest session data after successful login`() = runTest {
        // Arrange: set up a full happy-path scenario via the shared helper
        setupSuccessfulLoginWithEmptyBag()

        // Execute: attempt to log in with the provided credentials
        useCase(loginPayload)

        // Assert: guest tokens and bag id are wiped so the next session starts clean
        coVerify(exactly = 1) { dataStore.clearGuestSessionData() }
    }

    // Helper that stubs every dependency for the straightforward happy path:
    // login succeeds, account details are fetched, guest bag has no items, auth bag is loaded.
    private fun setupSuccessfulLoginWithEmptyBag(authBagId: String = "auth-bag-123") {
        every { bagRepository.bag } returns MutableStateFlow(null)
        coEvery { authRepository.login(any()) } returns Result.success(tokenPair)
        coJustRun { dataStore.saveAuthTokenPair(any(), any()) }
        coEvery { accountDetailsUseCase.invoke() } returns Result.success(accountDetails)
        coJustRun { dataStore.saveAccountDetails(accountDetails) }
        coEvery { dataStore.getAuthBagId() } returns authBagId
        coEvery { getBagUseCase.invoke(authBagId) } returns Result.success(mockk())
        coJustRun { dataStore.clearGuestSessionData() }
    }
}
