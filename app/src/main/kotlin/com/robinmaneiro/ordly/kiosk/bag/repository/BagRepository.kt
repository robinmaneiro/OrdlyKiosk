package com.robinmaneiro.ordly.kiosk.bag.repository

import com.robinmaneiro.ordly.kiosk.bag.model.BagResponse
import kotlinx.coroutines.flow.StateFlow

interface BagRepository {
    val bag: StateFlow<BagResponse?>
    suspend fun getBagItems(bagId: String): Result<BagResponse>

    suspend fun addToBag(bagId: String, payload: String): Result<BagResponse>

    suspend fun updateBagItem(bagId: String, payload: String, bagItemId: String): Result<BagResponse>

    suspend fun removeFromBag(bagId: String, bagItemId: String): Result<BagResponse>

    suspend fun mergeBags(sourceBagId: String, targetBagId: String): Result<BagResponse>

    suspend fun removeAllBagItems(bagId: String): Result<BagResponse>
}
