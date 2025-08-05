package com.robinmaneiro.orderkiosk.networking

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

object RequestManager {
    val httpClient = HttpClient(CIO)

    
}