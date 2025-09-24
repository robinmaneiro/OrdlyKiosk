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

    override suspend fun addToBag(productId: String, quantity: Int): BagResponse? {
        val response = NetworkManager.postRequest<BagResponse>("http://192.168.1.162:8080/basket", "{ \"productId\": \"$productId\",\"quantity\": $quantity}")
        _bag.update { response }
        return response
    }

    override suspend fun getBagItems(): BagResponse? {
        val response = NetworkManager.getRequest<BagResponse>("http://192.168.1.162:8080/basket")
        _bag.update { response }
        return response
    }

    override suspend fun updateBagItem(bagItemId: String, newQuantity: Int): BagResponse? {
        val response = NetworkManager.patchRequest<BagResponse>("http://192.168.1.162:8080/basket/$bagItemId", "{ \"productId\": \"$bagItemId\",\"quantity\": $newQuantity}")
        _bag.update { response }
        return response
    }

    override suspend fun removeFromBag(bagItemId: String): BagResponse? {
        val response = NetworkManager.deleteRequest<BagResponse>("http://192.168.1.162:8080/basket/$bagItemId")
        _bag.update { response }
        return response
    }

    override suspend fun removeAllBagItems(): BagResponse? {
        val response = NetworkManager.deleteRequest<BagResponse>("http://192.168.1.162:8080/basket/all")
        _bag.update { response }
        return response
    }
}
