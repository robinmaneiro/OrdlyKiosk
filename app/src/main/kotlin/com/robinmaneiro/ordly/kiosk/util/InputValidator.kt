package com.robinmaneiro.ordly.kiosk.util

import android.util.Patterns
import androidx.annotation.StringRes
import com.robinmaneiro.ordly.kiosk.R

object InputValidator {

    const val MIN_PASSWORD_LENGTH = 8

    @StringRes
    fun validateEmail(email: String): Int? = when {
        email.isBlank() -> R.string.error_field_required
        !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> R.string.error_email_invalid
        else -> null
    }

    @StringRes
    fun validatePassword(password: String): Int? = when {
        password.isBlank() -> R.string.error_field_required
        password.length < MIN_PASSWORD_LENGTH -> R.string.error_password_too_short
        else -> null
    }

    @StringRes
    fun validateRequired(value: String): Int? = when {
        value.isBlank() -> R.string.error_field_required
        else -> null
    }
}
