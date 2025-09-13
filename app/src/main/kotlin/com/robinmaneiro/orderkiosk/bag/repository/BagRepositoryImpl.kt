package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.BagItemResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BagRepositoryImpl : BagRepository {
    private val _bag: MutableStateFlow<BagItemResponse?> = MutableStateFlow(null)
    override val bag: StateFlow<BagItemResponse?> = _bag.asStateFlow()

    override suspend fun addToBag(productId: String, quantity: Int): BagItemResponse? {
        val response = NetworkManager.postRequest<BagItemResponse>("http://192.168.1.162:8080/basket", "{ \"productId\": \"$productId\",\"quantity\": $quantity}")
        _bag.update { response }
        return response
    }

    override suspend fun getBagItems(): BagItemResponse? {
        val response = NetworkManager.getRequest<BagItemResponse>("http://192.168.1.162:8080/basket")
        _bag.update { response }
        return response
    }

    override suspend fun removeFromBag(bagItemId: String): BagItemResponse? {
        val response = NetworkManager.deleteRequest<BagItemResponse>("http://192.168.1.162:8080/basket/$bagItemId")
        _bag.update { response }
        return response
    }

    override suspend fun updateBagItem(bagItemId: String, newQuantity: Int): BagItemResponse? {
        val response = NetworkManager.patchRequest<BagItemResponse>("http://192.168.1.162:8080/basket/$bagItemId", "{ \"productId\": \"$bagItemId\",\"quantity\": $newQuantity}")
        _bag.update { response }
        return response
    }
}
