package uk.co.softlantic.orderkiosk.offers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class OffersViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()
    private val clickHereList = listOf(
        "TOUCH TO START",
        "TOCA PARA EMPEZAR",
        "BERÜHREN SIE ZUM STARTEN",
        "APPUYEZ POUR DÉMARRER"
    )

    init {
        startTextRotation()
    }

    private fun startTextRotation() {
        viewModelScope.launch {
            var index = 0
            while (true) {
                index = index.inc() % clickHereList.size // Loop back to start
                _uiState.update { it.copy(clickHereList[index]) }
                delay(2000L) // Wait for 2 seconds
            }
        }
    }
    data class UiState(
        val clickHereText: String = ""
    )
}
