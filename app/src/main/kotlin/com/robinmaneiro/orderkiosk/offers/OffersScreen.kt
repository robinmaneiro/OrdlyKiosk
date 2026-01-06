package com.robinmaneiro.orderkiosk.offers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import org.koin.androidx.compose.koinViewModel
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.theme.Iceberg
import com.robinmaneiro.orderkiosk.util.extensions.fadingEdge

@Composable
fun OffersScreen(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val viewModel = koinViewModel<OffersViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    OffersContent(mainUiEvent, modifier, uiState.clickHereText)
}

@Composable
private fun OffersContent(
    mainUiEvent: (NavigationEvent) -> Unit,
    modifier: Modifier = Modifier,
    clickHereText: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .clickable {
                mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.WelcomeScreen.route))
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            AsyncImage(
                modifier = Modifier
                    .fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(R.drawable.burger_offer)
                    .build(),
                contentScale = ContentScale.FillWidth,
                contentDescription = null
            )

            Row(
                modifier = Modifier
                    .fadingEdge(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            0.2f to Iceberg.copy(alpha = 0.4f),
                            0.5f to Iceberg.copy(alpha = 0.6f),
                            1f to Iceberg.copy(alpha = 0.8f),
                        )
                    )
                    .height(160.dp)
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .background(Iceberg)
                    .padding(top = 24.dp),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = clickHereText,
                    style = MaterialTheme.typography.labelLarge.copy(color = Color.Black, fontSize = 64.sp),
                )
            }
        }
    }
}

@PreviewPixelTablet
@Composable
private fun OffersContentPreview() {
    OffersContent(
        mainUiEvent = {},
        clickHereText = "TOUCH TO START"
    )
}
