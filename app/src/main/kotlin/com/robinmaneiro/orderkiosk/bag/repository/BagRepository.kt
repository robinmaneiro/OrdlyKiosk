package com.robinmaneiro.orderkiosk.bag.repository

import com.robinmaneiro.orderkiosk.bag.model.AddToBagPayload
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import kotlinx.coroutines.flow.StateFlow

interface BagRepository {
    val bag: StateFlow<BagResponse?>
    suspend fun getBagItems(): Result<BagResponse>

    suspend fun addToBag(payload: String): Result<BagResponse>

    suspend fun updateBagItem(bagItemId: String, newQuantity: Int): Result<BagResponse>

    suspend fun removeFromBag(bagItemId: String): Result<BagResponse>

    suspend fun removeAllBagItems(): Result<BagResponse>
}
