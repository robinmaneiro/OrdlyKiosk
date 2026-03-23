package com.robinmaneiro.orderkiosk.account.personaldetails.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.personaldetails.model.UpdatePhonePayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class UpdatePhoneNumberUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val accountRepository = mockk<AccountRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = UpdatePhoneNumberUseCase(accountRepository)

    // Shared payload — phone number split into local number, dialing code, and country code
    private val updatePhonePayload = UpdatePhonePayload(
        phoneNumber = "7911123456",
        dialingCode = "+44",
        countryCode = "GB"
    )

    @Test
    fun `invoke - returns updated account details on success`() = runTest {
        // Arrange: repository returns the full updated account object after the change
        val expected = mockk<AccountDetailsResponse>()
        coEvery { accountRepository.updatePhoneNumber(any()) } returns Result.success(expected)

        // Execute: update the phone number on the account
        val result = useCase(updatePhonePayload)

        // Assert: the result is successful and contains the refreshed account details
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - delegates to repository`() = runTest {
        // Arrange: stub repository to return a successful response
        coEvery { accountRepository.updatePhoneNumber(any()) } returns Result.success(mockk())

        // Execute: update the phone number on the account
        useCase(updatePhonePayload)

        // Assert: the repository was called exactly once — the use case doesn't bypass it
        coVerify(exactly = 1) { accountRepository.updatePhoneNumber(any()) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. invalid phone number format)
        val exception = Exception("Update failed")
        coEvery { accountRepository.updatePhoneNumber(any()) } returns Result.failure(exception)

        // Execute: update the phone number on the account
        val result = useCase(updatePhonePayload)

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
