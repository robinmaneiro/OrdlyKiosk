package com.robinmaneiro.orderkiosk.bag.repository

interface BagRepository {
    suspend fun fetchBagItems()

    suspend fun addToBag(productId: String)

    suspend fun removeFromBag(bagItemId: String)

    suspend fun updateBagItem(bagItemId: String)
}
