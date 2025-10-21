package com.robinmaneiro.orderkiosk.networking

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fasterxml.jackson.databind.SerializationFeature
import com.robinmaneiro.orderkiosk.account.usecase.RefreshTokenUseCase
import com.robinmaneiro.orderkiosk.datastore.DataStoreRepository
import io.ktor.client.HttpClient
import io.ktor.client.call.NoTransformationFoundException
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
    lateinit var httpClient: HttpClient // TODO: Change this!
    lateinit var refreshTokenUseCase: RefreshTokenUseCase

    fun initializeChucker(context: Context, dataStore: DataStoreRepository, refreshTokenUseCase: RefreshTokenUseCase) {
        this.refreshTokenUseCase = refreshTokenUseCase // TODO: Change for nullable or something
        val okhttpEngine = OkHttp.create {
            val chuckerInterceptor = ChuckerInterceptor.Builder(context).collector(ChuckerCollector(context)).maxContentLength(length = 250000L).redactHeaders(emptySet())
                .alwaysReadResponseBody(false)
                .build()

            addInterceptor(chuckerInterceptor)
        }

        httpClient = HttpClient(okhttpEngine) {
            install(plugin = ContentNegotiation) {
                jackson {
                    enable(SerializationFeature.INDENT_OUTPUT)
                }
            }

            defaultRequest {
                contentType(ContentType.Application.Json)

                val authAccessToken = runBlocking { dataStore.getAuthAccessToken().takeUnless(String::isEmpty) }
                val guestAccessToken = runBlocking { dataStore.getGuestAccessToken().takeUnless(String::isEmpty) }

                (authAccessToken ?: guestAccessToken)?.let {token ->
                    header("Authorization", "Bearer $token")
                }
            }
        }
    }

    suspend inline fun <reified T> handleRequest(request: suspend () -> HttpResponse): Result<T> {
        return runCatching {
            val response = request()

            when {
                response.status.isSuccess() -> response.body<T>()
                response.status.value == 401 -> throw ResponseException(response, "Unauthorized Access: ${response.status.value}")
                else -> throw ResponseException(response, "HTTP: ${response.status.value}")
            }
        }.recover { exception ->
            if (exception is ResponseException && exception.response.status.value == HttpStatusCode.Unauthorized.value) {
                refreshTokenUseCase.invoke()
                return runCatching { request().body() }
            }

            throw exception
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
            val response = httpClient.post(urlString) {
                headers.forEach { (header, value) -> header(header, value) }
                setBody(stringBody)
            }

            if (!response.status.isSuccess()) {
                throw ResponseException(response, "HTTP ${response.status.value}")
            }

            response
        }
    }

    suspend inline fun <reified T> patchRequest(
        urlString: String,
        headers: Map<String, String> = emptyMap(),
        stringBody: String
    ): Result<T> {
        return handleRequest {
            val response = httpClient.patch(urlString) {
                headers.forEach { (header, value) -> header(header, value) }
                setBody(stringBody)
            }

            if (!response.status.isSuccess()) {
                throw ResponseException(response, "HTTP ${response.status.value}") // TODO: Is this interesting to keep? or should remove?
            }

            response
        }
    }

    suspend inline fun <reified T> deleteRequest(
        urlString: String,
        headers: Map<String, String> = emptyMap()
    ): Result<T> {
        return handleRequest {
            val response = httpClient.delete(urlString) {
                headers.forEach { (header, value) -> header(header, value) }
            }

            response
        }
    }
    //endregion
}
