package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import com.robinmaneiro.orderkiosk.util.SERVER_BASE_URL
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BagRepositoryImpl(private val networkManager: NetworkManager) : BagRepository {
    private val _bag: MutableStateFlow<BagResponse?> = MutableStateFlow(null)
    override val bag: StateFlow<BagResponse?> = _bag.asStateFlow()

    override suspend fun addToBag(bagId: String, payload: String): Result<BagResponse> {
        val response = networkManager.postRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId", stringBody = payload)
        response.onSuccess { response -> _bag.update { response } }
        return response
    }
    override suspend fun getBagItems(bagId: String): Result<BagResponse> {
        val response = networkManager.getRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun updateBagItem(bagId: String, payload: String, bagItemId: String): Result<BagResponse> {
        val response = networkManager.patchRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId/$bagItemId", stringBody = payload)
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun removeFromBag(bagId: String, bagItemId: String): Result<BagResponse> {
        val response = networkManager.deleteRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId/$bagItemId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun mergeBags(sourceBagId: String, targetBagId: String): Result<BagResponse> {
        val response = networkManager.getRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$sourceBagId/merge/$targetBagId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun removeAllBagItems(bagId: String): Result<BagResponse> {
        val response = networkManager.deleteRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId/all")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }
}
