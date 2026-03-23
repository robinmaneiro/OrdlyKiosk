package com.robinmaneiro.orderkiosk.account.registration.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.account.registration.model.RegisterPayload
import com.robinmaneiro.orderkiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository

class RegisterAccountUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val authRepository = mockk<AuthRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = RegisterAccountUseCase(authRepository)

    // Shared test payload — a realistic set of registration details reused across tests
    private val registerPayload = RegisterPayload(
        title = "Mr",
        firstName = "John",
        lastName = "Doe",
        email = "john@test.com",
        password = "password123"
    )

    @Test
    fun `invoke - returns registration response on success`() = runTest {
        // Arrange: stub repository to return a successful response with the new user id
        val expected = RegistrationResponse(id = "user-123")
        coEvery { authRepository.registerUser(any()) } returns Result.success(expected)

        // Execute: register the new account
        val result = useCase(registerPayload)

        // Assert: the result is successful and contains the new user's details
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - delegates to repository`() = runTest {
        // Arrange: stub repository to return a successful response
        coEvery { authRepository.registerUser(any()) } returns Result.success(mockk())

        // Execute: register the new account
        useCase(registerPayload)

        // Assert: the repository was called exactly once — the use case doesn't bypass it
        coVerify(exactly = 1) { authRepository.registerUser(any()) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. email already in use)
        val exception = Exception("Registration failed")
        coEvery { authRepository.registerUser(any()) } returns Result.failure(exception)

        // Execute: register the new account
        val result = useCase(registerPayload)

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
