package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.BagItemResponse

interface BagRepository {
    suspend fun getBagItems(): BagItemResponse?

    suspend fun addToBag(productId: String): BagItemResponse?

    suspend fun removeFromBag(bagItemId: String): BagItemResponse?

    suspend fun updateBagItem(bagItemId: String): BagItemResponse?
}
