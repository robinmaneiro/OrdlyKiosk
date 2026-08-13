package com.robinmaneiro.ordly.kiosk.auth.usecase

import com.robinmaneiro.ordly.kiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.MergeBagsUseCase
import com.robinmaneiro.ordly.kiosk.fake.FakeAccountRepository
import com.robinmaneiro.ordly.kiosk.fake.FakeAuthRepository
import com.robinmaneiro.ordly.kiosk.fake.FakeBagRepository
import com.robinmaneiro.ordly.kiosk.fake.FakeDataStoreRepository
import com.robinmaneiro.ordly.kiosk.fake.TestData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import java.io.IOException

class LoginUserUseCaseTest {

    private lateinit var fakeAuthRepository: FakeAuthRepository
    private lateinit var fakeBagRepository: FakeBagRepository
    private lateinit var fakeDataStore: FakeDataStoreRepository
    private lateinit var fakeAccountRepository: FakeAccountRepository
    private lateinit var useCase: LoginUserUseCase

    @Before
    fun setup() {
        fakeAuthRepository = FakeAuthRepository()
        fakeBagRepository = FakeBagRepository()
        fakeDataStore = FakeDataStoreRepository()
        fakeAccountRepository = FakeAccountRepository()

        useCase = LoginUserUseCase(
            authRepository = fakeAuthRepository,
            bagRepository = fakeBagRepository,
            dataStore = fakeDataStore,
            accountDetailsUseCase = AccountDetailsUseCase(fakeAccountRepository),
            getBagUseCase = GetBagUseCase(fakeBagRepository),
            mergeBagsUseCase = MergeBagsUseCase(fakeBagRepository)
        )
    }

    @Test
    fun `successful login saves tokens and account details`() = runTest {
        val tokenPair = TestData.tokenPairResponse()
        val accountDetails = TestData.accountDetailsResponse()
        fakeAuthRepository.loginResult = Result.success(tokenPair)
        fakeAccountRepository.getAccountDetailsResult = Result.success(accountDetails)
        fakeBagRepository.getBagItemsResult = Result.success(TestData.emptyBagResponse())

        val result = useCase.invoke(loginPayload())

        assertTrue(result.isSuccess)
        assertEquals("access-token", fakeDataStore.getAuthAccessToken())
        assertEquals("refresh-token", fakeDataStore.getAuthRefreshToken())
        assertNotNull(fakeDataStore.savedAccountDetails)
        assertEquals("Robin", fakeDataStore.savedAccountDetails?.firstName)
    }

    @Test
    fun `successful login clears guest session data`() = runTest {
        fakeDataStore.saveGuestSessionPair("guest-access", "guest-refresh")
        fakeAuthRepository.loginResult = Result.success(TestData.tokenPairResponse())
        fakeAccountRepository.getAccountDetailsResult = Result.success(TestData.accountDetailsResponse())
        fakeBagRepository.getBagItemsResult = Result.success(TestData.emptyBagResponse())

        useCase.invoke(loginPayload())

        assertEquals("", fakeDataStore.getGuestAccessToken())
        assertEquals("", fakeDataStore.getGuestRefreshToken())
    }

    @Test
    fun `login merges bags when guest bag has items`() = runTest {
        fakeAuthRepository.loginResult = Result.success(TestData.tokenPairResponse())
        fakeAccountRepository.getAccountDetailsResult = Result.success(TestData.accountDetailsResponse())
        fakeBagRepository.setBag(TestData.bagResponse())
        fakeBagRepository.mergeBagsResult = Result.success(TestData.bagResponse())

        val result = useCase.invoke(loginPayload())

        assertTrue(result.isSuccess)
    }

    @Test
    fun `login fetches auth bag when guest bag is empty`() = runTest {
        fakeAuthRepository.loginResult = Result.success(TestData.tokenPairResponse())
        fakeAccountRepository.getAccountDetailsResult = Result.success(TestData.accountDetailsResponse())
        fakeBagRepository.setBag(TestData.emptyBagResponse())
        fakeBagRepository.getBagItemsResult = Result.success(TestData.emptyBagResponse())

        val result = useCase.invoke(loginPayload())

        assertTrue(result.isSuccess)
    }

    @Test
    fun `failed login returns failure`() = runTest {
        fakeAuthRepository.loginResult = Result.failure(IOException("Network error"))

        val result = useCase.invoke(loginPayload())

        assertTrue(result.isFailure)
    }

    @Test
    fun `failed account details removes auth tokens`() = runTest {
        fakeAuthRepository.loginResult = Result.success(TestData.tokenPairResponse())
        fakeAccountRepository.getAccountDetailsResult = Result.failure(IOException("Server error"))

        useCase.invoke(loginPayload())

        assertEquals("", fakeDataStore.getAuthAccessToken())
        assertEquals("", fakeDataStore.getAuthRefreshToken())
        assertNull(fakeDataStore.savedAccountDetails)
    }

    private fun loginPayload() = com.robinmaneiro.ordly.kiosk.account.login.model.LoginPayload(
        email = "robin@test.com",
        password = "password123"
    )
}
