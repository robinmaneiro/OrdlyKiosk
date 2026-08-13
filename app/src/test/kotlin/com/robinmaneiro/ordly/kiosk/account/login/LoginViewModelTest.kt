package com.robinmaneiro.ordly.kiosk.account.login

import com.robinmaneiro.ordly.kiosk.R
import com.robinmaneiro.ordly.kiosk.account.accountdetails.AccountDetailsUseCase
import com.robinmaneiro.ordly.kiosk.account.login.model.LoginPayload
import com.robinmaneiro.ordly.kiosk.auth.usecase.LoginUserUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.ordly.kiosk.bag.usecase.MergeBagsUseCase
import com.robinmaneiro.ordly.kiosk.fake.FakeAccountRepository
import com.robinmaneiro.ordly.kiosk.fake.FakeAuthRepository
import com.robinmaneiro.ordly.kiosk.fake.FakeBagRepository
import com.robinmaneiro.ordly.kiosk.fake.FakeDataStoreRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var loginUserUseCase: LoginUserUseCase

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        loginUserUseCase = LoginUserUseCase(
            authRepository = FakeAuthRepository(),
            bagRepository = FakeBagRepository(),
            dataStore = FakeDataStoreRepository(),
            accountDetailsUseCase = AccountDetailsUseCase(FakeAccountRepository()),
            getBagUseCase = GetBagUseCase(FakeBagRepository()),
            mergeBagsUseCase = MergeBagsUseCase(FakeBagRepository())
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = LoginViewModel(loginUserUseCase)

    @Test
    fun `initial state has no errors and is not loading`() {
        val viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertEquals(false, state.isLoading)
        assertNull(state.errorMessage)
        assertNull(state.emailError)
        assertNull(state.passwordError)
    }

    @Test
    fun `login with blank email shows required field error`() {
        val viewModel = createViewModel()

        viewModel.loginUser(LoginPayload(email = "", password = "password123"))

        assertEquals(R.string.error_field_required, viewModel.uiState.value.emailError)
        assertEquals(false, viewModel.uiState.value.isLoading)
    }

    @Test
    fun `login with blank password shows required field error`() {
        val viewModel = createViewModel()

        viewModel.loginUser(LoginPayload(email = "", password = ""))

        assertEquals(R.string.error_field_required, viewModel.uiState.value.passwordError)
    }

    @Test
    fun `login with short password shows too short error`() {
        val viewModel = createViewModel()

        viewModel.loginUser(LoginPayload(email = "", password = "short"))

        assertEquals(R.string.error_password_too_short, viewModel.uiState.value.passwordError)
    }

    @Test
    fun `validation errors prevent network call`() {
        val viewModel = createViewModel()

        viewModel.loginUser(LoginPayload(email = "", password = ""))

        // Should not be loading since validation failed before network call
        assertEquals(false, viewModel.uiState.value.isLoading)
        assertNotNull(viewModel.uiState.value.emailError)
        assertNotNull(viewModel.uiState.value.passwordError)
    }

    @Test
    fun `clearEmailError resets email error`() {
        val viewModel = createViewModel()
        viewModel.loginUser(LoginPayload(email = "", password = "password123"))
        assertNotNull(viewModel.uiState.value.emailError)

        viewModel.clearEmailError()

        assertNull(viewModel.uiState.value.emailError)
    }

    @Test
    fun `clearPasswordError resets password error`() {
        val viewModel = createViewModel()
        viewModel.loginUser(LoginPayload(email = "", password = ""))
        assertNotNull(viewModel.uiState.value.passwordError)

        viewModel.clearPasswordError()

        assertNull(viewModel.uiState.value.passwordError)
    }

    @Test
    fun `clearing one error does not affect other errors`() {
        val viewModel = createViewModel()
        viewModel.loginUser(LoginPayload(email = "", password = ""))
        assertNotNull(viewModel.uiState.value.emailError)
        assertNotNull(viewModel.uiState.value.passwordError)

        viewModel.clearEmailError()

        assertNull(viewModel.uiState.value.emailError)
        assertNotNull(viewModel.uiState.value.passwordError)
    }
}
