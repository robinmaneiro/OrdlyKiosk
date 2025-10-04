package com.robinmaneiro.orderkiosk.livecontent

import com.robinmaneiro.orderkiosk.networking.NetworkManager

object LiveContent {
    suspend fun getString(key: String, default: String): Result<String> {
        return NetworkManager.getRequest<String>("")
    }

    suspend fun getInteger(key: String, default: Int): Result<Int> {
        return NetworkManager.getRequest<Int>("")
    }

    suspend fun getFloat(key: String, default: Float): Result<Float> {
        return NetworkManager.getRequest<Float>("")
    }

    suspend fun getLong(key: String, default: Long): Result<Long> {
        return NetworkManager.getRequest<Long>("")
    }

    suspend inline fun <reified T> getCustomObject(key: String, clazz: Class<T>): Result<T> {
        return NetworkManager.getRequest<T>("")
    }
}
