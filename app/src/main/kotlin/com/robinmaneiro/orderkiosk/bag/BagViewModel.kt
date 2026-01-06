package com.robinmaneiro.orderkiosk.bag

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.bag.model.BagResponse
import com.robinmaneiro.orderkiosk.bag.model.UpdateBagItemPayload
import com.robinmaneiro.orderkiosk.bag.usecase.BagSelectorUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.GetBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveAllBagItemsUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.RemoveFromBagUseCase
import com.robinmaneiro.orderkiosk.bag.usecase.UpdateBagItemUseCase

class BagViewModel(
    private val bagSelectorUseCase: BagSelectorUseCase,
    private val getBagUseCase: GetBagUseCase,
    private val updateBagItemUseCase: UpdateBagItemUseCase,
    private val removeFromBagUseCase: RemoveFromBagUseCase,
    private val removeAllBagItemsUseCase: RemoveAllBagItemsUseCase
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<Actions>(Channel.BUFFERED)
    val actions = _actions.receiveAsFlow()

    init {
        showLoader()
        viewModelScope.launch {
            getBagUseCase.invoke(bagId = bagSelectorUseCase.invoke())
                .onSuccess { response ->
                    response.updateUiState()
                }
                .onFailure {
                    hideLoader()
                }
        }
    }

    fun onHandleEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            UiEvent.RemoveAllItems -> removeAllItems()
            is UiEvent.IncreaseQuantity -> increaseQuantity(uiEvent.bagItem)
            is UiEvent.DecreaseQuantity -> decreaseQuantity(uiEvent.bagItem)
            is UiEvent.RemoveItem -> removeItem(uiEvent.bagItem)
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

    private fun increaseQuantity(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.inc()
            val updateBagItemPayload = UpdateBagItemPayload(
                bagItemId = bagItem.itemId,
                quantity = newQuantity
            )
            updateBagItemUseCase.invoke(
                bagId = bagSelectorUseCase.invoke(),
                updateBagItemPayload = updateBagItemPayload
            )
                .onSuccess { response ->
                    response.updateUiState()
                }
                .onFailure {
                    hideLoader()
                }
        }
    }

    private fun decreaseQuantity(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            val newQuantity = bagItem.quantity.dec()
            val updateBagItemPayload = UpdateBagItemPayload(
                bagItemId = bagItem.itemId,
                quantity = newQuantity
            )
            updateBagItemUseCase.invoke(
                bagId = bagSelectorUseCase.invoke(),
                updateBagItemPayload = updateBagItemPayload
            )
                .onSuccess { response ->
                    response.updateUiState()
                }
                .onFailure {
                    hideLoader()
                }
        }
    }

    private fun removeItem(bagItem: BagItem) {
        showLoader()
        viewModelScope.launch {
            removeFromBagUseCase.invoke(
                bagId = bagSelectorUseCase.invoke(),
                bagItemId = bagItem.itemId
            )
                .onSuccess { response ->
                    response.updateUiState()
                }
                .onFailure {
                    hideLoader()
                    // TODO:  Handle error
                }
        }
    }

    private fun removeAllItems() {
        showLoader()
        viewModelScope.launch {
            removeAllBagItemsUseCase.invoke(
                bagId = bagSelectorUseCase.invoke()
            )
                .onSuccess { bagResponse ->
                    hideLoader()
                    _actions.trySend(Actions.NavigateBack)
                }
                .onFailure {
                    hideLoader()
                    // TODO: Handle error
                }
        }
    }

    sealed interface UiEvent {
        data object RemoveAllItems : UiEvent
        data class RemoveItem(val bagItem: BagItem) : UiEvent
        data class IncreaseQuantity(val bagItem: BagItem) : UiEvent
        data class DecreaseQuantity(val bagItem: BagItem) : UiEvent
    }

    sealed interface Actions {
        data object NavigateBack : Actions
    }

    data class UiState(
        val bagItems: ImmutableList<BagItem> = persistentListOf(),
        val itemCount: Int = 0,
        val formattedTotalCost: String = "",
        val isLoading: Boolean = false
    )
}
