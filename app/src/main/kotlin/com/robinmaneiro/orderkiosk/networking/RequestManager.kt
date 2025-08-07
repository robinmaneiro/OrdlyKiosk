package com.robinmaneiro.orderkiosk.networking

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.module.kotlin.jsonMapper
import com.robinmaneiro.orderkiosk.menu.MenuItems
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLBuilder
import io.ktor.http.append
import io.ktor.http.headers
import io.ktor.serialization.jackson.jackson
import org.slf4j.event.LoggingEvent

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

    suspend inline fun <reified T> getRequest(): T? {
        val response = httpClient.get("http://192.168.1.162:8080/menu_items") {
            headers {
                append(NetworkConstants.Headers.CONTENT_TYPE, NetworkConstants.Values.CONTENT_TYPE )
            }
        }

        return response.body<T>()
    }
}
