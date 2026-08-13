package com.robinmaneiro.ordly.kiosk.account.registration

import com.robinmaneiro.ordly.kiosk.R
import com.robinmaneiro.ordly.kiosk.account.registration.model.RegisterPayload
import com.robinmaneiro.ordly.kiosk.account.registration.usecase.RegisterAccountUseCase
import com.robinmaneiro.ordly.kiosk.fake.FakeAuthRepository
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
class RegistrationViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fakeAuthRepository: FakeAuthRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        fakeAuthRepository = FakeAuthRepository()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun createViewModel() = RegistrationViewModel(
        registerAccountUseCase = RegisterAccountUseCase(fakeAuthRepository)
    )

    @Test
    fun `initial state has no errors`() {
        val viewModel = createViewModel()

        val state = viewModel.uiState.value
        assertEquals(false, state.hasError)
        assertNull(state.firstNameError)
        assertNull(state.lastNameError)
        assertNull(state.emailError)
        assertNull(state.passwordError)
    }

    @Test
    fun `blank first name shows required error`() {
        val viewModel = createViewModel()

        viewModel.registerAccount(allBlankPayload())

        assertEquals(R.string.error_field_required, viewModel.uiState.value.firstNameError)
    }

    @Test
    fun `blank last name shows required error`() {
        val viewModel = createViewModel()

        viewModel.registerAccount(allBlankPayload())

        assertEquals(R.string.error_field_required, viewModel.uiState.value.lastNameError)
    }

    @Test
    fun `blank email shows required error`() {
        val viewModel = createViewModel()

        viewModel.registerAccount(allBlankPayload())

        assertEquals(R.string.error_field_required, viewModel.uiState.value.emailError)
    }

    @Test
    fun `blank password shows required error`() {
        val viewModel = createViewModel()

        viewModel.registerAccount(allBlankPayload())

        assertEquals(R.string.error_field_required, viewModel.uiState.value.passwordError)
    }

    @Test
    fun `short password shows too short error`() {
        val viewModel = createViewModel()

        viewModel.registerAccount(allBlankPayload().copy(password = "abc"))

        assertEquals(R.string.error_password_too_short, viewModel.uiState.value.passwordError)
    }

    @Test
    fun `all blank fields show all errors simultaneously`() {
        val viewModel = createViewModel()

        viewModel.registerAccount(allBlankPayload())

        val state = viewModel.uiState.value
        assertNotNull(state.firstNameError)
        assertNotNull(state.lastNameError)
        assertNotNull(state.emailError)
        assertNotNull(state.passwordError)
    }

    @Test
    fun `validation errors prevent network call and do not change loading state`() {
        val viewModel = createViewModel()

        viewModel.registerAccount(allBlankPayload())

        assertEquals(false, viewModel.uiState.value.hasError)
        assertNotNull(viewModel.uiState.value.firstNameError)
    }

    @Test
    fun `clearFirstNameError resets first name error`() {
        val viewModel = createViewModel()
        viewModel.registerAccount(allBlankPayload())
        assertNotNull(viewModel.uiState.value.firstNameError)

        viewModel.clearFirstNameError()

        assertNull(viewModel.uiState.value.firstNameError)
    }

    @Test
    fun `clearLastNameError resets last name error`() {
        val viewModel = createViewModel()
        viewModel.registerAccount(allBlankPayload())
        assertNotNull(viewModel.uiState.value.lastNameError)

        viewModel.clearLastNameError()

        assertNull(viewModel.uiState.value.lastNameError)
    }

    @Test
    fun `clearEmailError resets email error`() {
        val viewModel = createViewModel()
        viewModel.registerAccount(allBlankPayload())
        assertNotNull(viewModel.uiState.value.emailError)

        viewModel.clearEmailError()

        assertNull(viewModel.uiState.value.emailError)
    }

    @Test
    fun `clearPasswordError resets password error`() {
        val viewModel = createViewModel()
        viewModel.registerAccount(allBlankPayload())
        assertNotNull(viewModel.uiState.value.passwordError)

        viewModel.clearPasswordError()

        assertNull(viewModel.uiState.value.passwordError)
    }

    @Test
    fun `clearing one error does not affect other errors`() {
        val viewModel = createViewModel()
        viewModel.registerAccount(allBlankPayload())

        viewModel.clearFirstNameError()

        assertNull(viewModel.uiState.value.firstNameError)
        assertNotNull(viewModel.uiState.value.lastNameError)
        assertNotNull(viewModel.uiState.value.emailError)
        assertNotNull(viewModel.uiState.value.passwordError)
    }

    private fun allBlankPayload() = RegisterPayload(
        title = "Mr",
        firstName = "",
        lastName = "",
        email = "",
        password = ""
    )
}
