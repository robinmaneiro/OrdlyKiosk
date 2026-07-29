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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import androidx.compose.ui.res.stringResource
import org.koin.androidx.compose.koinViewModel
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.QRCodeDisplay
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar

@Composable
fun OrderSummaryScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<OrderSummaryViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(OrderSummaryViewModel.UiState())

    Scaffold(
        modifier = modifier,
        topBar = {
            SimpleTopBar(title = stringResource(R.string.screen_title_order_summary), onBack = {
                mainUiEvent.invoke(NavigationEvent.NavigateUp)
            })
        }
    ) { padding ->
        val bagResponse = uiState.bagResponse ?: return@Scaffold

        OrderSummaryContent(
            padding = padding,
            products = bagResponse.items
        )
    }
}

@Composable
private fun OrderSummaryContent(
    padding: PaddingValues,
    products: ImmutableList<BagItem>
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SectionOrderDetails()
        Spacer(Modifier.height(10.dp))
//        SectionItems(
//            products = products TODO: Remove?
//        )
        Spacer(Modifier.height(10.dp))
        SectionOrderProgress()
        Spacer(Modifier.height(10.dp))
        SectionRoulette()
    }
}

@Composable
private fun SectionOrderDetails(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.order_summary_thank_you),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 48.sp)

        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = stringResource(R.string.order_summary_order_number),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 32.sp)
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            "1391", // TODO: Follow a pattern where the first pair of digits is the station number, the second pair sequential order assignation.
            style = MaterialTheme.typography.labelLarge.copy(fontSize = 120.sp)
        )
    }
}

@Composable
private fun SectionItems(
    modifier: Modifier = Modifier,
    products: ImmutableList<BagItem>
) {
    LazyColumn(
        modifier = modifier.height(200.dp)
    ) {
        // Just get them from the bag flow
        items(products) {
            Text(it.title)
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
        text = stringResource(R.string.order_summary_get_progress)
    )

    QRCodeDisplay("https://www.reddit.com", Modifier.size(200.dp))
}

@Composable
private fun SectionRoulette() {
    // Direct to spin the roulette
    Text(
        style = MaterialTheme.typography.bodyMedium,
        text = stringResource(R.string.order_summary_roulette)
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
    OrderSummaryContent(PaddingValues(), persistentListOf())
}
