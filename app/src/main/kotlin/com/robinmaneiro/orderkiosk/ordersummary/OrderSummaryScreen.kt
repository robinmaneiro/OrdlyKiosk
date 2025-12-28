package com.robinmaneiro.orderkiosk.ordersummary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

@Composable
fun OrderSummaryScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = "Order Summary", onBack = {
                mainUiEvent.invoke(NavigationEvent.NavigateUp)
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
        Spacer(Modifier.height(10.dp))
        SectionItems()
        Spacer(Modifier.height(10.dp))
        SectionOrderProgress()
        Spacer(Modifier.height(10.dp))
        SectionRoulette()
    }
}

@Composable
private fun SectionOrderDetails(
    modifier: Modifier = Modifier
) {
    Row {
        Text(
            "YOUR ORDER:",
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp)
        )
        Text(
            "80801",
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 48.sp)
        )
    }
}

@Composable
private fun SectionItems(
    modifier: Modifier = Modifier
) {
    val xd = listOf(
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",
        "Example of a product",

    )
    LazyColumn(
        Modifier.height(200.dp)
    ) {
        // Just get them from the bag flow
        items(xd) {
            Text(it)
        }
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

    Box(
        modifier = Modifier
            .size(200.dp)
            .background(Color.Blue)
    )
}

@Composable
private fun SectionRoulette() {
    // Direct to spin the roulette
    Text(
        style = MaterialTheme.typography.bodyMedium,
        text = "Spin the roulette for a chance to get a free meal!"
    )

    Box(
        modifier = Modifier
            .size(50.dp)
            .background(shape = CircleShape, color = Color.Red)
    )

    // TODO: Move me to new screens - to add some sharing in socials if win
}

@PreviewPixelTablet
@Composable
private fun OrderSummaryContentPreview() {
    OrderSummaryContent(PaddingValues())
}
