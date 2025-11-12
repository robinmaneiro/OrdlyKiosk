package com.robinmaneiro.orderkiosk.networking

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.robinmaneiro.orderkiosk.account.guestsession.usecase.RefreshGuestSessionUseCase
import com.robinmaneiro.orderkiosk.account.login.usecase.RefreshTokenUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import io.ktor.serialization.jackson.jackson
import kotlinx.coroutines.runBlocking

object NetworkManager {
    // TODO: Remove 'lateinit' variables
    lateinit var httpClient: HttpClient
    lateinit var refreshTokenUseCase: RefreshTokenUseCase
    lateinit var refreshGuestSessionUseCase: RefreshGuestSessionUseCase
    lateinit var dataStore: DataStoreRepository

    fun initialize(context: Context, dataStore: DataStoreRepository, refreshTokenUseCase: RefreshTokenUseCase, refreshGuestSessionUseCase: RefreshGuestSessionUseCase) {
        this.refreshGuestSessionUseCase = refreshGuestSessionUseCase
        this.refreshTokenUseCase = refreshTokenUseCase
        this.dataStore = dataStore

        val okhttpEngine = OkHttp.create {
            val chuckerInterceptor = ChuckerInterceptor.Builder(context)
                .collector(ChuckerCollector(context))
                .maxContentLength(length = 250000L)
                .redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()

            addInterceptor(chuckerInterceptor)
        }

        httpClient = HttpClient(okhttpEngine) {
            install(plugin = ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                    configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                }
            }

            defaultRequest {
                contentType(ContentType.Application.Json)

                val token = runBlocking {
                    dataStore.getAuthAccessToken().ifEmpty { dataStore.getGuestAccessToken() } // User is not considered logged-in until the 'users/me' call is done, check token instead.
                }

                header("Authorization", "Bearer $token")
            }
        }
    }

    suspend inline fun <reified T> handleRequest(request: suspend () -> HttpResponse): Result<T> {
        return runCatching {
            val response = request()

            when {
                response.status.isSuccess() -> response.body<T>()
                response.status.value == HttpStatusCode.Unauthorized.value -> throw ResponseException(response, "Unauthorized Access: ${response.status.value}")
                else -> throw ResponseException(response, "HTTP: ${response.status.value}")
            }
        }.recover { exception ->
            if (exception is ResponseException && exception.response.status.value == HttpStatusCode.Unauthorized.value) {
                if (dataStore.isUserLoggedIn()) {
                    refreshTokenUseCase.invoke()
                } else {
                    refreshGuestSessionUseCase.invoke()
                }

                return runCatching { request().body() } // Repeat the request that originally returned a 401.
            }

            return Result.failure(exception) // Return any other exception to be handled individually
        }
    }

    //region Network Requests
    suspend inline fun <reified T> getRequest(
        urlString: String,
        headers: Map<String, String> = emptyMap()
    ): Result<T> {
        return handleRequest {
            httpClient.get(urlString) {
                headers.forEach { (name, value) -> header(name, value) }
            }
        }
    }

    suspend inline fun <reified T> postRequest(
        urlString: String,
        headers: Map<String, String> = emptyMap(),
        stringBody: String
    ): Result<T> {
        return handleRequest {
            httpClient.post(urlString) {
                headers.forEach { (header, value) -> header(header, value) }
                setBody(stringBody)
            }
        }
    }

    suspend inline fun <reified T> patchRequest(
        urlString: String,
        headers: Map<String, String> = emptyMap(),
        stringBody: String
    ): Result<T> {
        return handleRequest {
            httpClient.patch(urlString) {
                headers.forEach { (header, value) -> header(header, value) }
                setBody(stringBody)
            }
        }
    }

    suspend inline fun <reified T> deleteRequest(
        urlString: String,
        headers: Map<String, String> = emptyMap()
    ): Result<T> {
        return handleRequest {
            httpClient.delete(urlString) {
                headers.forEach { (header, value) -> header(header, value) }
            }
        }
    }
    //endregion
}
