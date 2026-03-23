package com.robinmaneiro.orderkiosk.account.personaldetails.usecase

import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import com.robinmaneiro.orderkiosk.account.accountdetails.model.AccountDetailsResponse
import com.robinmaneiro.orderkiosk.account.personaldetails.model.UpdateEmailPayload
import com.robinmaneiro.orderkiosk.account.repository.AccountRepository

class UpdateEmailAddressUseCaseTest {

    // Mock the repository so network/db calls never execute during tests
    private val accountRepository = mockk<AccountRepository>()

    // The use case under test, constructed with the mocked dependency
    private val useCase = UpdateEmailAddressUseCase(accountRepository)

    // Shared payload — the new email address to update to
    private val updateEmailPayload = UpdateEmailPayload(emailAddress = "new@test.com")

    @Test
    fun `invoke - returns updated account details on success`() = runTest {
        // Arrange: repository returns the full updated account object after the change
        val expected = mockk<AccountDetailsResponse>()
        coEvery { accountRepository.updateEmailAddress(any()) } returns Result.success(expected)

        // Execute: update the email address on the account
        val result = useCase(updateEmailPayload)

        // Assert: the result is successful and contains the refreshed account details
        assertTrue(result.isSuccess)
        assertEquals(expected, result.getOrNull())
    }

    @Test
    fun `invoke - delegates to repository`() = runTest {
        // Arrange: stub repository to return a successful response
        coEvery { accountRepository.updateEmailAddress(any()) } returns Result.success(mockk())

        // Execute: update the email address on the account
        useCase(updateEmailPayload)

        // Assert: the repository was called exactly once — the use case doesn't bypass it
        coVerify(exactly = 1) { accountRepository.updateEmailAddress(any()) }
    }

    @Test
    fun `invoke - propagates failure from repository`() = runTest {
        // Arrange: repository returns a failure (e.g. email already taken)
        val exception = Exception("Update failed")
        coEvery { accountRepository.updateEmailAddress(any()) } returns Result.failure(exception)

        // Execute: update the email address on the account
        val result = useCase(updateEmailPayload)

        // Assert: the failure is surfaced back to the caller unchanged
        assertTrue(result.isFailure)
        assertEquals(exception, result.exceptionOrNull())
    }
}
