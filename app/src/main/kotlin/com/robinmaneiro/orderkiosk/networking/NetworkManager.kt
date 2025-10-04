package com.robinmaneiro.orderkiosk.networking

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.http.isSuccess
import io.ktor.serialization.jackson.jackson

object NetworkManager {
    lateinit var httpClient: HttpClient // TODO: Dangerous or not? :/

    fun initializeChucker(context: Context) {
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
        }
    }

    //region Network Requests
    suspend inline fun <reified T> getRequest(urlString: String): Result<T> {
        return runCatching {
            val response = httpClient.get(urlString) {
                headers {
                    append(NetworkConstants.Headers.CONTENT_TYPE, NetworkConstants.Values.CONTENT_TYPE)
                }
            }

            response.body<T>()
        }
    }

    suspend inline fun <reified T> postRequest(urlString: String, stringBody: String): Result<T> {
        return runCatching {
            val response = httpClient.post(urlString) {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    $stringBody
                """.trimIndent()
                )
            }

            if (!response.status.isSuccess()) {
                throw ResponseException(response, "HTTP ${response.status.value}")
            }

            response.body<T>()
        }
    }

    suspend inline fun <reified T> patchRequest(urlString: String, stringBody: String): Result<T> {
        return runCatching {
            val response = httpClient.patch(urlString) {
                contentType(ContentType.Application.Json)
                setBody(
                    """
                    $stringBody
                """.trimIndent()
                )
            }

            if (!response.status.isSuccess()) {
                throw ResponseException(response, "HTTP ${response.status.value}")
            }

            response.body<T>()
        }
    }

    suspend inline fun <reified T> deleteRequest(urlString: String): Result<T> {
        return runCatching {
            val response = httpClient.delete(urlString) {
                headers {
                    append(NetworkConstants.Headers.CONTENT_TYPE, NetworkConstants.Values.CONTENT_TYPE)
                }
            }

            response.body<T>()
        }
    }
    //endregion
}
