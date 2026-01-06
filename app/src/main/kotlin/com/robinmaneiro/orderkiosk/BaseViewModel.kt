package com.robinmaneiro.orderkiosk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import com.robinmaneiro.orderkiosk.networking.ConnectivityObserver

class BaseViewModel(private val observer: ConnectivityObserver) : ViewModel() {
    val connectivityStatus = observer.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConnectivityObserver.Status.Available)
}
