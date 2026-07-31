package com.robinmaneiro.orderkiosk.util

import androidx.annotation.StringRes
import io.ktor.client.plugins.ResponseException
import io.ktor.http.HttpStatusCode
import java.io.IOException
import com.robinmaneiro.orderkiosk.R

object ErrorMapper {

    @StringRes
    fun getErrorMessage(throwable: Throwable?): Int = when {
        throwable == null -> R.string.error_dialog_body
        throwable is IOException -> R.string.error_network
        throwable is ResponseException && throwable.response.status.value == HttpStatusCode.Unauthorized.value -> R.string.error_unauthorized
        throwable is ResponseException && throwable.response.status.value >= HttpStatusCode.InternalServerError.value -> R.string.error_server
        else -> R.string.error_dialog_body
    }
}
