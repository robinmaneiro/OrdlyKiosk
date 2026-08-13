package com.robinmaneiro.ordly.kiosk.account.personaldetails

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.robinmaneiro.ordly.kiosk.NavigationEvent
import com.robinmaneiro.ordly.kiosk.R
import com.robinmaneiro.ordly.kiosk.ui.ErrorDialog
import com.robinmaneiro.ordly.kiosk.ui.PreviewPixelTablet
import com.robinmaneiro.ordly.kiosk.ui.SimpleTopBar
import org.koin.androidx.compose.koinViewModel

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
            SimpleTopBar(title = stringResource(R.string.screen_title_my_account), onBack = {
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

        uiState.errorMessage?.let { bodyRes ->
            ErrorDialog(bodyRes = bodyRes) { mainUiEvent.invoke(NavigationEvent.NavigateUp) }
        }
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
