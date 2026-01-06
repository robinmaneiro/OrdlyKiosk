package uk.co.softlantic.orderkiosk.bag.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import uk.co.softlantic.orderkiosk.bag.model.BagResponse
import uk.co.softlantic.orderkiosk.networking.NetworkManager
import uk.co.softlantic.orderkiosk.util.SERVER_BASE_URL

class BagRepositoryImpl : BagRepository {
    private val _bag: MutableStateFlow<BagResponse?> = MutableStateFlow(null)
    override val bag: StateFlow<BagResponse?> = _bag.asStateFlow()

    override suspend fun addToBag(bagId: String, payload: String): Result<BagResponse> {
        val response = NetworkManager.postRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId", stringBody = payload)
        response.onSuccess { response -> _bag.update { response } }
        return response
    }
    override suspend fun getBagItems(bagId: String): Result<BagResponse> {
        val response = NetworkManager.getRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun updateBagItem(bagId: String, payload: String, bagItemId: String): Result<BagResponse> {
        val response = NetworkManager.patchRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId/$bagItemId", stringBody = payload)
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun removeFromBag(bagId: String, bagItemId: String): Result<BagResponse> {
        val response = NetworkManager.deleteRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId/$bagItemId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun mergeBags(sourceBagId: String, targetBagId: String): Result<BagResponse> {
        val response = NetworkManager.getRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$sourceBagId/merge/$targetBagId")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }

    override suspend fun removeAllBagItems(bagId: String): Result<BagResponse> {
        val response = NetworkManager.deleteRequest<BagResponse>("$SERVER_BASE_URL/api/v1/basket/$bagId/all")
        response.onSuccess { response -> _bag.update { response } }
        return response
    }
}
