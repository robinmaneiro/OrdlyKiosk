package com.robinmaneiro.orderkiosk.networking

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.http.headers
import io.ktor.serialization.jackson.jackson

object RequestManager {
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

    suspend inline fun <reified T> getRequest(urlString: String): T? {
        val response = httpClient.get(urlString) {
            headers {
                append(NetworkConstants.Headers.CONTENT_TYPE, NetworkConstants.Values.CONTENT_TYPE )
            }
        }

        return response.body<T>()
    }
}
