package com.robinmaneiro.orderkiosk.networking

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.patch
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headers
import io.ktor.serialization.jackson.jackson

object NetworkManager {
    private val successHttpCodes = listOf(
        HttpStatusCode.OK,
        HttpStatusCode.Created,
        HttpStatusCode.Accepted,
        HttpStatusCode.NoContent
    )

    val httpClient = HttpClient(CIO) {
        install(plugin = ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
    }

    private val okhttpEngine = OkHttp.create {
//        addInterceptor(chuckerInterceptor) TODO: Add interceptor
    }

    //region Network Requests
    suspend inline fun <reified T> getRequest(urlString: String): T? {
        val response = httpClient.get(urlString) {
            headers {
                append(NetworkConstants.Headers.CONTENT_TYPE, NetworkConstants.Values.CONTENT_TYPE)
            }
        }

        return response.body<T>()
    }

    suspend inline fun <reified T> postRequest(urlString: String, stringBody: String): T? {
        val response = httpClient.post(urlString) {
            contentType(ContentType.Application.Json)
            setBody(
                """
        $stringBody
        """.trimIndent()
            )
        }

        return response.body<T>()
    }

    suspend inline fun <reified T> patchRequest(urlString: String): T? {
        val response = httpClient.patch(urlString) {
            headers {
                append(NetworkConstants.Headers.CONTENT_TYPE, NetworkConstants.Values.CONTENT_TYPE)
            }
        }

        return response.body()
    }

    suspend inline fun <reified T> deleteRequest(urlString: String): T? {
        val response = httpClient.delete(urlString) {
            headers {
                append(NetworkConstants.Headers.CONTENT_TYPE, NetworkConstants.Values.CONTENT_TYPE)
            }
        }

        return response.body()
    }
    //endregion
}
