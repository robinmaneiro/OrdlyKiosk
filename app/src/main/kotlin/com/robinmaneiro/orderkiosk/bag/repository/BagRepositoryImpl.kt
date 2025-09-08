package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.networking.NetworkManager

class BagRepositoryImpl: BagRepository {
    override suspend fun addToBag(productId: String) {
        NetworkManager.postRequest<Unit>("http://192.168.1.162:8080/bag/add", """
            {
                "id": "$productId",
                "quantity": 1
            }
        """.trimIndent())
    }

    override suspend fun fetchBagItems() {
        TODO("Not yet implemented")
    }

    override suspend fun removeFromBag(bagItemId: String) {
        TODO("Not yet implemented")
    }

    override suspend fun updateBagItem(bagItemId: String) {
        TODO("Not yet implemented")
    }
}