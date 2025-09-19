package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import kotlinx.coroutines.flow.StateFlow

interface BagRepository {
    val bag: StateFlow<BagResponse?>
    suspend fun getBagItems(): BagResponse?

    suspend fun addToBag(productId: String, quantity: Int): BagResponse?

    suspend fun removeFromBag(bagItemId: String): BagResponse?

    suspend fun updateBagItem(bagItemId: String, newQuantity: Int): BagResponse?
}
