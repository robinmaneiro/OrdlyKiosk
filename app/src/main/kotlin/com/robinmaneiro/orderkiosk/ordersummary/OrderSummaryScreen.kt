package com.robinmaneiro.orderkiosk.ordersummary

import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.bag.model.BagItem
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.QRCodeDisplay
import com.robinmaneiro.orderkiosk.ui.RouletteWheel
import com.robinmaneiro.orderkiosk.ui.SimpleTopBar
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun OrderSummaryScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<OrderSummaryViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle(OrderSummaryViewModel.UiState())

    var showSpinDialog by remember { mutableStateOf(false) }

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
            products = bagResponse.items,
            onRouletteTapped = { showSpinDialog = true }
        )
    }

    if (showSpinDialog) {
        RouletteSpinDialog(onDismiss = { showSpinDialog = false })
    }
}

@Composable
private fun OrderSummaryContent(
    padding: PaddingValues,
    products: ImmutableList<BagItem>,
    onRouletteTapped: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(padding)
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
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
        SectionRoulette(onRouletteTapped = onRouletteTapped)
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
private fun SectionRoulette(onRouletteTapped: () -> Unit) {
    Text(
        style = MaterialTheme.typography.bodyMedium,
        text = stringResource(R.string.order_summary_roulette)
    )

    Spacer(Modifier.height(12.dp))

    RouletteWheel(
        rotation = 0f,
        onClick = onRouletteTapped,
        wheelSize = 120.dp
    )

    // TODO: Move me to new screens - to add some sharing in socials if win
}

@Composable
private fun RouletteSpinDialog(onDismiss: () -> Unit) {
    val rotation = remember { Animatable(0f) }
    val scope = rememberCoroutineScope()
    var isSpinning by remember { mutableStateOf(false) }

    BackHandler(enabled = true) { if (!isSpinning) onDismiss() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.scrim.copy(alpha = 0.5f))
            .clickable { if (!isSpinning) onDismiss() },
        contentAlignment = Alignment.Center
    ) {
        Card(
            shape = RectangleShape,
            modifier = Modifier.clickable(enabled = false) {},
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier
                    .width(420.dp)
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = stringResource(R.string.roulette_spin_win),
                    style = MaterialTheme.typography.headlineMedium
                )

                RouletteWheel(
                    rotation = rotation.value,
                    onClick = {
                        if (!isSpinning) {
                            scope.launch {
                                isSpinning = true
                                rotation.animateTo(
                                    targetValue = rotation.value + 1800f + (0..360).random().toFloat(),
                                    animationSpec = tween(
                                        durationMillis = 5_000,
                                        easing = CubicBezierEasing(0.05f, 0.7f, 0.1f, 1.0f)
                                    )
                                )
                                isSpinning = false
                            }
                        }
                    },
                    wheelSize = 280.dp
                )

                Text(
                    text = if (isSpinning) {
                        stringResource(R.string.roulette_good_luck)
                    } else {
                        stringResource(R.string.roulette_tap_to_spin)
                    },
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center
                )

                TextButton(
                    onClick = onDismiss,
                    enabled = !isSpinning
                ) {
                    Text(
                        text = stringResource(R.string.btn_close),
                        color = MaterialTheme.colorScheme.primary,
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}

@PreviewPixelTablet
@Composable
private fun OrderSummaryContentPreview() {
    OrderSummaryContent(PaddingValues(), persistentListOf(), onRouletteTapped = {})
}
