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

    override suspend fun addToBag(productId: String, quantity: Int): Result<BagResponse> {
        val response = NetworkManager.postRequest<BagResponse>("http://192.168.1.162:8080/basket", "{ \"productId\": \"$productId\",\"quantity\": $quantity}")
        response.onSuccess { _bag::update }
        return response
    }

    override suspend fun getBagItems(): Result<BagResponse> {
        val response = NetworkManager.getRequest<BagResponse>("http://192.168.1.162:8080/basket")
        response.onSuccess { _bag::update }
        return response
    }

    override suspend fun updateBagItem(bagItemId: String, newQuantity: Int): Result<BagResponse> {
        val response = NetworkManager.patchRequest<BagResponse>("http://192.168.1.162:8080/basket/$bagItemId", "{ \"productId\": \"$bagItemId\",\"quantity\": $newQuantity}")
        response.onSuccess { _bag::update }
        return response
    }

    override suspend fun removeFromBag(bagItemId: String): Result<BagResponse> {
        val response = NetworkManager.deleteRequest<BagResponse>("http://192.168.1.162:8080/basket/$bagItemId")
        response.onSuccess { _bag::update }
        return response
    }

    override suspend fun removeAllBagItems(): Result<BagResponse> {
        val response = NetworkManager.deleteRequest<BagResponse>("http://192.168.1.162:8080/basket/all")
        response.onSuccess { _bag::update }
        return response
    }
}
