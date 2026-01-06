package uk.co.softlantic.orderkiosk

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import uk.co.softlantic.orderkiosk.networking.ConnectivityObserver
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class BaseViewModel(private val observer: ConnectivityObserver) : ViewModel() {
    val connectivityStatus = observer.observe()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ConnectivityObserver.Status.Available)
}
