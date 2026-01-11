package com.robinmaneiro.orderkiosk.welcome

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.welcome.model.LanguageData
import kotlin.collections.listOf

class WelcomeViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState())
    val uiState = _uiState.asStateFlow()

    private val _actions = Channel<Actions>(Channel.CONFLATED)
    val actions = _actions.receiveAsFlow()

    init {
        // TODO: Change this to retrieve all languages and set selected the default one.
        _uiState.update {
            it.copy(
                languageOptions = listOf(
                    LanguageData(
                        R.drawable.flag_gb,
                        "en",
                        "English",
                        true
                    ),
                    LanguageData(
                        R.drawable.flag_es,
                        "es",
                        "Spanish",
                        false
                    ),
                    LanguageData(
                        R.drawable.flag_de,
                        "de",
                        "German",
                        false
                    ),
                    LanguageData(
                        R.drawable.flag_fr,
                        "fr",
                        "French",
                        false
                    )
                )
            )
        }
    }

    fun onHandleEvent(uiEvent: UiEvent) {
        when (uiEvent) {
            is UiEvent.OnLanguageSelected -> updateLanguage(uiEvent.languageCode)
        }
    }

    private fun updateLanguage(languageCode: String) {
        // TODO: Probably can change a variable in the datastore and listen to a flow that will try to retrieve an updated json for this
        //  specific language when that happens.
        _uiState.update {
            it.copy(
                languageOptions = uiState.value.languageOptions.map { languageData -> languageData.copy(isSelected = languageData.languageAlpha2Code == languageCode) }
            )
        }
    }

    sealed interface UiEvent {
        data class OnLanguageSelected(val languageCode: String) : UiEvent
    }

    data class UiState(
        val isLoading: Boolean = false,
        val languageOptions: List<LanguageData> = emptyList()
    )

    sealed interface Actions {
        data object ShowErrorDialog : Actions
        data class ShowToastMessage(val message: String) : Actions
    }
}
