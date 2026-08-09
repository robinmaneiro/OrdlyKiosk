package com.robinmaneiro.orderkiosk.livecontent

import com.robinmaneiro.orderkiosk.networking.NetworkManager

class LiveContent(@PublishedApi internal val networkManager: NetworkManager) {
    suspend fun getString(key: String, default: String): Result<String> {
        return networkManager.getRequest<String>("")
    }

    suspend fun getInteger(key: String, default: Int): Result<Int> {
        return networkManager.getRequest<Int>("")
    }

    suspend fun getFloat(key: String, default: Float): Result<Float> {
        return networkManager.getRequest<Float>("")
    }

    suspend fun getLong(key: String, default: Long): Result<Long> {
        return networkManager.getRequest<Long>("")
    }

    suspend inline fun <reified T> getCustomObject(key: String, clazz: Class<T>): Result<T> {
        return networkManager.getRequest<T>("")
    }
}
