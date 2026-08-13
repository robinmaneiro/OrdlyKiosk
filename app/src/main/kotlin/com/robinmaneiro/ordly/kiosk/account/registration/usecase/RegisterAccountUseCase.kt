package com.robinmaneiro.ordly.kiosk.account.registration.usecase

import com.robinmaneiro.ordly.kiosk.account.registration.model.RegisterPayload
import com.robinmaneiro.ordly.kiosk.account.registration.model.RegistrationResponse
import com.robinmaneiro.ordly.kiosk.auth.repository.AuthRepository
import com.robinmaneiro.ordly.kiosk.util.Mapper

class RegisterAccountUseCase(
    val authRepository: AuthRepository
) {
    suspend operator fun invoke(registrationPayload: RegisterPayload): Result<RegistrationResponse> {
        val payload = Mapper.asSerializedStringResult(registrationPayload).getOrElse { exception -> return Result.failure(exception) }
        return authRepository.registerUser(payload)
    }
}
