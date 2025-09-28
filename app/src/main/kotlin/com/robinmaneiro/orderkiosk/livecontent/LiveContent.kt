package com.robinmaneiro.orderkiosk.livecontent

import com.robinmaneiro.orderkiosk.networking.NetworkManager

object LiveContent {
    suspend fun getString(key: String, default: String): String? {
        return NetworkManager.getRequest<String>("")
    }
}
