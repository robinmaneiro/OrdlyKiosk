package com.robinmaneiro.orderkiosk.account.personaldetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

@Composable
fun MyAccountScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<MyAccountViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "My Account", onBack = {
                mainUiEvent.invoke(NavigationEvent.NavigateUp)
            })
        }
    ) {
        MyAccountScreenContent(
            it,
            uiState.firstName,
            uiState.lastName,
            uiState.dateOfBirth,
            uiState.phoneNumber
        )
    }
}

@Composable
private fun MyAccountScreenContent(
    paddingValues: PaddingValues,
    firstName: String,
    lastName: String,
    dateOfBirth: String,
    phoneNumber: String
) {
    Column(
        modifier = Modifier.padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(firstName)
        Text(lastName)
        Text(dateOfBirth)
        Text(phoneNumber)
    }
}

@Composable
@PreviewPixelTablet
private fun MyAccountScreenContentPreview() {
    MyAccountScreenContent(
        PaddingValues(0.dp),
        "Robin",
        "Maneiro",
        "14/07/90",
        "07395246451"
    )
}
