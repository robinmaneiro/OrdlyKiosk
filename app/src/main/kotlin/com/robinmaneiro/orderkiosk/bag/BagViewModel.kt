package com.robinmaneiro.orderkiosk.bag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.bag.model.BagItem
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
            val response = getBagUseCase.invoke() ?: run { hideLoader(); return@launch }

            _uiState.update {
                it.copy(
                    bagItems = response.items,
                    itemCount = response.itemCount,
                    formattedTotalCost = response.formattedTotalCost,
                    isLoading = false
                )
            }
        }
    }

    private fun showLoader() = _uiState.update { it.copy(isLoading = true) }

    private fun hideLoader() = _uiState.update { it.copy(isLoading = false) }

    fun increaseQuantity(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.inc()
            val response = updateBagItemUseCase.invoke(bagItem.itemId, newQuantity) ?: run { hideLoader(); return@launch }

            _uiState.update {
                it.copy(
                    bagItems = response.items,
                    isLoading = false
                )
            }
        }
    }

    fun decreaseQuantity(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.dec()
            val response = updateBagItemUseCase.invoke(bagItem.itemId, newQuantity) ?: return@launch // TODO: Return for now, show loaders then

            _uiState.update {
                it.copy(
                    bagItems = response.items,
                    isLoading = false
                )
            }
        }
    }

    fun removeItem(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val response = removeFromBagUseCase.invoke(bagItem.itemId) ?: return@launch // TODO: Return for now, show loaders then

            _uiState.update {
                it.copy(
                    bagItems = response.items,
                    isLoading = false
                )
            }
        }
    }

    fun removeAllItems() {
        showLoader()
        viewModelScope.launch {
            val response = removeAllBagItemsUseCase.invoke() ?: return@launch

            _uiState.update {
                it.copy(
                    bagItems = response.items,
                    isLoading = false
                )
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