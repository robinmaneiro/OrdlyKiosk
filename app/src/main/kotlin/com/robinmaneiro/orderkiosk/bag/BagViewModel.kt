package com.robinmaneiro.orderkiosk.bag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.model.UpdateBagItemPayload
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.UpdateBagItemUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BagViewModel(
    private val getBagUseCase: GetBagUseCase,
    private val updateBagItemUseCase: UpdateBagItemUseCase,
    private val removeFromBagUseCase: RemoveFromBagUseCase,
    private val removeAllBagItemsUseCase: RemoveAllBagItemsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        showLoader()
        viewModelScope.launch {
            getBagUseCase.invoke()
                .onSuccess { it.updateUiState() }
                .onFailure { hideLoader() }
        }
    }

    private fun showLoader() = _uiState.update { it.copy(isLoading = true) }

    private fun hideLoader() = _uiState.update { it.copy(isLoading = false) }

    private fun BagResponse.updateUiState() {
        _uiState.update {
            it.copy(
                bagItems = items,
                itemCount = itemCount,
                formattedTotalCost = formattedTotalCost,
                isLoading = false
            )
        }
    }

    fun increaseQuantity(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.inc()
            val updateBagItemPayload = UpdateBagItemPayload(
                bagItemId = bagItem.itemId,
                quantity = newQuantity
            )
            updateBagItemUseCase.invoke(updateBagItemPayload)
                .onSuccess { response ->
                    response.updateUiState()
                }
                .onFailure {
                    hideLoader()
                }
        }
    }

    fun decreaseQuantity(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.dec()
            val updateBagItemPayload = UpdateBagItemPayload(
                bagItemId = bagItem.itemId,
                quantity = newQuantity
            )
            updateBagItemUseCase.invoke(updateBagItemPayload)
                .onSuccess { response ->
                    response.updateUiState()
                }
                .onFailure {
                    hideLoader()
                }
        }
    }

    fun removeItem(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            removeFromBagUseCase.invoke(bagItem.itemId)
                .onSuccess { response ->
                    response.updateUiState()
                }
                .onFailure {
                    // TODO:  Handle error
                }
        }
    }

    fun removeAllItems() {
        showLoader()
        viewModelScope.launch {
            removeAllBagItemsUseCase.invoke()
                .onSuccess { bagResponse ->
                    bagResponse.updateUiState()
                }
                .onFailure {
                    // TODO: Handle error
                }
        }
    }

    data class UiState(
        val bagItems: List<BagItem> = emptyList(),
        val itemCount: Int = 0,
        val formattedTotalCost: String = "",
        val isLoading: Boolean = false
    )
}