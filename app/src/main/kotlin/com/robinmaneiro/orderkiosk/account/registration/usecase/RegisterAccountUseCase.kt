package com.robinmaneiro.orderkiosk.account.registration.usecase

import com.robinmaneiro.orderkiosk.account.registration.model.RegisterPayload
import com.robinmaneiro.orderkiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.orderkiosk.auth.repository.AuthRepository
import com.robinmaneiro.orderkiosk.util.Mapper

class RegisterAccountUseCase(
    val authRepository: AuthRepository
) {
    suspend operator fun invoke(registrationPayload: RegisterPayload): Result<RegistrationResponse> {
        val payload = Mapper.asSerializedStringResult(registrationPayload).getOrElse { exception -> return Result.failure(exception) }
        return authRepository.registerUser(payload)
    }
}
