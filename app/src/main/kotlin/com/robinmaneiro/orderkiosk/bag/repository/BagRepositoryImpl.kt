package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BagRepositoryImpl : BagRepository {
    private val _bag: MutableStateFlow<BagResponse?> = MutableStateFlow(null)
    override val bag: StateFlow<BagResponse?> = _bag.asStateFlow()

    override suspend fun addToBag(bagId: String, payload: String): Result<BagResponse> {
        val response = NetworkManager.postRequest<BagResponse>("http://192.168.1.162:8080/api/v1/basket", stringBody = payload)
        response.onSuccess { response -> _bag.update { response } }
        return response
    }
    override suspend fun getBagItems(bagId: String): Result<BagResponse> {
        val response = NetworkManager.getRequest<BagResponse>("http://192.168.1.162:8080/api/v1/basket/$bagId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun updateBagItem(bagId: String, payload: String, bagItemId: String): Result<BagResponse> {
        val response = NetworkManager.patchRequest<BagResponse>("http://192.168.1.162:8080/api/v1/basket/${bagId}/$bagItemId", stringBody = payload)
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun removeFromBag(bagId: String, bagItemId: String): Result<BagResponse> {
        val response = NetworkManager.deleteRequest<BagResponse>("http://192.168.1.162:8080/api/v1/basket/${bagId}/$bagItemId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun removeAllBagItems(bagId: String): Result<BagResponse> {
        val response = NetworkManager.deleteRequest<BagResponse>("http://192.168.1.162:8080/api/v1/basket/${bagId}/all")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }
}
