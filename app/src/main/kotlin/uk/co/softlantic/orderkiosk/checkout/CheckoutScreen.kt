package uk.co.softlantic.orderkiosk.checkout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import uk.co.softlantic.orderkiosk.NavigationEvent
import uk.co.softlantic.orderkiosk.Screens
import uk.co.softlantic.orderkiosk.ui.PreviewPixelTablet
import uk.co.softlantic.orderkiosk.ui.SimpleTopBar

@Composable
fun CheckoutScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Checkout", onBack = {
                mainUiEvent.invoke(NavigationEvent.NavigateUp)
            })
        }
    ) { padding ->
        CheckoutContent(padding, mainUiEvent)
    }
}

@Composable
private fun CheckoutContent(
    padding: PaddingValues,
    mainUiEvent: (NavigationEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
            mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.OrderSummaryScreen.route))
        }) {
            Text(
                text = "Pay Now"
            )
        }
    }
}

@PreviewPixelTablet
@Composable
fun CheckoutContentPreview() {
    CheckoutContent(PaddingValues()) {}
}
