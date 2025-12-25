package com.robinmaneiro.orderkiosk.checkout

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
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

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