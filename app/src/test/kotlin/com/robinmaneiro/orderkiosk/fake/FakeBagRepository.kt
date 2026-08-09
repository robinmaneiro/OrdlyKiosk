package com.robinmaneiro.orderkiosk.fake

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.repository.BagRepository

class FakeBagRepository : BagRepository {
    private val _bag = MutableStateFlow<BagResponse?>(null)
    override val bag: StateFlow<BagResponse?> = _bag

    var getBagItemsResult: Result<BagResponse> = Result.failure(IllegalStateException("Not configured"))
    var addToBagResult: Result<BagResponse> = Result.failure(IllegalStateException("Not configured"))
    var updateBagItemResult: Result<BagResponse> = Result.failure(IllegalStateException("Not configured"))
    var removeFromBagResult: Result<BagResponse> = Result.failure(IllegalStateException("Not configured"))
    var mergeBagsResult: Result<BagResponse> = Result.failure(IllegalStateException("Not configured"))
    var removeAllBagItemsResult: Result<BagResponse> = Result.failure(IllegalStateException("Not configured"))

    override suspend fun getBagItems(bagId: String): Result<BagResponse> {
        return getBagItemsResult.also { result ->
            result.getOrNull()?.let { _bag.value = it }
        }
    }

    override suspend fun addToBag(bagId: String, payload: String): Result<BagResponse> {
        return addToBagResult.also { result ->
            result.getOrNull()?.let { _bag.value = it }
        }
    }

    override suspend fun updateBagItem(bagId: String, payload: String, bagItemId: String): Result<BagResponse> {
        return updateBagItemResult.also { result ->
            result.getOrNull()?.let { _bag.value = it }
        }
    }

    override suspend fun removeFromBag(bagId: String, bagItemId: String): Result<BagResponse> {
        return removeFromBagResult.also { result ->
            result.getOrNull()?.let { _bag.value = it }
        }
    }

    override suspend fun mergeBags(sourceBagId: String, targetBagId: String): Result<BagResponse> {
        return mergeBagsResult.also { result ->
            result.getOrNull()?.let { _bag.value = it }
        }
    }

    override suspend fun removeAllBagItems(bagId: String): Result<BagResponse> {
        return removeAllBagItemsResult.also { result ->
            result.getOrNull()?.let { _bag.value = it }
        }
    }

    fun setBag(bagResponse: BagResponse?) {
        _bag.value = bagResponse
    }
}
