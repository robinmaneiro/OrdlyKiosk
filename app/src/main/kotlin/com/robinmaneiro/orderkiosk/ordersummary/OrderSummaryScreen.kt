package com.robinmaneiro.orderkiosk.ordersummary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.robinmaneiro.orderkiosk.MainUiEvent
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

@Composable
fun OrderSummaryScreen(
    mainUiEvent: (MainUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Order Summary", onBack = {
                mainUiEvent.invoke(MainUiEvent.NavigateUp)
            })
        }
    ) { padding ->
        OrderSummaryContent(padding)
    }
}

@Composable
private fun OrderSummaryContent(padding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "This is ORDER SUMMARY SCREEN!!!!"
        )
    }
}
