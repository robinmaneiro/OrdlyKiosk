package uk.co.softlantic.orderkiosk.account.registration.usecase

import uk.co.softlantic.orderkiosk.account.registration.model.RegisterPayload
import uk.co.softlantic.orderkiosk.account.registration.model.RegistrationResponse
import uk.co.softlantic.orderkiosk.auth.repository.AuthRepository
import uk.co.softlantic.orderkiosk.util.Mapper

class RegisterAccountUseCase(
    val authRepository: AuthRepository
) {
    suspend operator fun invoke(registrationPayload: RegisterPayload): Result<RegistrationResponse> {
        val payload = Mapper.asSerializedStringResult(registrationPayload).getOrElse { exception -> return Result.failure(exception) }
        return authRepository.registerUser(payload)
    }
}
