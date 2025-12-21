package com.robinmaneiro.orderkiosk.offers

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.robinmaneiro.orderkiosk.MainUiEvent
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet

@Composable
fun OffersScreen(
    mainUiEvent: (MainUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    OffersContent(mainUiEvent, modifier)
}

@Composable
private fun OffersContent(
    mainUiEvent: (MainUiEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .clickable {
                mainUiEvent.invoke(MainUiEvent.NavigateToDestination(Screens.WelcomeScreen.route))
            },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "This is the OFFERS SCREEN"
        )
    }
}

@PreviewPixelTablet
@Composable
private fun OffersContentPreview() {
    OffersContent(
        mainUiEvent = {}
    )
}
