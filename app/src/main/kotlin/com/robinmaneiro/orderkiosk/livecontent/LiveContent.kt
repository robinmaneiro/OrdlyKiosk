package com.robinmaneiro.orderkiosk.livecontent

import com.robinmaneiro.orderkiosk.networking.NetworkManager

object LiveContent {
    suspend fun getString(key: String, default: String): String? {
        return NetworkManager.getRequest<String>("")
    }

    suspend fun getInteger(key: String, default: Int): Int? {
        return NetworkManager.getRequest<Int>("")
    }

    suspend fun getFloat(key: String, default: Float): Float? {
        return NetworkManager.getRequest<Float>("")
    }

    suspend fun getLong(key: String, default: Long): Long? {
        return NetworkManager.getRequest<Long>("")
    }

    suspend inline fun <reified T> getCustomObject(key: String, clazz: Class<T>): T? {
        return NetworkManager.getRequest<T>("")
    }
}
