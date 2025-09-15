package com.robinmaneiro.orderkiosk.bag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.UpdateBagItemUseCase
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BagViewModel(
    val getBagUseCase: GetBagUseCase,
    val updateBagItemUseCase: UpdateBagItemUseCase,
    val removeFromBagUseCase: RemoveFromBagUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        showLoader()
        viewModelScope.launch {
            val bagItems = getBagUseCase.invoke() ?: run { hideLoader(); return@launch }

            delay(3000L)

            _uiState.update {
                it.copy(
                    bagItems = bagItems,
                    isLoading = false
                )
            }
        }
    }

    fun increaseQuantity(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.inc()

            val updatedBagResponse = updateBagItemUseCase.invoke(bagItem.productId, newQuantity) ?: run { hideLoader(); return@launch }
            delay(2000L)

            _uiState.update {
                it.copy(
                    bagItems = updatedBagResponse,
                    isLoading = false
                )
            }
        }
    }

    fun decreaseQuantity(bagItem: BagItem) {
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.dec()

            val updatedBagResponse = if (newQuantity > 0) {
                updateBagItemUseCase.invoke(bagItem.productId, newQuantity)
            } else {
                removeFromBagUseCase.invoke(bagItem.productId)
            } ?: return@launch // TODO: Return for now, show loaders then

            _uiState.update {
                it.copy(bagItems = updatedBagResponse)
            }
        }
    }

    fun showLoader() = _uiState.update { it.copy(isLoading = true) }

    fun hideLoader() = _uiState.update { it.copy(isLoading = false) }

    data class UiState(
        val bagItems: List<BagItem> = emptyList(),
        val isLoading: Boolean = false
    )
}