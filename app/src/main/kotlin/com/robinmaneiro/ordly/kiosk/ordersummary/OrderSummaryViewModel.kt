package com.robinmaneiro.ordly.kiosk.ordersummary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.robinmaneiro.ordly.kiosk.bag.model.BagResponse
import com.robinmaneiro.ordly.kiosk.bag.repository.BagRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

class OrderSummaryViewModel(
    bagRepository: BagRepository
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = combine(_uiState, bagRepository.bag) { state, bag ->
        state.copy(
            bagResponse = bag
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(),
        initialValue = UiState()
    )

    data class UiState(
        val bagResponse: BagResponse? = null,
    )
}
