package uk.co.softlantic.orderkiosk.bag.repository

import kotlinx.coroutines.flow.StateFlow
import uk.co.softlantic.orderkiosk.bag.model.BagResponse

interface BagRepository {
    val bag: StateFlow<BagResponse?>
    suspend fun getBagItems(bagId: String): Result<BagResponse>

    suspend fun addToBag(bagId: String, payload: String): Result<BagResponse>

    suspend fun updateBagItem(bagId: String, payload: String, bagItemId: String): Result<BagResponse>

    suspend fun removeFromBag(bagId: String, bagItemId: String): Result<BagResponse>

    suspend fun mergeBags(sourceBagId: String, targetBagId: String): Result<BagResponse>

    suspend fun removeAllBagItems(bagId: String): Result<BagResponse>
}
