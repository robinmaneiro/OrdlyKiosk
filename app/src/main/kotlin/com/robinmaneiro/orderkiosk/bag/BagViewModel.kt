package com.robinmaneiro.orderkiosk.bag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class BagViewModel(
    val getBagUseCase: GetBagUseCase,
    val removeFromBagUseCase: RemoveFromBagUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val bagItems = getBagUseCase.invoke() ?: return@launch

            _uiState.update {
                it.copy(
                    bagItems = bagItems
                )
            }
        }
    }

    fun deleteBagItem(bagItemId: String) {
        viewModelScope.launch {
            val updatedBagItems = removeFromBagUseCase.invoke(bagItemId) ?: return@launch
            _uiState.update {
                it.copy(bagItems = updatedBagItems)
            }
        }
    }

    data class UiState(
        val bagItems: List<BagItem> = emptyList()
    )
}