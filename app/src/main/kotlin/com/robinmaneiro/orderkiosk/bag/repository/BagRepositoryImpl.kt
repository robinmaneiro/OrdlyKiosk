package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.BagItemResponse
import com.robinmaneiro.orderkiosk.networking.NetworkManager

class BagRepositoryImpl : BagRepository {
    override suspend fun addToBag(productId: String): BagItemResponse? {
        return NetworkManager.postRequest<BagItemResponse>("http://192.168.1.162:8080/basket/add", "{ \"productId\": \"$productId\",\"quantity\": 1}")
    }

    override suspend fun getBagItems(): BagItemResponse? {
        return NetworkManager.getRequest<BagItemResponse>("http://192.168.1.162:8080/basket/get")
    }

    override suspend fun removeFromBag(bagItemId: String): BagItemResponse? {
        return NetworkManager.deleteRequest<BagItemResponse>("http://192.168.1.162:8080/basket/delete/$bagItemId")
    }

    override suspend fun updateBagItem(bagItemId: String): BagItemResponse? {
        return NetworkManager.patchRequest<BagItemResponse>("")
    }
}
