package com.robinmaneiro.orderkiosk.offers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.FontScaling
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.robinmaneiro.orderkiosk.MainUiEvent
import com.robinmaneiro.orderkiosk.R
import com.robinmaneiro.orderkiosk.Screens
import com.robinmaneiro.orderkiosk.ui.PreviewPixelTablet
import com.robinmaneiro.orderkiosk.ui.theme.Aquamarine40

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
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .clickable {
                mainUiEvent.invoke(MainUiEvent.NavigateToDestination(Screens.WelcomeScreen.route))
            },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            modifier = Modifier
                .size(400.dp, 400.dp)
                .weight(1f),
            model = ImageRequest.Builder(LocalContext.current)
                .data(R.drawable.item_test_big_mac)
                .build(),
            contentDescription = null
        )

        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(Aquamarine40.copy(alpha = 0.8F)),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "TOUCH TO START",
                style = MaterialTheme.typography.labelLarge.copy(color = Color.DarkGray, fontSize = 64.sp),
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@PreviewPixelTablet
@Composable
private fun OffersContentPreview() {
    OffersContent(
        mainUiEvent = {}
    )
}
