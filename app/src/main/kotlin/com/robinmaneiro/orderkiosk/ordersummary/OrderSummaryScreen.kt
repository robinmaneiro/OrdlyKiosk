package com.robinmaneiro.orderkiosk.ordersummary

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.robinmaneiro.orderkiosk.MainUiEvent
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
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
        SectionOrderDetails()
        SectionItems()
        SectionOrderProgress()
    }
}

@Composable
private fun SectionOrderDetails(
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            "Order Number:"
        )
        Text(
            "12345"
        )
    }
}

@Composable
private fun SectionItems(
    modifier: Modifier = Modifier
) {
    LazyColumn {
        // Just get them from the bag flow
    }
}

@Composable
private fun SectionOrderProgress(
    modifier: Modifier = Modifier
) {
    // Display here services
    // e.g. WhatsApp / SMS / Telegram? / WeChat?
    // improve UX editing sorting of options based on language selections

    Text(
        text = "Get Progress of your order "
    )

    Row {

    }
}

@Composable
private fun SectionRoulette() {
    // Direct to spin the roulette

    // TODO: Move me to new screens - to add some sharing in socials if win
}

@PreviewPixelTablet
@Composable
private fun OrderSummaryContentPreview() {
    OrderSummaryContent(PaddingValues())
}