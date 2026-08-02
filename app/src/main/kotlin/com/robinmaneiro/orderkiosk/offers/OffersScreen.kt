package com.robinmaneiro.orderkiosk.offers

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import org.koin.androidx.compose.koinViewModel
import com.robinmaneiro.orderkiosk.NavigationEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet

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
    val pulse by rememberInfiniteTransition(label = "cta-pulse").animateFloat(
        initialValue = 1f,
        targetValue = 1.045f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1100),
            repeatMode = RepeatMode.Reverse
        ),
        label = "cta-scale"
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                mainUiEvent.invoke(NavigationEvent.NavigateToDestination(Screens.WelcomeScreen.route))
            }
    ) {
        AsyncImage(
            modifier = Modifier.fillMaxSize(),
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.burger_offer)
                .build(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )

        // Dark warm overlay
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        0f to Color(0x26000000),
                        1f to Color(0xBF000000)
                    )
                )
        )

        // Pulsing pill CTA
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 64.dp)
                .graphicsLayer { scaleX = pulse; scaleY = pulse }
                .background(
                    color = MaterialTheme.colorScheme.primary,
                    shape = CircleShape
                )
                .padding(horizontal = 48.dp, vertical = 20.dp)
        ) {
            Text(
                text = clickHereText,
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@PreviewPixelTablet
@Composable
private fun OffersContentPreview() {
    OffersContent(
        mainUiEvent = {},
        clickHereText = "Tap anywhere to start"
    )
}
