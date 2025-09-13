package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.BagItemResponse
import kotlinx.coroutines.flow.StateFlow

interface BagRepository {
    val bag: StateFlow<BagItemResponse?>
    suspend fun getBagItems(): BagItemResponse?

    suspend fun addToBag(productId: String, quantity: Int): BagItemResponse?

    suspend fun removeFromBag(bagItemId: String): BagItemResponse?

    suspend fun updateBagItem(bagItemId: String, newQuantity: Int): BagItemResponse?
}
